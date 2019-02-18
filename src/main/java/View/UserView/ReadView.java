package View.UserView;


import View.AController;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.LinkedList;

public class ReadView extends AController {

    public javafx.scene.control.TextField Username;
    public javafx.scene.control.Label User;
    public javafx.scene.control.Button back;


    public void SearchUser() {
        LinkedList<String> details = controller.userDetails(Username.getText());
        if (details == null)
            showAlert("User name does not exist!");
        else{
            Parent root=null;
            String pass = details.get(1);
            String temp="";
            for(int i=0;i<pass.length();i++)
                temp+='*';
            String detail = "Username: "+details.get(0)+'\n'+"Password: "+temp+'\n'+"First name: "+details.get(3)+'\n'+"Last name: "+details.get(4)+'\n'+"City: "+details.get(5);
            TextArea textArea = new TextArea();
            textArea.setText(detail);
            textArea.setStyle("-fx-background: transparent");
            VBox box = new VBox();
            box.getChildren().addAll(textArea);
            Scene scene = new Scene(box, 600, 480);
            scene.getStylesheets().add(getClass().getResource("/TravelApp.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(back.getScene().getWindow());
            stage.show();
            Username.setText("");
        }


    }



    public void backOrigin(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }
}