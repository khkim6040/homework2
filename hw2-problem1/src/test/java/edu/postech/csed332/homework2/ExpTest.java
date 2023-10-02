package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;

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
    void testInvalidVariable() {
        assertThrows(IllegalArgumentException.class, () ->
                new Variable(0)
        );
    }



}
