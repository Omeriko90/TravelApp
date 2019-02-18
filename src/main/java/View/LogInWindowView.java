package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class LogInWindowView {
    public javafx.scene.control.Button btnLogIn;
    public javafx.scene.control.Button btnSignIn;
    public javafx.scene.control.Button btnLogInAsaGuest;
    //this function taking care about sign in of a new user


    public void createWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/User Fxml/Create.fxml"));
            root = myLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 480);
        scene.getStylesheets().add(getClass().getResource("/TravelApp.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void LogInWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/LogIn.fxml"));
            root = myLoader.load();        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 480);
        scene.getStylesheets().add(getClass().getResource("/TravelApp.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void LogInAsAGuestWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/CrudAsAGuest.fxml"));
            root = myLoader.load();        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 480);
        scene.getStylesheets().add(getClass().getResource("/TravelApp.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
