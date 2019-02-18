package View;

import View.AController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CrudAsAGuestView extends AController {

    public javafx.scene.control.Button btnUsersCrud;
    public javafx.scene.control.Button btnVacationCrud;
    public javafx.scene.control.Button backCrudGuest;

    public void userCrudWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/User Fxml/UserCrudWithoutLogIn.fxml"));
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

    public void vacationCrudWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/Vacation Fxml/VacationCrudWithoutLog.fxml"));
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
    public void backOriginStartWindow (ActionEvent event) throws IOException {
        Stage stage = (Stage) backCrudGuest.getScene().getWindow();
        stage.close();
    }
}
