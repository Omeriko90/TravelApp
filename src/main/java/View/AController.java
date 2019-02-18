package View;

import Controller.Controller;
import javafx.scene.control.Alert;

public class AController {
    //This Controller is common to all buttons
    public Controller controller;

    public AController (){
        controller = new Controller();
    }


    // A generic show alert function for all type of alerts
    public void showAlert(String property) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(property);
        alert.showAndWait();
    }
}
