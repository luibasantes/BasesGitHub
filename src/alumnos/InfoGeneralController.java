/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class InfoGeneralController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public Label periodo;
    @FXML
    public Label rector;
    @FXML
    public Label vice;
    @FXML
    public Label estudiantes;
    @FXML
    public Label profesores;
    
    @FXML
    public void cerrar(){
        AdministradorController a=new AdministradorController();
        a.loaderPane.getChildren();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Calendar fecha = Calendar.getInstance();
            int x=fecha.get(Calendar.YEAR);
            periodo.setText("PERIODO LECTIVO:"+x+"-"+(x+1));
            Conexion.procedure=Conexion.connection.prepareCall("{call getRector()}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            rector.setText("Rector:\t"+Conexion.result.getString("NombreCompleto"));
            Conexion.procedure=Conexion.connection.prepareCall("{call getViceRector()}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            vice.setText("Vicerector:\t"+Conexion.result.getString("NombreCompleto"));
            Conexion.procedure=Conexion.connection.prepareCall("{call getContarAlumnos('"+ x +"-"+(x+1)+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            estudiantes.setText("NO.DE ESTUDIANTES ACTUALES: "+Conexion.result.getString(1));
            Conexion.procedure=Conexion.connection.prepareCall("{call getContarProfesores()}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            profesores.setText("NO.DE PROFESORES ACTUALES: "+Conexion.result.getString(1));
            
        } catch (SQLException ex) {
            Logger.getLogger(InfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
