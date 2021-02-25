package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType
import foofun.text.pineapple.structures.StringNode

/**
 * SourceCharacter ::=  #x0009 | #x000A | #x000D | [#x0020-#xFFFF]
 * StringCharacter ::= SourceCharacter - '"'
 * String ::= '"' '"' Ignored | '"' StringCharacter '"' Ignored
 */
class StringParser implements Parser {

    @Override
    StringNode parse(Lexer lexer) {
        switch (lexer.lookAhead()) {
            case TokenType.TOKEN_DUOQUOTE:
                lexer.nextTokenIs(TokenType.TOKEN_DUOQUOTE)
                lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)
                return new StringNode("")
            case TokenType.TOKEN_QUOTE:
                lexer.nextTokenIs(TokenType.TOKEN_QUOTE)
                String str = lexer.scanBeforeToken('"')
                lexer.nextTokenIs(TokenType.TOKEN_QUOTE)
                lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)
                return new StringNode(str)
            default:
                // return "", errors.New("parseString(): not a string.")
                return null
        }
    }
}
