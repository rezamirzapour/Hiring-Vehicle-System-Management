package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Garage;
import model.vehicle.Vehicle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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

    @FXML
    private ComboBox<Integer> garageSelector;


    public void initialize(URL location, ResourceBundle resources) {
        loadVehicles(garages.get(0));
        loadGarageSelector(garages);
        loadFromFile();

        garageSelector.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                clearTableData(tbData);
                loadVehicles(garages.get(Integer.parseInt(garageSelector.getValue().toString())));
            }
        });
    }


    private ObservableList<Vehicle> vehiclesList1 = FXCollections.observableArrayList(
            new Vehicle("Pride", "factory1", 2016, "Description1"),
            new Vehicle("Tiba", "factory2", 2017, "Description2"),
            new Vehicle("Tondar", "factory3", 2018, "Description3"),
            new Vehicle("Jack", "factory4", 2019, "Description4")

    );

    private ObservableList<Vehicle> vehiclesList2 = FXCollections.observableArrayList(
            new Vehicle("Tiba", "factory2", 2017, "Description2"),
            new Vehicle("Tondar", "factory3", 2018, "Description3"),
            new Vehicle("Jack", "factory4", 2019, "Description4"),
            new Vehicle("Tiba", "factory2", 2017, "Description2"),
            new Vehicle("Pride", "factory1", 2016, "Description1")
    );

    private ObservableList<Vehicle> vehiclesList3 = FXCollections.observableArrayList(
            new Vehicle("Tondar", "factory3", 2018, "Description3"),
            new Vehicle("Jack", "factory4", 2019, "Description4"),
            new Vehicle("Tiba", "factory2", 2017, "Description2"),
            new Vehicle("Pride", "factory1", 2016, "Description1"),
            new Vehicle("Tiba", "factory2", 2017, "Description2")
    );

    private ObservableList<Garage> garages = FXCollections.observableArrayList(
            new Garage(vehiclesList1),
            new Garage(vehiclesList2),
            new Garage(vehiclesList3)
    );




    private void loadVehicles(Garage garage)
    {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        createYear.setCellValueFactory(new PropertyValueFactory<>("createYear"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        for (int i = 0; i < garage.getVehicles().size(); i++) {
            tbData.getItems().add(garage.getVehicles().get(i));
        }
    }
    private void clearTableData(TableView<Vehicle> tbData) {
        tbData.getItems().clear();
    }

    private void loadFromFile() {

    }

    private void loadGarageSelector(ObservableList<Garage> garages) {
        for (int i = 0; i < garages.size(); i++) {
            garageSelector.getItems().add(i);
        }
    }

    private Garage selectedGarage(List<Garage> garages, int index) {
        return garages.get(index);
    }

}
