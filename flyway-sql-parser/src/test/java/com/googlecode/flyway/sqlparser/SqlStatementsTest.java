package com.googlecode.flyway.sqlparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.tree.ParseTree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlStatementsTest {

    @Test
    public void testSimpleComment() {
        SqlStatementsParser parser = parser("sc");
        ParseTree tree = parser.simpleComment();
        assertEquals("([] sc)", tree.toStringTree());
    }

    @Test
    public void testBracktedComment() {
        SqlStatementsParser parser = parser("bc");
        ParseTree tree = parser.bracketedComment();
        assertEquals("([] bc)", tree.toStringTree());
    }

    @Test
    public void testComment() {
        SqlStatementsParser parser = parser("bc");
        ParseTree tree = parser.comment();
        assertEquals("([] ([17] bc))", tree.toStringTree());

        parser = parser("sc");
        tree = parser.comment();
        assertEquals("([] ([16] sc))", tree.toStringTree());
    }

    @Test
    public void testWhitespace() {
        SqlStatementsParser parser = parser("w");
        ParseTree tree = parser.whitespace();
        assertEquals("([] w)", tree.toStringTree());
    }
    
    private SqlStatementsParser parser(String input){
        SqlStatementsLexer lexer = new SqlStatementsLexer(new ANTLRInputStream(input));
        SqlStatementsParser parser = new SqlStatementsParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener( new DiagnosticErrorListener() );
        return parser;
    }
}
