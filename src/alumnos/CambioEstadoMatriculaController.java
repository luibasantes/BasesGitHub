/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class CambioEstadoMatriculaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField busquedaF;
    @FXML
    RadioButton nameRadio,idRadio,matRadio;
    @FXML
    Button cambiarButton;
    @FXML
    Label nameLabel,matLabel,statusLabel;
    @FXML
    ComboBox statusBox;
    String name;
    String mat;
    
    ToggleGroup group;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        statusBox.getItems().add("ACTIVO");
        statusBox.getItems().add("INACTIVO");
        
        group = new ToggleGroup();
        nameRadio.setToggleGroup(group);
        idRadio.setToggleGroup(group);
        matRadio.setToggleGroup(group);
    }    
    
    public void buscarAlumno(ActionEvent event) throws SQLException{
        if(nameRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call buscarAlumnos(?,?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 1);
            Conexion.procedure.setString(3, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
               name=Conexion.result.getString("nombreA");
               mat=Conexion.result.getString("NO_Matricula");
               nameLabel.setText("Nombre:\t"+name);
               matLabel.setText("Matricula:\t"+mat);
               statusLabel.setText("Estado:\t"+Conexion.result.getString("estado"));
               
            }
            else{
                Alert error=new Alert(AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
        else if(idRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call buscarAlumnos(?,?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 1);
            Conexion.procedure.setString(3, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
               name=Conexion.result.getString("nombreA");
               mat=Conexion.result.getString("NO_Matricula");
               nameLabel.setText("Nombre:\t"+name);
               matLabel.setText("Matricula:\t"+mat);
               statusLabel.setText("Estado:\t"+Conexion.result.getString("estado"));
               
            }
            else{
                Alert error=new Alert(AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
        else if(matRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call buscarAlumnos(?,?,?)}");
            String b=busquedaF.getText();
            System.out.println(b);
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 1);
            Conexion.procedure.setString(3, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
               name=Conexion.result.getString("nombreA");
               mat=Conexion.result.getString("NO_Matricula");
               nameLabel.setText("Nombre:\t"+name);
               matLabel.setText("Matricula:\t"+mat);
               statusLabel.setText("Estado:\t"+Conexion.result.getString("estado"));
               
            }
            else{
                Alert error=new Alert(AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
    }
    
    public void cambiarEstadoMatricula(ActionEvent e) throws SQLException{
        String status=(String) statusBox.getValue();
        Conexion.procedure=Conexion.connection.prepareCall("{call cambiarEstadoMatricula(?,?)}");
        Conexion.procedure.setString(1, mat);
        Conexion.procedure.setString(2, status);
        Conexion.procedure.execute();
        Alert suceed=new Alert(AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("Cambio de estado realizado con exito");
            suceed.setContentText("EL alumno "+name+" tiene matricula en estado "+status);
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
        
    }
}
