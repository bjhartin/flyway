package com.googlecode.flyway.sqlparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;


public class HelloTest {
    @Test
    public void testHello() {
        HelloLexer lexer = new HelloLexer(new ANTLRInputStream("hello sir"));
        HelloParser parser = new HelloParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener( new DiagnosticErrorListener() );

        // Perform the Parse
        ParseTree r = parser.r();
        System.out.println(r.toStringTree());
    }
}
