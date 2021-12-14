grammar Python3;

@header {
    package parser;
}

tokens { INDENT, DEDENT }

@lexer::members {
    private java.util.Stack<Integer> indents = new java.util.Stack<>();
    private int openedParens = 0; // ignore indents when inside parens/brackets/braces
    private int current_indent = 0; // keep track of leading spaces on each line
    private boolean seenNonSpace = false; // only count spaces as indents when we haven't seen a non-space token on this line

    private Token bufferedToken = null;

    /* look at https://github.com/antlr/antlr4/blob/master/runtime/Java/src/org/antlr/v4/runtime/Lexer.java */
    @Override
    public Token nextToken() {
        Token t = nextTokenHelper();
        System.out.println("\u001b[32m  Actual token: " + t + "; " + indents + "\u001b[0m"); //]
        return t;
    }
    public Token nextTokenHelper() {
        Token t;
        if (bufferedToken != null) {
            t = bufferedToken;
            bufferedToken = null;
        } else {
            t = super.nextToken();
        }
        System.out.println(t);
        if (!seenNonSpace && t.getType() != NL) {
            System.out.println("First token of line");
            if (indents.empty()) {
                System.out.println("We have not indented whileyet");
                if (current_indent > 0) {
                    System.out.println("This token is indented");
                    indents.push(current_indent);
//                    current_indent = 0;
                    bufferedToken = t;
                    return new CommonToken(Python3Parser.INDENT, "<INDENT>");
                } else {
                    System.out.println("This token is the same indent level");
                }
            } else {
                int previous_indent = indents.peek();
                if (current_indent > previous_indent) {
                    System.out.println("This token is more indented");
                    indents.push(current_indent);
//                    current_indent = 0;
                    bufferedToken = t;
                    return new CommonToken(Python3Parser.INDENT, "<INDENT>");
                } else if (current_indent < previous_indent) {
                    System.out.println("This token is less indented");
                    indents.pop();
                    bufferedToken = t;
                    return new CommonToken(Python3Parser.DEDENT, "<DEDENT>");
                } else {
                    System.out.println("This token is the same indent level");
                }
            }
        }
        if (t.getType() == NL) {
            seenNonSpace = false;
            current_indent = 0;
        } else {
            seenNonSpace = true;
        }
        return t;
/*
        if (_input.LA(1) == EOF && indents.isEmpty()) {
            // We reached EOF and are not currently indented
            emitEOF();
        } else if (_input.LA(1) == EOF) {
            // We reached EOF, but need to dedent
            indents.pop();
            emit(new CommonToken(Python3Parser.DEDENT, "aaaaaa"));
        } else {
            // We are not at EOF
            // TODO
            return super.nextToken();
        }*/
    }

}

parse
    : statement* EOF
    ;

statement
    : NL                                            #emptyStatement
    | 'while' expression ':' (statement | block)    #whileStatement
    | IDENTIFIER '=' expression NL                  #assignmentStatement
    | expression NL                                 #expressionStatement
    | 'for' IDENTIFIER 'in' expression ':' (statement | block)    #forStatement
    ;

/* Allow NL+ at the beginning of block, because NL is always inserted before INDENT, and multiple may be inserted if
   there are blank lines between the previous line and the indented line */
block
    : NL+ INDENT statement+ DEDENT
    ;

/* ANTLR resolves ambiguities in favor of the alternative given first, implicitly allowing us to specify operator precedence */
/* Python3 operator precedence listed here: https://docs.python.org/3/reference/expressions.html#operator-precedence */
expression
    : IDENTIFIER LPAREN ((expression',')* expression)? RPAREN           #functionCallExpression
    | lhs=expression op=('*'|'/') rhs=expression                        #mulExpression
    | lhs=expression op=('+'|'-') rhs=expression                        #addExpression
    | lhs=expression op=('<'|'<='|'>'|'>='|'=='|'!=') rhs=expression    #comparisonExpression
    | lhs=expression op='and' rhs=expression                          #andExpression
    | lhs=expression op='or' rhs=expression                           #orExpression
    | LBRACE (e1=expression (',' e2=expression)*)? RBRACE               #setOrDictExpression
    | a=atom                                     #atomExpression
    ;

// Python3 Atom definitions: https://docs.python.org/3/reference/expressions.html#atoms
atom
    : INT  #intAtom
    | STRING #stringAtom
    | IDENTIFIER #identifierAtom
    | LPAREN e=expression RPAREN #parenAtom
    ;

// Python3 Lexical Analysis: https://docs.python.org/3/reference/lexical_analysis.html
INT : '0' | [1-9][0-9]*; //Integer
STRING : '"' ~["]* '"';
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;
LPAREN : '(' { ++openedParens; };
RPAREN : ')' { --openedParens; };
LBRACKET : '[' { ++openedParens; };
RBRACKET : ']' { --openedParens; };
LBRACE : '{' { ++openedParens; };
RBRACE : '}' { --openedParens; };

SPACE : [ \t] {
    if (openedParens > 0 || seenNonSpace) skip();
    else {
        current_indent++;
        skip();
    }
};

NL : [\r]?[\n] {
    if (openedParens > 0) skip();
};

COMMENT : '#' ~[\r\n]* -> skip;
