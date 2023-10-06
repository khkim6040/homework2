package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExpTest {

    @Test
    void testParserOK() {
        String expStr = "((p1 || (p2 && (! p3))) || true)";
        Exp exp = ExpParser.parse(expStr);
        assertEquals(expStr, exp.toPrettyString());
    }

    @Test
    void testParserError() {
        assertThrows(IllegalStateException.class, () -> {
            Exp exp = ExpParser.parse("p1 || p2 && ! p0 || true");
        });
    }

    /*
     * TODO: add  test methods to achieve at least 80% branch coverage of the classes:
     *  Conjunction, Constant, Disjunction, Negation, Variable.
     * Each test method should have appropriate JUnit assertions to test a single behavior.
     * You should not add arbitrary code to test methods to just increase coverage.
     */
    @Test
    void testConjunction() {
        Exp exp = ExpParser.parse("(p1 && p2)");
        assertEquals("(p1 && p2)", exp.toPrettyString());
    }
    @Test
    void testConstant() {
        Exp exp = ExpParser.parse("true");
        assertEquals("true", exp.toPrettyString());
    }
    @Test
    void testDisjunction() {
        Exp exp = ExpParser.parse("(p1 || p2)");
        assertEquals("(p1 || p2)", exp.toPrettyString());
    }
    @Test
    void testNegation() {
        Exp exp = ExpParser.parse("(! p1)");
        assertEquals("(! p1)", exp.toPrettyString());
    }
    @Test
    void testVariable() {
        Exp exp = ExpParser.parse("p1");
        assertEquals("p1", exp.toPrettyString());
    }
    @Test
    void testConjunctionGetVariables() {
        Exp exp = ExpParser.parse("(p1 && p2)");
        assertEquals(Set.of(1, 2), exp.getVariables());
    }
    @Test
    void testDisjunctionGetVariables() {
        Exp exp = ExpParser.parse("(p1 || p2)");
        assertEquals(Set.of(1, 2), exp.getVariables());
    }
    @Test
    void testNegationGetVariables() {
        Exp exp = ExpParser.parse("(! p1)");
        assertEquals(Set.of(1), exp.getVariables());
    }
    @Test
    void testConstantGetVariables() {
        Exp exp = ExpParser.parse("true");
        assertEquals(Set.of(), exp.getVariables());
    }
    @Test
    void testVariableGetVariables() {
        Exp exp = ExpParser.parse("p1");
        assertEquals(Set.of(1), exp.getVariables());
    }
    @Test
    void testInvalidVariable() {
        assertThrows(IllegalArgumentException.class, () ->
                new Variable(0)
        );
    }
    @Test
    void testVariableEvaluate() {
        Exp exp = ExpParser.parse("p1");
        assertEquals(true, exp.evaluate(Map.of(1, true)));
        assertEquals(false, exp.evaluate(Map.of(1, false)));
    }
    @Test
    void testConstantEvaluate() {
        Exp exp = ExpParser.parse("true");
        assertEquals(true, exp.evaluate(Map.of()));
    }
    @Test
    void testConjunctionEvaluate() {
        Exp exp = ExpParser.parse("(p1 && p2)");
        assertEquals(true, exp.evaluate(Map.of(1, true, 2, true)));
        assertEquals(false, exp.evaluate(Map.of(1, true, 2, false)));
    }
    @Test
    void testDisjunctionEvaluate() {
        Exp exp = ExpParser.parse("(p1 || p2)");
        assertEquals(true, exp.evaluate(Map.of(1, true, 2, true)));
        assertEquals(true, exp.evaluate(Map.of(1, true, 2, false)));
        assertEquals(true, exp.evaluate(Map.of(1, false, 2, true)));
        assertEquals(false, exp.evaluate(Map.of(1, false, 2, false)));
    }
    @Test
    void testNegationEvaluate() {
        Exp exp = ExpParser.parse("(! p1)");
        assertEquals(true, exp.evaluate(Map.of(1, false)));
        assertEquals(false, exp.evaluate(Map.of(1, true)));
    }
    @ParameterizedTest
    @CsvSource({"(p1 && true), p1", "(p1 && p1), p1", "(p1 && false), false",
            "(p1 && ! p1), false", "(p1 && (p1 || p2)), p1",
            "(p1 && p2), (p1 && p2)", "(p1 && (p2 && (p2 || p3))), (p1 && p2)",
            "((p1 && true) && p2), (p1 && p2)", "((p1 && p1) && p2), (p1 && p2)",
            "(p1 || true) && ! (p2 && ! p3), ((! p2) || p3)"
    })
    void testConjunctionSimplify(String input, String expected) {
        Exp exp = ExpParser.parse(input);
        assertEquals(expected, exp.simplify().toPrettyString());
    }
    @ParameterizedTest
    @CsvSource({"(p1 || false), p1", "(p1 || p1), p1", "(p1 || true), true",
            "(p1 || ! p1), true", "(p1 || (p1 && p2)), p1",
            "(p1 || p2), (p1 || p2)", "(p1 || (p2 || (p2 && p3))), (p1 || p2)",
            "((p1 || false) || p2), (p1 || p2)", "((p1 || p1) || p2), (p1 || p2)"
    })
    void testDisjunctionSimplify(String input, String expected) {
        Exp exp = ExpParser.parse(input);
        assertEquals(expected, exp.simplify().toPrettyString());
    }
    @ParameterizedTest
    @CsvSource({"(! true), false", "(! false), true", "(! p1), (! p1)",
            "(! (! p1)), p1", "(! (p1 && p2)), ((! p1) || (! p2))",
            "(! (p1 || p2)), ((! p1) && (! p2))", "(! (p1 && (p1 || p2))), (! p1)",
            "(! (p1 || (p1 && p2))), (! p1)", "(! (p1 && (p2 || p1))), (! p1)",
            "(! (p1 || (p2 && p1))), (! p1)"
    })
    void testNegationSimplify(String input, String expected) {
        Exp exp = ExpParser.parse(input);
        assertEquals(expected, exp.simplify().toPrettyString());
    }
    @Test
    void testConstantSimplify() {
        Exp exp = ExpParser.parse("true");
        assertEquals("true", exp.simplify().toPrettyString());
    }
    @Test
    void testVariableSimplify() {
        Exp exp = ExpParser.parse("p1");
        assertEquals("p1", exp.simplify().toPrettyString());
    }
}
