package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType
import foofun.text.pineapple.structures.Variable

/**
 * Name ::= [_A-Za-z][_0-9A-Za-z]*
 * Variable ::= "$" Name Ignored
 */
class VariableParser implements Parser {

    @Override
    Variable parse(Lexer lexer) {
        Variable variable = new Variable()

        variable.lineNum = lexer.lineNum
        lexer.nextTokenIs(TokenType.TOKEN_VAR_PREFIX)

        variable.name = this.parseName(lexer)

        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)

        return variable
    }

    String parseName(Lexer lexer) {
        // _,
        String name = lexer.nextTokenIs(TokenType.TOKEN_NAME)
        return name
    }
}
