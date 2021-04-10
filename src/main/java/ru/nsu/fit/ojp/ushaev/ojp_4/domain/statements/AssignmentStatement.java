package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;

public class AssignmentStatement implements Statement {

    private final String varName;
    private final Expression expression;

    public AssignmentStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public String getName() {
        return varName;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }
}
