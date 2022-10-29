
grammar jsonToBnl;

value : object
      | array
      | string
      | NUMBER
      | DECIMAL
      | TRUE
      | FALSE
      | NULL;

object : OBRACE members CBRACE
       | OBRACE CBRACE;

members : members COMMA member
        | member;

member : D_QUOTES ID D_QUOTES COLON value;

array : OBRACKET CBRACKET
      | OBRACKET arrayContent CBRACKET;

arrayContent : arrayContent COMMA value
             | value;

string : D_QUOTES content D_QUOTES
       | S_QUOTES content S_QUOTES
       | D_QUOTES D_QUOTES
       | S_QUOTES S_QUOTES;

content : content char
        | char;

char : BACKSLASH S_QUOTES
     | BACKSLASH D_QUOTES
     | BACKSLASH BACKSLASH
     | CHARACTER;


Newline    : [\r\n]+                   -> skip;
Whitespace : [ \r\n\t\f]+              -> skip;
COMMA      : ','                       ;
COLON      : ':'                       ;
S_QUOTES   : '\''                      ;
D_QUOTES   : '"'                       ;
BACKSLASH  : '\\'                      ;
FALSE      : '#f' | 'false'            ;
TRUE       : '#t' | 'true'             ;
NULL       : 'null'                    ;
CHARACTER  : ~['"\\]                   ;
NUMBER     : [-]?[0-9]+('/'[0-9]+)?    ;
DECIMAL    : [-]?[0-9]+'.'[0-9]+       ;
ID         : [a-zA-Z][.a-zA-Z0-9\-]*   ;

OBRACE     : '{'  ;
CBRACE     : '}'  ;
OBRACKET   : '['  ;
CBRACKET   : ']'  ;
