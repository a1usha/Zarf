package ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.ExpressionGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

public class Value extends Expression {

    private final String value;

    public Value(String value, Type type) {
        super(type);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void create(ExpressionGenerator expressionGenerator) {
        expressionGenerator.generate(this);
    }
}
