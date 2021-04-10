package ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.StatementGenerator;

public interface Statement {
    void create(StatementGenerator statementGenerator);
}
