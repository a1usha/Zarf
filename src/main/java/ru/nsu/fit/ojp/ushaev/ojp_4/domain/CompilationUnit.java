package ru.nsu.fit.ojp.ushaev.ojp_4.domain;

import ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements.Statement;

import java.util.List;

public class CompilationUnit {

    private final List<Statement> statements;

    public CompilationUnit(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
