package app.common;

public record Foo2(String foo) {
    @Override
    public String toString() {
        return "Foo1 [" + this.foo + "]";
    }
}
