public class BooleanAssertion {
    private final boolean actual;

    public BooleanAssertion(boolean actual) {
        this.actual = actual;
    }

    public BooleanAssertion isTrue() {
        if (!actual) {
            throw new AssertionError("Expected value to be true, but was false.");
        }
        return this;
    }

    public BooleanAssertion isFalse() {
        if (actual) {
            throw new AssertionError("Expected value to be false, but was true.");
        }
        return this;
    }

    public BooleanAssertion isEqualTo(boolean expected) {
        if (actual != expected) {
            throw new AssertionError("Expected " + actual + " to be equal to " + expected);
        }
        return this;
    }
}