package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.vehicle.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private TableView<Vehicle> tbData;

    @FXML
    private TableColumn<Vehicle, Integer > id;

    @FXML
    private TableColumn<Vehicle, String> model;

    @FXML
    private TableColumn<Vehicle, String> factory;

    @FXML
    private TableColumn<Vehicle, Integer> createYear;

    @FXML
    private TableColumn<Vehicle, String> description;

    public void initialize(URL location, ResourceBundle resources) {
        loadVehicles();
        loadFromFile();
    }

    private ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList(
            new Vehicle("Pride", "factory1", 2016, "Description1"),
            new Vehicle("Tiba", "factory2", 2017, "Description2"),
            new Vehicle("Tondar", "factory3", 2018, "Description3"),
            new Vehicle("Jack", "factory4", 2019, "Description4")

    );


    private void loadVehicles()
    {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        createYear.setCellValueFactory(new PropertyValueFactory<>("createYear"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbData.setItems(vehiclesList);
    }

    private void loadFromFile() {

    }

}
