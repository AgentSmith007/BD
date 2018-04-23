package view;

import beans.Employee;
import connection.DataBases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;
import java.util.List;

public class EmployeeListController {

    @FXML
    private TableView<Employee> tableView;

    public void initialize() {

        try {
            TableColumn<Employee, String> fioColumn = new TableColumn<>("ФИО");
            TableColumn<Employee, Integer> phoneColumn = new TableColumn<>("Номер телефона");
            TableColumn<Employee, String> emailColumn = new TableColumn<>("E-mail");
            TableColumn<Employee, Integer> ageColumn = new TableColumn<>("Возраст");

            // Defines how to fill data for each cell.
            fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
            fioColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            fioColumn.setMinWidth(200);
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
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

            // Display row data
            List<Employee> list = DataBases.getEmployees();
            ObservableList<Employee> observableList = FXCollections.observableList(list);
            tableView.setItems(observableList);

            tableView.getColumns().addAll(fioColumn, phoneColumn, emailColumn, ageColumn);

            //button.setOnAction(event -> EmployeeInputForm.showForm());

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
