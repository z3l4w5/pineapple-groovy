package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType
import foofun.text.pineapple.structures.Statement


/**
 * Statement ::= Print | Assignment
 */
class StatementParser implements Parser {

    PrintParser printParser

    AssignmentParser assignmentParser

    @Override
    Statement parse(Lexer lexer) {
        switch (lexer.lookAhead()) {
            case TokenType.TOKEN_PRINT:
                return this.printParser.parse(lexer)
            case TokenType.TOKEN_VAR_PREFIX:
                return this.assignmentParser.parse(lexer)
            default:
                // return nil, errors.New("parseStatement(): unknown Statement.")
                return null
        }
    }
}
