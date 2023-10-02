package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean expression.
 */
public interface Exp {
    /**
     * Returns the set of all variables in this Boolean expression.
     * For example, if the formula is "(p1 || p2) && (p2 || ! p3)",
     * this method returns the set {1, 2, 3}.
     *
     * @return the set of variables in this expression
     */
    Set<Integer> getVariables();

    /**
     * Evaluates the truth value of this Boolean expression, given
     * a truth assignment. A truth assignment is a map from variable
     * identifiers to Boolean values. For example, suppose that the
     * formula is "(p1 || p2) && (p2 || ! p3)". This method returns
     * true, given {1 |-> true, 2 |-> false, 3 |-> false}.
     *
     * @param assignment a truth assignment
     * @return true or false
     */
    Boolean evaluate(Map<Integer, Boolean> assignment);

    /**
     * Returns a new expression that is obtained by simplifying this
     * expression according to the logical equivalence laws, given
     * in the homework instruction. For example, if the formula is
     * "(p1 || true) && ! (p2 && ! p3)", this function returns the
     * new expression "p1 && (! p2 || p3)".
     * Note: the object itself should not change by this method.
     *
     * @return a new simplified expression
     */
    Exp simplify();

    /**
     * Returns a string representation of this expression.
     *
     * @return a string representation
     */
    String toPrettyString();
}