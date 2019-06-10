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

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
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

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                desroyVehicle(garages.get(Integer.parseInt(garageSelector.getValue().toString())).getVehicles(), tbData.getSelectionModel().getSelectedItem().getId());
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


    private void loadBasicType() {
        vehicleType2.getItems().add("Machine");
        vehicleType2.getItems().add("Motor");
        vehicleType2.getItems().add("Bus");
        vehicleType2.getItems().add("Lorry");
    }


    private void loadVehicles(Garage garage) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        createYear.setCellValueFactory(new PropertyValueFactory<>("createYear"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        for (int i = 0; i < garage.getVehicles().size(); i++) {
            tbData.getItems().add(garage.getVehicles().get(i));
        }
        // Set Default Selected Row
        tbData.getSelectionModel().selectFirst();
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
        garageSelector.setValue(0);
    }

    private void desroyVehicle(List<Vehicle> vehicles, int id) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId() == id) {
                vehicles.remove(i);
                break;
            }
        }
        clearTableData(tbData);
        loadVehicles(garages.get(Integer.parseInt(garageSelector.getValue().toString())));
    }

    private void loadAddVehicleScene() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("view/AddVehicle.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
}
