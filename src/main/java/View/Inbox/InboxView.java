package View.Inbox;

import View.AController;
import View.LogInView;
import View.VacationView.ConversionsDisplayer;
import View.VacationView.VacationChooserDisplayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InboxView extends AController {

    public javafx.scene.control.Button btnGetAllRequestsForPurchase;
    public javafx.scene.control.Button btnGetAllRequestsForConversions;
    public javafx.scene.control.Button btnBack;
    public javafx.scene.control.Button btnGetAllConfirmations;

    @FXML
    public TableView tableForConversions = new TableView();
    public TableView tableForPurchase = new TableView();

    public void getAllRequestsForConversion() {
        tableForConversions = new TableView();
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("All Conversions Requests");
        stage.setWidth(800);
        stage.setHeight(800);
        final Label lable = new Label("Please Confirm or decline your requests:");
        lable.setFont(new Font("Gabriola", 24));
        tableForConversions.setEditable(false);

        TableColumn vacationID = new TableColumn("Your Vacation ID");
        vacationID.setMinWidth(150);
        vacationID.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, String>("myVacationID"));

        TableColumn fromUserName = new TableColumn("Asked From");
        fromUserName.setMinWidth(100);
        fromUserName.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, String>("UserName"));

        TableColumn vacationDetails = new TableColumn("Vacation details");
        vacationDetails.setMinWidth(150);
        vacationDetails.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, Button>("btnGetDetails"));

        TableColumn acceptReq = new TableColumn("Accept");
        acceptReq.setMinWidth(100);
        acceptReq.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, Button>("btnAccept"));

        TableColumn declineReq = new TableColumn("Decline");
        declineReq.setMinWidth(100);
        declineReq.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, Button>("btnDecline"));


        tableForConversions.setItems(getDataForConversions());
        tableForConversions.getColumns().addAll(vacationID, fromUserName, vacationDetails, acceptReq, declineReq);
        tableForConversions.setMinWidth(700);
        tableForConversions.setMinHeight(700);

        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20, 0, 10, 10));
        vbox.getChildren().addAll(lable, tableForConversions);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList getDataForConversions() {
        ArrayList<String[]> allRquestsForConversions = controller.getAllConversionRequests();
        ObservableList<ConversionsDisplayer> requests = FXCollections.observableArrayList();
        for (String[] request : allRquestsForConversions) {
            requests.add(new ConversionsDisplayer(request[0], request[2], request[1]));
        }
        return requests;
    }

    public void getAllRequestsForPurchase() {
        tableForPurchase = new TableView();
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("All Purchase Requests");
        stage.setWidth(800);
        stage.setHeight(800);
        final Label lable = new Label("Please Confirm or decline your requests:");
        lable.setFont(new Font("Gabriola", 24));
        tableForPurchase.setEditable(false);

        TableColumn vacationID = new TableColumn("Your Vacation ID");
        vacationID.setMinWidth(150);
        vacationID.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, String>("myVacationID"));

        TableColumn fromUserName = new TableColumn("Asked From");
        fromUserName.setMinWidth(100);
        fromUserName.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, String>("UserName"));

        /*TableColumn vacationDetails = new TableColumn("Vacation details");
        vacationDetails.setMinWidth(100);
        vacationDetails.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer,Button>("btnGetDetails"));
*/
        TableColumn acceptReq = new TableColumn("Accept");
        acceptReq.setMinWidth(100);
        acceptReq.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, Button>("btnAccept"));

        TableColumn declineReq = new TableColumn("Decline");
        declineReq.setMinWidth(100);
        declineReq.setCellValueFactory(new PropertyValueFactory<ConversionsDisplayer, Button>("btnDecline"));


        tableForPurchase.setItems(getDataForPurchase());
        tableForPurchase.getColumns().addAll(vacationID, fromUserName, acceptReq, declineReq);
        tableForPurchase.setMinWidth(700);
        tableForPurchase.setMinHeight(700);

        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20, 0, 10, 10));
        vbox.getChildren().addAll(lable, tableForPurchase);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();

    }

    //getting all the requests for purchase for current user
    private ObservableList getDataForPurchase() {
        ArrayList<String[]> allRquestsForPurchase = controller.FoundRequetForPurchase(LogInView.currentUser);
        ObservableList<ConversionsDisplayer> requests = FXCollections.observableArrayList();
        for (String[] request : allRquestsForPurchase) {
            requests.add(new ConversionsDisplayer(request[0], request[1], ""));
        }
        return requests;
    }

    //back button
    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    //get all confirmations for purchase or conversions of current user
    public void getAllConfirmations() {
        ArrayList<String[]> allNewPurchaseConfirmations = controller.queryApprove(LogInView.currentUser);
        String[] record;
        if (allNewPurchaseConfirmations.size() > 0) {
            for (int i = 0; i < allNewPurchaseConfirmations.size(); i++) {
                record = allNewPurchaseConfirmations.get(i);
                showAlert("your request for Purchase vacationID " + record[0] + "\n " +
                        "in date " + record[2] + "\n" +
                        " was approve by " + record[1]);
            }
        }
        controller.updateSaleDbRequestForSendApprovmentToBuyer(LogInView.currentUser);
        ArrayList<String[]> allNewConversionsConfirmations = controller.gelAllConversionsApproval(LogInView.currentUser);
        if (allNewConversionsConfirmations.size() > 0) {
            for (int i = 0; i < allNewConversionsConfirmations.size(); i++) {
                record = allNewConversionsConfirmations.get(i);
                showAlert("your request for conversion your vacation number " + record[1] + "\n " +
                        "with vacation number " + record[0] + "\n" +
                        " in date " + record[3] + "\n" +
                        " was approve by " + record[2]);
            }
            controller.updateThetConfirmationSentToBuyer(LogInView.currentUser);
        } else {
            showAlert("No new confirmations in you inbox");
        }
    }

    //get all decline for purchase and conversions of current user
    public void getDeclinesList() {
        ArrayList<String[]> allDeclinesForConversions = controller.getConversionsDecline(LogInView.currentUser);
        String[] record;
        if (allDeclinesForConversions.size() > 0) {
            for (int i = 0; i < allDeclinesForConversions.size(); i++) {
                record = allDeclinesForConversions.get(i);
                showAlert("your request for conversion your vacation number " + record[1] + "\n " +
                        "with vacation number " + record[0] + "\n" +
                        " in date " + record[3] + "\n" +
                        " was decline by " + record[2]);
            }
            controller.updateDeclineConversionsSentToBuyer(LogInView.currentUser);
        }
        ArrayList<String[]> allDeclinesForPurchase = controller.getAllPurchaseDeclines(LogInView.currentUser);
        if (allDeclinesForPurchase.size() > 0) {
            for (int i = 0; i < allDeclinesForPurchase.size(); i++) {
                record = allDeclinesForPurchase.get(i);
                showAlert("your request for purchase vacationID " + record[0] + "\n " +
                        "in date " + record[2] + "\n" +
                        " was decline by " + record[1]);
            }
            controller.updateDeclinePurchaseSentToBuyer(LogInView.currentUser);
        } else {
            showAlert("No declines in you inbox");
        }
    }



}
