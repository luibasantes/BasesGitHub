/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class InfoUsuarioController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @FXML
    ListView ide,nombre,fechaN,genero,dir,tele,disca,estado,estu,titulo,cargo,sueldo,dep,jornada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Conexion.procedure=Conexion.connection.prepareCall("{call getInfoEmpleado('"+Usuario.Id+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            ide.getItems().add(Conexion.result.getString(1));
            nombre.getItems().add(Conexion.result.getString(2));
            fechaN.getItems().add(Conexion.result.getString(3));
            genero.getItems().add(Conexion.result.getString(4));
            dir.getItems().add(Conexion.result.getString(5));
            tele.getItems().add(Conexion.result.getString(6));
            disca.getItems().add(Conexion.result.getString(7));
            estu.getItems().add(Conexion.result.getString(8));
            titulo.getItems().add(Conexion.result.getString(9));
            cargo.getItems().add(Conexion.result.getString(10));
            estado.getItems().add(Conexion.result.getString(11));
            sueldo.getItems().add(Conexion.result.getString(12));
            dep.getItems().add(Conexion.result.getString(13));
            jornada.getItems().add(Conexion.result.getString(14));
        } catch (SQLException ex) {
            Logger.getLogger(InfoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
