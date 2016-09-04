/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author HOME
 */
public class InfoDepartamentoProfeController implements Initializable {
    @FXML
    private TextField txtDepartamento;
    @FXML
    private TableView<Empleado> tablaT;
    @FXML
    private TableColumn<Empleado, String> columIdEmpleado;
    @FXML
    private TableColumn<Empleado, String> columNombreC;
    @FXML
    private TableColumn<Empleado, String> columCargo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("id"));
        columNombreC.setCellValueFactory(new PropertyValueFactory<>("nombreC"));
        columCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call getNombreDepartamento('" + Usuario.Id + "')};");
            Conexion.result = Conexion.procedure.executeQuery();
            Conexion.result.next();
            String departamento = Conexion.result.getString(1);
            txtDepartamento.setText(departamento);
            Conexion.procedure = Conexion.connection.prepareCall("{call getInfoDepartamentos('" + departamento + "')}");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                tablaT.getItems().add(new Empleado(Conexion.result.getString(1), Conexion.result.getString(2), Conexion.result.getString(3)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
