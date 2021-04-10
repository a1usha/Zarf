package ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.arithmetic.*;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.ConditionalExpression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Expression;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Reference;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.expressions.Value;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.Variable;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;
import ru.nsu.fit.ojp.ushaev.ojp_4.stringutils.Concatenation;
import ru.nsu.fit.ojp.ushaev.ojp_4.stringutils.StringExpression;

public class ExpressionGenerator {

    private final MethodVisitor mv;
    private final VariableStorage variableStorage;

    public ExpressionGenerator(MethodVisitor mv, VariableStorage variableStorage) {
        this.mv = mv;
        this.variableStorage = variableStorage;
    }

    public void generate(Reference reference) {
        String varName = reference.getVarName();
        int id = variableStorage.getVariableIndex(varName);
        Variable var = variableStorage.getVariable(varName);
        Type type = var.getType();

        if (type.getTypeClass() == int.class) {
            mv.visitVarInsn(Opcodes.ILOAD, id);
        } else {
            mv.visitVarInsn(Opcodes.ALOAD, id);
        }
    }

    // Push value on stack
    public void generate(Value value) {
        Type type = value.getType();
        String strValue = value.getValue();

        if (type.getTypeClass() == int.class) {
            int intValue = Integer.parseInt(strValue);
            mv.visitLdcInsn(intValue);
        } else {
            strValue = strValue.replace("\"", "");
            mv.visitLdcInsn(strValue);
        }
    }

    public void generate(Addition addition) {
        evaluateArithmeticComponents(addition);
        mv.visitInsn(Opcodes.IADD);
    }

    public void generate(Subtraction subtraction) {
        evaluateArithmeticComponents(subtraction);
        mv.visitInsn(Opcodes.ISUB);
    }

    public void generate(Multiplication multiplication) {
        evaluateArithmeticComponents(multiplication);
        mv.visitInsn(Opcodes.IMUL);
    }

    public void generate(Division division) {
        evaluateArithmeticComponents(division);
        mv.visitInsn(Opcodes.IDIV);
    }

    public void generate(ModDivision mod) {
        evaluateArithmeticComponents(mod);
        mv.visitInsn(Opcodes.IREM);
    }

    public void generate(Concatenation concatenation) {
        evaluateArithmeticComponents(concatenation);

        mv.visitVarInsn(Opcodes.ASTORE, 101);
        mv.visitVarInsn(Opcodes.ASTORE, 102);

        mv.visitTypeInsn(Opcodes.NEW,"java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/StringBuilder","<init>","()V",false);
        mv.visitVarInsn(Opcodes.ALOAD, 102);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",false);
        mv.visitVarInsn(Opcodes.ALOAD, 101);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",false);

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder",
                "toString","()Ljava/lang/String;",false);
    }

    private void evaluateArithmeticComponents(StringExpression expression) {
        Expression leftExpression = expression.getLeftExpression();
        Expression rightExpression = expression.getRightExpression();

        leftExpression.create(this);
        rightExpression.create(this);
    }

    private void evaluateArithmeticComponents(ArithmeticExpression expression) {
        Expression leftExpression = expression.getLeftExpression();
        Expression rightExpression = expression.getRightExpression();

        leftExpression.create(this);
        rightExpression.create(this);
    }

    public void generate(ConditionalExpression conditionalExpression) {
        Expression leftExpression = conditionalExpression.getLeftExpression();
        Expression rightExpression = conditionalExpression.getRightExpression();
        Type type = leftExpression.getType();
        if(type != rightExpression.getType()) {
            throw new IllegalArgumentException("Cant compare different types"); //not yet supported
        }
        leftExpression.create(this);
        rightExpression.create(this);

        Signs compareSign = conditionalExpression.getCompareSign();
        Label trueLabel = new Label();
        Label endLabel = new Label();

        mv.visitJumpInsn(compareSign.getOpcode(),trueLabel);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        mv.visitLabel(trueLabel);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(endLabel);
    }
}
