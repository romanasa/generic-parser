package expression.operations;

import java.math.BigInteger;

public class BigIntegerOperation implements Operation<BigInteger> {
    public BigInteger add(BigInteger x, BigInteger y) throws NumberException {
        return x.add(y);
    }

    public BigInteger sub(BigInteger x, BigInteger y) throws NumberException {
        return x.subtract(y);
    }

    public BigInteger mul(BigInteger x, BigInteger y) throws NumberException {
        return x.multiply(y);
    }

    private void checkZero(BigInteger a, String message) throws NumberException {
        if (a.equals(BigInteger.ZERO)) {
            throw new NumberException(message);
        }
    }

    private void checkPositive(BigInteger a, String message) throws NumberException {
        if (a.compareTo(BigInteger.ZERO) <= 0) {
            throw new NumberException(message);
        }
    }

    public BigInteger div(BigInteger x, BigInteger y) throws NumberException {
        checkZero(y, "Division by zero: (" + x + ") / (" + y + ")");
        return x.divide(y);
    }

    public BigInteger mod(BigInteger x, BigInteger y) throws NumberException {
        checkPositive(y, "Module by non positive number: (" + x + ") % (" + y + ")");
        BigInteger result = x.remainder(y);
        if (result.compareTo(BigInteger.ZERO) < 0) {
            result = result.add(y);
        }
        return result;
    }

    public BigInteger abs(BigInteger x) throws NumberException {
        return x.abs();
    }

    public BigInteger square(BigInteger x) throws NumberException {
        return x.multiply(x);
    }

    public BigInteger neg(BigInteger x) throws NumberException {
        return x.negate();
    }

    public BigInteger parseNumber(String s) throws NumberException {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            throw new NumberException("Wrong BigInteger number: " + s);
        }
    }
}
