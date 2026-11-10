package app.common;

public record Foo1(String foo) {
    @Override
    public String toString() {
        return "Foo1 [" + this.foo + "]";
    }
}
