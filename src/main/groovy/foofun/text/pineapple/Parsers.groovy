package foofun.text.pineapple

import foofun.text.pineapple.parsers.AssignmentParser
import foofun.text.pineapple.parsers.PrintParser
import foofun.text.pineapple.parsers.SourceCodeParser
import foofun.text.pineapple.parsers.StatementParser
import foofun.text.pineapple.parsers.StringParser
import foofun.text.pineapple.parsers.VariableParser
import foofun.text.pineapple.structures.SourceCode
import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.lexer.TokenType

/**
 * 语法分析器构建，读取所有语句集合
 */
class Parsers {

    SourceCodeParser sourceParser

    void init() {
        this.sourceParser = new SourceCodeParser()

        StatementParser statementParser = new StatementParser()
        this.sourceParser.statementParser = statementParser

        PrintParser printParser = new PrintParser()
        AssignmentParser assignmentParser = new AssignmentParser()
        VariableParser variableParser = new VariableParser()

        StringParser stringParser = new StringParser()

        assignmentParser.stringParser = stringParser
        assignmentParser.variableParser = variableParser
        printParser.variableParser = variableParser

        statementParser.assignmentParser = assignmentParser
        statementParser.printParser = printParser
    }

    SourceCode parse(String code) {

        Lexer lexer = new Lexer(code)

        SourceCode sourceCode = this.sourceParser.parse(lexer)

        lexer.nextTokenIs(TokenType.TOKEN_EOF)

        return sourceCode
    }
}
