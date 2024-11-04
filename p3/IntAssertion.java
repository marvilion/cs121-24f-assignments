public class IntAssertion {
    private final int value;

    public IntAssertion(int value) {
        this.value = value;
    }

    public IntAssertion isEqualTo(int expected) {
        if (value != expected) {
            throw new AssertionError("Expected " + value + " to be equal to " + expected);
        }
        return this;
    }

    public IntAssertion isLessThan(int expected) {
        if (value >= expected) {
            throw new AssertionError("Expected " + value + " to be less than " + expected);
        }
        return this;
    }

    public IntAssertion isGreaterThan(int expected) {
        if (value <= expected) {
            throw new AssertionError("Expected " + value + " to be greater than " + expected);
        }
        return this;
    }
}