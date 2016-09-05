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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

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
        Conexion.procedure=Conexion.connection.prepareCall("{call mostrarProfesoresDisponibles()}");
        Conexion.result=Conexion.procedure.executeQuery();
        while(Conexion.result.next()){
            if(!profesorBox.getItems().contains(Conexion.result.getString(1)))
                profesorBox.getItems().add(Conexion.result.getString(1));
        }
        
    }
    
    public void asignarDirigente(ActionEvent e) throws SQLException{
        String dirigente=(String) profesorBox.getValue();
        String[] curso=((String) cursoBox.getValue()).split(",");
        Conexion.procedure=Conexion.connection.prepareCall("{call esDirigente('"+dirigente+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        Conexion.result.next();
        if(Conexion.result.getInt(1)==0){
            Conexion.procedure=Conexion.connection.prepareCall("{call asignarDirigente('"+dirigente+"','"+curso[0]+"','"+curso[1]+"','"+PeriodoLectivo.periodo+"')}");
            Conexion.procedure.execute();
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("El curso fue asginado correctamene al profesor seleccionado");
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
        }
        else{
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de ingreso");
            error.setHeaderText("CURSO NO ASIGNABLE");
            error.setContentText("El curso no se puede asignar a una persona con un curso a cargo");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
    }
    
}
