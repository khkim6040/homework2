package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean expression whose top-level operator is ! (not).
 */
public record Negation(Exp subExp) implements Exp {

    @Override
    public Set<Integer> getVariables() {
        // TODO: implement this
        return subExp().getVariables();
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        return !subExp().evaluate(assignment);
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        Exp exp = subExp();
        // ! (! exp) == exp
        if (exp instanceof Negation) {
            return ((Negation) exp).subExp().simplify();
        }
        // ! (true) == false, ! (false) == true
        // ! (exp1 && exp2) == ! exp1 || ! exp2, ! (exp1 || exp2) == ! exp1 && ! exp2

        return this;
    }

    @Override
    public String toPrettyString() {
        return "(! " + subExp().toPrettyString() + ")";
    }
}
