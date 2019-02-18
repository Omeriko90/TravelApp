package Model;

import Controller.Controller;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AModel {
    public Controller controller;
    private Connection conn;


    public abstract void create (ArrayList<String> details);

    public abstract void search (String details);

    public abstract void update (ArrayList<String> details);

    public abstract void delete (ArrayList<String> details);
}
