package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType
import foofun.text.pineapple.structures.Assignment

/**
 * Assignment  ::= Variable Ignored "=" Ignored String Ignored
 */
class AssignmentParser implements Parser {

    VariableParser variableParser
    StringParser stringParser

    @Override
    Assignment parse(Lexer lexer) {
        Assignment assignment = new Assignment()

        assignment.lineNum = lexer.lineNum

        assignment.variable = this.variableParser.parse(lexer)

        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)
        lexer.nextTokenIs(TokenType.TOKEN_EQUAL)
        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)

        assignment.string = this.stringParser.parse(lexer)

        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)

        return assignment
    }
}
