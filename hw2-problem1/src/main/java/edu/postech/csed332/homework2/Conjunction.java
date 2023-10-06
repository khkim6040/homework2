package edu.postech.csed332.homework2;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is && (and).
 */
public record Conjunction(Exp... subExps) implements Exp {
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
            if(!key){
                return false;
            }
        }
        return true;
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        this.subExps[0] = this.subExps[0].simplify();
        this.subExps[1] = this.subExps[1].simplify();
        Exp firstExp = this.subExps[0];
        Exp secondExp = this.subExps[1];
        // exp && exp => exp
        if (firstExp.toPrettyString().equals(secondExp.toPrettyString())) {
            return firstExp.simplify();
        }
        if (secondExp instanceof Constant) {
            // exp && false => false
            if (!((Constant) secondExp).value()) {
                return new Constant(false);
            }
            // exp && true => exp
            else {
                return firstExp.simplify();
            }
        }
        if (firstExp instanceof Constant) {
            // false && exp => false
            if (!((Constant) firstExp).value()) {
                return new Constant(false);
            }
            // true && exp => exp
            else {
                return secondExp.simplify();
            }
        }
        // exp && ! exp => false
        if (secondExp instanceof Negation) {
            if (firstExp.toPrettyString().equals(((Negation) secondExp).subExp().toPrettyString())) {
                return new Constant(false);
            }
        }
        if (firstExp instanceof Negation) {
            if (secondExp.toPrettyString().equals(((Negation) firstExp).subExp().toPrettyString())) {
                return new Constant(false);
            }
        }
        // exp1 && (exp1 || exp2) => exp1
        if (secondExp instanceof Disjunction) {
            for(Exp exp: ((Disjunction) secondExp).subExps()){
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
                .collect(Collectors.joining(" && ")) + ")";
    }
}