package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.vehicle.*;
import database.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Vehicle> tbData;

    @FXML
    private TableColumn<Vehicle, Integer> id;

    @FXML
    private TableColumn<Vehicle, String> model;

    @FXML
    private TableColumn<Vehicle, String> factory;

    @FXML
    private TableColumn<Vehicle, Integer> createYear;

    @FXML
    private TableColumn<Vehicle, String> description;

    @FXML
    private ComboBox<Integer> garageSelector;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button toExcelButton;

    @FXML
    private Button toPdfButton;

    @FXML
    private Button hireButton;

    @FXML
    private Label busCount;

    @FXML
    private Label machineCount;

    @FXML
    private Label lorryCount;

    @FXML
    private Label motorCount;

    public void initialize(URL location, ResourceBundle resources) {
        busCount.setText("0");
        machineCount.setText("0");
        lorryCount.setText("0");
        motorCount.setText("0");
        loadBasicType();
        loadVehicles(0);
        loadGarageSelector();


        garageSelector.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()));
            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbConnection db = new DbConnection();
                db.deleteVehicle(tbData.getSelectionModel().getSelectedItem().getId());
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()));
                db.close();
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadAddVehicleScene();
            }
        });
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadAddEditScene();
            }
        });

        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadVehicles(Integer.parseInt(garageSelector.getValue().toString()));
            }
        });
        toPdfButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbConnection db = new DbConnection();
                db.toPdf(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue());

            }
        });
        toExcelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbConnection db = new DbConnection();
                db.toExcel(Integer.parseInt(garageSelector.getValue().toString()), filterComboBox.getValue());
            }
        });
        hireButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadHireScene();
            }
        });
    }

    private void loadBasicType() {
        filterComboBox.getItems().add("All");
        filterComboBox.getItems().add("Machine");
        filterComboBox.getItems().add("Motor");
        filterComboBox.getItems().add("Bus");
        filterComboBox.getItems().add("Lorry");
        filterComboBox.setValue("All");
    }

    private void loadVehicles(int garageId) {
        int busCounter = 0;
        int machineCounter = 0;
        int lorryCounter = 0;
        int motorCounter = 0;

        clearTableData(tbData);
        DbConnection db = new DbConnection();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        createYear.setCellValueFactory(new PropertyValueFactory<>("createYear"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        for (int i = 0; i < db.getAllVehicle(garageId, filterComboBox.getValue()).size(); i++) {
            tbData.getItems().add(db.getAllVehicle(garageId, filterComboBox.getValue()).get(i));
            if (db.getAllVehicle(garageId, filterComboBox.getValue()).get(i) instanceof Bus) {
                busCounter++;
            } else if (db.getAllVehicle(garageId, filterComboBox.getValue()).get(i) instanceof Machine)
                machineCounter++;
            else if (db.getAllVehicle(garageId, filterComboBox.getValue()).get(i) instanceof Lorry) lorryCounter++;
            else if (db.getAllVehicle(garageId, filterComboBox.getValue()).get(i) instanceof Motor) motorCounter++;
        }
        machineCount.setText(String.valueOf(machineCounter));
        busCount.setText(String.valueOf(busCounter));
        lorryCount.setText(String.valueOf(lorryCounter));
        motorCount.setText(String.valueOf(motorCounter));
        // Set Default Selected Row
        tbData.getSelectionModel().selectFirst();
        db.close();
    }

    private void clearTableData(TableView<Vehicle> tbData) {
        tbData.getItems().clear();
    }

    private void loadGarageSelector() {
        garageSelector.getItems().add(0);
        DbConnection db = new DbConnection();
        for (int i = 0; i < db.getGarageIds().size(); i++) {
            garageSelector.getItems().add(db.getGarageIds().get(i));
        }
        garageSelector.setValue(0);
        db.close();
    }

    private void destroyVehicle(List<Vehicle> vehicles, int id) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId() == id) {
                vehicles.remove(i);
                break;
            }
        }
        clearTableData(tbData);
        loadVehicles(Integer.parseInt(garageSelector.getValue().toString()));
    }

    private void loadAddVehicleScene() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Create.fxml"));
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAddEditScene() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Edit.fxml"));
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHireScene() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Hire.fxml"));
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
