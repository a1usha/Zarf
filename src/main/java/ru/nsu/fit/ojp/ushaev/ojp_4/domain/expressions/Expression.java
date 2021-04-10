package ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.ExpressionGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

public abstract class Expression {

    private final Type type;

    public Expression(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract void create(ExpressionGenerator expressionGenerator);
}
