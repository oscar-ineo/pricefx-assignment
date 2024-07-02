package pricefxgroovy

/**
 * Author: Rod Madden
 * Date: 4/9/2021
 * Purpose: PriceFX homework challenge
 */

// ---------------------------
//
// HOMEWORK
//
// Use Groovy to write code under "YOUR CODE GOES BELOW THIS LINE" comment.
// Use Groovy closures whenever possible
// You should develop and present your code in IntelliJ Idea ( Community Edition )
// Do not over-engineer the solution.
//
// Assume you got some data from a customer - your task is to design a routine that will calculate the average Product price per GROUP.
//
// The Price of each Product is calculated as:
// Cost * (1 + Margin)
//
// If the number of products contained in a Group ( G1, G2, G3, etc. ) is more then 4, then reduce the average product price for that Group by 20%
//
// Assume there can be a large number of products.
//
// Plus points:
// - use Groovy closures ( wherever it makes sense )
// - make the category look-up performance effective

// contains information about [Product, Group, Cost] ...example ["A", "G1", 20.1],
def products = [
        ["A", "G1", 20.1],
        ["B", "G2", 98.4],
        ["C", "G1", 49.7],
        ["D", "G3", 35.8],
        ["E", "G3", 105.5],
        ["F", "G1", 55.2],
        ["G", "G1", 12.7],
        ["H", "G3", 88.6],
        ["I", "G1", 5.2],
        ["J", "G2", 72.4]]

// contains information about Category classification based on product Cost
// [Category, Cost range from (inclusive), Cost range to (exclusive)]
// i.e. if a Product has Cost between 0 and 25, it belongs to category C1
// ranges are mutually exclusive and the last range has a null as upper limit.
def category = [
        ["C3", 50, 75],
        ["C4", 75, 100],
        ["C2", 25, 50],
        ["C5", 100, null],
        ["C1", 0, 25]]

// contains information about margins for each product Category
// [Category, Margin (either percentage or absolute value)]
def margins = [
        "C1" : "20%",
        "C2" : "30%",
        "C3" : "0.4",
        "C4" : "50%",
        "C5" : "0.6"]

// ---------------------------
//
// YOUR CODE GOES BELOW THIS LINE
//
// Assign the 'result' variable so the assertion at the end validates
//
// ---------------------------

groupOrder = [ "G1", "G2", "G3" ]
def categoryOrder = [ "C1", "C2", "C3", "C4", "C5" ]
orderedCategories = category.sort { a, b -> categoryOrder.indexOf( a.get(0) ) <=> categoryOrder.indexOf( b.get(0) ) }
fixedMargins = fixMargins(margins)

/**
 * As we receive the margins in multiple formats, we populate a new map with a unified format,
 * which we'll use in the logic for calculating the average prices.
 * @param margins The margins map with diverse percentage formats.
 * @return The list of margins with the unified percentage format.
 */
static LinkedHashMap<String, BigDecimal> fixMargins(LinkedHashMap<String, String> margins) {
    LinkedHashMap<String, BigDecimal> finalMargins = []

    for (margin in margins) {
        finalMargins.put(margin.key, convertMargin(margin.value))
    }

    return finalMargins
}

/**
 * As we can have multiple margin formats, we unify them in BigDecimal format for convenience.
 * @param margin The margin in percentage or decimal format
 * @return The margin in BigDecimal format
 */
static BigDecimal convertMargin(String margin) {
    if (margin.contains("%")) {
        margin = margin.replace("%", "")
        def newMargin = new BigDecimal(margin)
        newMargin = newMargin / 100
        return newMargin
    } else {
        return new BigDecimal(margin)
    }
}

/**
 * Calculate the price of the product
 * @param cost Cost of the product
 * @param categories Available categories
 * @return
 */
BigDecimal calculatePrice(BigDecimal cost) {
    String category = getCategory(cost)

    if (category == null) {
        throw new Exception("Category could not be found.")
    }

    return cost * (1 + fixedMargins.get(category))
}

/**
 * Get the category of a given cost
 * @param cost Cost of the product
 * @return The category given to a cost
 */
String getCategory(BigDecimal cost) {
    for (List<Serializable> category in orderedCategories) {
        Integer minValue = category.get(1) as Integer
        Integer maxValue = category.get(2) as Integer

        if (cost >= minValue && (cost < maxValue || maxValue == null)) {
            return category.get(0)
        }
    }
    return null
}

/**
 * Logic to populate a result map with average prices for each product group, the average is calculated by margins
 * for each category and applied a discount if the group size is greater than 4.
 * @param products The unaltered product list
 * @return A map containing the average prices for each product following the format: ["keyGroup" : valueAverage, ... ]
 */
HashMap<String, BigDecimal> calculateAveragePrices(ArrayList<List<Serializable>> products) {
    HashMap<String, BigDecimal> result = new HashMap<>()

    for (String group in groupOrder) {
        BigDecimal sum = 0.0
        ArrayList<List<Serializable>> groupProducts = products.findAll({ ( it.get(1) == group ) })

        for (product in groupProducts) {
            sum += calculatePrice(product.get(2) as BigDecimal)
        }

        if (groupProducts.size() > 4) {
            BigDecimal average = sum / groupProducts.size()
            average = (average - average * 0.2)
            result.put(group, average.round(1))
        } else {
            BigDecimal average = sum / groupProducts.size()
            result.put(group, average.round(1))
        }
    }

    return result
}

def result= calculateAveragePrices(products)

// ---------------------------
//
// IF YOUR CODE WORKS, YOU SHOULD GET "It works!" WRITTEN IN THE CONSOLE
//
// ---------------------------
assert result == [
        "G1" : 30.0,
        "G2" : 124.5,
        "G3" : 116.1
] : "It doesn't work"

println "It works!"
