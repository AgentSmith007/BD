package view;

import beans.Employee;
import connection.DataBases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeListController {

    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private Button deleteEmployeeButton;
    @FXML
    private Button addEmployeeButton;

    public void initialize() {

        try {
            initializeEmployeeTable();
            initializeEmployeeButtons();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initializeEmployeeTable() throws SQLException {
        TableColumn<Employee, String> fioColumn = new TableColumn<>("ФИО");
        TableColumn<Employee, String> phoneColumn = new TableColumn<>("Номер телефона");
        TableColumn<Employee, String> emailColumn = new TableColumn<>("E-mail");
        TableColumn<Employee, Integer> ageColumn = new TableColumn<>("Возраст");

        // Defines how to fill data for each cell.
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        fioColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fioColumn.setMinWidth(210);
        fioColumn.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {

            TablePosition<Employee, String> position = event.getTablePosition();
            Employee employee = event.getTableView().getItems().get(position.getRow());

            String newFio = event.getNewValue();
            employee.setFio(newFio);
            try {
                DataBases.updateEmployee(employee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setMinWidth(140);
        phoneColumn.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {

            TablePosition<Employee, String> position = event.getTablePosition();
            Employee employee = event.getTableView().getItems().get(position.getRow());

            String newNumber = event.getNewValue();
            employee.setPhonenumber(newNumber);
            try {
                DataBases.updateEmployee(employee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setMinWidth(180);
        emailColumn.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {

            TablePosition<Employee, String> position = event.getTablePosition();
            Employee employee = event.getTableView().getItems().get(position.getRow());

            String newEmail = event.getNewValue();
            employee.setEmail(newEmail);
            try {
                DataBases.updateEmployee(employee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ageColumn.setMinWidth(70);
        ageColumn.setOnEditCommit((TableColumn.CellEditEvent<Employee, Integer> event) -> {

            TablePosition<Employee, Integer> position = event.getTablePosition();
            Employee employee = event.getTableView().getItems().get(position.getRow());

            try {
                int newAge = event.getNewValue();
                if (newAge < 14 || newAge > 100) throw new NumberFormatException();
                DataBases.updateEmployee(employee);
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка редактирования сотрудника");
                alert.setContentText("Введено некорректное значение возраста сотрудника.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Display row data
        List<Employee> employeeList = DataBases.getEmployees();
        ObservableList<Employee> observableList = FXCollections.observableList(employeeList);
        employeeTableView.setItems(observableList);
        employeeTableView.getColumns().addAll(fioColumn, phoneColumn, emailColumn, ageColumn);
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initializeEmployeeButtons() {
        addEmployeeButton.setOnAction(actionEvent -> {
            Optional<Employee> result = getAddEmployeeDialog().showAndWait();
            result.ifPresent(list -> {
                try {
                    DataBases.addEmployee(result.get());
                    employeeTableView.getItems().add(result.get());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteEmployeeButton.setOnAction(actionEvent -> {
            int row = employeeTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение");
                alert.setHeaderText("Удалить сотрудника?");
                alert.setContentText("Сотрудник будет безвозвратно удалён.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    try {
                        DataBases.removeEmployee(employeeTableView.getItems().get(row).getId());
                        employeeTableView.getItems().remove(row);
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 2292) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText("Ошибка удаления сотрудника");
                            alert.setContentText("Невозможно удалить сотрудника, связанного с существующими резюме.");
                            alert.showAndWait();
                        } else e.printStackTrace();
                    }
                }
            }
        });
    }

    private Dialog<Employee> getAddEmployeeDialog() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Добавление сотрудника");
        dialog.setHeaderText("Заполните информацию о сотруднике");

        ButtonType confirmButton = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, cancelButton);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField fioField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        TextField ageField = new TextField();
        fioField.setPromptText("ФИО");
        phoneField.setPromptText("Номер телефона");
        emailField.setPromptText("E-mail");
        ageField.setPromptText("Возраст");

        grid.add(new Label("ФИО:"), 0, 0);
        grid.add(new Label("Телефон:"), 0, 1);
        grid.add(new Label("E-mail:"), 0, 2);
        grid.add(new Label("Возраст:"), 0, 3);
        grid.add(fioField, 1, 0);
        grid.add(phoneField, 1, 1);
        grid.add(emailField, 1, 2);
        grid.add(ageField, 1, 3);

        Node loginButton = dialog.getDialogPane().lookupButton(confirmButton);
        loginButton.setDisable(true);

        fioField.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty()));
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButton) {
                try {
                    int age = Integer.parseInt(ageField.getText());
                    if (age < 14 || age > 100) throw new NumberFormatException();
                    return new Employee(-1, fioField.getText(), phoneField.getText(), emailField.getText(), age);
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка добавления сотрудника");
                    alert.setContentText("Введено некорректное значение возраста сотрудника.");
                    alert.showAndWait();
                    return null;
                }
            }
            else return null;
        });
        return dialog;
    }
}
