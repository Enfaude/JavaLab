package pwr.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ElementPrinter {
    public static void printList(List<IElement> input) {
        System.out.println("List:");
        for (IElement element : input) {
            printElement(element);
        }
    }

    public static void printIntList(List<IntElement> input) {
        System.out.println("List:");
        for (IElement element : input) {
            printElement(element);
        }
    }

    public static void printElement(IElement element) {
        System.out.println(elementToString(element));
    }

    public static String elementToString(IElement element) {
        StringBuilder sb = new StringBuilder();
        sb.append(Main.activeBundle.getString("nameLabel.text")).append(element.getName()).append(" | ").append(Main.activeBundle.getString("valueLabel.text")).append(element.getValue());
        return sb.toString();
    }

    public static ObservableList<String> listToView(List<IElement> input) {
        ObservableList<String> result = FXCollections.observableArrayList();
        for (IElement element : input) {
            result.add(elementToString(element));
        }
        return result;
    }

}
