package utils;

public class GenericPair {
    private Object a, b;

    public GenericPair(Object a, Object b) {

    }

    public Object zetFirst() {
        return a;
    }

    public Object getSecond() {
        return b;
    }

    public void setFirst(Object a) {
        this.a = a;
    }

    public void setSecond(Object b) {
        this.b = b;
    }

    // region Testing
    public static void main(String[] args) {
        GenericPair gPair1 = new GenericPair("New York", "Albany");
        GenericPair gPair2 = new GenericPair("Kyle Lee", 11);
    }
    // endregion
}