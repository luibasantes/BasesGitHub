/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class CrearDepartamentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public Button btnIngresar;
    @FXML
    public TextField txtID, txtNombreD, txtDesc;
    
    @FXML
    public void IngresarDepartamento(){
        String id, nombre, desc;
        try{
            id = txtID.getText().toUpperCase();
            Conexion.procedure = Conexion.connection.prepareCall("{call verificarDepartamento('" + id + "')}");
            Conexion.result = Conexion.procedure.executeQuery();
            Conexion.result.next();
            String verificar = Conexion.result.getString(1);
            if (verificar.equals("1")){
                nombre = txtNombreD.getText();
                desc = txtDesc.getText();
                Conexion.procedure = Conexion.connection.prepareCall("call guardarDepartamento('" + id + "','" + nombre + "','" + desc +"')");
                Conexion.procedure.execute();
                Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
                suceed.setTitle("EXITO!");
                suceed.setHeaderText("El departamneto fue creado correctamene a la Base de Datos");
                suceed.initStyle(StageStyle.UTILITY);
                suceed.showAndWait();
                Conexion.procedure.close();
                Conexion.result.close();
                txtID.clear();
                txtNombreD.clear();
                txtDesc.clear();
            }else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Datos Ingresados Incorrectos");
                error.setContentText("El departamento ingresada ya existe.");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
