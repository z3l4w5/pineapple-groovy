package foofun.text.pineapple.visitors

import foofun.text.pineapple.structures.Assignment
import foofun.text.pineapple.structures.Print

/**
 * 抽象语法树命令语句解析
 */
interface StatementVisitor {

    void execute(Print print)

    void execute(Assignment assignment)
}