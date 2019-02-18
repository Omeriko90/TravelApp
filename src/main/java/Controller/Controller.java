package Controller;

import Model.Model;
import View.LogInView;
import javafx.scene.control.DatePicker;
import sun.rmi.runtime.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

//The controller combining between the model and the view, there are no logic function here
public class Controller {
    private Model model;
    //private LogInView log;

    public Controller(){
        model = new Model();
    }

    public String createUser(String userName, String password, String firstName, String lastName, String cityName, DatePicker Date, String Login, byte[] pic) {
        return model.newUser(userName, password, firstName, lastName, cityName, Date, Login,pic);
    }

    public String SearchUserForConnection(String userName) {
        return model.userDetailsForConnection(userName);
    }

    public String updateValue (String columnName, String userName, String newValue){
        return model.updateValue(columnName, userName, newValue);
    }

    public String deleteUser(String username) {
        return model.deleteUser(username);
    }

    public boolean verifyPassword (String userName, String Password) {return model.checkPass(userName, Password);}

    public void updatePass(String userName, String newPass) { model.updatePass(userName, newPass); }

    public String addVacation(ArrayList<String> allVacationDetails) {
        allVacationDetails.add(LogInView.currentUser);
        return model.addVacation(allVacationDetails);
    }

    public ArrayList<String[]> getAllRecords() {
        return model.getAllRecords();
    }

    //this function returns all vacations that current user published
    public ArrayList<String[]> getAllVacations(String filterChoice, String filterChoiceForSearch) {
        return model.getAllVacations();
    }

    /**
     * sends to the model request for purchase the wanted vacation by vacation ID
     * @param vacationDetails
     * @return
     */
    public void addRequest(ArrayList<String> vacationDetails) {
        model.addRequest(vacationDetails);
    }

    public String[] getAllvacationDetails(String userName , String vacationID){
        return model.getVacationDetails(userName,vacationID);
    }


    //this function returns all vacations that current user published
    public ArrayList<String> getAllUserVacations() throws SQLException {
        return model.getAllUserVacations(LogInView.currentUser);
    }

    public void deleteVacation(String vacationIdtoDelete) {
        model.deleteVacation(vacationIdtoDelete);
    }

    public byte[] getPicture(String userName) {
        return model.getPicture(userName);
    }
    public LinkedList<String> userDetails(String userName) {
        return model.userDetails(userName);
    }

    //getting all new requests for purchase from db
    public ArrayList<String[]> FoundRequetForPurchase(String currentUser) {
         return model.FoundRequetForPurchase(currentUser);
    }

    public void updateSaleDbRequestForvVacationPermit(String[] details) {
         model.updateSaleDbRequestForvVacationPermit(details);
    }

    public void updateConversionApproved(String[] details){
        model.updateConversionApproved(details);
    }

    public void deleteFromVacationAfterSale(String currentUser , String vacationID) {
        model.deleteFromVacationAfterSale(currentUser, vacationID);
    }

    public void declineConversion(String[] details){
        model.declineConversion(details);
    }

    public void declinePurchase(String[] details){
        model.declinePurchase(details);
    }

    //gets from the DB all confirmations of the current user
    public ArrayList<String[]> queryApprove(String currentUser) {
        return model.queryApprove(currentUser);
    }

    //gets from the model all Conversions Confirmation of current user
    public ArrayList<String[]> gelAllConversionsApproval(String currentUser){
        return model.getAllConversionsApproved(currentUser);
    }

    public void updateSaleDbRequestForSendApprovmentToBuyer(String currentUser) {
        model.updateSaleDbRequestForSendApprovmentToBuyer(currentUser);
    }

    public void updateThetConfirmationSentToBuyer(String userName){
        model.updateThetConfirmationSentToBuyer(userName);
    }

    public void updateSaleDbRequestForApproval(String currentUser) {
        model.updateSaleDbRequestForApproval(currentUser);
    }

    public void sendRequestForReplacement(ArrayList<String> allDetailsForConversion) {
        model.addRequestForReplacement(allDetailsForConversion);
    }

    public ArrayList<String[]> getAllConversionRequests() {
        return model.getAllUserConversionsRequests(LogInView.currentUser);
    }

    //gets all conversions decline of current user
    public ArrayList<String[]> getConversionsDecline(String currentUser) {
        return model.getAllConversionsDecline(currentUser);
    }

    public ArrayList<String[]> getAllPurchaseDeclines(String currentUser) {
        return model.getPurchaseDeclines(currentUser);
    }

    public void updateDeclineConversionsSentToBuyer(String currentUser) {
        model.updateDeclineConversionsSentToBuyer(currentUser);
    }

    public void updateDeclinePurchaseSentToBuyer(String currentUserName){
        model.updateDeclinePurchaseSentToBuyer(currentUserName);
    }
}
