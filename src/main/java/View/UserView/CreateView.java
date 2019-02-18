package View.UserView;

import View.AController;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;

public class CreateView extends AController {

    public javafx.scene.control.TextField FirstName;
    public javafx.scene.control.TextField LastName;
    public javafx.scene.control.TextField Username;
    public javafx.scene.control.TextField City;
    public javafx.scene.control.PasswordField Password;
    public javafx.scene.control.DatePicker Date;
    public javafx.scene.control.TextArea Notes;
    public javafx.scene.control.Button back;
    public javafx.scene.control.TextField profilePic;
    private byte[] pic;

    //The different alerts send to the user after check the inputs value in the create scene
    public void addUser () {
        if (requiredFields()) {
            String ans = controller.createUser(Username.getText(), Password.getText(), FirstName.getText(), LastName.getText(), City.getText(), Date,"0",pic);
            if (ans == "userName")
                showAlert("User Name already exist. Please choose other user name.");
            else if (ans == "password")
                showAlert("Short password. Please choose password with at least 6 charactes");
            else if (ans == "date")
                showAlert("Hi there someone that's born in the future.\nPlease put the right DOB");
            else if(ans=="adult")
                showAlert("App is limited to 18+ years old");
            else if (ans == "firstName" || ans == "lastName" || ans == "city")
                showAlert(ans + " Name is illegal. Please try again");
            else if(ans.equals("pic"))
                showAlert("You didn't choose profile picture");
            else {
                showAlert("User added successfully!");
                Username.setText("");
                Password.setText("");
                FirstName.setText("");
                LastName.setText("");
                Date.setValue(null);
                City.setText("");
                Notes.setText("");
                Notes.setVisible(false);
                profilePic.setText("");

            }
        }
    }

    //After we finish our operation on the create scene, this function clean the text field
    private void clearStyle() {
        Username.setStyle("-fx-border-width: 0px");
        Password.setStyle("-fx-border-width: 0px");
        FirstName.setStyle("-fx-border-width: 0px");
        LastName.setStyle("-fx-border-width: 0px");
        City.setStyle("-fx-border-width: 0px");
        Date.setStyle("-fx-border-width: 0px");
        profilePic.setStyle("-fx-border-width: 0px");
        Notes.setText("");
    }
    //function that required from the user to insert the current field value
    private boolean requiredFields() {
        clearStyle();
        String note = "";
        if(Username.getText().length() == 0){
            Username.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please enter Username\n";
        }
        if(Password.getText().length() == 0){
            Password.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please enter Passowrd\n";
        }
        if(FirstName.getText().length() == 0){
            FirstName.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please enter your first name\n";
        }
        if(LastName.getText().length() == 0){
            LastName.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please enter your last name\n";
        }
        if(City.getText().length() == 0){
            City.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please enter the name of your city\n";
        }
        if(Date.getValue() == null){
            Date.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please enter your birthday date\n";
        }
        if(pic==null){
            profilePic.setStyle("-fx-border-color: #fb2520; -fx-border-style: solid; -fx-border-width: 1px;");
            note+="Please choose a profile picture";
        }
        Notes.setVisible(true);
        Notes.setText(note);
        return (note.length()==0);
    }
    //back button
    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void choosePicture(ActionEvent actionEvent) {
        LinkedList<String> extensions = new LinkedList<>();
        extensions.add("jpeg");extensions.add("png");extensions.add("jpg");
        JFileChooser fileChooser = new JFileChooser();
        boolean first = true;
        String extention = "no extension";
        do {
            if(!first) {
                showAlert("Please choose a file with the extension of jpg/jpeg/png");
            }
            fileChooser.showOpenDialog(null);

            if(fileChooser.getSelectedFile()!=null) {
                String photo = fileChooser.getSelectedFile().getName();
                extention = photo.substring(photo.indexOf('.') + 1);
            }
            first = false;
        }while (!extensions.contains(extention.toLowerCase()));
        File f = fileChooser.getSelectedFile();
        profilePic.setText(f.getAbsolutePath());
        File image = new File(f.getAbsolutePath());
        try {
            FileInputStream fis =  new FileInputStream(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            for(int readNum;(readNum=fis.read(buff))!=-1;)
                baos.write(buff,0,readNum);
            pic = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}