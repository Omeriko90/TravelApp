package View.VacationView;

import View.AController;
import View.LogInView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VacationDisplayer extends AController {


    private final SimpleStringProperty UserName;
    private final SimpleStringProperty VacationID;
    private final SimpleStringProperty FromDate;
    private final SimpleStringProperty ToDate;


    private final SimpleStringProperty Destination;
    private Button btnGetDetails;
    private Button btnPurchaseByCash;
    private Button btnPurchaseByReplace;

    @FXML
    public TableView table = new TableView();

    public  ArrayList<String > currentVacationDetails;


    public VacationDisplayer(String [] allVacationDetail){//String username, String vacationid, String fromDate, String toDate, String destination) {

        this.UserName = new SimpleStringProperty(allVacationDetail[0]);
        this.VacationID = new SimpleStringProperty(allVacationDetail[1]);
        this.FromDate = new SimpleStringProperty(allVacationDetail[2]);
        this.ToDate = new SimpleStringProperty(allVacationDetail[3]);
        this.Destination = new SimpleStringProperty(allVacationDetail[4]);
        btnGetDetails = new Button("Get vacation details");
        btnGetDetails.setVisible(true);
        btnGetDetails.setDisable(false);
        btnPurchaseByCash = new Button("Purchase by cash");
        btnPurchaseByCash.setDisable(false);
        btnPurchaseByCash.setVisible(true);
        btnPurchaseByReplace = new Button("Purchase by replace");
        btnPurchaseByReplace.setVisible(true);
        btnPurchaseByReplace.setDisable(false);

        currentVacationDetails=new ArrayList<>();
        currentVacationDetails.add(0,allVacationDetail[1]);
        currentVacationDetails.add(1,allVacationDetail[0]);
        currentVacationDetails.add(2,LogInView.currentUser);

        btnGetDetails.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vacation Price: " + allVacationDetail[5] +"\n" + "Flight company: " + allVacationDetail[6]
            +"\n"+ "Include hotel: " + allVacationDetail[7] + "\n" + "Include bags: "+ allVacationDetail[8] + "\n"
            +"Number of tickets: "+ allVacationDetail[9] + "\n" + "Seller type: " +allVacationDetail[10] + "\n"
            +"Vacation kind: "+allVacationDetail[11] + "\n" +"Hotel rate: " + allVacationDetail[11]);
            alert.show();
        });

        btnPurchaseByCash.setOnAction(event -> {
            ArrayList <String> vacationDetails = new ArrayList<>();
            if(!LogInView.currentUser.equals(getUserName())) {
                vacationDetails.add(0, allVacationDetail[1]);
                vacationDetails.add(1, LogInView.currentUser);
                controller.addRequest(vacationDetails);
                showAlert("The request sent to the vacation owner, you will get confirmation in your inbox");
            }else{
                showAlert("sorry, you cant purchase your vacations");
            }
        });
        //display all current user vacations to choose which one to replace
        btnPurchaseByReplace.setOnAction(event -> {
            if(!LogInView.currentUser.equals(getUserName())) {
                try {
                    ArrayList<String> allUserVacations = controller.getAllUserVacations();
                    ObservableList<VacationChooserDisplayer> vacationsForReplace = FXCollections.observableArrayList();
                    for (String record : allUserVacations) {
                        vacationsForReplace.add(new VacationChooserDisplayer(record,currentVacationDetails));//record[0],record[1], record[2],record[3],record[4]));
                    }

                    table = new TableView();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Vacation Options");
                    stage.setWidth(600);
                    stage.setHeight(600);
                    final Label lable = new Label("Please choose which vacation you want to replace by:");
                    lable.setFont(new Font("Gabriola", 24));
                    table.setEditable(false);

                    TableColumn vacationID = new TableColumn("Vacation ID");
                    vacationID.setMinWidth(100);
                    vacationID.setCellValueFactory(new PropertyValueFactory<VacationChooserDisplayer, String>("vacationID"));

                    TableColumn checkBox = new TableColumn("");
                    checkBox.setMinWidth(40);
                    checkBox.setCellValueFactory(new PropertyValueFactory<VacationChooserDisplayer, CheckBox>("vacationChooser"));

                    table.setItems(vacationsForReplace);
                    table.getColumns().addAll(vacationID, checkBox);
                    table.setMinHeight(500);
                    table.setMinWidth(300);

                    final VBox vbox = new VBox();
                    vbox.setSpacing(20);
                    vbox.setPadding(new Insets(20, 0, 10, 10));
                    vbox.getChildren().addAll(lable, table);

                    ((Group) scene.getRoot()).getChildren().addAll(vbox);
                    stage.setScene(scene);
                    stage.show();


                } catch (Exception e) {

                }
                //controller.sendRequestForReplacement(allDetailsForReplace);
            }else{
                showAlert("sorry, you cant purchase your vacations");
            }
        });
    }

    public String getVacationID() {
        return VacationID.get();
    }

    public SimpleStringProperty vacationIDProperty() {
        return VacationID;
    }

    public void setVacationID(String vacationID) {
        this.VacationID.set(vacationID);
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

    public String getFromDate() {
        return FromDate.get();
    }

    public SimpleStringProperty fromDateProperty() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        this.FromDate.set(fromDate);
    }

    public String getToDate() {
        return ToDate.get();
    }

    public SimpleStringProperty toDateProperty() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        this.ToDate.set(toDate);
    }

    public String getDestination() {
        return Destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return Destination;
    }

    public void setDestination(String destination) {
        this.Destination.set(destination);
    }

    public Button getBtnGetDetails() {
        return btnGetDetails;
    }

    public void setBtnGetDetails(Button btnGetDetails) {
        this.btnGetDetails = btnGetDetails;
    }

    public Button getBtnPurchaseByCash() {
        return btnPurchaseByCash;
    }

    public void setBtnPurchaseByCash(Button btnPurchaseByCash) {
        this.btnPurchaseByCash = btnPurchaseByCash;
    }

    public Button getBtnPurchaseByReplace() {
        return btnPurchaseByReplace;
    }

    public void setBtnPurchaseByReplace(Button btnPurchaseByReplace) {
        this.btnPurchaseByReplace = btnPurchaseByReplace;
    }

}
