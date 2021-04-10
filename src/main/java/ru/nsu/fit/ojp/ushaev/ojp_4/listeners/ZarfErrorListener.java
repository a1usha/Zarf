package ru.nsu.fit.ojp.ushaev.ojp_4.listeners;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ZarfErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        final String errorFormat = "Error occurred at line %d,char %d. Details:%n%s";
        final String errorMsg = String.format(errorFormat, line, charPositionInLine, msg);
        System.out.println(errorMsg);
    }
}
