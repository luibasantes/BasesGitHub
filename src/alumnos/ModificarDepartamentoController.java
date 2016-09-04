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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class ModificarDepartamentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public Button btnIngresar;
    @FXML
    public TextField txtNombreD, txtDesc;
    @FXML
    public ComboBox cBoxID;
    @FXML
    public void ModificarDepartamento(){
        String id, nombre, desc;
        try{
            id=(String)cBoxID.getValue();
            nombre=txtNombreD.getText();
            desc=txtDesc.getText();
            Conexion.procedure = Conexion.connection.prepareCall("{call modificarDepartamento('" + id + "','" + nombre + "','" + desc + "')}");
            Conexion.procedure.execute();
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("El departamneto fue modificado correctamene en la Base de Datos");
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
            Conexion.procedure.close();
            Conexion.result.close();
            txtNombreD.clear();
            txtDesc.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    @FXML
    public void actualizarComboBox(){
        txtNombreD.clear();
        txtDesc.clear();
        try{
            String id=(String)cBoxID.getValue();
            Conexion.procedure = Conexion.connection.prepareCall("{call getDepartamentosInfo('"+id+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            txtNombreD.setText(Conexion.result.getString(1));
            txtDesc.setText(Conexion.result.getString(2));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call getDepartamentosID()}");
            Conexion.result = Conexion.procedure.executeQuery();
            while(Conexion.result.next()){
                cBoxID.getItems().add(Conexion.result.getString(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }    
    
}
