# Polynomial
Rutgers University - Data Structures Programming Assignment

The purpose of this assignment was to create a program that can accept and manipulate polynomials and operate on them.
The primary data structure used was singly-linked linked lists, as defined in the Node class.
Each node contains a Term object (which in turn contains a float coefficient and integer degree), as well as the next Node.

Polynomial.java is the driving class of the program, containing three major methods: add, multiply, and evaluate.
All polynomials are in order of decreasing degree.

The add method takes two Node objects as inputs, each one containing the head of the polynomial linked list.
The method traverses through each list, adding together the coefficients terms with matching degree values, and putting them in a result list.
This result list is then returned at the end of the add method.

The multiply method also takes two Node objects as inputs.
The method simply traverses through both lists using a nested while loop, multiplying all terms together and storing them in a product list.
At the end of the nested loop, once all the multiplication has been done, the product loop is sent to the private method "sort", where it is sorted to decreasing degree order.
Lastly, the sorted product list is sent to the "simplify" method where all the like terms are combined, and the simplified product list is returned as the result.

The third method is the evaluate method, which takes in a Node containing the head of the polynomial and a float value containing the value to be evaluated.
Using a while loop, this method traverses through the list containing the polynomial, evaluating each term at the float value specified in the input.
These terms are all summed up and returned at the end of the loop.
