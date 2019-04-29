package expression.expressions;

import expression.exceptions.ParseException;
import expression.operations.NumberException;
import expression.operations.Operation;

public abstract class AbstractOperator<T> implements TripleExpression<T> {

    private TripleExpression<T> first, second;
    protected Operation<T> operation;

    public AbstractOperator(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    public abstract T operator(T first, T second) throws NumberException, ParseException;

    public T evaluate(T x, T y, T z) throws NumberException, ParseException {
        return operator(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}