package foofun.text.pineapple.visitors

import foofun.text.pineapple.structures.Assignment
import foofun.text.pineapple.structures.Print

/**
 * 抽象语法树命令语句解析
 */
interface StatementVisitor {

    /**
     * 执行print语句
     */
    void execute(Print print)

    /**
     * 执行赋值语句
     */
    void execute(Assignment assignment)
}