package expression.exceptions;

class ExpressionString {
    private String s;
    private int pos;

    @Override
    public String toString() {
        String out = s + '\n' +
                "-".repeat(Math.max(0, pos)) +
                '^' +
                "-".repeat(Math.max(0, s.length() - (pos + 1)));
        return (isEmpty() ? "end of string" : s.substring(pos)) + "\n" + out;
    }

    ExpressionString(String s) {
        this.s = s;
        this.pos = 0;
        skip();
    }

    char first() {
        return s.charAt(pos);
    }

    char second() {
        return s.charAt(pos + 1);
    }

    void skip() {
        while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) {
            pos++;
        }
    }

    void removeFirst() {
        pos++;
        skip();
    }

    void removeFirstWithoutSkip() {
        pos++;
    }

    void removeFirst(int len) {
        pos += len;
        skip();
    }

    boolean isEmpty() {
        return pos >= s.length();
    }

    boolean hasTwo() {
        return pos + 1 < s.length();
    }

    boolean contains(String operator) {
        return has(operator) && (pos + operator.length() == s.length() ||
                !Character.isLetter(s.charAt(pos + operator.length())));
    }

    private boolean has(String operator) {
        int right = pos + operator.length();
        return right <= s.length() && s.substring(pos, right).equals(operator);
    }
}
