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

    }


}
