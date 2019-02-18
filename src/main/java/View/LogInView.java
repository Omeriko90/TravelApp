package View;


import Model.SqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogInView extends AController {

    public javafx.scene.control.TextField Username;
    public javafx.scene.control.Label User;
    public javafx.scene.control.Button back;
    public javafx.scene.control.TextField UserPassword;
    public javafx.scene.control.Label Password;
    public javafx.scene.control.Button btnLogIn;
    public javafx.scene.control.Button backLogIn;
    public javafx.scene.control.CheckBox saleVacation;
    public javafx.scene.control.Button btnContinue;
    static public String currentUser = "";
    static boolean isLogingIn = false;


    public String getUserName() {
        return currentUser;
    }

    @FXML
    public void initialize() {
        saleVacation.setVisible(false);
        btnContinue.setVisible(false);
        btnContinue.setDisable(true);
    }

    public void connect(ActionEvent actionEvent) {
        boolean approvment = false;
        Connection connection = SqlConnection.ConnectorSales();
        if (controller.SearchUserForConnection(Username.getText()) == "")
            showAlert("User name not found!");
        else if (!controller.verifyPassword(Username.getText(), UserPassword.getText()))
            showAlert("Wrong Password!");
        else {
            showAlert("You're in :)");
            btnContinue.setDisable(false);
            btnContinue.setVisible(true);
            currentUser = Username.getText();
            isLogingIn = true;
            /*boolean foundRequetForPurchase=controller.FoundRequetForPurchase(currentUser);
            if(foundRequetForPurchase) {
                saleVacation.setVisible(true);
                approvment = true;
            }
            if (approvment) {
                controller.updateSaleDbRequestForvVacationPermit(currentUser);
                controller.deleteFromVacationAfterSale(currentUser);
            }
            boolean InboxKnowing=controller.queryApprove(currentUser);
            if(InboxKnowing){
                showAlert("Your vacation purchase request has been successfully approved. The purchase was made");
                controller.updateSaleDbRequestForSendApprovmentToBuyer(currentUser);
            }
*/
            Username.setText("");
            UserPassword.setText("");
        }
    }





    public void updateSaleDbRequestForApproval(ActionEvent actionEvent) {
        if(saleVacation.isSelected())
            controller.updateSaleDbRequestForApproval(currentUser);
    }

    public void Continue() {
        DisplayCrudUserWindow();
    }

    private void DisplayCrudUserWindow() {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/CrudAfterLogIn.fxml"));
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

    public boolean isConnect() {
        return isLogingIn;
    }

    public void backOriginStartWindow(ActionEvent event) throws IOException {
        saleVacation.setVisible(false);
        Stage stage = (Stage) backLogIn.getScene().getWindow();
        stage.close();
    }


}
