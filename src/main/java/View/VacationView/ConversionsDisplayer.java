package View.VacationView;

import View.AController;
import View.LogInView;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

public class ConversionsDisplayer extends AController {
    private final SimpleStringProperty UserName;
    private final SimpleStringProperty myVacationID;
    private String newVacationID;
    private Button btnGetDetails;
    private Button btnAccept;
    private Button btnDecline;


    public ConversionsDisplayer(String vacationID ,String BuyerUserName , String hisVacationID) {
        UserName = new SimpleStringProperty(BuyerUserName);
        myVacationID = new SimpleStringProperty(vacationID);
        newVacationID=hisVacationID;
        btnGetDetails = new Button("Get Vacation Details");

        btnGetDetails.setOnAction(event -> {
            String[] vacationDetails = controller.getAllvacationDetails(BuyerUserName,hisVacationID);
            if(vacationDetails!=null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("From Date: " + vacationDetails[0] + "\n" + "To Date: " + vacationDetails[1]
                        + "\n" + "Destination: " + vacationDetails[2] + "\n" + "Price " + vacationDetails[3] + "\n"
                        + "Flight By: " + vacationDetails[4] + "\n" + "Include Hotel: " + vacationDetails[5] + "\n"
                        + "Include Bag: " + vacationDetails[6] + "\n" + "Number of Tickets: " + vacationDetails[7] +
                        "\n" + "Vacation Kind: " + vacationDetails[8] + "\n" + "Hotel Rate: " + vacationDetails[9]);
                alert.show();
            }else{

            }
        });

        btnAccept = new Button("Accept");
        btnAccept.setOnAction(event -> {
            String[] details = new String[3];
            //purchase accepted
            if(hisVacationID.equals("")){
                details[0] = LogInView.currentUser;
                details[1] = getMyVacationID();
                details[2] = BuyerUserName;
                controller.updateSaleDbRequestForvVacationPermit(details);
                showAlert("the Purchase approve, the confirmation will send to the buyer!");
            }
            //conversion accepted
            else{
                details[0] = LogInView.currentUser;
                details[1] = getMyVacationID();
                details[2] = hisVacationID;
                controller.updateConversionApproved(details);
                showAlert("the Conversion approve, the confirmation will send to the buyer!");
            }
            controller.deleteFromVacationAfterSale(LogInView.currentUser,getMyVacationID());
            //remove the buyer vacation from vacation for sale
            if(!hisVacationID.equals("")){
                controller.deleteFromVacationAfterSale(BuyerUserName,hisVacationID);
            }
        });

        btnDecline = new Button("Decline");
        btnDecline.setOnAction(event -> {
            if(!hisVacationID.equals("")) {
                String[] details = new String[3];
                details[0] = LogInView.currentUser;
                details[1] = getMyVacationID();
                details[2] = hisVacationID;
                controller.declineConversion(details);
                showAlert("Your decline sent to the buyer");
            }
            //decline purchase by cash
            else{
                String[] details = new String[3];
                details[0] = LogInView.currentUser;
                details[1] = getMyVacationID();
                details[2] = BuyerUserName;
                controller.declinePurchase(details);
                showAlert("your decline was sent to the buyer");
            }
        });

    }

    public String getMyVacationID() {
        return myVacationID.get();
    }

    public SimpleStringProperty myVacationIDProperty() {
        return myVacationID;
    }

    public void setMyVacationID(String myVacationID) {
        this.myVacationID.set(myVacationID);
    }

    public String getNewVacationID() {
        return newVacationID;
    }

    public void setNewVacationID(String newVacationID) {
        this.newVacationID = newVacationID;
    }


    public String getUserName() {
        return UserName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName.set(userName);
    }

    public String getVacationID() {
        return myVacationID.get();
    }

    public SimpleStringProperty vacationIDProperty() {
        return myVacationID;
    }

    public void setVacationID(String vacationID) {
        this.myVacationID.set(vacationID);
    }

    public Button getBtnGetDetails() {
        return btnGetDetails;
    }

    public void setBtnGetDetails(Button btnGetDetails) {
        this.btnGetDetails = btnGetDetails;
    }

    public Button getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(Button btnAccept) {
        this.btnAccept = btnAccept;
    }

    public Button getBtnDecline() {
        return btnDecline;
    }

    public void setBtnDecline(Button btnDecline) {
        this.btnDecline = btnDecline;
    }

}
