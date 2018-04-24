import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/EmployeeListScene.fxml"));
        Parent root = loader.load();
        stage.setTitle("Проектирование баз данных");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
