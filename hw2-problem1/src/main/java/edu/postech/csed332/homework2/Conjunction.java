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
        return null;
    }

    @Override
    public String toPrettyString() {
        return "(" + Arrays.stream(subExps()).map(Exp::toPrettyString)
                .collect(Collectors.joining(" && ")) + ")";
    }
}