package utility;

import lexparser.MovingPictureLexer;
import lexparser.MovingPictureParser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class LexparserFactory {

	static public MovingPictureLexer makeLexer(ANTLRInputStream inputText) {
		final MovingPictureLexer lexer = new MovingPictureLexer(inputText);
		lexer.addErrorListener(
				new BaseErrorListener() {
					@Override
					public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
							int line, int charPositionInLine, String msg,
							RecognitionException e)
					{

						throw new LexparserException(msg, e);
					}
				}
		);
		return lexer;
	}
	
	static public MovingPictureParser makeParser(ANTLRInputStream inputText) {
		final MovingPictureLexer lexer = makeLexer(inputText);
		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final MovingPictureParser parser = new MovingPictureParser(tokenStream);
		parser.addErrorListener(
				new BaseErrorListener() {
					@Override
					public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
							int line, int charPositionInLine, String msg,
							RecognitionException e)
					{
						throw new LexparserException(e.getMessage(), e);
					}
				}
		);
		return parser;
	}
		
}
