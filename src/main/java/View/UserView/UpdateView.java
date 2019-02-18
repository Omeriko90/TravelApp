package View.UserView;

import View.AController;
import View.LogInView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;


public class UpdateView extends AController {

    public String currentUserName="";
    public javafx.scene.control.Label WantedField;
    public ComboBox<String> comboBox;
    public javafx.scene.control.Label Value;
    public javafx.scene.control.TextField newValue;
    public javafx.scene.control.Label pass;
    public javafx.scene.control.TextField newPass;
    public javafx.scene.control.Label verify;
    public javafx.scene.control.TextField verifyPass;
    public javafx.scene.control.DatePicker Date;
    public javafx.scene.control.Button btnUpdate;
    public javafx.scene.control.Button back;
    public LogInView logInView = new LogInView();

    public void connect() {
        comboBox.setVisible(true);
        comboBox.setDisable(false);
    }


    //function that check which column the user want to update and check the value insert
    public void checkAndUpdate() {
        currentUserName = logInView.getUserName();
        comboBoxChoice();
        String ans = "";
        if (comboBox.getValue() != "DOB")
            ans = controller.updateValue(comboBox.getValue(), currentUserName, newValue.getText());
        else
            ans = controller.updateValue(comboBox.getValue(), currentUserName, Date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        if (ans == "Username")
            showAlert("Username can contains only Letters.");
        else if (ans == "password")
            showAlert("Incorrect password. Please try again.");
        else if (comboBox.getValue().equals("password") && !newPass.getText().equals(verifyPass.getText()))
            showAlert("The verify password different from the new password.\nPlease try again");
        else if (comboBox.getValue().equals("password") && newPass.getText().equals(verifyPass.getText()) && newPass.getText().length() < 6) {
            showAlert("password must be in length 6 or more");
        } else if (ans == "date")
            showAlert("Wrong DOB. Please try again.");
        else if(ans.equals("dateAdult"))
            showAlert("Age has to be bigger then 18 ");
        else if (ans == "firstName")
            showAlert("Invalid first name");
        else if (ans == "lastName")
            showAlert("Invalid last name");
        else if (ans == "city")
            showAlert("Invalid city name");
        else if(ans.equals("checkUserName")){
            showAlert("User name already exist, please try with a new one!");
        }
        else {
            if (comboBox.getValue() == "Password")
                controller.updatePass(currentUserName, verifyPass.getText());
            showAlert("Update Done!");
            initialFields();
        }
        comboBox.setVisible(true);
        comboBox.setDisable(false);
        newValue.setText("");
        newPass.setText("");
        verifyPass.setText("");
    }

    public void initialize() {
        comboBox.getItems().addAll("Username", "Password", "DOB", "First", "Last", "City");
    }

    @FXML
    public void comboBoxChoice() {
        //if (LogInView.isLogingIn) {
            btnUpdate.setVisible(true);
            btnUpdate.setDisable(false);
            //User.setText("");
            newValue.setDisable(false);
            newValue.setVisible(true);
            Value.setDisable(false);
            Value.setVisible(true);
            if (comboBox.getValue().equals("Username")) {
                Value.setText("New User Name:");
                Date.setDisable(true);
                Date.setVisible(false);
                passOnOrOff(true, false);
            } else if (comboBox.getValue().equals("Password")) {
                Value.setText("Current Password:");
                Date.setDisable(true);
                Date.setVisible(false);
                passOnOrOff(false, true);
            } else if (comboBox.getValue().equals("DOB")) {
                Value.setText("New Birth Date:");
                Date.setDisable(false);
                Date.setVisible(true);
                newValue.setDisable(true);
                newValue.setVisible(false);
                passOnOrOff(true, false);
            } else if (comboBox.getValue().equals("First")) {
                Value.setText("New First Name:");
                Date.setDisable(true);
                Date.setVisible(false);
                passOnOrOff(true, false);
            } else if (comboBox.getValue().equals("Last")) {
                Value.setText("New Last Name:");
                Date.setDisable(true);
                Date.setVisible(false);
                passOnOrOff(true, false);
            } else if (comboBox.getValue().equals("City")) {
                Value.setText("New City Name:");
                Date.setDisable(true);
                Date.setVisible(false);
                passOnOrOff(true, false);
            }
        } //else showAlert("no User Logged In , Please Log In and try again");
    //}

    private void passOnOrOff(boolean dis, boolean vis) {
        pass.setDisable(dis);
        pass.setVisible(vis);
        newPass.setDisable(dis);
        newPass.setVisible(vis);
        verify.setDisable(dis);
        verify.setVisible(vis);
        verifyPass.setDisable(dis);
        verifyPass.setVisible(vis);
    }

    private void initialFields() {
        comboBox.setDisable(true);
        comboBox.setVisible(false);
        newValue.setDisable(true);
        newValue.setVisible(false);
        Value.setDisable(true);
        Value.setVisible(false);
        btnUpdate.setDisable(true);
        btnUpdate.setVisible(false);
        passOnOrOff(true, false);
    }

    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }
}
