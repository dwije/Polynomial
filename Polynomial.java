package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	private static Node concatenate(Node head, float coeff, int degree) {
		if (head == null) {
			head = new Node(coeff, degree, null);
			return head;
		}
		Node current = head;
		while (current.next != null) {
			current = current.next;
		}
		current.next = new Node(coeff, degree, null);
		return head;
	}
	
	private static Node sort(Node head) {
		if (head == null) {
			return null;
		}
		if (head.next == null) {
			return head;
		}
		Node current1 = head;
		Node current2 = null;
		while (current1 != null) {
			current2 = current1.next;
			while (current2 != null) {
				if (current1.term.degree > current2.term.degree) {
					Term temp = current1.term;
					current1.term = current2.term;
					current2.term = temp;
				}
				current2 = current2.next;
			}
			current1 = current1.next;
		}
		return head;
	}
	
	private static Node simplify(Node head) {
		if (head == null) {
			return null;
		}
		if (head.next == null) {
			return head;
		}
		Node current1 = head;
		Node current2 = head.next;
		Node result = null;
		while (true) {
			if (current1 != null && current2 == null) {
				if (current1.term.coeff != 0) {
					result = concatenate(result, current1.term.coeff, current1.term.degree);
				}
				return result;
			}
			if (current1.term.degree != current2.term.degree) {
				if (current1.term.coeff != 0) {
					result = concatenate(result, current1.term.coeff, current1.term.degree);
				}
				current1 = current1.next;
				current2 = current2.next;
			}
			else if (current1.term.degree == current2.term.degree) {
				float coeffSum = current1.term.coeff;
				int degree = current1.term.degree;
				while (current1.term.degree == current2.term.degree && current2 != null) {
					coeffSum += current2.term.coeff;
					current2 = current2.next;
				}
				if (coeffSum != 0) {
					result = concatenate(result, coeffSum, degree);
				}
				if (current2 == null) {
					return result;
				}
				current1 = current2;
				current2 = current2.next;
			}
		}
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if (poly1 == null && poly2 != null) {
			return poly2;
		}
		if (poly1 != null && poly2 == null) {
			return poly1;
		}
		if (poly1 == null && poly2 == null) {
			return null;
		}
		Node current1 = poly1;
		Node current2 = poly2;
		Node result = null;
		while (true) {
			if (current1.term.degree == current2.term.degree) {
				if (current1.term.coeff + current2.term.coeff != 0) {
					result = concatenate(result, current1.term.coeff + current2.term.coeff, current1.term.degree);
				}
				current1 = current1.next;
				current2 = current2.next;
			}
			else if (current1.term.degree < current2.term.degree) {
				if (current1.term.coeff != 0) {
					result = concatenate(result, current1.term.coeff, current1.term.degree);
					
				}
				current1 = current1.next;
			}
			else if (current1.term.degree > current2.term.degree) {
				if (current2.term.coeff != 0) {
					result = concatenate(result, current2.term.coeff, current2.term.degree);
				}
				current2 = current2.next;
			}
			if (current1 == null && current2 != null) {
				while (current2 != null) {
					result = concatenate(result, current2.term.coeff, current2.term.degree);
					current2 = current2.next;
				}
				break;
			}
			if (current1 != null && current2 == null) {
				while (current1 != null) {
					result = concatenate(result, current1.term.coeff, current1.term.degree);
					current1 = current1.next;
				}
				break;
			}
			if (current1 == null && current2 == null) {
				break;
			}
		}
		return result;
		
		
		/*
		int poly1length = 0;
		int poly2length = 0;
		Node current1 = poly1;
		Node current2 = poly2;
		while (current1 != null) {
			current1 = current1.next;
			poly1length++;
		}
		while (current2 != null) {
			current2 = current2.next;
			poly2length++;
		}
		for (int i=poly1length;i >= 0;i--) {
			int n = 1;
			current1 = poly1;
			while (n < i) {
				current1 = current1.next;
				n++;
			}
		}
		*/
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if (poly1 == null || poly2 == null) {
			return null;
		}
		Node current1 = poly1;
		Node current2 = poly2;
		Node product = null;
		while (current1 != null) {
			while (current2 != null) {
				if (current1.term.coeff * current2.term.coeff != 0) {
					product = concatenate(product, current1.term.coeff * current2.term.coeff, current1.term.degree + current2.term.degree);
				}
				current2 = current2.next;
			}
			current1 = current1.next;
			current2 = poly2;
		}
		return simplify(sort(product));
		/*
		product = simplify(sort(product));
		return product;
		*/
	}
	
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if (poly == null) {
			return 0;
		}
		float result = 0;
		Node current = poly;
		while (current != null) {
			result += current.term.coeff * (Math.pow(x, current.term.degree));
			current = current.next;
		}
		return result;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
	public static void main (String[] args) {
		Node poly1 = new Node(1,2,new Node(2,3, new Node(3,4, null)));
		Node poly2 = null;
		add(poly1,poly2);
	}
}