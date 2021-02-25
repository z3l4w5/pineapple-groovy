package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType
import foofun.text.pineapple.structures.SourceCode
import foofun.text.pineapple.structures.Statement

/**
 * SourceCode      ::= Statement+
 */
class SourceCodeParser implements Parser {

    StatementParser statementParser

    @Override
    SourceCode parse(Lexer lexer) {
        SourceCode sourceCode = new SourceCode()

        sourceCode.lineNum = lexer.lineNum

        sourceCode.statements = this.parseStatements(lexer)

        return sourceCode
    }

    List<Statement> parseStatements(Lexer lexer) {
        def statements = []

        while (this.isSourceCodeEnd(lexer.lookAhead())) {
            Statement statement = this.statementParser.parse(lexer)
            statements << statement
        }

        return statements
    }

    boolean isSourceCodeEnd(TokenType token) {
        if (token == TokenType.TOKEN_EOF) {
            return true
        }
        return false
    }
}
