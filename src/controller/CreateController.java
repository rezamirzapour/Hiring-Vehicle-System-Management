package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Garage;
import model.vehicle.Bus;
import model.vehicle.Machine;
import model.vehicle.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateController extends Controller {

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
    private Button createButton;

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

    public void initialize(URL location, ResourceBundle resources) {
        filterComboBox.getItems().add("All");
        filterComboBox.setValue("All");
        vehicleType.setValue("Machine");
        loadBasicType(vehicleType);
        loadBasicType(filterComboBox);
        loadGarageSelector(garageSelector);
        loadVehicles(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue(), tbData, id, model, factory, createYear, description);
        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue(), tbData, id, model, factory, createYear, description);
            }
        });
        garageSelector.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue(), tbData, id, model, factory, createYear, description);
            }
        });
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbConnection db = new DbConnection();
                db.insertVehicle(
                        modelTextField.getText(),
                        factoryTextField.getText(),
                        Integer.parseInt(createTextField.getText()),
                        descriptionTextField.getText(),
                        vehicleType.getSelectionModel().getSelectedItem(),
                        garageSelector.getSelectionModel().getSelectedItem()
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