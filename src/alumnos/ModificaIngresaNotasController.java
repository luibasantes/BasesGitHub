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

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class ModificaIngresaNotasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox cursoBox,MateriaBox;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos(?)}");
            Conexion.procedure.setString(1, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            while(Conexion.result.next()){
                cursoBox.getItems().add(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ModificaIngresaNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.procedure.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModificaIngresaNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.result.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModificaIngresaNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    public void cargarMaterias(ActionEvent e) throws SQLException{
       MateriaBox.getItems().clear();
       String[] curso=((String) cursoBox.getValue()).split(",");
       Conexion.procedure=Conexion.connection.prepareCall("{call mostrarPensum(?,?,?)}");
       Conexion.procedure.setString(1, curso[0]);
       Conexion.procedure.setString(2, curso[1]);
       Conexion.procedure.setString(3, PeriodoLectivo.periodo);
       Conexion.result=Conexion.procedure.executeQuery();
       while(Conexion.result.next())
           MateriaBox.getItems().add(Conexion.result.getString("nombreM"));
    }
    
    @FXML
    public void mostrarNotas(ActionEvent e){}
    
}
