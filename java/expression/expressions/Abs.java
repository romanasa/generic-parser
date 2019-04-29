package expression.expressions;

import expression.operations.NumberException;
import expression.operations.Operation;

public class Square<T> extends AbstractOperator<T> implements TripleExpression<T> {

    public Square(TripleExpression<T> first, Operation<T> o) {
        super(first, first, o);
    }

    public T operator(T a, T b) throws NumberException {
        return operation.square(a);
    }
}