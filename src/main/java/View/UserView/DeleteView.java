package View.UserView;

import View.AController;
import View.LogInView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class DeleteView extends AController {

    public javafx.scene.control.Button back;
    public LogInView myLogger = new LogInView();


    public void DeleteUser() {
        controller.deleteUser(myLogger.getUserName());
        showAlert("User deleted.");
    }

    public void connect(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete your profile?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DeleteUser();
            backLogInWindow(event);

        } else {
        }
        showAlert("Please Log In First and try again");
    }

    public void backOrigin (ActionEvent event) throws IOException {
        Stage stage = (Stage)back.getScene().getWindow();
        stage.close();
    }

    public void backLogInWindow(ActionEvent event){
        try {
            backOrigin(event);
            Parent root = null;
            try {
                FXMLLoader myLoader = new FXMLLoader();
                myLoader.setLocation(getClass().getResource("/fxml/LogInWindow.fxml"));
                root = myLoader.load();        } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 600, 480);
            scene.getStylesheets().add(getClass().getResource("/TravelApp.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


