package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;

import java.util.Optional;

public class IfStatement implements Statement {

    private final Expression condition;
    private final Statement trueStatement;
    private final Statement falseStatement;

    public IfStatement(Expression condition, Statement trueStatement, Statement falseStatement) {

        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getTrueStatement() {
        return trueStatement;
    }

    public Optional<Statement> getFalseStatement() {
        return Optional.ofNullable(falseStatement);
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }
}
