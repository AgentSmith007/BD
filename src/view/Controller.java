package view;

import beans.*;
import connection.DataBases;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private TableView<Enterprise> enterpriseTableView;
    @FXML
    private TableView<Speciality> specialityTableView;
    @FXML
    private TableView<Vacancy> vacancyTableView;
    @FXML
    private TableView<Resume> resumeTableView;
    @FXML
    private TableView<VacancyStats> vacancyStatsTableView;
    @FXML
    private TableView<ResumeStats> resumeStatsTableView;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button deleteEmployeeButton;
    @FXML
    private Button addEnterpriseButton;
    @FXML
    private Button deleteEnterpriseButton;
    @FXML
    private Button addSpecialityButton;
    @FXML
    private Button deleteSpecialityButton;
    @FXML
    private Button addVacancyButton;
    @FXML
    private Button deleteVacancyButton;
    @FXML
    private Button addResumeButton;
    @FXML
    private Button deleteResumeButton;

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
            initializeResumeTable();
            initializeResumeButtons();
            initializeResumeStatsTable();
            initializeVacancyStatsTable();
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

            Employee employee = event.getTableView().getItems().get(event.getTablePosition().getRow());
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

            Employee employee = event.getTableView().getItems().get(event.getTablePosition().getRow());
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

            Employee employee = event.getTableView().getItems().get(event.getTablePosition().getRow());
            try {
                int newAge = event.getNewValue();
                if (newAge < 14 || newAge > 100) throw new NumberFormatException();
                employee.setAge(newAge);
                DataBases.updateEmployee(employee);
            } catch (NumberFormatException e) {
                Alert alert = Dialogs.getErrorAlert("Ошибка редактирования сотрудника", "Введено некорректное значение возраста сотрудника.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        employeeTableView.setItems(FXCollections.observableList(DataBases.getEmployees()));
        employeeTableView.getColumns().addAll(getIndexColumn(employeeTableView), fioColumn, phoneColumn, emailColumn, ageColumn);
    }

    private void initializeEmployeeButtons() {
        addEmployeeButton.setOnAction(actionEvent -> {
            Optional<Employee> result = Dialogs.getAddEmployeeDialog().showAndWait();
            result.ifPresent(list -> {
                try {
                    DataBases.addEmployee(result.get());
                    employeeTableView.setItems(FXCollections.observableList(DataBases.getEmployees()));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteEmployeeButton.setOnAction(actionEvent -> {
            int row = employeeTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = Dialogs.getConfirmationAlert("Удалить сотрудника?", "Сотрудник и все его резюме будут безвозвратно удалены.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeEmployee(employeeTableView.getItems().get(row).getId());
                        employeeTableView.getItems().remove(row);
                        resumeTableView.setItems(FXCollections.observableList(DataBases.getResumes()));
                        resumeStatsTableView.setItems(FXCollections.observableList(DataBases.getResumeStats()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @SuppressWarnings("unchecked")
    private void initializeEnterpriseTable() throws SQLException {
        TableColumn<Enterprise, String> nameColumn = new TableColumn<>("Название");
        TableColumn<Enterprise, String> phoneColumn = new TableColumn<>("Номер телефона");

        // Defines how to fill data for each cell.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setMinWidth(200);
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Enterprise, String> event) -> {

            Enterprise enterprise = event.getTableView().getItems().get(event.getTablePosition().getRow());
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
        phoneColumn.setMinWidth(200);
        phoneColumn.setOnEditCommit((TableColumn.CellEditEvent<Enterprise, String> event) -> {

            Enterprise enterprise = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newNumber = event.getNewValue();
            enterprise.setPhone(newNumber);
            try {
                DataBases.updateEnterprise(enterprise);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        enterpriseTableView.setItems(FXCollections.observableList(DataBases.getEnterprises()));
        enterpriseTableView.getColumns().addAll(getIndexColumn(enterpriseTableView), nameColumn, phoneColumn);
    }

    private void initializeEnterpriseButtons() {
        addEnterpriseButton.setOnAction(actionEvent -> {
            Optional<Enterprise> result = Dialogs.getAddEnterpriseDialog().showAndWait();
            result.ifPresent(list -> {
                try {
                    DataBases.addEnterprise(result.get());
                    enterpriseTableView.setItems(FXCollections.observableList(DataBases.getEnterprises()));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteEnterpriseButton.setOnAction(actionEvent -> {
            int row = enterpriseTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = Dialogs.getConfirmationAlert("Удалить предприятие?", "Предприятие и все его вакансии будут безвозвратно удалены.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeEnterprise(enterpriseTableView.getItems().get(row).getId());
                        enterpriseTableView.getItems().remove(row);
                        vacancyTableView.setItems(FXCollections.observableList(DataBases.getVacancies()));
                        vacancyStatsTableView.setItems(FXCollections.observableList(DataBases.getVacancyStats()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initializeSpecialityTable() throws SQLException {
        TableColumn<Speciality, String> nameColumn = new TableColumn<>("Название");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setMinWidth(200);
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Speciality, String> event) -> {

            Speciality speciality = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newName = event.getNewValue();
            speciality.setName(newName);
            try {
                DataBases.updateSpeciality(speciality);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        specialityTableView.setItems(FXCollections.observableList(DataBases.getSpecialities()));
        specialityTableView.getColumns().addAll(getIndexColumn(specialityTableView), nameColumn);
    }

    private void initializeSpecialityButtons() {
        addSpecialityButton.setOnAction(actionEvent -> {
            Optional<Speciality> result = Dialogs.getAddSpecialityDialog().showAndWait();
            result.ifPresent(list -> {
                try {
                    DataBases.addSpeciality(result.get());
                    specialityTableView.setItems(FXCollections.observableList(DataBases.getSpecialities()));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteSpecialityButton.setOnAction(actionEvent -> {
            int row = specialityTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = Dialogs.getConfirmationAlert("Удалить специальность?", "Специальность будет безвозвратно удалена.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeSpeciality(specialityTableView.getItems().get(row).getId());
                        specialityTableView.getItems().remove(row);
                        vacancyTableView.setItems(FXCollections.observableList(DataBases.getVacancies()));
                        resumeTableView.setItems(FXCollections.observableList(DataBases.getResumes()));
                        resumeStatsTableView.setItems(FXCollections.observableList(DataBases.getResumeStats()));
                        vacancyStatsTableView.setItems(FXCollections.observableList(DataBases.getVacancyStats()));
                    } catch (SQLException e) {
                        if (e.getErrorCode() == 2292) {
                            alert = Dialogs.getErrorAlert("Ошибка удаления специальности", "Невозможно удалить специальность, связанную с существующей вакансией.");
                            alert.showAndWait();
                        } else e.printStackTrace();
                    }
                }
            }
        });
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

            Vacancy vacancy = event.getTableView().getItems().get(event.getTablePosition().getRow());
            try {
                int newExperience = event.getNewValue();
                if (newExperience < 0 || newExperience > 50) throw new NumberFormatException();
                vacancy.setExperience(newExperience);
                DataBases.updateVacancy(vacancy);
            } catch (NumberFormatException e) {
                Alert alert = Dialogs.getErrorAlert("Ошибка редактирования вакансии", "Введено некорректное значение требуемого опыта работы.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        salaryColumn.setMinWidth(80);
        salaryColumn.setOnEditCommit((TableColumn.CellEditEvent<Vacancy, Integer> event) -> {

            Vacancy vacancy = event.getTableView().getItems().get(event.getTablePosition().getRow());
            try {
                int newSalary = event.getNewValue();
                if (newSalary < 0) throw new NumberFormatException();
                vacancy.setSalary(newSalary);
                DataBases.updateVacancy(vacancy);
            } catch (NumberFormatException e) {
                Alert alert = Dialogs.getErrorAlert("Ошибка редактирования вакансии", "Введено некорректное значение заработной платы.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        vacancyTableView.setItems(FXCollections.observableList(DataBases.getVacancies()));
        vacancyTableView.getColumns().addAll(getIndexColumn(vacancyTableView), enterpriseColumn, specialityColumn, experienceColumn, salaryColumn);
    }

    private void initializeVacancyButtons() {
        addVacancyButton.setOnAction(actionEvent -> {
            Optional<Vacancy> result = Dialogs.getAddVacancyDialog().showAndWait();
            result.ifPresent(vacancy -> {
                try {
                    DataBases.addVacancy(result.get());
                    vacancyTableView.setItems(FXCollections.observableList(DataBases.getVacancies()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteVacancyButton.setOnAction(actionEvent -> {
            int row = vacancyTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = Dialogs.getConfirmationAlert("Удалить вакансию?", "Вакансия будет безвозвратно удалена.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeVacancy(vacancyTableView.getItems().get(row).getId());
                        vacancyTableView.getItems().remove(row);
                        vacancyStatsTableView.setItems(FXCollections.observableList(DataBases.getVacancyStats()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initializeResumeTable() throws SQLException {
        TableColumn<Resume, Employee> employeeColumn = new TableColumn<>("Претендент");
        TableColumn<Resume, Speciality> specialityColumn = new TableColumn<>("Специальность");
        TableColumn<Resume, Integer> experienceColumn = new TableColumn<>("Опыт работы");
        TableColumn<Resume, Date> dateColumn = new TableColumn<>("Дата создания");

        employeeColumn.setCellValueFactory(param -> {
            try {
                Employee employee = DataBases.getEmployee(param.getValue().getEmployeeID());
                return new SimpleObjectProperty<>(employee);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
        employeeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(employeeTableView.getItems()));

        employeeColumn.setOnEditCommit((TableColumn.CellEditEvent<Resume, Employee> event) -> {
            try {
                int row = event.getTablePosition().getRow();
                Employee newEmployee = event.getNewValue();
                Resume resume = event.getTableView().getItems().get(row);
                resume.setEmployeeID(newEmployee.getId());
                DataBases.updateResume(resume);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        employeeColumn.setMinWidth(250);

        specialityColumn.setCellValueFactory(param -> {
            try {
                return new SimpleObjectProperty<>(DataBases.getSpeciality(param.getValue().getSpecialityID()));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
        specialityColumn.setCellFactory(ComboBoxTableCell.forTableColumn(specialityTableView.getItems()));

        specialityColumn.setOnEditCommit((TableColumn.CellEditEvent<Resume, Speciality> event) -> {
            try {
                int row = event.getTablePosition().getRow();
                Speciality newSpeciality = event.getNewValue();
                Resume resume = event.getTableView().getItems().get(row);
                resume.setSpecialityID(newSpeciality.getId());
                DataBases.updateResume(resume);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        specialityColumn.setMinWidth(130);

        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        experienceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        experienceColumn.setMinWidth(100);
        experienceColumn.setOnEditCommit((TableColumn.CellEditEvent<Resume, Integer> event) -> {

            Resume resume = event.getTableView().getItems().get(event.getTablePosition().getRow());
            try {
                int newExperience = event.getNewValue();
                if (newExperience < 0 || newExperience > 60) throw new NumberFormatException();
                resume.setExperience(newExperience);
                DataBases.updateResume(resume);
            } catch (NumberFormatException e) {
                Alert alert = Dialogs.getErrorAlert("Ошибка редактирования резюме", "Введено некорректное значение опыта работы.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        dateColumn.setMinWidth(110);
        dateColumn.setOnEditCommit((TableColumn.CellEditEvent<Resume, Date> event) -> {

            Resume resume = event.getTableView().getItems().get(event.getTablePosition().getRow());
            try {
                Date newDate = event.getNewValue();
                if (newDate.after(new Date())) throw new ParseException("", 0);
                resume.setDate(newDate);
                DataBases.updateResume(resume);
            } catch (ParseException e) {
                Alert alert = Dialogs.getErrorAlert("Ошибка редактирования резюме", "Введена некорректная дата создания резюме.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        resumeTableView.setItems(FXCollections.observableList(DataBases.getResumes()));
        resumeTableView.getColumns().addAll(getIndexColumn(resumeTableView), employeeColumn, specialityColumn, experienceColumn, dateColumn);
    }

    private void initializeResumeButtons() {
        addResumeButton.setOnAction(actionEvent -> {
            Optional<Resume> result = Dialogs.getAddResumeDialog().showAndWait();
            result.ifPresent(resume -> {
                try {
                    DataBases.addResume(result.get());
                    resumeTableView.setItems(FXCollections.observableList(DataBases.getResumes()));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteResumeButton.setOnAction(actionEvent -> {
            int row = resumeTableView.getSelectionModel().getSelectedIndex();
            if (row != -1) {
                Alert alert = Dialogs.getConfirmationAlert("Удалить резюме?", "Резюме будет безвозвратно удалено.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    try {
                        DataBases.removeResume(resumeTableView.getItems().get(row).getId());
                        resumeTableView.getItems().remove(row);
                        resumeStatsTableView.setItems(FXCollections.observableList(DataBases.getResumeStats()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initializeResumeStatsTable() throws SQLException {
        TableColumn<ResumeStats, String> specialityColumn = new TableColumn<>("Специальность");
        TableColumn<ResumeStats, Integer> numberResumesColumn = new TableColumn<>("Количество резюме");

        specialityColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        specialityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        specialityColumn.setMinWidth(150);

        numberResumesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfResumes"));
        numberResumesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberResumesColumn.setMinWidth(150);

        resumeStatsTableView.setItems(FXCollections.observableList(DataBases.getResumeStats()));
        resumeStatsTableView.getColumns().addAll(getIndexColumn(resumeStatsTableView), specialityColumn, numberResumesColumn);
    }

    @SuppressWarnings("unchecked")
    private void initializeVacancyStatsTable() throws SQLException {
        TableColumn<VacancyStats, String> specialityColumn = new TableColumn<>("Специальность");
        TableColumn<VacancyStats, Integer> numberResumesColumn = new TableColumn<>("Количество вакансий");
        TableColumn<VacancyStats, Integer> averageSalaryColumn = new TableColumn<>("Средняя зарплата");

        specialityColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        specialityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        specialityColumn.setMinWidth(150);

        numberResumesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfVacancies"));
        numberResumesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberResumesColumn.setMinWidth(150);

        averageSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("averageSalary"));
        averageSalaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        averageSalaryColumn.setMinWidth(150);

        vacancyStatsTableView.setItems(FXCollections.observableList(DataBases.getVacancyStats()));
        vacancyStatsTableView.getColumns().addAll(getIndexColumn(vacancyStatsTableView), specialityColumn, numberResumesColumn, averageSalaryColumn);
    }

    private <T> TableColumn<T, Integer> getIndexColumn(TableView table) {
        TableColumn<T, Integer> indexColumn = new TableColumn<>("№");
        indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        indexColumn.setSortable(false);
        indexColumn.setResizable(false);
        indexColumn.setMinWidth(30);
        indexColumn.setMaxWidth(30);
        return indexColumn;
    }
}
