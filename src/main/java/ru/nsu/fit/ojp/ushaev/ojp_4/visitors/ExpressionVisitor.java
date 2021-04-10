package ru.nsu.fit.ojp.ushaev.ojp_4.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import ru.nsu.fit.ojp.ushaev.ojp_4.ZarfBaseVisitor;
import ru.nsu.fit.ojp.ushaev.ojp_4.ZarfParser;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.arithmetic.*;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.ConditionalExpression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Reference;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Value;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.Variable;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.SupportedTypes;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.TypeResolver;
import ru.nsu.fit.ojp.ushaev.ojp_4.stringutils.Concatenation;

public class ExpressionVisitor extends ZarfBaseVisitor<Expression> {

    private final VariableStorage variableStorage;

    public ExpressionVisitor(VariableStorage variableStorage) {
        this.variableStorage = variableStorage;
    }

    @Override
    public Expression visitVarReference(ZarfParser.VarReferenceContext ctx) {
        String varName = ctx.getText();
        Variable variable = variableStorage.getVariable(varName);
        return new Reference(varName, variable.getType());
    }

    @Override
    public Expression visitValue(ZarfParser.ValueContext ctx) {
        String value = ctx.getText();
        Type type = TypeResolver.typeFromValue(value);
        return new Value(value, type);
    }

    @Override
    public Expression visitADD(ZarfParser.ADDContext ctx) {
        ZarfParser.ExpressionContext leftExprContext = ctx.expression(0);
        ZarfParser.ExpressionContext rightExprContext = ctx.expression(1);

        Expression leftExpression = leftExprContext.accept(this);
        Expression rightExpression = rightExprContext.accept(this);

        System.out.println(leftExpression.getType());
        System.out.println(rightExpression.getType());

        if (leftExpression.getType() == SupportedTypes.STRING && rightExpression.getType() == SupportedTypes.STRING) {
            return new Concatenation(leftExpression.getType(), leftExpression, rightExpression);
        }

        TerminalNode operator = ctx.PLUS_MINUS();
        switch (operator.getSymbol().getText()) {
            case "+":
                return new Addition(leftExpression.getType(), leftExpression, rightExpression);
            case "-":
                return new Subtraction(leftExpression.getType(), leftExpression, rightExpression);
            default:
                throw new IllegalArgumentException("Smth wrong");
        }
    }

    @Override
    public Expression visitMULTIPLY(ZarfParser.MULTIPLYContext ctx) {
        ZarfParser.ExpressionContext leftExprContext = ctx.expression(0);
        ZarfParser.ExpressionContext rightExprContext = ctx.expression(1);

        Expression leftExpression = leftExprContext.accept(this);
        Expression rightExpression = rightExprContext.accept(this);

        TerminalNode operator = ctx.MULT_DIV();
        switch (operator.getSymbol().getText()) {
            case "*":
                return new Multiplication(leftExpression.getType(), leftExpression, rightExpression);
            case "/":
                return new Division(leftExpression.getType(), leftExpression, rightExpression);
            case "%":
                return new ModDivision(leftExpression.getType(), leftExpression, rightExpression);
            default:
                throw new IllegalArgumentException("Smth wrong");
        }
    }

    @Override
    public ConditionalExpression visitConditionalExpression(ZarfParser.ConditionalExpressionContext ctx) {
        ZarfParser.ExpressionContext leftExpressionCtx = ctx.expression(0); //get left side expression ( ex. 1 < 5  -> it would mean get "1")
        ZarfParser.ExpressionContext rightExpressionCtx = ctx.expression(1); //get right side expression

        Expression leftExpression = leftExpressionCtx.accept(this); //get mapped (to POJO) left expression using this visitor

        //rightExpression might be null! Example: 'if (x)' checks x for nullity. The solution for this case is to assign integer 0 to the rightExpr
        Expression rightExpression = rightExpressionCtx != null ? rightExpressionCtx.accept(this) : new Value("0", SupportedTypes.INT);

        Signs cmpSign = ctx.cmp != null ? Signs.fromString(ctx.cmp.getText()) : Signs.NEQ; //if there is no cmp sign use '!=0' by default
        return new ConditionalExpression(cmpSign, leftExpression, rightExpression);
    }
}
