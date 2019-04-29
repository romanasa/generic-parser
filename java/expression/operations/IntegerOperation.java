package expression.operations;

public class IntegerOperation implements Operation<Integer> {

    private boolean needCheck;

    public IntegerOperation(boolean needCheck) {
        this.needCheck = needCheck;
    }


    public Integer add(Integer x, Integer y) throws NumberException {
        if (needCheck) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkAdd(Integer x, Integer y) throws NumberException {
        if (x < 0 && y < 0 && Integer.MIN_VALUE - x > y) {
            throw new NumberException("Integer overflow: (" + x + ") + (" + y + ")");
        }
        if (x > 0 && y > 0 && x > Integer.MAX_VALUE - y) {
            throw new NumberException("Integer overflow: (" + x + ") + (" + y + ")");
        }
    }

    public Integer sub(Integer x, Integer y) throws NumberException {
        if (needCheck) {
            checkSubtract(x, y);
        }
        return x - y;
    }

    private void checkSubtract(Integer x, Integer y) throws NumberException {
        if (y == Integer.MIN_VALUE) {
            if (x >= 0) {
                throw new NumberException("Integer overflow: (" + x + ") - (" + y + ")");
            }
        } else {
            if (x < 0 && -y < 0 && Integer.MIN_VALUE - x > -y) {
                throw new NumberException("Integer overflow: (" + x + ") - (" + y + ")");
            }
            if (x > 0 && -y > 0 && x > Integer.MAX_VALUE + y) {
                throw new NumberException("Integer overflow: (" + x + ") - (" + y + ")");
            }
        }
    }

    public Integer mul(Integer x, Integer y) throws NumberException {
        if (needCheck) {
            checkMultiply(x, y);
        }
        return x * y;
    }

    private void checkMultiply(Integer x, Integer y) throws NumberException {
        if (x > y) {
            int t = x ^ y;
            x = t ^ x;
            y = t ^ x;
        }
        if (x == 0 || y == 0 || x == 1) {
            return;
        }
        if (y == -1) {
            if (x == Integer.MIN_VALUE) {
                throw new NumberException("Integer overflow: (" + x + ") * (" + y + ")");
            } else {
                return;
            }
        }
        if (x * y / y != x) {
            throw new NumberException("Integer overflow: (" + x + ") * (" + y + ")");
        }
    }

    private void checkZero(int a, String message) throws NumberException {
        if (a == 0) {
            throw new NumberException(message);
        }
    }

    public Integer div(Integer x, Integer y) throws NumberException {
        checkZero(y, "Division by zero: (" + x + ") / (" + y + ")");
        if (needCheck) {
            checkDivide(x, y);
        }
        return x / y;
    }

    private void checkDivide(Integer x, Integer y) throws NumberException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new NumberException("Integer overflow: (" + x + ") * (" + y + ")");
        }
    }

    public Integer mod(Integer x, Integer y) throws NumberException {
        checkZero(y, "Division by zero: (" + x + ") % (" + y + ")");
        return x % y;
    }

    public Integer abs(Integer x) throws NumberException {
        if (needCheck) {
            checkAbs(x);
        }
        return Math.abs(x);
    }

    private void checkAbs(Integer x) throws NumberException {
        if (x == Integer.MIN_VALUE) {
            throw new NumberException("Integer overflow: abs(" + x + ")");
        }
    }

    public Integer square(Integer x) throws NumberException {
        if (needCheck) {
            checkSquare(x);
        }
        return x * x;
    }

    private void checkSquare(Integer x) throws NumberException {
        if (x == 0 || x == 1) {
            return;
        }
        if (x * x / x != x) {
            throw new NumberException("Integer overflow: square (" + x + ")");
        }
    }


    public Integer neg(Integer x) throws NumberException {
        if (needCheck) {
            checkNegate(x);
        }
        return -x;
    }

    private void checkNegate(Integer x) throws NumberException {
        if (x == Integer.MIN_VALUE) {
            throw new NumberException("Integer overflow: -(" + x + ")");
        }
    }

    public Integer parseNumber(String s) throws NumberException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new NumberException("Wrong Integer number: " + s);
        }
    }
}
