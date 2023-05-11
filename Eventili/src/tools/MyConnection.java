/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP
 */
public class MyConnection {

    private Connection cnx;
    private String url = "jdbc:mysql://localhost:3306/eventili";
    private String user = "root";
    private String pwd = "";
    public static MyConnection c;

    private MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion établie");
            Statement stat;
            stat = cnx.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if (c == null) {
            c = new MyConnection();
        }
        return c;
    }

    public Connection getCnx() {
        return cnx;
    }

}
