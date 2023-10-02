package edu.postech.csed332.homework2;

import edu.postech.csed332.homework2.parser.BooleanExpBaseVisitor;
import edu.postech.csed332.homework2.parser.BooleanExpLexer;
import edu.postech.csed332.homework2.parser.BooleanExpParser;
import org.antlr.v4.runtime.*;

/**
 * A parser for boolean expressions. The syntax is as follows:
 * <p>
 * Exp ::=  true  |  false  |  variable  | ! Exp  |  ( Exp )  |  Exp && Exp  |  Exp || Exp
 * <p>
 * where variable has the form pN with positive identifier N, e.g., p1, p2, p3, ...
 */
public class ExpParser {
    /**
     * Parse a string and create its Exp data structure.
     *
     * @param str string to be parsed
     * @return an Exp instance
     * @throws IllegalStateException if the string cannot be parsed as Exp
     */
    public static Exp parse(String str) throws IllegalStateException {

        var lexer = new BooleanExpLexer(CharStreams.fromString(str));
        var parser = getBooleanExpParser(lexer);

        return new BooleanExpBaseVisitor<Exp>() {
            @Override
            public Exp visitParenExp(BooleanExpParser.ParenExpContext ctx) {
                return visit(ctx.sub);
            }

            @Override
            public Exp visitUnaryExp(BooleanExpParser.UnaryExpContext ctx) {
                return new Negation(visit(ctx.sub));
            }

            @Override
            public Exp visitBinaryExp(BooleanExpParser.BinaryExpContext ctx) {
                return switch (ctx.op.getType()) {
                    case BooleanExpLexer.AND -> new Conjunction(visit(ctx.left), visit(ctx.right));
                    case BooleanExpLexer.OR -> new Disjunction(visit(ctx.left), visit(ctx.right));
                    default -> throw new UnsupportedOperationException();
                };
            }

            @Override
            public Exp visitConstant(BooleanExpParser.ConstantContext ctx) {
                return new Constant(Boolean.parseBoolean(ctx.value.getText()));
            }

            @Override
            public Exp visitVariable(BooleanExpParser.VariableContext ctx) {
                return new Variable(Integer.parseUnsignedInt(ctx.name.getText().substring(1)));
            }
        }.visit(parser.expression());
    }

    private static BooleanExpParser getBooleanExpParser(BooleanExpLexer lexer) {
        var parser = new BooleanExpParser(new CommonTokenStream(lexer));

        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line,
                                    int charPositionInLine,
                                    String msg,
                                    RecognitionException e) {
                throw new IllegalStateException("Failed to parse at line " + line + " (" + msg + ")", e);
            }
        });
        return parser;
    }
}
