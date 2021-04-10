package ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.ExpressionGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

public class Reference extends Expression {

    private final String varName;

    public Reference(String varName, Type type) {
        super(type);
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }

    @Override
    public void create(ExpressionGenerator expressionGenerator) {
        expressionGenerator.generate(this);
    }
}
