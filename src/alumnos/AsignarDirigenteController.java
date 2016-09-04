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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Hawk
 */
public class AsignarDirigenteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox cursoBox,profesorBox;
    @FXML
    Label dirLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos(?)};");
            Conexion.procedure.setString(1, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            while(Conexion.result.next()){
                cursoBox.getItems().add(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Conexion.result.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngresoAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.procedure.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngresoAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void mostrarDirigente(ActionEvent e) throws SQLException{
        String cursoArray[]=((String) cursoBox.getValue()).split(",");
        Conexion.procedure=Conexion.connection.prepareCall("{call mostrarDirigente('"+cursoArray[0]+"','" +cursoArray[1]+"','" + PeriodoLectivo.periodo+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        if(Conexion.result.next())
            dirLabel.setText("Dirigente: "+Conexion.result.getString(1));
        else
            dirLabel.setText("Dirigente: Ninguno");
        
    }
    
}
