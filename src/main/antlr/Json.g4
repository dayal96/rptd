grammar Json;

json: value;

value : object            # jsonObject
      | array             # jsonArray
      | primitive         # jsonPrimitive
      ;


primitive : STRING        # jsonString
          | NUMBER        # number
          | DECIMAL       # decimal
          | TRUE          # true
          | FALSE         # false
          | NULL          # null
          ;

object : OBRACE members CBRACE   # nonEmptyObject
       | OBRACE CBRACE           # emptyObject
       ;

members : member COMMA members   # recurMember
        | member                 # singleMember
        ;

member : STRING COLON value;

array : OBRACKET CBRACKET                 # emptyArray
      | OBRACKET arrayContent CBRACKET    # nonEmptyArray
      ;

arrayContent : value COMMA arrayContent   # recurArray
             | value                      # singleArray
             ;


WHITESPACE : [ \r\n\t\f]+                       -> skip;
DECIMAL    : [-]?[0-9]+'.'[0-9]+                ;
NUMBER     : [-]?[0-9]+                         ;
STRING     : '"'(~["\\] | '\\"' | '\\\\')*'"'   ;

TRUE       : 'true'   ;
FALSE      : 'false'  ;
NULL       : 'null'   ;
BACKSLASH  : '\\'     ;
D_QUOTES   : '"'      ;
COMMA      : ','      ;
COLON      : ':'      ;
OBRACE     : '{'      ;
CBRACE     : '}'      ;
OBRACKET   : '['      ;
CBRACKET   : ']'      ;
