package com.example.customermanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class HelloController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> provinceComboBox;
    @FXML private Label statusLabel;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> provinceColumn;

    // ObservableList storing all customer entries
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Populate the Province dropdown
        provinceComboBox.getItems().addAll(
                "Central", "Copperbelt", "Eastern", "Luapula",
                "Lusaka", "Muchinga", "Northern", "North-Western",
                "Southern", "Western"
        );

        // Bind table columns to Customer property getters
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

        // Attach list to TableView
        customerTable.setItems(customerList);
    }

    @FXML
    private void handleAddCustomer() {
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String province = provinceComboBox.getValue();

        // Input Validation
        if (name.isEmpty()) {
            statusLabel.setText("Validation Error: Name cannot be empty.");
            return;
        }

        if (province == null || province.isEmpty()) {
            statusLabel.setText("Validation Error: Please select a province.");
            return;
        }

        // Add Customer
        customerList.add(new Customer(name, province));
        statusLabel.setText(""); // Clear error message

        // Clear input fields
        nameField.clear();
        provinceComboBox.setValue(null);
    }

    @FXML
    private void handleDeleteCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            statusLabel.setText("Validation Error: Select a customer to delete.");
            return;
        }

        // Confirm Deletion Alert
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete customer '" + selectedCustomer.getName() + "'?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            customerList.remove(selectedCustomer);
            statusLabel.setText("");
        }
    }
}