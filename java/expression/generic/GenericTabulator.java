package expression.generic;

import expression.BaseTest;
import expression.exceptions.ExpressionParser;
import expression.exceptions.ParseException;
import expression.expressions.TripleExpression;
import expression.operations.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {

    private Map<String, Operation<?>> modes = Map.of(
        "i", new IntegerOperation(true),
            "d", new DoubleOperation(),
            "bi", new BigIntegerOperation(),
            "u", new IntegerOperation(false),
            "f", new FloatOperation(),
            "b", new ByteOperation()
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return tab(expression, x1, x2, y1, y2, z1, z2, modes.get(mode));
    }

    private <T> Object[][][] tab(String expression, int x1, int x2, int y1, int y2, int z1, int z2, Operation<T> op) {
        Object[][][] values = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];

        try {
            TripleExpression<T> cur = new ExpressionParser<>(op).parse(expression);


            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    for (int z = z1; z <= z2; z++) {
                        try {
                            values[x - x1][y - y1][z - z1] = cur.evaluate(op.parseNumber(Integer.toString(x)),
                                    op.parseNumber(Integer.toString(y)),
                                    op.parseNumber(Integer.toString(z)));
                        } catch (NumberException e) {
                            values[x - x1][y - y1][z - z1] = null;
                        }
                    }
                }
            }

        } catch (ParseException | NumberException e) {
            System.out.println(e.getMessage());
        }
        return values;
    }
}
