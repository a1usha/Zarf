package ru.nsu.fit.ojp.ushaev.ojp_4;

import ru.nsu.fit.ojp.ushaev.ojp_4.bytecodegeneration.BytecodeGenerator;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.CompilationUnit;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.FlagStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compiler {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(Compiler.class));

    public static void main(String[] args) throws Exception {
        new Compiler().compile(args);
    }

    public void compile(String[] args) throws Exception {
        ArgumentErrors argumentsErrors = getArgumentValidationErrors(args);
        if (argumentsErrors != ArgumentErrors.NONE) {
            LOGGER.log(Level.WARNING, "baaad");
            return;
        }

        File zarfFile = new File(args[0]);
        String fileAbsolutePath = zarfFile.getAbsolutePath();
        ZarfLangParser parser = new ZarfLangParser();
        CompilationUnit compilationUnit = parser.getCompilationUnit(fileAbsolutePath);
        saveBytecodeToClassFile(zarfFile.getName(), compilationUnit, parser.getStorage(), parser.getFlagStorage());
    }

    private ArgumentErrors getArgumentValidationErrors(String[] args) {

        if (args.length != 1) {
            return ArgumentErrors.NO_FILE;
        }
        String filePath = args[0];
        if (!filePath.endsWith(".zf")) {
            return ArgumentErrors.BAD_FILE_EXTENSION;
        }
        return ArgumentErrors.NONE;
    }

    private void saveBytecodeToClassFile(String fileName, CompilationUnit compilationUnit, VariableStorage variableStorage, FlagStorage flagStorage) throws IOException {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        String className = fileName.replace(".zf", "");
        byte[] bytecode = bytecodeGenerator.generate(compilationUnit, className, variableStorage, flagStorage);

        OutputStream os = new FileOutputStream(fileName.replace(".zf", ".class"));
        os.write(bytecode);
        os.close();
    }

    private enum ArgumentErrors {
        NO_FILE,
        BAD_FILE_EXTENSION,
        NONE
    }

}
