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
