import View.AController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TravelApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(TravelApp.class.getResource("/fxml/LogInWindow.fxml"));
        Scene scene = new Scene(root, 600, 480);
        scene.getStylesheets().add(getClass().getResource("/TravelApp.css").toExternalForm());
        primaryStage.setTitle("TravelApp");
        AController controller = new AController();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
