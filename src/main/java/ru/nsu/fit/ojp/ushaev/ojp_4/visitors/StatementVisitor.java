package ru.nsu.fit.ojp.ushaev.ojp_4.visitors;

import ru.nsu.fit.ojp.ushaev.ojp_4.ZarfBaseVisitor;
import ru.nsu.fit.ojp.ushaev.ojp_4.ZarfParser;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements.*;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.Flag;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.FlagStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.Variable;

import java.util.List;
import java.util.stream.Collectors;

public class StatementVisitor extends ZarfBaseVisitor<Statement> {

    private final VariableStorage variableStorage;
    private final ExpressionVisitor expressionVisitor;
    private final FlagStorage flagStorage;

    public StatementVisitor(VariableStorage variableStorage, FlagStorage flagStorage) {
        this.variableStorage = variableStorage;
        this.expressionVisitor = new ExpressionVisitor(variableStorage);
        this.flagStorage = flagStorage;
    }

    @Override
    public Statement visitPrintStatement(ZarfParser.PrintStatementContext ctx) {
        ZarfParser.ExpressionContext expressionContext = ctx.expression();
        Expression expression = expressionContext.accept(expressionVisitor);
        return new PrintStatement(expression);
    }

    @Override
    public Statement visitVariableDeclaration(ZarfParser.VariableDeclarationContext ctx) {
        String varName = ctx.name().getText();
        ZarfParser.ExpressionContext expressionContext = ctx.expression();
        Expression expression = expressionContext.accept(expressionVisitor);
        variableStorage.addVariable(new Variable(varName,expression.getType()));
        return new VariableDeclarationStatement(varName, expression);
    }

    @Override
    public Statement visitGotoStatement(ZarfParser.GotoStatementContext ctx) {
        String flagName = ctx.name().getText();
        return new GotoStatement(flagName);
    }

    @Override
    public Statement visitFlagDeclaration(ZarfParser.FlagDeclarationContext ctx) {
        String flagName = ctx.name().getText();
        flagStorage.addFlag(new Flag(flagName));
        return new FlagDeclarationStatement(flagName);
    }

    @Override
    public Statement visitIfStatement(ZarfParser.IfStatementContext ctx) {
        ZarfParser.ExpressionContext conditionalExpressionContext = ctx.expression();
        Expression condition = conditionalExpressionContext.accept(expressionVisitor);

        Statement trueStatement = ctx.trueStatement.accept(this);
        Statement falseStatement = null;

        if (ctx.falseStatement != null)
            falseStatement = ctx.falseStatement.accept(this);

        return new IfStatement(condition, trueStatement, falseStatement);
    }

    @Override
    public Statement visitAssignmentStatement(ZarfParser.AssignmentStatementContext ctx) {
        String varName = ctx.name().getText();
        ZarfParser.ExpressionContext expressionContext = ctx.expression();
        Expression expression = expressionContext.accept(expressionVisitor);

        variableStorage.removeVariable(varName);
        variableStorage.addVariable(new Variable(varName,expression.getType()));
        return new VariableDeclarationStatement(varName, expression);
    }

    @Override
    public Statement visitBlock(ZarfParser.BlockContext ctx) {
        List<ZarfParser.StatementsContext> blockstatementsCtx = ctx.statements();
        VariableStorage newVariableStorage = new VariableStorage(variableStorage);

        StatementVisitor statementVisitor = new StatementVisitor(newVariableStorage, flagStorage);
        List<Statement> statements = blockstatementsCtx.stream().map(smtt -> smtt.accept(statementVisitor)).collect(Collectors.toList());
        return new Block(statements, newVariableStorage);
    }
}
