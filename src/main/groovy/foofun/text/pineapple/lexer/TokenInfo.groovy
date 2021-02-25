package foofun.text.pineapple.lexer

/**
 * 分词解析时的返回信息
 */
class TokenInfo {

    Integer lineNum
    String token
    TokenType tokenType

    TokenInfo(Integer lineNum, TokenType tokenType, String token) {
        this.lineNum = lineNum
        this.tokenType = tokenType
        this.token = token
    }
}
