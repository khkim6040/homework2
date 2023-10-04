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
        if (exp instanceof Constant) {
            return new Constant(!((Constant) exp).value());
        }
        // ! (exp1 && exp2) == ! exp1 || ! exp2
        if (exp instanceof Conjunction) {
            Exp firstExp = ((Conjunction) exp).subExps()[0];
            Exp secondExp = ((Conjunction) exp).subExps()[1];
            return new Disjunction(new Negation(firstExp).simplify(), new Negation(secondExp).simplify()).simplify();
        }
        // ! (exp1 || exp2) == ! exp1 && ! exp2
        if (exp instanceof Disjunction) {
            Exp firstExp = ((Disjunction) exp).subExps()[0];
            Exp secondExp = ((Disjunction) exp).subExps()[1];
            return new Conjunction(new Negation(firstExp).simplify(), new Negation(secondExp).simplify()).simplify();
        }
        return this;
    }

    @Override
    public String toPrettyString() {
        return "(! " + subExp().toPrettyString() + ")";
    }
}
