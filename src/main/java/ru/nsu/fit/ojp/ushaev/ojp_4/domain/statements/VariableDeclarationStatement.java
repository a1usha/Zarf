package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;

public class VariableDeclarationStatement implements Statement {

    private final String name;
    private final Expression expression;

    public VariableDeclarationStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }
}
