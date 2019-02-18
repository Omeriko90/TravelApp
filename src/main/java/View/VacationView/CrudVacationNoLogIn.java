package View.VacationView;

import View.AController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CrudVacationNoLogIn extends AController {

    public javafx.scene.control.Button btnSearchVacation;
    public javafx.scene.control.Button backVacation;
    public CrudVacationLogIn crudVacationLogIn=new CrudVacationLogIn();


    public void backToOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage)backVacation.getScene().getWindow();
        stage.close();
    }

    public void showVacationsInDb(ActionEvent actionEvent){
        crudVacationLogIn.DisplayWindow(actionEvent);
    }




}
