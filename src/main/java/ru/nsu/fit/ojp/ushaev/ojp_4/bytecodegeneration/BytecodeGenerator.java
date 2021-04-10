package ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.CompilationUnit;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.statements.Statement;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.FlagStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;

import java.util.List;

public class BytecodeGenerator {

    public byte[] generate(CompilationUnit compilationUnit, String name, VariableStorage variableStorage, FlagStorage flagStorage) {

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        MethodVisitor mv;

        cw.visit(52, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);
        {
            //declare static void main
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

            List<Statement> statements = compilationUnit.getStatements();
            StatementGenerator statementGenerator = new StatementGenerator(mv, variableStorage, flagStorage);
            statements.forEach(statement -> statement.create(statementGenerator));

            mv.visitInsn(Opcodes.RETURN); //add return instruction

            mv.visitMaxs(0, 0); //set max stack and max local variables
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
