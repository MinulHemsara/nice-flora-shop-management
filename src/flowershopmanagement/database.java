/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershopmanagement;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author minul
 */
public class database {
    public static Connection con(){
        
        Connection conn = null; 
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost/flower","minul","");
          
                  
        } catch (Exception e) {
            e.printStackTrace();
        }
          return conn;
    }
}
