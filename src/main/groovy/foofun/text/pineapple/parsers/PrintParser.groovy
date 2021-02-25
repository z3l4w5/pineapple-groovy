package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType
import foofun.text.pineapple.structures.Print

/**
 * Print ::= "print" "(" Ignored Variable Ignored ")" Ignored
 */
class PrintParser implements Parser {

    VariableParser variableParser

    @Override
    Print parse(Lexer lexer) {
        Print print = new Print()

        print.lineNum = lexer.lineNum
        lexer.nextTokenIs(TokenType.TOKEN_PRINT)
        lexer.nextTokenIs(TokenType.TOKEN_LEFT_PAREN)
        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)

        print.variable = this.variableParser.parse(lexer)

        // if print.Variable, err = ; err != nil

        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)
        lexer.nextTokenIs(TokenType.TOKEN_RIGHT_PAREN)
        lexer.lookAheadAndSkip(TokenType.TOKEN_IGNORED)

        return print
    }
}
