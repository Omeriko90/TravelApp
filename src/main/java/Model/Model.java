package Model;

import javafx.scene.control.DatePicker;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class Model {

    private Connection conn;
    private Vacation vacation;
    private Sale sale;
    private Conversions conversions;

    public Model() {
        vacation=new Vacation();
        sale=new Sale();
        conversions = new Conversions();
    }

    //A function that accepts values ​​from the user to creating a new user in the system
    //This function send the inputs value to different check's functions
    public String newUser(String userName, String password, String firstName, String lastName, String cityName, DatePicker Date, String Login, byte[] pic) {
        String birthDate = Date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (checkUsername(userName))
            return "userName";
        if (password.length() < 6)
            return "password";
        if (!checkDate(birthDate))
            return "date";
        if (!checkAdults(birthDate))
            return "adult";
        if (!checkString(firstName))
            return "firstName";
        if (!checkString(lastName))
            return "lastName";
        if (!checkString(cityName))
            return "city";
        //after checking insert the values to the database
        String query = "insert into Users (Username,Password,DOB,First,Last,City,logIn,Profile_Picture) values(?,?,?,?,?,?,?,?)";
        try {
            conn = SqlConnection.Connector();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,userName);
            pst.setString(2,password);
            pst.setString(3,birthDate);
            pst.setString(4,firstName);
            pst.setString(5,lastName);
            pst.setString(6,cityName);
            pst.setString(7,Login);
            pst.setBytes(8,pic);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    //Create button - This function check if the current user name already exists in the DB
    public boolean checkUsername(String username) {
        String query = "select count(*) as found from Users where Username='" + username + "'";
        try {
            conn = SqlConnection.Connector();
            PreparedStatement pst = conn.prepareStatement(query);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    boolean found = rs.getBoolean(1);
                    if (found)
                        return true;
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Read button - This function return to the user his details from the DB
    public String userDetailsForConnection(String username) {
        conn = SqlConnection.Connector();
        if (checkUsername(username)) {
            String query = "select * from Users where Username='" + username + "'";
            String UserFound = "";
            try {
                Statement pst = conn.createStatement();
                try (ResultSet rs = pst.executeQuery(query)) {
                    ResultSet nrs = pst.executeQuery(query);
                    UserFound = UserFound + nrs.getString(1);
                    for (int i = 3; i <= 6; i++) {
                        UserFound = UserFound + " , " + nrs.getString(i);
                    }
                    conn.close();
                    return ("User's Details:\n " + UserFound);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public LinkedList<String> userDetails(String username) {
        conn = SqlConnection.Connector();
        LinkedList<String> userDetails = new LinkedList<>();
        if (checkUsername(username)) {
            String query = "select * from Users where Username=\"" + username + "\"";
            try {
                Statement pst = conn.createStatement();
                try (ResultSet rs = pst.executeQuery(query)) {
                    ResultSet nrs = pst.executeQuery(query);
                    for(int i=1;i<8;i++)
                        userDetails.add(nrs.getString(i));
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userDetails;
    }

    //Update button - This function update in the DB the value that the user choose to change
    //this function send to the check's functions too
    public String updateValue(String columnName, String userName, String newValue) {
        if (columnName == "Password") {
            if (!checkPass(userName, newValue))
                return "password";
        } else if (columnName == "DOB") {
            if (!checkDate(newValue))
                return "date";
            if(!checkAdults(newValue))
                return "dateAdult";
        } else if (!checkString(newValue))
            return columnName;
        else if (checkUsername(newValue))
            return "checkUserName";
        String query = "update Users set " + columnName + "=" + "'" + newValue + "' where Username='" + userName + "'";
        try {
            conn = SqlConnection.Connector();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void updatePass(String userName, String newPass) {
        conn = SqlConnection.Connector();
        String query = "update Users set Password =" + "\"" + newPass + "\" where Username=\"" + userName + "\"";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete button- this function delete the user's details from the DB according to the userName received from the user
    public String deleteUser(String userName) {
        conn = SqlConnection.Connector();
        if (checkUsername(userName)) {
            String query = "delete from Users where Username=\"" + userName + "\"";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.execute();
                conn.close();
                vacation.deleteAllUserVacations(userName);
                return "";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "not exist";
    }

    //when the user wants to update his password, he needs to type his old password and we check if it is correct before changing
    public boolean checkPass(String userName, String currPass) {
        conn = SqlConnection.Connector();
        String query = "select Password from Users where Username='" + userName + "'";
        String originalPass = "";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            originalPass = rs.getString(1);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return originalPass.equals(currPass);
    }

    //The function checks whether the date's input makes sense
    public boolean checkDate(String date) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.parse(date, dtf);
        return today.isAfter(currentDate);
    }

    //check if the user is adults
    public boolean checkAdults(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.parse(date, dtf);
        String string = "01-10-2000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate grownUp = LocalDate.parse(string, formatter);
        return grownUp.isAfter(currentDate);
    }

    //check that the input in the name/last name/ city is only a letters
    public boolean checkString(String value) {
        String check = value;
        check = check.toLowerCase();
        for (int i = 0; i < check.length(); i++) {
            if (check.charAt(i) != ' ' && (check.charAt(i) > 'z' || check.charAt(i) < 'a')) {
                return false;
            }
        }
        return true;
    }
    public boolean checkDateForVacation(String fromDate ,String toDate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fromDater = LocalDate.parse(fromDate, dtf);
        LocalDate toDater = LocalDate.parse(toDate, dtf);
        return (fromDater.isAfter(today) && toDater.isAfter(today) && toDater.isAfter(fromDater));
    }

    public boolean checkOnlyNumbers(String price){
        for(int i=0;i<price.length();i++){
            if(price.charAt(i)<'0' || price.charAt(0)>'9')
                return false;
        }
        return true;
    }

    //the function making tests for the field and pass the fields for insert in the vacation layer
    public String addVacation(ArrayList<String> allVacationDetails) {
        if(!checkDateForVacation(allVacationDetails.get(0),allVacationDetails.get(1)))
            return "date";
        if (!checkString(allVacationDetails.get(2)))
            return "destination";
        if(!checkOnlyNumbers(allVacationDetails.get(3)))
            return "price";
        vacation.create(allVacationDetails);
        return "";
    }

    //return all the columns from the DB vacation according to the filterChoiceForSearch
    public ArrayList<String[]> getAllRecords() {
        vacation.search("");
        return vacation.getOutPutSearch();
    }

    //return all the vacation details from the DB vacation according to the filterChoice
    public ArrayList<String[]> getAllVacations() {
        vacation.search("");
        return vacation.getOutPutSearch();
    }

    //this function add the request to the sales data base with 1 in the column of waitForApprovement
    public void addRequest(ArrayList<String> vacationDetails) {
        sale.create(vacationDetails);
    }

    //this function getting from the vacations-user tables all vacations ot the wanted user name
    public ArrayList<String> getAllUserVacations(String userName) throws SQLException {
        return vacation.getAllUserVacations(userName);
    }

    public void deleteVacation(String vacationIdtoDelete) {
        ArrayList<String> vacationToDelete = new ArrayList<>();
        vacationToDelete.add(vacationIdtoDelete);
        vacation.delete(vacationToDelete);
    }

    public byte[] getPicture(String userName) {
        conn = SqlConnection.Connector();
        String query = "select * from Users where Username=\"" + userName + "\"";
        byte[] picture = null;
        try {
            Statement pst = conn.createStatement();
            try (ResultSet nrs = pst.executeQuery(query)) {
                picture = nrs.getBytes(8);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return picture;
    }


    public ArrayList<String[]> FoundRequetForPurchase(String currentUser) {
        return sale.FoundRequetForPurchase(currentUser);
    }

    //0-user name sale person 1-vacation id 2 - buyer user name
    public void updateSaleDbRequestForvVacationPermit(String[] allDetails) {
        sale.updateSaleDbRequestForvVacationPermit(allDetails);
    }

    //update that the conversion was approve in the db by the sale person
    public void updateConversionApproved(String[] details){
        conversions.updateRequestConfirmation(details);
    }
    public void deleteFromVacationAfterSale(String currentUser , String vacationID) {
        vacation.deleteFromVacationAfterSale(currentUser, vacationID);
    }

    public void declineConversion(String[] details ){
        conversions.declineConversion(details);
    }

    public void declinePurchase(String[] details){
        sale.declinePurchase(details);
    }

    //return all confirmations that waiting for current user
    public ArrayList<String[]> queryApprove(String currentUser) {
        return sale.queryApprove(currentUser);
    }

    public void updateSaleDbRequestForSendApprovmentToBuyer(String currentUser) {
        sale.updateSaleDbRequestForSendApprovmentToBuyer(currentUser);
    }

    public void updateThetConfirmationSentToBuyer(String currentUser){
        conversions.updateThatConfirmationSentToBuyer(currentUser);
    }
    public void updateSaleDbRequestForApproval(String currentUser) {
        sale.updateSaleDbRequestForApproval(currentUser);
    }

    //adding new request for conversions in the conversions data base
    public void addRequestForReplacement(ArrayList<String> allDetailsForConversion) {
        conversions.create(allDetailsForConversion);
    }

    public ArrayList<String[]> getAllUserConversionsRequests(String currentUser) {
        return  conversions.getAllRequetsForConversions(currentUser);
    }

    public String[] getVacationDetails(String currentuser, String vacationID){
        try {
            return vacation.getVacationDetails(currentuser, vacationID);
        }catch (Exception e){

        }
        return null;
    }

    public ArrayList<String[]> getAllConversionsApproved(String currentUser){
        return conversions.getAllConversionsApproved(currentUser);
    }

    public ArrayList<String[]> getAllConversionsDecline(String currentUser) {
        return conversions.getAllConversionsDeclined(currentUser);
    }

    public ArrayList<String[]> getPurchaseDeclines(String currentUser) {
        return sale.getAllPurchaseDeclines(currentUser);
    }

    public void updateDeclineConversionsSentToBuyer(String currentUser) {
        conversions.updateDeclineConversionsSentToBuyer(currentUser);
    }

    public void updateDeclinePurchaseSentToBuyer(String currentUser){
        sale.updateDeclinePurchaseSentToBuyer(currentUser);
    }
}
