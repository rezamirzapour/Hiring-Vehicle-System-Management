package controller;

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
import model.vehicle.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML
    private TableColumn<?, ?> factory;

    @FXML
    private TableColumn<?, ?> createYear;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TextField modelTextField;

    @FXML
    private TableView<?> tbData;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField factoryTextField11;

    @FXML
    private ComboBox<?> garageSelector;

    @FXML
    private Button createButton;

    @FXML
    private TableColumn<?, ?> model;

    @FXML
    private TextField factoryTextField1;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox<?> vehicleType;


    public void initialize(URL location, ResourceBundle resources) {
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
