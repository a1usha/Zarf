package ru.nsu.fit.ojp.ushaev.ojp_4.domain.arithmetic;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.ExpressionGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

public class Subtraction extends ArithmeticExpression {

    public Subtraction(Type type, Expression leftExpression, Expression rightExpression) {
        super(type, leftExpression, rightExpression);
    }

    @Override
    public void create(ExpressionGenerator expressionGenerator) {
        expressionGenerator.generate(this);
    }
}
