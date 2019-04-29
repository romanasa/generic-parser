package expression.operations;

public class ByteOperation implements Operation<Byte> {

    public Byte add(Byte x, Byte y) throws NumberException {
        return (byte)(x + y);
    }

    public Byte sub(Byte x, Byte y) throws NumberException {
        return (byte)(x - y);
    }

    public Byte mul(Byte x, Byte y) throws NumberException {
        return (byte)(x * y);
    }

    private void checkZero(byte a, String message) throws NumberException {
        if (a == 0) {
            throw new NumberException(message);
        }
    }

    public Byte div(Byte x, Byte y) throws NumberException {
        checkZero(y, "Division by zero: (" + x + ") / (" + y + ")");
        return (byte)(x / y);
    }

    public Byte mod(Byte x, Byte y) throws NumberException {
        checkZero(y, "Division by zero: (" + x + ") % (" + y + ")");
        return (byte)(x % y);
    }

    public Byte abs(Byte x) throws NumberException {
        return (byte)Math.abs(x);
    }

    public Byte square(Byte x) throws NumberException {
        return (byte)(x * x);
    }

    public Byte neg(Byte x) throws NumberException {
        return (byte)-x;
    }

    public Byte parseNumber(String s) throws NumberException {
        try {
            return (byte)Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new NumberException("Wrong Byte number: " + s);
        }
    }
}
