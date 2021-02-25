package foofun.text.pineapple

import foofun.text.pineapple.structures.Assignment
import foofun.text.pineapple.structures.Print
import foofun.text.pineapple.structures.SourceCode
import foofun.text.pineapple.structures.Statement
import foofun.text.pineapple.visitors.DefaultStatementVisitor
import foofun.text.pineapple.visitors.StatementVisitor

/**
 * 执行后端
 */
class Backend {

    StatementVisitor statementVisitor

    def Execute(String code) {
        // parse
        Parsers parsers = new Parsers()
        parsers.init()
        SourceCode sourceCode = parsers.parse(code)

        this.statementVisitor = new DefaultStatementVisitor()

        // resolve
        this.resolveAST(sourceCode)
    }

    void resolveAST(SourceCode sourceCode) {

        for (Statement statement : sourceCode.statements) {
            this.resolveStatement(statement)
        }
    }

    void resolveStatement(Statement statement) {
        if (statement instanceof Assignment) {
            this.statementVisitor.execute(statement)
        } else if (statement instanceof Print) {
            this.statementVisitor.execute(statement)
        }
    }

    static main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: ${args[0]} filename\n",)
            return
        }
        String filename = args[1]

        String code = new File(filename).text

        // execute
        Backend backend = new Backend()
        backend.Execute(code)
    }
}
