package View.UserView;
import View.AController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

//This class describe the window's style and load the correct fxml to each buttoon
public class UserCrudView extends AController{
    public javafx.scene.control.Button delete;
    public javafx.scene.control.Button update;
    public javafx.scene.control.Button read;
    public javafx.scene.control.Button backUserCrudNoLogIn;



    public void searchWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/User Fxml/Read.fxml"));
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

    public void updateWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/User Fxml/Update.fxml"));
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

    public void DeleteWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/User Fxml/Delete.fxml"));
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

    public void backToOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) backUserCrudNoLogIn.getScene().getWindow();
        stage.close();
    }

}
