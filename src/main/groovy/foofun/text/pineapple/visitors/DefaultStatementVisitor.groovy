package foofun.text.pineapple.visitors

import foofun.text.pineapple.structures.Assignment
import foofun.text.pineapple.structures.Print

class DefaultStatementVisitor implements StatementVisitor {

    Map<String, String> variables = new HashMap<String, String>()

    void execute(Print print) {
        String varName = print.variable.name

        // errors.New("resolvePrint(): variable name can NOT be empty.")

        // errors.New(fmt.Sprintf("resolvePrint(): variable '$%s'not found.", varName))

        println this.variables[varName]
    }

    void execute(Assignment assignment) {
        String varName = assignment.variable.name

        this.variables[varName] = assignment.string
    }
}
