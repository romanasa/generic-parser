package expression.exceptions;
import expression.expressions.*;

class Result<T> {
    TripleExpression<T> accumulator;
    ExpressionString rest;

    Result(TripleExpression<T> accumulator, ExpressionString rest) {
        this.accumulator = accumulator;
        this.rest = rest;
    }
}
