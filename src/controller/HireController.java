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

public class HireController extends Controller {
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
    private Button hireButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private ComboBox<String> priodComboBox;

    @FXML
    private TextField userPhone;

    @FXML
    private Button goToHiredListButton;

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
        loadBasicPriodTime(priodComboBox);
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
        hireButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hire();
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadDashboardScene();
            }
        });

        goToHiredListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadHiredVehiclesScene();
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

    private void loadBasicPriodTime(ComboBox<String> priodComboBox) {
        priodComboBox.getItems().add("1 Day");
        priodComboBox.getItems().add("1 Week");
        priodComboBox.getItems().add("1 Month");
        priodComboBox.getItems().add("1 Year");
        priodComboBox.setValue("1 Day");
    }

    private void hire() {
        DbConnection db = new DbConnection();
        db.hireVehicle(tbData.getSelectionModel().getSelectedItem().getId(), userPhone.getText(), priodComboBox.getValue());
        db.close();
    }
    private void loadHiredVehiclesScene() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/HiredVehicles.fxml"));
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

