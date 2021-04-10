//header
grammar Zarf;

//parser rules
compilationUnit : (statements)* EOF;
variableDeclaration : VARIABLE name EQUALS expression;
printStatement : PRINT expression ;

flagDeclaration : '!' name;
gotoStatement : 'goto' name;

value : NUMBER
      | STRING ;

expression :
            varReference #VARREFERENCEE
           | value        #VALUEE
           | '('expression MULT_DIV expression')' #MULTIPLY
           | expression MULT_DIV expression  #MULTIPLY
           | '(' expression PLUS_MINUS expression ')' #ADD
           | expression PLUS_MINUS expression #ADD
           | expression cmp='>' expression #conditionalExpression
           | expression cmp='<' expression #conditionalExpression
           | expression cmp='==' expression #conditionalExpression
           | expression cmp='!=' expression #conditionalExpression
           | expression cmp='>=' expression #conditionalExpression
           | expression cmp='<=' expression #conditionalExpression
           ;

varReference : ID ;
name : ID ;

ifStatement :  'if'  '('? expression ')'? trueStatement=statements ('else' falseStatement=statements?)?;
assignmentStatement : name EQUALS expression ;

block : '{' statements* '}' ;

statements : block
            | printStatement
            | variableDeclaration
            | assignmentStatement
            | ifStatement
            | flagDeclaration
            | gotoStatement
            ;

// TOKENS
VARIABLE : 'let' ;
PRINT : 'say' ;
EQUALS : '=' ;
NUMBER : '-'?[0-9.]+ ;
STRING : '"'.*?'"' ;
ID : [a-zA-Z0-9]+ ;
WS: [ \t\n\r]+ -> skip ;

PLUS_MINUS : PLUS | MINUS ;
MULT_DIV : MULT | DIV | MOD ;

PLUS : '+' ;
MINUS : '-' ;
MULT : '*' ;
DIV : '/' ;
MOD : '%';