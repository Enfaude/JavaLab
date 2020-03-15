package pwr.java;

/**
 * Class representing elements with float values
 */
public class FloatElement implements IElement {
    /**
     * name of the element
     */
    String name;
    /**
     * value of the element
     */
    float value;

    /**
     * name getter
     * @return name of the float element
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * value getter
     * @return value of the float element
     */
    @Override
    public float getValue() {
        return value;
    }

    /**
     * Constructor of float element
     * @param name - name of the element
     * @param value - value of the element
     */
    public FloatElement(String name, float value) {
        this.name = name;
        this.value = value;
    }
}
