/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;

/**
 *
 * @author ncm
 */
//public class Lexical_Analyzer {
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class  Lexical_Analyzer {

    public static enum TokenType {

        // Token types cannot have underscores
        Keyword("abstract|boolean|byte|case|catch|char|class|continue|default|do|double|else|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while|false|true|null+"),
        Comment("[//|/*|*/]+-?[ _a-zA-Z0-9]+|a-zA-Z0-9]+[*/]"), Integer("-?[0-9]+ "), Identifier("[ _a-zA-Z0-9]+|a-zA-Z0-9]+ null"), Operator("[*|/|+|-|%|=|&||]+[*|/|+|-|%|=|&||]"),
        Specialcharacter("[*|{|}|(|)|;|,]"), WHITESPACE(null);

        public final String pattern;

        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }

    public static class Token {

        public TokenType type;
        public String data;

        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("(%s : %s)", type.name(), data);
        }
    }

    public static ArrayList<Token> lex(String input) {
        // The tokens to return
        ArrayList<Token> tokens = new ArrayList<Token>();

        // Lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values()) {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            if (matcher.group(TokenType.Integer.name()) != null) {
                tokens.add(new Token(TokenType.Integer, matcher.group(TokenType.Integer.name())));
                continue;
            } else if (matcher.group(TokenType.Identifier.name()) != null) {
                tokens.add(new Token(TokenType.Identifier, matcher.group(TokenType.Identifier.name())));
                continue;
            } else if (matcher.group(TokenType.Operator.name()) != null) {
                tokens.add(new Token(TokenType.Operator, matcher.group(TokenType.Operator.name())));
                continue;
            } else if (matcher.group(TokenType.Keyword.name()) != null) {
                tokens.add(new Token(TokenType.Keyword, matcher.group(TokenType.Keyword.name())));
                continue;
            } else if (matcher.group(TokenType.Specialcharacter.name()) != null) {
                tokens.add(new Token(TokenType.Specialcharacter, matcher.group(TokenType.Specialcharacter.name())));
                continue;
            } else if (matcher.group(TokenType.Comment.name()) != null) {
                tokens.add(new Token(TokenType.Comment, matcher.group(TokenType.Comment.name())));
                continue;
            } else if (matcher.group(TokenType.WHITESPACE.name()) != null) {
                tokens.add(new Token(TokenType.WHITESPACE, matcher.group(TokenType.WHITESPACE.name())));
                continue;

            }
        }

        return tokens;
    }
}