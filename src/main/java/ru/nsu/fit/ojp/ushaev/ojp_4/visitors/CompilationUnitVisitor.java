package ru.nsu.fit.ojp.ushaev.ojp_4.visitors;

import ru.nsu.fit.ojp.ushaev.ojp_4.ZarfBaseVisitor;
import ru.nsu.fit.ojp.ushaev.ojp_4.ZarfParser;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.CompilationUnit;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements.Statement;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.FlagStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationUnitVisitor extends ZarfBaseVisitor<CompilationUnit> {

    private final VariableStorage variableStorage;
    private final FlagStorage flagStorage;

    public CompilationUnitVisitor(VariableStorage variableStorage, FlagStorage flagStorage) {
        this.variableStorage = variableStorage;
        this.flagStorage = flagStorage;
    }

    @Override
    public CompilationUnit visitCompilationUnit(ZarfParser.CompilationUnitContext ctx) {
        List<Statement> statements = getStatements(ctx);
        return new CompilationUnit(statements);
    }

    private List<Statement> getStatements(ZarfParser.CompilationUnitContext ctx) {
        StatementVisitor statementVisitor = new StatementVisitor(variableStorage, flagStorage);
        return ctx.statements().stream()
                .map(block -> block.accept(statementVisitor))
                .collect(Collectors.toList());
    }
}
