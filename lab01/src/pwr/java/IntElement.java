package pwr.java;

public class IntElement implements IElement {
    String name;
    int value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getValue() {
        return (float) value;
    }

    IntElement(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
