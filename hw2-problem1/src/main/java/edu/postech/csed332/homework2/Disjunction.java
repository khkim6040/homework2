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
        // exp || false == exp
        // exp || true == true
        // exp || exp == exp
        boolean false_flag = false;
        boolean true_flag = false;
        boolean same_flag = false;
        Exp result = null;
        for(Exp exp: subExps) {
            if(result == null) {
                result = exp;
                continue;
            }
            if(exp instanceof Constant) {
                if(((Constant) exp).value()) {
                    true_flag = true;
                }
                else {
                    false_flag = true;
                }
            }
            else if(result.toPrettyString().equals(exp.toPrettyString())) {
                same_flag = true;
            }
        }
        if(false_flag || same_flag) {
            return result;
        }
        else if(true_flag) {
            return new Constant(true);
        }
        // exp || ! exp == true
        boolean negation_flag = false;
        same_flag = false;
        for(Exp exp: subExps){
            if(result == null) {
                result = exp;
                continue;
            }
            if(exp instanceof Negation) {
                negation_flag = true;
                String exp_str = exp.toPrettyString().substring(3, exp.toPrettyString().length() - 1);
                if(result.toPrettyString().equals(exp_str)) {
                    same_flag = true;
                }
            }
        }
        if(negation_flag && same_flag){
            return new Constant(true);
        }
        // exp1 || (exp1 && exp2) == exp1
        boolean conjunction_flag = false;
        same_flag = false;
        for(Exp exp: subExps){
            if(result == null) {
                result = exp;
                continue;
            }
            if(exp instanceof Conjunction){
                conjunction_flag = true;
                // how to get each subexpression of exp?
                for(Exp conjunction_exp: ((Conjunction) exp).subExps()) {
                    if (result.toPrettyString().equals(conjunction_exp.toPrettyString())) {
                        same_flag = true;
                    }
                }
            }
        }
        if(conjunction_flag && same_flag){
            return result;
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