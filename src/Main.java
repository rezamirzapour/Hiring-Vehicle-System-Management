import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Dashboard.fxml"));
        primaryStage.setTitle("Hire Vehicle Management");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
