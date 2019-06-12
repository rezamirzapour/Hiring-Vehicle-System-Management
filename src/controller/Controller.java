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

public class Controller implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    protected void loadVehicles(int garageId, TableView<Vehicle> tbData, TableColumn<?, ?> id, TableColumn<?, ?> model, TableColumn<?, ?> factory, TableColumn<?, ?> createYear, TableColumn<?, ?> description) {
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

    protected void clearTableData(TableView<Vehicle> tbData) {
        tbData.getItems().clear();
    }

    protected void loadGarageSelector(ComboBox<Integer> garageSelector) {
        DbConnection db = new DbConnection();
        for (int i = 0; i < db.getGarageIds().size(); i++) {
            garageSelector.getItems().add(db.getGarageIds().get(i));
        }
        garageSelector.setValue(1);
        db.close();
    }

    protected void loadBasicType(ComboBox<String> vehicleType) {
        vehicleType.getItems().add("Machine");
        vehicleType.getItems().add("Motor");
        vehicleType.getItems().add("Bus");
        vehicleType.getItems().add("Lorry");
    }
}