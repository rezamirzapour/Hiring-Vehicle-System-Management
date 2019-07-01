package controller;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.vehicle.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController extends Controller {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> model;

    @FXML
    private TableColumn<?, ?> factory;

    @FXML
    private TableColumn<?, ?> createYear;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableView<Vehicle> tbData;

    @FXML
    private ComboBox<Integer> garageSelector;

    @FXML
    private Button editButton;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField factoryTextField;

    @FXML
    private TextField createTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox<String> vehicleType;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Label busCount;

    @FXML
    private Label machineCount;

    @FXML
    private Label lorryCount;

    @FXML
    private Label motorCount;

    public void initialize(URL location, ResourceBundle resources) {
        filterComboBox.getItems().add("All");
        filterComboBox.setValue("All");
        vehicleType.setValue("Machine");
        loadBasicType(vehicleType);
        loadBasicType(filterComboBox);
        loadGarageSelector(garageSelector);
        loadVehicles(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue(), tbData, id, model, factory, createYear, description, busCount, machineCount, lorryCount, motorCount);
        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue(), tbData, id, model, factory, createYear, description, busCount, machineCount, lorryCount, motorCount);
            }
        });
        garageSelector.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue(), tbData, id, model, factory, createYear, description, busCount, machineCount, lorryCount, motorCount);
            }
        });
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbConnection db = new DbConnection();
                db.updateVehicle(
                        tbData.getSelectionModel().getSelectedItem().getId(),
                        modelTextField.getText(),
                        factoryTextField.getText(),
                        Integer.parseInt(createTextField.getText()),
                        descriptionTextField.getText(),
                        vehicleType.getSelectionModel().getSelectedItem()
                );
                db.close();
                loadDashboardScene();
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadDashboardScene();
            }
        });
    }

    private void loadDashboardScene() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Dashboard.fxml"));
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
