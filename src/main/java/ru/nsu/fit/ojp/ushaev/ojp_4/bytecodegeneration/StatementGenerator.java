package ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements.*;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.FlagStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StatementGenerator {

    private final MethodVisitor mv;
    private final ExpressionGenerator expressionGenerator;
    private final VariableStorage variableStorage;
    private final FlagStorage flagStorage;
    private final Map<String, Label> labelMap;

    public StatementGenerator(MethodVisitor mv, VariableStorage variableStorage, FlagStorage flagStorage) {
        this.mv = mv;
        this.flagStorage = flagStorage;
        this.expressionGenerator = new ExpressionGenerator(mv, variableStorage);
        this.variableStorage = variableStorage;
        this.labelMap = new HashMap<>();
    }

    public StatementGenerator(MethodVisitor mv, VariableStorage variableStorage, FlagStorage flagStorage, Map<String, Label> labelMap) {
        this.mv = mv;
        this.flagStorage = flagStorage;
        this.expressionGenerator = new ExpressionGenerator(mv, variableStorage);
        this.variableStorage = variableStorage;
        this.labelMap = labelMap;
    }

    public void generate(PrintStatement printStatement) {
        Expression expression = printStatement.getExpression();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // generate expression first
        expression.create(expressionGenerator);
        Type type = expression.getType();
        String descriptor = "(" + type.getDescriptor() + ")V";
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false);
    }

    public void generate(VariableDeclarationStatement variableDeclarationStatement) {
        Expression expression = variableDeclarationStatement.getExpression();
        String name = variableDeclarationStatement.getName();
        int id = variableStorage.getVariableIndex(name);
        Type type = expression.getType();
        // generate expression first
        expression.create(expressionGenerator);

        if (type.getTypeClass() == int.class) {
            mv.visitVarInsn(Opcodes.ISTORE, id);
        } else {
            mv.visitVarInsn(Opcodes.ASTORE, id);
        }
    }

    public void generate(AssignmentStatement assignmentStatement) {
        Expression expression = assignmentStatement.getExpression();
        String name = assignmentStatement.getName();
        int id = variableStorage.getVariableIndex(name);
        Type type = expression.getType();
        // generate expression first
        expression.create(expressionGenerator);

        if (type.getTypeClass() == int.class) {
            mv.visitVarInsn(Opcodes.ISTORE, id);
        } else {
            mv.visitVarInsn(Opcodes.ASTORE, id);
        }
    }

    public void generate(IfStatement ifStatement) {
        Expression condition = ifStatement.getCondition();
        condition.create(expressionGenerator);
        Label trueLabel = new Label();
        Label endLabel = new Label();

        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);
        Optional<Statement> falseStatement = ifStatement.getFalseStatement();
        falseStatement.ifPresent(statement -> statement.create(this));
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        mv.visitLabel(trueLabel);
        ifStatement.getTrueStatement().create(this);
        mv.visitLabel(endLabel);
    }

    public void generate(Block block) {
        VariableStorage newVariableStorage = block.getStorage();
        List<Statement> statements = block.getStatements();
        StatementGenerator statementGenerator = new StatementGenerator(mv, newVariableStorage, flagStorage, labelMap);
        statements.forEach(statement -> statement.create(statementGenerator));
    }

    public void generate(FlagDeclarationStatement flagDeclarationStatement) {
        Label gotoLabel;

        if (labelMap.containsKey(flagDeclarationStatement.getName())) {
            gotoLabel = labelMap.get(flagDeclarationStatement.getName());
        } else {
            gotoLabel = new Label();
            labelMap.put(flagDeclarationStatement.getName(), gotoLabel);
        }

        mv.visitLabel(gotoLabel);
    }

    public void generate(GotoStatement gotoStatement) {
        Label gotoLabel;

        if (labelMap.containsKey(gotoStatement.getName())) {
            gotoLabel = labelMap.get(gotoStatement.getName());
        } else {
            gotoLabel = new Label();
            labelMap.put(gotoStatement.getName(), gotoLabel);
        }

        mv.visitJumpInsn(Opcodes.GOTO, gotoLabel);
    }
}
