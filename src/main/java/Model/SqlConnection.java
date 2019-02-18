package Model;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.orm.jpa.vendor.Database;

public class SqlConnection {

    //The connection to the DB
    public static Connection Connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myDatabase.db");
            return conn;
        }catch (Exception e){
            return null;
        }
    }

    public static Connection ConnectorVacation(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myDatabaseVacation.db");
            return conn;
        }catch (Exception e){
            return null;
        }
    }

    public static Connection ConnectorSales(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myDatabaseSales.db");
            return conn;
        }catch (Exception e){
            return null;
        }
    }

    public static Connection ConnectorConversions(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myDatabaseConversions.db");
            return conn;
        }catch (Exception e){
            return null;
        }
    }


}