package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;

public class PrintStatement implements Statement {

    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }
}
