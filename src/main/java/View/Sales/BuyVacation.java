package View.Sales;

import View.AController;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BuyVacation extends AController {

    public javafx.scene.control.Button btnSendRequest;
    public javafx.scene.control.Button btnBack;
    public javafx.scene.control.TextField txtVacationId;
    public javafx.scene.control.TextField txtCreditCard;

    public void sendRequest(ActionEvent actionEvent){/*
        //the answer after check the vacationId and credit number
        boolean empty=false;
        ArrayList<String>detailsRequest=new ArrayList<>();
        if(txtVacationId.getText().length()==0 || txtCreditCard.getText().length()==0)
            showAlert("Must enter content in fields");
        else {
            empty=true;
            detailsRequest.add(txtVacationId.getText());
            detailsRequest.add(txtCreditCard.getText());
        }
        if(empty) {
            String ans = controller.addRequest(detailsRequest);
            if (ans.equals("vacation"))
                showAlert("vacation id not found, please try again");
            else if (ans.equals("credit"))
                showAlert("credit card number is illegal, please try again");
            else
                showAlert("The request for purchase sent to the vacation owner, you will get the approvement in your inbox");
        }
        */
    }

    //back button
    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }



}
