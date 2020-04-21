package task2;

import task1.MyTokenizer;
import task1.Token;
import task1.Token.*;


/**
 * Name: Parser.java
 * <p>
 * The main objective of this class is to implement a simple parser.
 * It should be able to parser the following grammar rule:
 * <exp>    ::= <term> | <term> + <exp> | <term> - <exp>
 * <term>   ::=  <factor> | <factor> * <term> | <factor> / <term>
 * <factor> ::= <unsigned integer> | ( <exp> )
 *
 * @author: Ziyu Cui
 * @Uid: u7004551
 * @Date: 8/4/2020
 */

public class Parser {

    MyTokenizer _tokenizer;

    public Parser(MyTokenizer tokenizer) {
        _tokenizer = tokenizer;
    }

    /*
    <exp>    ::= <term> | <term> + <exp> | <term> - <exp>
    <term>   ::=  <factor> | <factor> * <term> | <factor> / <term>
    <factor> ::= <unsigned integer> | ( <exp> )
     */
    public Exp parseExp() {
        // TODO: Implement parse function for <exp>
        // ########## YOUR CODE STARTS HERE ##########
        Exp term = parseTerm();
        if (_tokenizer.hasNext() && _tokenizer.current().type().equals(Type.ADD)) {
            _tokenizer.next();
            Exp exp = parseExp();
            return new AddExp(term, exp);
        } else if (_tokenizer.hasNext() && _tokenizer.current().type().equals(Type.SUB)) {
            _tokenizer.next();
            Exp exp = parseExp();
            return new SubExp(term, exp);
        } else if(_tokenizer.hasNext() &&_tokenizer.current().type().equals(Type.RBRA)){
            return term;
        }else{
            return term;
        }
        // ########## YOUR CODE ENDS HERE ##########
    }

    public Exp parseTerm() {
        // TODO: Implement parse function for <term>
        // ########## YOUR CODE STARTS HERE ##########
        Exp factor = parseFactor();
        if (_tokenizer.hasNext() && _tokenizer.current().type().equals(Type.MUL)) {
            _tokenizer.next();
            Exp term = parseTerm();
            return new MultExp(factor, term);
        } else if (_tokenizer.hasNext() && _tokenizer.current().type().equals(Type.DIV)) {
            _tokenizer.next();
            Exp term = parseTerm();
            return new DivExp(factor, term);
        } else if(_tokenizer.hasNext() &&_tokenizer.current().type().equals(Type.RBRA)){
            return factor;
        }else{
            return factor;
        }

        // ########## YOUR CODE ENDS HERE ##########
    }

    public Exp parseFactor() {
        // TODO: Implement parse function for <factor>
        // ########## YOUR CODE STARTS HERE ##########
        if (_tokenizer.current().type().equals(Type.LBRA)) {
            _tokenizer.next();
            Exp exp = new Parser(_tokenizer).parseExp();
            _tokenizer.next();
            return exp;
        } else if (_tokenizer.current().type().equals(Type.RBRA)) {
            return null;
        } else {
            Exp uint = new IntExp(Integer.parseInt(_tokenizer.current().token()));
            _tokenizer.next();
            return uint;
        }


        // ########## YOUR CODE ENDS HERE ##########
    }
}
