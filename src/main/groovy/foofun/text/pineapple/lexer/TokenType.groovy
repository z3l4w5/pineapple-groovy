package foofun.text.pineapple.lexer

/**
 * 分词类型
 */
enum TokenType {
    TOKEN_EOF(0, "EOF"),           // "EOF",
    TOKEN_VAR_PREFIX(1, '$'),    // "$",
    TOKEN_LEFT_PAREN(2, "("),   // "(",
    TOKEN_RIGHT_PAREN(3, ")"),   // ")",
    TOKEN_EQUAL(4, "="),         // "=",
    TOKEN_QUOTE(5, "\""),         // "\"",
    TOKEN_DUOQUOTE(6, "\"\""),      // "\"\"",
    TOKEN_NAME(7, "Name"),          // "Name",
    TOKEN_PRINT(8, "print"),         // "print",
    TOKEN_IGNORED(9, "Ignored");       // "Ignored",

    int code
    String name

    TokenType(int code, String name) {
        this.code = code
        this.name = name
    }
}