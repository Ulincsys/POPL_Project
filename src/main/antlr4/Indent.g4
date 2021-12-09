grammar Indent;

tokens { INDENT, DEDENT }

@lexer::members {
    private java.util.Stack<Integer> indents = new java.util.Stack<>();
    private int openedParens = 0; // ignore indents when inside parens/brackets/braces
    private int current_indent = 0; // keep track of leading spaces on each line
    private boolean seenNonSpace = false; // only count spaces as indents when we haven't seen a non-space token on this line

    private Token bufferedToken = null;

/*    @Override
    public void emit(Token t) {
        super.setToken(t);
        tokens.offer(t); // push_front
    }*/
    /* look at https://github.com/antlr/antlr4/blob/master/runtime/Java/src/org/antlr/v4/runtime/Lexer.java */
    @Override
    public Token nextToken() {
        Token t = nextTokenHelper();
        System.out.println("  Actual token: " + t + "; " + indents);
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
                System.out.println("We have not indented yet");
                if (current_indent > 0) {
                    System.out.println("This token is indented");
                    indents.push(current_indent);
//                    current_indent = 0;
                    bufferedToken = t;
                    return new CommonToken(IndentParser.INDENT, "<INDENT>");
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
                    return new CommonToken(IndentParser.INDENT, "<INDENT>");
                } else if (current_indent < previous_indent) {
                    System.out.println("This token is less indented");
                    indents.pop();
                    bufferedToken = t;
                    return new CommonToken(IndentParser.DEDENT, "<DEDENT>");
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
            emit(new CommonToken(IndentParser.DEDENT, "aaaaaa"));
        } else {
            // We are not at EOF
            // TODO
            return super.nextToken();
        }*/
    }

}            
    

@header {
    package hello;
}

parse
    : statement
    ;

statement
    : 'stat' NL
    | 'while' expression ':' (statement | NL block)
    ;

block
    : INDENT statement+ DEDENT
    ;

/* ANTLR resolves ambiguities in favor of the alternative given first, implicitly allowing us to specify operator precedence */
expression
    : lhs=expression op=('*'|'/') rhs=expression #mulExpression
    | lhs=expression op=('+'|'-') rhs=expression #addExpression
    | LPAREN e=expression RPAREN                 #parenExpression
    | LBRACE (e1=expression (',' e2=expression)*)? RBRACE                 #setOrDictExpression
    | a=atom                                     #atomExpression
    ;

atom
    : INT  #intAtom
    ;

INT : [0-9]+;
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

NL : [\n] {
    if (openedParens > 0) skip();
};