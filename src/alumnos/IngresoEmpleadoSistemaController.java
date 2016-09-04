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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */


public class IngresoEmpleadoSistemaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TextField idF,cedulaF,nombreF,fechaF,generoF,dirF,teleF,discapacidadF,estadoF,nivelF,tituloF,ageF;
    @FXML
    ComboBox jornadaBox;
    @FXML
    Button ingresarButton;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        jornadaBox.getItems().add("MATUTINA");
        jornadaBox.getItems().add("VESPERTINA");
        jornadaBox.getItems().add("COMPLETA");
        ingresarButton.setDisable(true);
    }    
    
    public void enableButton(ActionEvent e) throws SQLException{
        if(!idF.getText().equals("") && !cedulaF.getText().equals("") && !nombreF.getText().equals("") && !fechaF.getText().equals("") && !generoF.getText().equals("") && !dirF.getText().equals("") && !teleF.getText().equals("") && !discapacidadF.getText().equals("")
           && !estadoF.getText().equals("") && !nivelF.getText().equals("") && !tituloF.getText().equals("") && !ageF.getText().equals("") && idF.getText().length()==10 && cedulaF.getText().length()==10)
        {
            Conexion.procedure=Conexion.connection.prepareCall("{call verificarID('"+idF.getText()+"','"+cedulaF.getText()+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            if(Conexion.result.getInt(1)==0)
                ingresarButton.setDisable(false);
            else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Datos Ingresados Incorrectos");
                error.setContentText("La identificacion O cedula ingresada ya existe.");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
    }
    
    public void ingresarEmpleado(ActionEvent e) throws SQLException{
        String arrayFecha[]=fechaF.getText().split(",");
        String arrayNombre[]=nombreF.getText().split(" ");
        Conexion.procedure=Conexion.connection.prepareCall("{call ingresarEmpleado('"+idF.getText()+"','"+cedulaF.getText()+"','"+nombreF.getText()+"','"+arrayFecha[0]+"','"+arrayFecha[1]+"','"+generoF.getText()+"','"+dirF.getText()+"','"+discapacidadF.getText()+"','"+estadoF.getText()+"','"+nivelF.getText()+"','"+tituloF.getText()+"','"+ageF.getText()+"','"+((String) jornadaBox.getValue())+"','"+(arrayNombre[0]+arrayNombre[2])+"')}");
        Conexion.procedure.execute();
        String arrayTelefono[]=teleF.getText().split(",");
        for(int i=0;i<arrayTelefono.length;i++){
            Conexion.procedure=Conexion.connection.prepareCall("{call ingresarTelefonoEmpleado('"+idF.getText()+"','"+arrayTelefono[i]+"')}");
            Conexion.procedure.execute();
        }
        Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
        suceed.setTitle("EXITO!");
        suceed.setHeaderText("El empleado fue ingresado correctamene a la Base de Datos");
        suceed.setContentText("La cuenta creada es:\t"+(arrayNombre[0]+arrayNombre[2])+"\n"+"El password es:\t"+cedulaF.getText());
        suceed.initStyle(StageStyle.UTILITY);
        suceed.showAndWait();
        Conexion.procedure.close();
        Conexion.result.close();
        
    }
}
