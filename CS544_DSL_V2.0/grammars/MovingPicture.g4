grammar MovingPicture;

program :		PROGRAM ID showpicture+ behaviors* EOF;

showpicture: 	SHOW picname setposition (NAMEED ID)? timecondition?;

picname:		LBRACKET .*? DOT ((JPG)|(GIF)|(JPEG)) RBRACKET;

setposition:	AT coordinate+;

//behaviordeclaration: behaviors timecondition*;

behaviors:		iterativebehavior
				| alternativebehavior
				| onewaybehavior
				;

basicmove:		action (THEN action)*;
				
onewaybehavior:	ID basicmove;

iterativebehavior: ID REPEAT (basicmove | onewaybehavior);

alternativebehavior: IF ID hit COMMA (basicmove | onewaybehavior | mirrorreflection);

boundry: 	LEFT | RIGHT | UP | DOWN | ANYBOUNDRY
			;

timecondition:	(aftertimecondition | untiltimecondition)
				;

aftertimecondition: AFTER (timestamp | timeperiod) (COMMA)?;

untiltimecondition: UNTIL timestamp (COMMA)?;

timestamp:		TIMESTAMP;

timeperiod:		INTEGER SEC;

action: 		straight
				| jump
				| disappear
				| hit
				| patrol
				;

speed:			WITH SPEED INTEGER (COMMA)?;

straight:		MOVE TO coordinate (timecondition)? (speed)?;

jump:			JUMP TO coordinate (timecondition)? ;

disappear:		DISAPPEAR (timecondition)?;

hit:			HIT (ID|boundry) ;

patrol:			PATROL BETWEEN coordinate (timecondition)? (speed)?;
				
coordinate:		LPAR INTEGER COMMA INTEGER RPAR;

mirrorreflection: MIRROR (timecondition)?;

// Reserved words
PROGRAM:	'program';
SHOW: 		'show';
NAMEED: 	'named';
AT: 		'at';
TO: 		'to';
DISAPPEAR:	'disappear';
MOVE:		'move';
JUMP:		'jump';
MIRROR:		'mirror reflection';
THEN:		'then';
WILL:		'will';
HIT:		'hit';
PATROL:		'patrol';
REPEAT:		'repeat';
UNTIL:		'until';
IF:			'if';
BETWEEN:    'between';
LEFT:		'left';
RIGHT:		'right';
UP:			'up';
DOWN:		'down';
ANYBOUNDRY:	'boundry';
OR:			'or';
SEC:		'sec';
MIN:		'min';
AFTER:		'after';
SPEED: 		'speed';
WITH: 		'with';

//Picture type
JPG:		'jpg';
JPEG:		'jpeg';
GIF:		'gif';



// Separators and operators
LPAR :			'(' ;
RPAR :			')' ;
COMMA :         ',' ;
Quotes :		'"' ;
LBRACKET :		'[' ;
RBRACKET :		']' ;
COLON :			':' ;
DOT:			'.' ;

//Basic elements
ID : 			LETTER (LETTER|DIGIT|'_'|'?')* ;
INTEGER : 		DIGIT+ ;
WS :			[ \t\r\n]+ -> skip ;
COMMENT :		'#' .*? ('\n'|EOF)->skip;
//TIME:			DIGIT DIGIT COLON DIGIT[0,5] DIGIT;
//SECOND:			DIGIT[0,5] DIGIT;
//MINUTE:			DIGIT DIGIT;
TIMESTAMP:		DIGIT DIGIT COLON DIGIT DIGIT;

fragment
LETTER :		[A-Za-z] ;
 
fragment
DIGIT :		[0-9] ;