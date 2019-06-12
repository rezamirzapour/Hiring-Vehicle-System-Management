package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Garage;
import model.vehicle.Vehicle;
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
    private ComboBox<String> vehicleType2;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    public void initialize(URL location, ResourceBundle resources) {

        loadBasicType();
        loadVehicles(1);
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


    }


    private void loadBasicType() {
        vehicleType2.getItems().add("Machine");
        vehicleType2.getItems().add("Motor");
        vehicleType2.getItems().add("Bus");
        vehicleType2.getItems().add("Lorry");
    }


    private void loadVehicles(int garageId) {
        clearTableData(tbData);
        DbConnection db = new DbConnection();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        createYear.setCellValueFactory(new PropertyValueFactory<>("createYear"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        for (int i = 0; i < db.getAllVehicle().size(); i++) {
            if (db.getAllVehicle().get(i).getGarageId() == garageId)
            tbData.getItems().add(db.getAllVehicle().get(i));
        }
        // Set Default Selected Row
        tbData.getSelectionModel().selectFirst();
        db.close();
    }

    private void clearTableData(TableView<Vehicle> tbData) {
        tbData.getItems().clear();
    }

    private void loadFromFile() {

    }

    private void loadGarageSelector() {
        DbConnection db = new DbConnection();
        for (int i = 0; i < db.getGarageIds().size(); i++) {
            garageSelector.getItems().add(db.getGarageIds().get(i));
        }
        garageSelector.setValue(1);
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
}
