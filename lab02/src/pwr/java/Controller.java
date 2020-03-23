package pwr.java;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller implements Initializable {
    ObservableList<IElement> contextList = FXCollections.observableArrayList();
    PigeonholeSort pigeonholeSort = new PigeonholeSort();
    QuickSort quickSort = new QuickSort();
    SelectionSort selectionSort = new SelectionSort();
    ObservableList<String> algAllOptions = FXCollections.observableArrayList(pigeonholeSort.getDescription(), quickSort.getDescription(), selectionSort.getDescription());
    ObservableList<String> algFloatOptions = FXCollections.observableArrayList(quickSort.getDescription(), selectionSort.getDescription());
    Boolean isListInteger = true;
    SimpleDateFormat dateFormat = new SimpleDateFormat(Main.activeBundle.getString("dateFormat"));

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
    ComboBox algSelect;
    @FXML
    MenuItem menuExit = new MenuItem();
    @FXML
    MenuItem menuEnglishUS = new MenuItem();
    @FXML
    MenuItem menuEnglishGB = new MenuItem();
    @FXML
    MenuItem menuPolish = new MenuItem();
    @FXML
    Label dateLabel = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAlgOptions();
        dateLabel.setText(dateFormat.format(new Date()));
    }

    void refreshView(List<IElement> input) {
        listView.setItems(ElementPrinter.listToView(input));
        listView.refresh();
        setAlgOptions();
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
        if (!(value == Math.floor(value))) {
            isListInteger = false;
        }
        refreshView(contextList);
    }

    public void setAlgOptions() {
        if (isListInteger == true) {
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

}
