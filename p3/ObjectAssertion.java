public class ObjectAssertion {
    private final Object object;

    public ObjectAssertion(Object object) {
        this.object = object;
    }

    public ObjectAssertion isNotNull() {
        if (object == null) {
            throw new AssertionError("Expected object to be not null, but was null");
        }
        return this;
    }

    public ObjectAssertion isNull() {
        if (object != null) {
            throw new AssertionError("Expected object to be null, but was not null");
        }
        return this;
    }

    public ObjectAssertion isEqualTo(Object expected) {
        if (!object.equals(expected)) {
            throw new AssertionError("Expected " + object + " to be equal to " + expected);
        }
        return this;
    }

    public ObjectAssertion isNotEqualTo(Object expected) {
        if (object.equals(expected)) {
            throw new AssertionError("Expected " + object + " to not be equal to " + expected);
        }
        return this;
    }

    public ObjectAssertion isInstanceOf(Class<?> clazz) {
        if (!clazz.isInstance(object)) {
            throw new AssertionError("Expected " + object + " to be an instance of " + clazz.getName());
        }
        return this;
    }
}
