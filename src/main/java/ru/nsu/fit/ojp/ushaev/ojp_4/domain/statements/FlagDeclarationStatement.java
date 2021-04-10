package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;

public class FlagDeclarationStatement implements Statement {

    private final String name;

    public FlagDeclarationStatement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }
}
