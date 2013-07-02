// This grammar attempts only to recognize individual SQL statements,
// but is based on the SQL-2003 BNF found at http://savage.net.au/SQL,
// and should therefore be much more accurate at things like detecting
// beginning and ends of multiline comments and strings.
grammar SqlStatements;
separator : (whitespace|comment)+;
comment : (simpleComment|bracketedComment);
simpleComment : 'sc';
bracketedComment : 'bc';
whitespace : 'w';

//<simple comment> ::= <simple comment introducer> [ <comment character>... ] <newline>
//<simple comment introducer> ::= <minus sign><minus sign> [ <minus sign>... ]
//<bracketed comment> ::= <bracketed comment introducer> <bracketed comment contents> <bracketed comment terminator>
//<bracketed comment introducer> ::= <slash> <asterisk>
//<bracketed comment terminator> ::= <asterisk> <slash>
//<bracketed comment contents> ::= [ { <comment character> | <separator> }... ]
//<comment character> ::= <nonquote character> | <quote>
//<newline> ::= !! See the Syntax Rules.
//<slash>
//<asterisk>
//<quote>
//<nonquote character>