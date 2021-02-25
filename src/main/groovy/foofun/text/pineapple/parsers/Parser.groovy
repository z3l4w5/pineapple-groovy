package foofun.text.pineapple.parsers

import foofun.text.pineapple.lexer.Lexer
import foofun.text.pineapple.structures.Node

/**
 * 抽象语法树解析器
 */
interface Parser {

    Node parse(Lexer lexer)
}
