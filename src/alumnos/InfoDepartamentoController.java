/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class InfoDepartamentoController implements Initializable {
    @FXML
    TableView<Empleado> tablaT;
    @FXML
    TableColumn<Empleado,String> columIdEmpleado,columNombreC,columCargo;
    @FXML
    Button mostrar;
    @FXML
    ComboBox cBoxDep;
    /**
     * Initializes the controller class.
     */
    public void mostrar(){
        tablaT.getItems().clear();
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call getInfoDepartamentos('"+(String)cBoxDep.getValue() +"')};");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                tablaT.getItems().add(new Empleado(Conexion.result.getString(1),Conexion.result.getString(2),Conexion.result.getString(3)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("id"));
        columNombreC.setCellValueFactory(new PropertyValueFactory<>("nombreC"));
        columCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call getDepartamentos()};");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                cBoxDep.getItems().add(Conexion.result.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }    
    
}
