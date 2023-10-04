package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean variable, identified by positive integers
 */
public record Variable(int identifier) implements Exp {
    /**
     * The identifier of this variable must be a positive integer.
     *
     * @param identifier a positive integer
     */
    public Variable {
        if (identifier <= 0)
            throw new IllegalArgumentException("Variable Id must be a positive integer");
    }

    @Override
    public Set<Integer> getVariables() {
        // TODO: implement this
        return Set.of(identifier);
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        return assignment.get(identifier);
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        return this;
    }

    @Override
    public String toPrettyString() {
        return "p" + identifier();
    }
}