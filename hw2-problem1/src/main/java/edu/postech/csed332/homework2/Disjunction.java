package edu.postech.csed332.homework2;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is || (or).
 */
public record Disjunction(Exp... subExps) implements Exp {

    @Override
    public Set<Integer> getVariables() {
        // TODO: implement this
        return Arrays.stream(subExps)
                .flatMap(exp -> exp.getVariables().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        for(Boolean key: assignment.values()){
            if(key){
                return true;
            }
        }
        return false;
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        this.subExps[0] = this.subExps[0].simplify();
        this.subExps[1] = this.subExps[1].simplify();
        Exp firstExp = this.subExps[0];
        Exp secondExp = this.subExps[1];
        // Identity and idempotent laws
        // exp || exp => exp
        if (firstExp.toPrettyString().equals(secondExp.toPrettyString())) {
            return firstExp.simplify();
        }
        if (secondExp instanceof Constant) {
            // exp || false => exp
            if (!((Constant) secondExp).value()) {
                return firstExp.simplify();
            }
            // exp || true => true
            else {
                return secondExp.simplify();
            }
        }
        if (firstExp instanceof Constant) {
            // false || exp => exp
            if (!((Constant) firstExp).value()) {
                return secondExp.simplify();
            }
            // true || exp => true
            else {
                return firstExp.simplify();
            }
        }
        // exp || ! exp => true
        if (secondExp instanceof Negation) {
            if (firstExp.toPrettyString().equals(((Negation) secondExp).subExp().toPrettyString())) {
                return new Constant(true);
            }
        }
        // exp1 || (exp1 && exp2) => exp1
        if (secondExp instanceof Conjunction) {
            for(Exp exp: ((Conjunction) secondExp).subExps()){
                if(firstExp.toPrettyString().equals(exp.toPrettyString())){
                    return firstExp.simplify();
                }
            }
        }
        // When there is no simplification
        return this;
    }

    @Override
    public String toPrettyString() {
        return "(" + Arrays.stream(subExps()).map(Exp::toPrettyString)
                .collect(Collectors.joining(" || ")) + ")";
    }
}