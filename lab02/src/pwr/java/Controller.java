package pwr.java;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller implements Initializable {
    ObservableList<IElement> contextList = Main.contextList;
    PigeonholeSort pigeonholeSort = new PigeonholeSort();
    QuickSort quickSort = new QuickSort();
    SelectionSort selectionSort = new SelectionSort();
    ObservableList<String> algAllOptions = FXCollections.observableArrayList(pigeonholeSort.getDescription(), quickSort.getDescription(), selectionSort.getDescription());
    ObservableList<String> algFloatOptions = FXCollections.observableArrayList(quickSort.getDescription(), selectionSort.getDescription());
    Boolean isListInteger = true;
    SimpleDateFormat dateFormat = new SimpleDateFormat(Main.defaultBundle.getString("dateFormat"));

    @FXML
    ListView<String> listView;
    @FXML
    TextField nameField = new TextField();
    @FXML
    TextField valueField = new TextField();
    @FXML
    Button addBtn = new Button();
    @FXML
    Button sortBtn = new Button();
    @FXML
    Button clearListBtn = new Button();
    @FXML
    ComboBox<String> algSelect;
    @FXML
    MenuItem menuExit = new MenuItem();
    @FXML
    Label countElementsLabel = new Label();
    @FXML
    Label dateLabel = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAlgOptions();
        dateLabel.setText(dateFormat.format(new Date()));
        countElementsLabel.setText(contextList.size() + formatCount());
        refreshView(contextList);
    }

    public void refreshView(List<IElement> input) {
        listView.setItems(ElementPrinter.listToView(input));
        listView.refresh();
        setAlgOptions();
        countElementsLabel.setText(formatCount());
    }

    public void clearContextList() {
        contextList.clear();
        isListInteger = true;
        refreshView(contextList);
    }

    public void addToContextList() {
        float value = Float.parseFloat(valueField.getText());
        contextList.add(new FloatElement(nameField.getText(), value));
        //check if value is integer
        if (value != Math.floor(value)) {
            isListInteger = false;
        }
        refreshView(contextList);
    }

    public void setAlgOptions() {
        if (isListInteger) {
            algSelect.setItems(algAllOptions);
        } else {
            algSelect.setItems(algFloatOptions);
        }
    }

    public void callSolve() {
        List<IElement> result = new ArrayList<>();
        if (algSelect.getValue().equals(pigeonholeSort.getDescription())) {
            List<IntElement> intContextList = new ArrayList<>();
            for (IElement element : contextList) {
                intContextList.add(new IntElement(element.getName(), (int) element.getValue()));
            }
            result.addAll(pigeonholeSort.solve(intContextList));
        } else if (algSelect.getValue().equals(quickSort.getDescription())) {
            result.addAll(quickSort.solveIElement(contextList));
        } else if (algSelect.getValue().equals(selectionSort.getDescription())) {
            result.addAll(selectionSort.solveIElement(contextList));
        }
        refreshView(result);
    }

    public void exitProgram() {
        Platform.exit();
    }

    public void switchLocalePL() throws IOException {
        Locale.setDefault(Main.localePL);
        Main.refresh();
    }

    public void switchLocaleENUS() throws IOException {
        Locale.setDefault(Main.localeUS);
        Main.refresh();
    }

    public void switchLocaleENUK() throws IOException {
        Locale.setDefault(Main.localeUK);
        Main.refresh();
    }

    String formatCount() {
        String[] formats = {Main.defaultBundle.getString("countElementsZero.text"), Main.defaultBundle.getString("countElementsOne.text"),
                Main.defaultBundle.getString("countElementsFew.text"), Main.defaultBundle.getString("countElementsMore.text")};
        double[] boundaries = {0, 1, 2, 5};
        ChoiceFormat format = new ChoiceFormat(boundaries, formats);
        return contextList.size() + " " + format.format(contextList.size());
    }

}
