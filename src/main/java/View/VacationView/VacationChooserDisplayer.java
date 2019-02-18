package View.VacationView;

import View.AController;
import View.LogInView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class VacationChooserDisplayer extends AController {

    private  final SimpleStringProperty vacationID;

    private final CheckBox vacationChooser;


    public VacationChooserDisplayer(String vacationid,ArrayList<String> currentVacationDetails) {
        this.vacationID = new SimpleStringProperty(vacationid);
        vacationChooser = new CheckBox();

        //sends to the controller the request for the replacement
        vacationChooser.setOnAction(event -> {
            if(vacationChooser.isSelected()){
                currentVacationDetails.add(3,vacationid);
                controller.sendRequestForReplacement(currentVacationDetails);
                showAlert("your request was sent to the sale person for approval,\n " +
                        "you will get confirmation to your inbox");
            }
        });
    }

    public String getVacationID() {
        return vacationID.get();
    }

    public SimpleStringProperty vacationIDProperty() {
        return vacationID;
    }

    public void setVacationID(String vacationID) {
        this.vacationID.set(vacationID);
    }

    public CheckBox getVacationChooser() {
        return vacationChooser;
    }

}
