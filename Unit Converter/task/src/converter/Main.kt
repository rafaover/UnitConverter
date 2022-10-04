package converter

fun main() {
    loopcheck@ while (true) {
        var conversion: Double?
        print("Enter what you want to convert (or exit): ")
        val userInput = readln().lowercase()

        if (userInput == "exit") break@loopcheck

        val (originalNumber, originalUnit, convertedUnit) = userInputSplit(userInput)

        // Validating inputs/Enums + exception checks
        if (originalNumber == null) {
            println("Wrong number")
            continue@loopcheck
        }
        if (originalUnit == null && convertedUnit != null) {
            println("Conversion from ??? to ${convertedUnit.plural} is impossible")
            continue@loopcheck
        }
        if (originalUnit != null && convertedUnit == null) {
            println("Conversion from ${originalUnit.plural} to ??? is impossible")
            continue@loopcheck
        }
        if (originalUnit == null && convertedUnit == null) {
            println("Conversion from ??? to ??? is impossible")
            continue@loopcheck
        }
        if (originalUnit != null && convertedUnit != null) {
            if (originalUnit.type != convertedUnit.type) {
                println("Conversion from ${originalUnit.plural} to ${convertedUnit.plural} is impossible")
                continue@loopcheck
            } else {
                // Conversion Calculus
                conversion = originalNumber * originalUnit.ratio / convertedUnit.ratio
                // Check if the units are singular or plural
                val postUnitCheck = if (conversion == 1.0) convertedUnit.singular else convertedUnit.plural
                val preUnitCheck = if (originalNumber == 1.0) originalUnit.singular else originalUnit.plural
                // Prints final text
                println("$originalNumber $preUnitCheck is $conversion $postUnitCheck")
            }
        }
    }
}

// Function to organize userInput in 3 variables: Number Unit1 Unit2
fun userInputSplit(userInput: String): Triple<Double?, Unidade?, Unidade?> {
    val userInputArray = userInput.split(" ")

    val originalNumber = try {
        userInputArray[0].toDouble()
    } catch (e: NumberFormatException) { null }

    val originalUnit = try {
        getUnit(userInputArray[1])
    } catch (e: IllegalArgumentException) { null }

    val convertedUnit = try {
        getUnit(userInputArray[3])
    } catch (e: IllegalArgumentException) { null }

    return Triple(originalNumber, originalUnit, convertedUnit)
}

// Function to set constant from Unidade for conversion based from User Input.
fun getUnit(unit: String): Unidade =
    when (unit) {
        "m", "meter", "meters" -> Unidade.METER
        "km", "kilometer", "kilometers" -> Unidade.KILOMETER
        "cm", "centimeter", "centimeters" -> Unidade.CENTIMETER
        "mm", "millimeter", "millimeters" -> Unidade.MILLIMETER
        "mi", "mile", "miles" -> Unidade.MILE
        "yd", "yard", "yards" -> Unidade.YARD
        "ft", "foot", "feet" -> Unidade.FOOT
        "in", "inch", "inches" -> Unidade.INCH
        "g", "gram", "grams" -> Unidade.GRAM
        "kg", "kilogram", "kilograms" -> Unidade.KILOGRAM
        "mg", "milligram", "milligrams" -> Unidade.MILLIGRAM
        "lb", "pound", "pounds" -> Unidade.POUND
        "oz", "ounce", "ounces" -> Unidade.OUNCE
//        "degree celsius", "degrees celsius", "celsius", "dc", "c" -> Unit.CELSIUS
//        "degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f" -> Unit.FAHRENHEIT
//        "kelvin", "kelvins", "k" -> Unit.Kelvin
        else -> throw IllegalArgumentException ("Wrong Unit. Try Again.")
    }
enum class Unidade (val short: String,
                    val singular: String,
                    val plural: String,
                    val ratio: Double,
                    val type: String
                    ) {
    METER("m","meter", "meters", 1.0, "Length"),
    KILOMETER("km","kilometer", "kilometers", 1000.0, "Length"),
    CENTIMETER("cm","centimeter", "centimeters", 0.01, "Length"),
    MILLIMETER("mm", "millimeter", "millimeters", 0.001, "Length"),
    MILE("mi","mile", "miles", 1609.35, "Length"),
    YARD("yd","yard", "yards", 0.9144, "Length"),
    FOOT("ft","foot", "feet", 0.3048, "Length"),
    INCH("in","inch", "inches", 0.0254, "Length"),
    GRAM("g", "gram", "grams", 1.0, "Weight"),
    KILOGRAM("kg", "kilogram", "kilograms", 1000.0, "Weight"),
    MILLIGRAM("mg", "milligram", "milligrams", 0.001, "Weight"),
    POUND("lb", "pound", "pounds", 453.592, "Weight"),
    OUNCE("oz","ounce", "ounces", 28.3495, "Weight");
    //CELSIUS("degree Celsius", "degrees Celsius", 1.0, "Temperature"),
    //KELVIN("Kelvin", "Kelvins", 1.0, "Temperature"),
    //FAHRENHEIT("degree Fahrenheit", "degrees Fahrenheit", 1.0, "Temperature")
}