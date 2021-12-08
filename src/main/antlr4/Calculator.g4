grammar Calculator;

@header {
    package hello;
}

parse
    : expression EOF
    ;

/* ANTLR resolves ambiguities in favor of the alternative given first, implicitly allowing us to specify operator precedence */
expression
    : lhs=expression op=('*'|'/') rhs=expression #mulExpression
    | lhs=expression op=('+'|'-') rhs=expression #addExpression
    | '(' e=expression ')'                       #parenExpression
    | a=atom                                     #atomExpression
    ;

atom
    : INT  #intAtom
    ;

INT : [0-9]+;

SPACE : [ \t\r\n] -> skip; /* TODO for python: not that */
