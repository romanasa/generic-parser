package expression.exceptions;

import expression.expressions.*;
import expression.expressions.Module;
import expression.operations.NumberException;
import expression.operations.Operation;

import java.text.CharacterIterator;

public class ExpressionParser<T> implements Parser<T> {

    private Operation<T> op;
    private ExpressionString expression;

    public ExpressionParser(Operation<T> op) {
        this.op = op;
    }

    public TripleExpression<T> parse(String expressionSpace) throws ParseException, NumberException {
        expression = new ExpressionString(expressionSpace);
        Result<T> result = plusMinus();
        if (!result.rest.isEmpty()) {
            throw new ParseException("Can't full parse, expected + or -, found: " + result.rest);
        }
        return result.accumulator;
    }

    private Result<T> plusMinus() throws ParseException, NumberException {
        Result<T> cur = mulDiv();
        while (!cur.rest.isEmpty()) {
            if (cur.rest.first() != '+' && cur.rest.first() != '-') {
                break;
            }
            char sign = cur.rest.first();
            cur.rest.removeFirst();

            TripleExpression<T> accumulator = cur.accumulator;
            cur = mulDiv();

            if (sign == '+') {
                cur.accumulator = new Add<T>(accumulator, cur.accumulator, op);
            } else {
                cur.accumulator = new Subtract<T>(accumulator, cur.accumulator, op);
            }
        }
        return cur;
    }

    private Result<T> mulDiv() throws ParseException, NumberException {
        Result<T> cur = unary();
        while (!cur.rest.isEmpty()) {
            if (cur.rest.first() != '*' && cur.rest.first() != '/' && !cur.rest.contains("mod")) {
                break;
            }

            if (cur.rest.contains("mod")) {
                cur.rest.removeFirst(3);
                TripleExpression<T> accumulator = cur.accumulator;
                cur = unary();
                cur.accumulator = new Module<T>(accumulator, cur.accumulator, op);
            } else {

                char sign = cur.rest.first();
                cur.rest.removeFirst();

                TripleExpression<T> accumulator = cur.accumulator;
                cur = unary();

                if (sign == '*') {
                    cur.accumulator = new Multiply<T>(accumulator, cur.accumulator, op);
                } else {
                    cur.accumulator = new Divide<T>(accumulator, cur.accumulator, op);
                }
            }
        }
        return cur;
    }

    private Result<T> unary() throws ParseException, NumberException {
        Result<T> cur;
        if (expression.hasTwo() && expression.first() == '-' && !Character.isDigit(expression.second())) {
            expression.removeFirst();
            cur = unary();
            cur.accumulator = new Negate<T>(cur.accumulator, op);
        } else if (expression.contains("square")) {
            expression.removeFirst(6);
            cur = unary();
            cur.accumulator = new Square<T>(cur.accumulator, op);
        } else if (expression.contains("abs")) {
            expression.removeFirst(3);
            cur = unary();
            cur.accumulator = new Abs<T>(cur.accumulator, op);
        } else {
            cur = bracket();
        }
        return cur;
    }

    private Result<T> bracket() throws ParseException, NumberException {
        if (expression.isEmpty()) {
            throw new ParseException("Expected value, found: " + expression);
        }
        if (expression.first() == '(') {
            expression.removeFirst();
            Result<T> cur = plusMinus();
            if (cur.rest.isEmpty() || cur.rest.first() != ')') {
                throw new ParseException("Expected close bracket found: " + cur.rest);
            }
            expression.removeFirst();
            return cur;
        }
        return variable();
    }

    private Result<T> variable() throws ParseException, NumberException {
        char name = expression.first();
        if (name == '-' || Character.isDigit(name)) {
            return num();
        }
        if (name != 'x' && name != 'y' && name != 'z') {
            if (Character.isLetter(name)) {
                throw new ParseException("Unknown variable: " + expression);
            }
            throw new ParseException("Expected value, found: " + expression);
        }
        expression.removeFirst();
        return new Result<T>(new Variable<T>(Character.toString(name)), expression);
    }

    private void readDigits(StringBuilder sb) {
        while (!expression.isEmpty() && Character.isDigit(expression.first())) {
            sb.append(expression.first());
            expression.removeFirstWithoutSkip();
        }
    }

    private Result<T> num() throws ParseException, NumberException {
        int sign = 1;

        if (expression.first() == '-') {
            sign = -1;
            expression.removeFirst();
        }
        if (expression.isEmpty()) {
            throw new ParseException("Expected digits, found: " + expression);
        }

        StringBuilder sb = new StringBuilder();
        readDigits(sb);
        expression.skip();

        T res = op.parseNumber(sb.toString());
        return new Result<T>(new Const<T>(res), expression);
    }
}
