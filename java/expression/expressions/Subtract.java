package expression.expressions;

import expression.operations.NumberException;
import expression.operations.Operation;

public class Subtract<T> extends AbstractOperator<T> implements TripleExpression<T> {

    public Subtract(TripleExpression<T> first, TripleExpression<T> second, Operation<T> o) {
        super(first, second, o);
    }

    public T operator(T a, T b) throws NumberException {
        return operation.sub(a, b);
    }
}