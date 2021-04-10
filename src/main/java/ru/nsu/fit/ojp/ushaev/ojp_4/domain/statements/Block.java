package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;

import java.util.List;

public class Block implements Statement {

    private final List<Statement> statements;
    private final VariableStorage variableStorage;

    public Block(List<Statement> statements, VariableStorage variableStorage) {
        this.statements = statements;
        this.variableStorage = variableStorage;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public VariableStorage getStorage() {
        return variableStorage;
    }

    @Override
    public void create(StatementGenerator statementGenerator) {
        statementGenerator.generate(this);
    }
}
