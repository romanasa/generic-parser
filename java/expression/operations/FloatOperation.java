package expression.operations;

public class DoubleOperation implements Operation<Double> {

    public Double add(Double x, Double y) throws NumberException {
        return x + y;
    }

    public Double sub(Double x, Double y) throws NumberException {
        return x - y;
    }

    public Double mul(Double x, Double y) throws NumberException {
        return x * y;
    }

    public Double div(Double x, Double y) throws NumberException {
        return x / y;
    }

    public Double mod(Double x, Double y) throws NumberException {
        return x % y;
    }

    public Double abs(Double x) throws NumberException {
        return Math.abs(x);
    }

    public Double square(Double x) throws NumberException {
        return x * x;
    }

    public Double neg(Double x) throws NumberException {
        return -x;
    }

    public Double parseNumber(String s) throws NumberException {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new NumberException("Wrong Double number: " + s);
        }
    }
}
