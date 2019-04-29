package expression.expressions;

import expression.operations.NumberException;
import expression.operations.Operation;

public class Negate<T> extends AbstractOperator<T> implements TripleExpression<T> {

    public Negate(TripleExpression<T> first, Operation<T> o) {
        super(first, first, o);
    }

    public T operator(T a, T b) throws NumberException {
        return operation.neg(a);
    }
}