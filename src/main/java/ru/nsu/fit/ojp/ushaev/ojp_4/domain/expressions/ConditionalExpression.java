package ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.ExpressionGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.arithmetic.Signs;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.SupportedTypes;

public class ConditionalExpression extends Expression {

    private final Signs compareSign;
    private final Expression leftExpression;
    private final Expression rightExpression;

    public ConditionalExpression(Signs compareSign, Expression leftExpression, Expression rightExpression) {
        super(SupportedTypes.BOOL);
        this.compareSign = compareSign;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public Signs getCompareSign() {
        return compareSign;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    @Override
    public void create(ExpressionGenerator expressionGenerator) {
        expressionGenerator.generate(this);
    }
}
