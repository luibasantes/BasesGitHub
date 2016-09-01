/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;

/**
 *
 * @author Hawk
 */
public class Conexion {
    public static Connection connection = null;
    public static Statement statement = null;
    public static ResultSet result = null;
    public static CallableStatement procedure= null;
}
