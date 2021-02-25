package foofun.text.pineapple.lexer

import java.util.regex.Matcher

/**
 * 词法分析器，分割 Token
 */
class Lexer {
    String sourceCode

    int lineNum

    TokenInfo nextTokenInfo

    static Map<String, TokenType> KEYWORDS = new HashMap<>()

    static {
        KEYWORDS.put("print", TokenType.TOKEN_PRINT)
    }

    Lexer(String sourceCode) {
        this.sourceCode = sourceCode
        this.lineNum = 1
        this.nextTokenInfo = null
    }

    def MatchToken() {

        // check ignored
        if (this.isIgnored()) {
            return new TokenInfo(this.lineNum, TokenType.TOKEN_IGNORED, "Ignored")
        }
        // finish
        if (this.sourceCode.length() == 0) {
            return new TokenInfo(this.lineNum, TokenType.TOKEN_EOF, "EOF")
        }
        // check token
        switch (this.sourceCode[0]) {
            case '$':
                this.skipSourceCode(1)
                return new TokenInfo(this.lineNum, TokenType.TOKEN_VAR_PREFIX, '$')
            case '(':
                this.skipSourceCode(1)
                return new TokenInfo(this.lineNum, TokenType.TOKEN_LEFT_PAREN, "(")
            case ')':
                this.skipSourceCode(1)
                return new TokenInfo(this.lineNum, TokenType.TOKEN_RIGHT_PAREN, ")")
            case '=':
                this.skipSourceCode(1)
                return new TokenInfo(this.lineNum, TokenType.TOKEN_EQUAL, "=")
            case '"':
                if (this.nextSourceCodeIs("\"\"")) {
                    // 当前是一个“”，空白字符串
                    this.skipSourceCode(2)

                    return new TokenInfo(this.lineNum, TokenType.TOKEN_DUOQUOTE, "\"\"")
                }

                this.skipSourceCode(1)
                return new TokenInfo(this.lineNum, TokenType.TOKEN_QUOTE, "\"")
        }
        // check multiple character token
        if (this.sourceCode[0] == '_' || this.isLetter(this.sourceCode.charAt(0))) {
            String t = this.scanName()
            if (Lexer.KEYWORDS.containsKey(t)) {
                // 是关键字
                TokenType tokenType = Lexer.KEYWORDS[t]
                return new TokenInfo(this.lineNum, tokenType, t)
            } else {
                // 是非关键字
                return new TokenInfo(this.lineNum, TokenType.TOKEN_NAME, t)
            }
        }

        throw new LexerException("MatchToken(): unexpected symbol near '${this.sourceCode[0]}'.")
    }

    /**
     * 向前看多个字符，不移动读取位置
     */
    private boolean nextSourceCodeIs(String s) {
        return this.sourceCode.startsWith(s)
    }

    private boolean isLetter(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'
    }

    private boolean isNewLine(char c) {
        return c == '\r' || c == '\n'
    }

    private boolean isWhiteSpace(char c) {
        switch (c) {
            case '\t':
            case '\n':
            case 0x0B:
            case '\f':
            case '\r':
            case ' ':
                return true
        }
        return false
    }

    /**
     * 前移读取位置
     */
    void skipSourceCode(int n) {
        this.sourceCode = this.sourceCode.substring(n)
    }

    // regex match patterns
    private def pattern = ~/^[_\d\w]+`/

    private scanName() {

        Matcher matcher = pattern.matcher(this.sourceCode)

        if (matcher.find()) {
            String token = matcher.group(0)
            this.skipSourceCode(token.length())
            return token
        }

        // panic("unreachable!")
        throw new LexerException('Name unreachable!')
    }

    /**
     * 跳过 换行、空白 字符
     */
    boolean isIgnored() {
        boolean isIgnored = false
        // target pattern
        // matching
        while (this.sourceCode.length() > 0) {
            if (this.nextSourceCodeIs("\r\n") || this.nextSourceCodeIs("\n\r")) {
                this.skipSourceCode(2)
                this.lineNum += 1
                isIgnored = true
            } else if (isNewLine(this.sourceCode.charAt(0))) {
                this.skipSourceCode(1)
                this.lineNum += 1
                isIgnored = true
            } else if (isWhiteSpace(this.sourceCode.charAt(0))) {
                this.skipSourceCode(1)
                isIgnored = true
            } else {
                break
            }
        }
        return isIgnored
    }

    def getNextToken() {
        // next token already loaded
        if (this.nextTokenInfo) {

            TokenInfo result = this.nextTokenInfo
            this.nextTokenInfo = null

            return result
        }
        return this.MatchToken()
    }

    /**
     * 判断下一个Token是否符合类型要求
     */
    TokenInfo nextTokenIs(TokenType tokenType) {
        TokenInfo tokenInfo = this.getNextToken()
        // syntax error
        if (tokenType != tokenInfo.tokenType) {
            throw new LexerException("NextTokenIs(): syntax error expected token: ${tokenType.name} but got ${tokenInfo.tokenType.name}")
        }
        return tokenInfo
    }

    TokenType lookAhead() {
        // lexer.nextToken* already setted
        if (this.nextTokenInfo) {
            return this.nextTokenInfo.tokenType
        }
        // set it
        int nowLineNum = this.lineNum
        TokenInfo tokenInfo = this.getNextToken()
        this.lineNum = nowLineNum

        this.nextTokenInfo = tokenInfo

        return tokenInfo.tokenType
    }

    void lookAheadAndSkip(TokenType expectedType) {
        // get next token
        int nowLineNum = this.lineNum
        TokenInfo tokenInfo = this.getNextToken()
        // not is expected type, reverse cursor
        if (tokenInfo.tokenType != expectedType) {
            this.lineNum = nowLineNum

            this.nextTokenInfo = tokenInfo
        }
    }

    // return content before token
    String scanBeforeToken(String token) {
        int index = this.sourceCode.indexOf(token)

        if (index < -1) {
            throw new LexerException("unreachable!")
        }

        String result = this.sourceCode.substring(0, index)

        this.skipSourceCode(index)

        return result
    }
}

