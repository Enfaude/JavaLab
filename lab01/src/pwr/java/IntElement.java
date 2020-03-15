package pwr.java;

/**
 * Class representing elements with integer values
 */
public class IntElement implements IElement {
    /**
     * name of the element
     */
    String name;
    /**
     * value of the element
     */
    int value;

    /**
     * name getter
     * @return name of the integer element
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * value getter
     * @return value of the integer element (cast to float to match interface's implementation
     */
    @Override
    public float getValue() {
        return (float) value;
    }

    /**
     * Constructor of integer element
     * @param name - name of the element
     * @param value - value of the element
     */
    IntElement(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
