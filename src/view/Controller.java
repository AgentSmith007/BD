package view;

import beans.Employee;
import beans.Enterprise;
import beans.Speciality;
import beans.Vacancy;
import connection.DataBases;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button deleteEmployeeButton;

    @FXML
    private TableView<Enterprise> enterpriseTableView;
    @FXML
    private Button addEnterpriseButton;
    @FXML
    private Button deleteEnterpriseButton;

    @FXML
    private TableView<Speciality> specialityTableView;
    @FXML
    private Button addSpecialityButton;
    @FXML
    private Button deleteSpecialityButton;

    @FXML
    private TableView<Vacancy> vacancyTableView;
    @FXML
    private Button addVacancyButton;
    @FXML
    private Button deleteVacancyButton;

    public void initialize() {

        try {
            initializeEmployeeTable();
            initializeEmployeeButtons();

            initializeEnterpriseTable();
            initializeEnterpriseButtons();

            initializeSpecialityTable();
            initializeSpecialityButtons();

            initializeVacancyTable();
            initializeVacancyButtons();

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
                employee.setAge(newAge);
                DataBases.updateEmployee(employee);
            } catch (NumberFormatException e) {
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

                    List<Employee> employeeList = DataBases.getEmployees();
                    ObservableList<Employee> observableList = FXCollections.observableList(employeeList);
                    employeeTableView.setItems(observableList);

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
                if (result.get() == ButtonType.OK) {
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
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка добавления сотрудника");
                    alert.setContentText("Введено некорректное значение возраста сотрудника.");
                    alert.showAndWait();
                    return null;
                }
            } else return null;
        });
        return dialog;
    }

    @SuppressWarnings("unchecked")
    private void initializeEnterpriseTable() throws SQLException {
        TableColumn<Enterprise, String> nameColumn = new TableColumn<>("Название");
        TableColumn<Enterprise, String> phoneColumn = new TableColumn<>("Номер телефона");

        // Defines how to fill data for each cell.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setMinWidth(355);
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Enterprise, String> event) -> {

            TablePosition<Enterprise, String> position = event.getTablePosition();
            Enterprise enterprise = event.getTableView().getItems().get(position.getRow());

            String newName = event.getNewValue();
            enterprise.setName(newName);
            try {
                DataBases.updateEnterprise(enterprise);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setMinWidth(355);
        phoneColumn.setOnEditCommit((TableColumn.CellEditEvent<Enterprise, String> event) -> {

            TablePosition<Enterprise, String> position = event.getTablePosition();
            Enterprise enterprise = event.getTableView().getItems().get(position.getRow());

            String newNumber = event.getNewValue();
            enterprise.setPhone(newNumber);
            try {
                DataBases.updateEnterprise(enterprise);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Display row data
        List<Enterprise> enterpriseList = DataBases.getEnterprises();
        ObservableList<Enterprise> observableList = FXCollections.observableList(enterpriseList);
        enterpriseTableView.setItems(observableList);
        enterpriseTableView.getColumns().addAll(nameColumn, phoneColumn);
        enterpriseTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initializeEnterpriseButtons() {
        addEnterpriseButton.setOnAction(actionEvent -> {
            Optional<Enterprise> result = getAddEnterpriseDialog().showAndWait();
            result.ifPresent(list -> {
                try {
                    DataBases.addEnterprise(result.get());

                    List<Enterprise> enterpriseList = DataBases.getEnterprises();
                    ObservableList<Enterprise> observableList = FXCollections.observableList(enterpriseList);
                    enterpriseTableView.setItems(observableList);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteEnterpriseButton.setOnAction(actionEvent -> {
            int row = enterpriseTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение");
                alert.setHeaderText("Удалить предприятие?");
                alert.setContentText("Предприятие будет безвозвратно удалено.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeEnterprise(enterpriseTableView.getItems().get(row).getId());
                        enterpriseTableView.getItems().remove(row);
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 2292) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText("Ошибка удаления предприятия");
                            alert.setContentText("Невозможно удалить предприятие, связанное с существующей вакансией.");
                            alert.showAndWait();
                        } else e.printStackTrace();
                    }
                }
            }
        });
    }

    private Dialog<Enterprise> getAddEnterpriseDialog() {
        Dialog<Enterprise> dialog = new Dialog<>();
        dialog.setTitle("Добавление предприятия");
        dialog.setHeaderText("Заполните информацию о предприятии");

        ButtonType confirmButton = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, cancelButton);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField nameField = new TextField();
        TextField phoneField = new TextField();
        nameField.setPromptText("Название");
        phoneField.setPromptText("Номер телефона");

        grid.add(new Label("Название:"), 0, 0);
        grid.add(new Label("Телефон:"), 0, 1);
        grid.add(nameField, 1, 0);
        grid.add(phoneField, 1, 1);
        Node loginButton = dialog.getDialogPane().lookupButton(confirmButton);
        loginButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty()));
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButton)
                return new Enterprise(-1, nameField.getText(), phoneField.getText());
            else return null;
        });
        return dialog;
    }

    @SuppressWarnings("unchecked")
    private void initializeSpecialityTable() throws SQLException {
        TableColumn<Speciality, String> nameColumn = new TableColumn<>("Название");

        // Defines how to fill data for each cell.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setMinWidth(710);
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Speciality, String> event) -> {

            TablePosition<Speciality, String> position = event.getTablePosition();
            Speciality speciality = event.getTableView().getItems().get(position.getRow());

            String newName = event.getNewValue();
            speciality.setName(newName);
            try {
                DataBases.updateSpeciality(speciality);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        List<Speciality> specialityList = DataBases.getSpecialities();
        ObservableList<Speciality> observableList = FXCollections.observableList(specialityList);
        specialityTableView.setItems(observableList);
        specialityTableView.getColumns().addAll(nameColumn);
        specialityTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initializeSpecialityButtons() {
        addSpecialityButton.setOnAction(actionEvent -> {
            Optional<Speciality> result = getAddSpecialityDialog().showAndWait();
            result.ifPresent(list -> {
                try {
                    DataBases.addSpeciality(result.get());

                    List<Speciality> enterpriseList = DataBases.getSpecialities();
                    ObservableList<Speciality> observableList = FXCollections.observableList(enterpriseList);
                    specialityTableView.setItems(observableList);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteSpecialityButton.setOnAction(actionEvent -> {
            int row = specialityTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение");
                alert.setHeaderText("Удалить специальность?");
                alert.setContentText("Специальность будет безвозвратно удалена.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeSpeciality(specialityTableView.getItems().get(row).getId());
                        specialityTableView.getItems().remove(row);
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 2292) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText("Ошибка удаления специальности");
                            alert.setContentText("Невозможно удалить специальность, связанную с существующей вакансией.");
                            alert.showAndWait();
                        } else e.printStackTrace();
                    }
                }
            }
        });
    }

    private Dialog<Speciality> getAddSpecialityDialog() {
        Dialog<Speciality> dialog = new Dialog<>();
        dialog.setTitle("Добавление специальности");
        dialog.setHeaderText("Заполните информацию о специальности");

        ButtonType confirmButton = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, cancelButton);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Название");

        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        Node loginButton = dialog.getDialogPane().lookupButton(confirmButton);
        loginButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty()));
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButton)
                return new Speciality(-1, nameField.getText());
            else return null;
        });
        return dialog;
    }


    @SuppressWarnings("unchecked")
    private void initializeVacancyTable() throws SQLException {
        TableColumn<Vacancy, Enterprise> enterpriseColumn = new TableColumn<>("Предприятие");
        TableColumn<Vacancy, Speciality> specialityColumn = new TableColumn<>("Специальность");
        TableColumn<Vacancy, Integer> experienceColumn = new TableColumn<>("Требуемый опыт");
        TableColumn<Vacancy, Integer> salaryColumn = new TableColumn<>("Зарплата");

        enterpriseColumn.setCellValueFactory(param -> {
            try {
                Enterprise enterprise = DataBases.getEnterprise(param.getValue().getEnterpriseID());
                return new SimpleObjectProperty<>(enterprise);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
        enterpriseColumn.setCellFactory(ComboBoxTableCell.forTableColumn(enterpriseTableView.getItems()));

        enterpriseColumn.setOnEditCommit((TableColumn.CellEditEvent<Vacancy, Enterprise> event) -> {
            try {
                int row = event.getTablePosition().getRow();
                Enterprise newEnterprise = event.getNewValue();
                Vacancy vacancy = event.getTableView().getItems().get(row);
                vacancy.setEnterpriseID(newEnterprise.getId());
                DataBases.updateVacancy(vacancy);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        enterpriseColumn.setMinWidth(130);


        specialityColumn.setCellValueFactory(param -> {
            try {
                Speciality speciality = DataBases.getSpeciality(param.getValue().getSpecialityID());
                return new SimpleObjectProperty<>(speciality);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
        specialityColumn.setCellFactory(ComboBoxTableCell.forTableColumn(specialityTableView.getItems()));

        specialityColumn.setOnEditCommit((TableColumn.CellEditEvent<Vacancy, Speciality> event) -> {
            try {
                int row = event.getTablePosition().getRow();
                Speciality newSpeciality = event.getNewValue();
                Vacancy vacancy = event.getTableView().getItems().get(row);
                vacancy.setSpecialityID(newSpeciality.getId());
                DataBases.updateVacancy(vacancy);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        specialityColumn.setMinWidth(130);


        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        experienceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        experienceColumn.setMinWidth(130);
        experienceColumn.setOnEditCommit((TableColumn.CellEditEvent<Vacancy, Integer> event) -> {

            TablePosition<Vacancy, Integer> position = event.getTablePosition();
            Vacancy vacancy = event.getTableView().getItems().get(position.getRow());

            try {
                int newExperience = event.getNewValue();
                if (newExperience < 0 || newExperience > 50) throw new NumberFormatException();
                vacancy.setExperience(newExperience);
                DataBases.updateVacancy(vacancy);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка редактирования вакансии");
                alert.setContentText("Введено некорректное значение требуемого опыта работы.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        salaryColumn.setMinWidth(80);
        salaryColumn.setOnEditCommit((TableColumn.CellEditEvent<Vacancy, Integer> event) -> {

            TablePosition<Vacancy, Integer> position = event.getTablePosition();
            Vacancy vacancy = event.getTableView().getItems().get(position.getRow());

            try {
                int newSalary = event.getNewValue();
                if (newSalary < 0) throw new NumberFormatException();
                vacancy.setSalary(newSalary);
                DataBases.updateVacancy(vacancy);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка редактирования вакансии");
                alert.setContentText("Введено некорректное значение заработной платы.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Display row data
        List<Vacancy> vacancyList = DataBases.getVacancies();
        ObservableList<Vacancy> observableList = FXCollections.observableList(vacancyList);
        vacancyTableView.setItems(observableList);
        vacancyTableView.getColumns().addAll(enterpriseColumn, specialityColumn, experienceColumn, salaryColumn);
        vacancyTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initializeVacancyButtons() {
        addVacancyButton.setOnAction(actionEvent -> {
            Optional<Vacancy> result = getAddVacancyDialog().showAndWait();
            result.ifPresent(vacancy -> {
                try {
                    DataBases.addVacancy(result.get());

                    List<Vacancy> vacancyList = DataBases.getVacancies();
                    ObservableList<Vacancy> observableList = FXCollections.observableList(vacancyList);
                    vacancyTableView.setItems(observableList);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteVacancyButton.setOnAction(actionEvent -> {
            int row = vacancyTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение");
                alert.setHeaderText("Удалить вакансию?");
                alert.setContentText("Вакансия будет безвозвратно удалена.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeVacancy(vacancyTableView.getItems().get(row).getId());
                        vacancyTableView.getItems().remove(row);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Dialog<Vacancy> getAddVacancyDialog() {
        throw new NotImplementedException();
    }
}
