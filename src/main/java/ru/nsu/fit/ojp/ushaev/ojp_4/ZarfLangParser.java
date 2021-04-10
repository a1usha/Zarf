package ru.nsu.fit.ojp.ushaev.ojp_4;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.CompilationUnit;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.FlagStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage.VariableStorage;
import ru.nsu.fit.ojp.ushaev.ojp_4.listeners.ZarfErrorListener;
import ru.nsu.fit.ojp.ushaev.ojp_4.visitors.CompilationUnitVisitor;

import java.io.IOException;

public class ZarfLangParser {

    private final VariableStorage variableStorage = new VariableStorage();
    private final FlagStorage flagStorage = new FlagStorage();

    public CompilationUnit getCompilationUnit(String fileAbsolutePath) throws IOException {
        CharStream charStream = CharStreams.fromFileName(fileAbsolutePath);
        ZarfLexer lexer = new ZarfLexer(charStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ZarfParser parser = new ZarfParser(tokenStream);

        ANTLRErrorListener errorListener = new ZarfErrorListener();
        parser.addErrorListener(errorListener);

        CompilationUnitVisitor compilationUnitVisitor = new CompilationUnitVisitor(variableStorage, flagStorage);
        return parser.compilationUnit().accept(compilationUnitVisitor);
    }

    public VariableStorage getStorage() {
        return variableStorage;
    }

    public FlagStorage getFlagStorage() {
        return flagStorage;
    }
}
