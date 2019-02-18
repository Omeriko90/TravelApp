package View.VacationView;

import View.AController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteVacationView extends AController{

    public javafx.scene.control.ComboBox <String> allUserVacations;
    public javafx.scene.control.Button back;

    @FXML
    public void initialize() throws SQLException {
        ArrayList <String> allVacationsofcurrentuser=controller.getAllUserVacations();
        allUserVacations.setItems(FXCollections.observableArrayList(allVacationsofcurrentuser));
    }

    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void deleteVacation(ActionEvent actionEvent)throws SQLException{
        String vacationIdtoDelete = allUserVacations.getValue();
        controller.deleteVacation(vacationIdtoDelete);
        showAlert("Vacation deleted");
        ArrayList <String> allVacationsofcurrentuser=controller.getAllUserVacations();
        allUserVacations.setItems(FXCollections.observableArrayList(allVacationsofcurrentuser));
    }
}
