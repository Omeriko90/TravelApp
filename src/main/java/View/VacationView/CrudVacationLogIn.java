package View.VacationView;

import View.AController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.ArrayList;


public class CrudVacationLogIn extends AController {

    public javafx.scene.control.Button btnCreateVacation;
    public javafx.scene.control.Button btnDeleteVacation;
    public javafx.scene.control.Button btnDisplayVacations;
    public javafx.scene.control.Button back;


    ArrayList<String[]> allrecords=null;

    @FXML
    public TableView table = new TableView();


    public void DeleteWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/Vacation Fxml/DeleteVacation.fxml"));
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

    public void createWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/Vacation Fxml/CreateVacation.fxml"));
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


    public void DisplayWindow(ActionEvent actionEvent) {
        try {
            table = new TableView();
            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setTitle("Vacations ");
            stage.setWidth(1100);
            stage.setHeight(800);
            final Label lable = new Label("All Vacations for sale:");
            lable.setFont(new Font("Gabriola", 24));
            table.setEditable(false);

            TableColumn UserName = new TableColumn("User Name");
            UserName.setMinWidth(40);
            UserName.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, String>("UserName"));

            TableColumn VacationID = new TableColumn("Vacation number");
            VacationID.setMinWidth(40);
            VacationID.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, String>("VacationID"));

            TableColumn FromDate = new TableColumn("From Date");
            FromDate.setMinWidth(40);
            FromDate.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, String>("FromDate"));

            TableColumn ToDate = new TableColumn("To Date");
            ToDate.setMinWidth(40);
            ToDate.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, String>("ToDate"));

            TableColumn Destination = new TableColumn("Destination");
            Destination.setMinWidth(100);
            Destination.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, String>("Destination"));

            TableColumn details = new TableColumn("Details");
            details.setMinWidth(200);
            details.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, Button>("btnGetDetails"));

            TableColumn purchaseButton = new TableColumn("Buy");
            purchaseButton.setMinWidth(200);
            purchaseButton.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, Button>("btnPurchaseByCash"));

            TableColumn replaceButton = new TableColumn("Buy by replace");
            replaceButton.setMinWidth(200);
            replaceButton.setCellValueFactory(new PropertyValueFactory<VacationDisplayer, Button>("btnPurchaseByReplace"));

            table.setItems(getData());
            table.getColumns().addAll(UserName,VacationID, FromDate, ToDate, Destination , details ,purchaseButton,replaceButton);
            table.setMinHeight(600);
            table.setMinWidth(1000);

            final VBox vbox = new VBox();
            vbox.setSpacing(20);
            vbox.setPadding(new Insets(20, 0, 10, 10));
            vbox.getChildren().addAll(lable, table);

            ((Group) scene.getRoot()).getChildren().addAll(vbox);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //back button
    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    private ObservableList<VacationDisplayer> getData() {
        allrecords=controller.getAllVacations("","");
        ObservableList<VacationDisplayer> rec = FXCollections.observableArrayList();
        for (String[] record:allrecords) {
            rec.add(new VacationDisplayer(record));//record[0],record[1], record[2],record[3],record[4]));
        }
        return rec;
    }

    public void inboxWindow(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader myLoader = new FXMLLoader();
            myLoader.setLocation(getClass().getResource("/fxml/Vacation Fxml/VacationsInbox.fxml"));
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


}
