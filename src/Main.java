import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/EmployeeListView.fxml"));
        Parent root = loader.load();
        stage.setTitle("Список сотрудников");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
