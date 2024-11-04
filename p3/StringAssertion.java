public class StringAssertion extends ObjectAssertion {
    private final String string;

    public StringAssertion(String string) {
        super(string);
        this.string = string;
    }

    public StringAssertion startsWith(String prefix) {
        if (!string.startsWith(prefix)) {
            throw new AssertionError("Expected string to start with " + prefix + ", but was " + string);
        }
        return this;
    }

    public StringAssertion isEmpty() {
        if (!string.isEmpty()) {
            throw new AssertionError("Expected string to be empty, but was " + string);
        }
        return this;
    }

    public StringAssertion contains(String substring) {
        if (!string.contains(substring)) {
            throw new AssertionError("Expected string to contain " + substring + ", but was " + string);
        }
        return this;
    }
}