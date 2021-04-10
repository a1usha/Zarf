package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;

public class GotoStatement implements Statement {

    private final String name;

    public GotoStatement(String name) {
        this.name = name;
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }

    public String getName() {
        return name;
    }
}
