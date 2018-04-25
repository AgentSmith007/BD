package view;

import beans.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Dialogs {

    public static Dialog<Employee> getAddEmployeeDialog() {
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


    public static Dialog<Enterprise> getAddEnterpriseDialog() {
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

    public static Dialog<Speciality> getAddSpecialityDialog() {
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

    public static Dialog<Vacancy> getAddVacancyDialog() {
        throw new NotImplementedException();
    }

    public static Dialog<Resume> getAddResumeDialog() {
        throw new NotImplementedException();
    }

    public static Alert getConfirmationAlert(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static Alert getErrorAlert(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
}
