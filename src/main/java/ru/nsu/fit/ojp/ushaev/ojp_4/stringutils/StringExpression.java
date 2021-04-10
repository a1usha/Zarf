package ru.nsu.fit.ojp.ushaev.ojp_4.stringutils;

import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

public abstract class StringExpression extends Expression {

    private final Expression leftExpression;
    private final Expression rightExpression;

    public StringExpression(Type type, Expression leftExpression, Expression rightExpression) {
        super(type);
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;

        if (type.getTypeClass() != String.class) {
            throw new IllegalArgumentException("Unsupported arithmetic operation with type: " + type.getName());
        }
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }
}
