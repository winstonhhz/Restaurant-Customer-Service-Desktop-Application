package com.example.restaurantcustomerservice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLSyntaxErrorException;
import java.sql.BatchUpdateException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransientConnectionException;


    public class database {
        public static Connection connectDB(){

            try{

                Class.forName("com.mysql.cj.jdbc.Driver");

                // Replace mysql username and password to your own
                Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'", "username", "password");
                return connect;
            }catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

