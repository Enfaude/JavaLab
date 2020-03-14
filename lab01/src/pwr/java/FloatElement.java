package pwr.java;

public class FloatElement implements IElement {
    String name;
    float value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getValue() {
        return value;
    }

    FloatElement(String name, float value) {
        this.name = name;
        this.value = value;
    }
}
