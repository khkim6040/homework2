package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean constant, either true or false.
 */
public record Constant(boolean value) implements Exp {

    @Override
    public Set<Integer> getVariables() {
        // TODO: implement this
        return null;
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        return null;
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        return null;
    }

    @Override
    public String toPrettyString() {
        return Boolean.toString(value());
    }
}