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
        // exp && true == exp
        // exp && exp == exp
        // exp && false == false
        boolean true_flag = false;
        boolean false_flag = false;
        boolean same_flag = false;
        Exp result = null;
        for(Exp exp: subExps){
            if(exp instanceof Constant){
                if(((Constant) exp).value()){
                    true_flag = true;
                }
                else{
                    false_flag = true;
                }
            }
            else {
                // how to check if two expressions are the same?
                if(result != null && result.toPrettyString().equals(exp.toPrettyString())) {
                    same_flag = true;
                }
                result = exp;
            }
        }
        if(true_flag || same_flag) {
            return result;
        }
        else if(false_flag){
            return new Constant(false);
        }
        // exp && ! exp == false
        boolean negation_flag = false;
        same_flag = false;
        for(Exp exp: subExps){
            if(exp instanceof Negation) {
                negation_flag = !negation_flag;
            }
            if(result != null &&
                    result.getVariables().equals(exp.getVariables()) &&
                    exp.toPrettyString().contains(result.toPrettyString())) {
                same_flag = true;
            }
            result = exp;
        }
        if(negation_flag && same_flag){
            return new Constant(false);
        }
        // exp1 && (exp1 || exp2) == exp1
        boolean disjunction_flag = false;
        same_flag = false;
        for(Exp exp: subExps){
            if(exp instanceof Disjunction){
                disjunction_flag = !disjunction_flag;
                if(result != null && exp.toPrettyString().contains(result.toPrettyString())){
                    same_flag = true;
                }
            }
            else {
                result = exp;
            }
        }
        if(disjunction_flag && same_flag){
            return result;
        }

        return this;
    }

    @Override
    public String toPrettyString() {
        return "(" + Arrays.stream(subExps()).map(Exp::toPrettyString)
                .collect(Collectors.joining(" && ")) + ")";
    }
}