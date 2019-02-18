package View.VacationView;

import View.AController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CreateVacationView extends AController{

    public javafx.scene.control.TextField txtDestination;
    public javafx.scene.control.TextField txtFlightCompany;
    public javafx.scene.control.TextField txtPrice;
    public javafx.scene.control.DatePicker fromDate;
    public javafx.scene.control.DatePicker toDate;
    public javafx.scene.control.CheckBox includesHotel;
    public javafx.scene.control.CheckBox includesBags;
    public javafx.scene.control.ChoiceBox <String> numberOfTickets;
    public javafx.scene.control.ChoiceBox <String> hotelRate;
    public javafx.scene.control.ChoiceBox <String> vacationKind;
    public javafx.scene.control.ChoiceBox <String> SellerKind;
    public javafx.scene.control.Label lblhotelRate;
    public javafx.scene.control.TextField txtFromCountry;
    public javafx.scene.control.Button back;
    public javafx.scene.control.Button submit;

    @FXML
    public void initialize() {
        numberOfTickets.setItems(FXCollections.observableArrayList(
                "Choose number of tickets" ,"1", "2","3","4","5","6","7","8","9","10"));
        hotelRate.setItems(FXCollections.observableArrayList("1","2","3","4","5"));
        vacationKind.setItems(FXCollections.observableArrayList("Exotic","Urban"));
        SellerKind.setItems(FXCollections.observableArrayList("Private","Travel agent"));
        hotelRate.setVisible(false);
        lblhotelRate.setVisible(false);
    }

    public void hotelSelected(){
        hotelRate.setVisible(true);
        lblhotelRate.setVisible(true);
    }

    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }


    public void submit(){
        boolean empty=true;
        ArrayList<String> allVacationDetails=new ArrayList<>();
        allVacationDetails.add(fromDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        allVacationDetails.add(toDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        if(txtDestination.getText().length()==0||txtPrice.getText().length()==0||txtFlightCompany.getText().length()==0||
                numberOfTickets.getValue().length()==0 || vacationKind.getValue().length()==0 || (includesHotel.isSelected()&&hotelRate.getValue().length()==0)
                ||SellerKind.getValue().length()==0 ) {
            empty=false;
            showAlert("You must fill out all fields while creating");
        }
        else {
            empty=true;
            allVacationDetails.add(txtDestination.getText());
            allVacationDetails.add(txtPrice.getText());
            allVacationDetails.add(txtFlightCompany.getText());
            allVacationDetails.add(String.valueOf(includesHotel.isSelected()));
            allVacationDetails.add(String.valueOf(includesBags.isSelected()));
            allVacationDetails.add(numberOfTickets.getValue());
            if (includesHotel.isSelected())
                allVacationDetails.add(hotelRate.getValue());
            else
                allVacationDetails.add("");
            allVacationDetails.add(vacationKind.getValue());
            allVacationDetails.add(SellerKind.getValue());
            allVacationDetails.add(txtFromCountry.getText());
            }
        String ans="?";
        if(empty)
            ans=controller.addVacation(allVacationDetails);
        if(ans.equals("date"))
            showAlert("The dates you have selected are invalid");
        else if(ans.equals("destination"))
            showAlert("The destination name can contain only letters");
        else if(ans.equals("price"))
            showAlert("Price can contain only numbers");
        else if(!ans.equals("?")) {
            showAlert("Your vacation was successfully created");
            clearStyle();
        }

    }

    private void clearStyle() {
        txtDestination.setStyle("-fx-border-width: 0px");
        txtFlightCompany.setStyle("-fx-border-width: 0px");
        txtPrice.setStyle("-fx-border-width: 0px");
        fromDate.setStyle("-fx-border-width: 0px");
        toDate.setStyle("-fx-border-width: 0px");
        includesHotel.setStyle("-fx-border-width: 0px");
        includesBags.setStyle("-fx-border-width: 0px");
        hotelRate.setStyle("-fx-border-width: 0px");
        vacationKind.setStyle("-fx-border-width: 0px");
        SellerKind.setStyle("-fx-border-width: 0px");
        numberOfTickets.setStyle("-fx-border-width: 0px");
    }

}
