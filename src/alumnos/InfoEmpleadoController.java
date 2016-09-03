/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author HOME
 */
public class InfoEmpleadoController implements Initializable {
    @FXML
    private ScrollPane infoEmpeladoPane;
    @FXML
    private ListView<String> lviewID, lviewNombre, lviewNac, lviewGenero, lviewDir, lviewTelef, lviewDisca, lviewEstadoC, lviewEstudios, lviewTitulo, lviewCargo, lviewTrabaja, lviewSueldo, lviewDepartamento, lviewJornada;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private RadioButton rbtnID, rbtnCedula, rbtnNombres;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void buscarEmpleado(ActionEvent event) {
        lviewID.getItems().clear();
        lviewNombre.getItems().clear();
        lviewNac.getItems().clear();
        lviewGenero.getItems().clear();
        lviewDir.getItems().clear();
        lviewTelef.getItems().clear();
        lviewDisca.getItems().clear();
        lviewEstadoC.getItems().clear();
        lviewEstudios.getItems().clear();
        lviewTitulo.getItems().clear();
        lviewCargo.getItems().clear();
        lviewTrabaja.getItems().clear();
        lviewSueldo.getItems().clear();
        lviewDepartamento.getItems().clear();
        lviewJornada.getItems().clear();
        
        try{
            if(rbtnID.isSelected()){
                String id = txtBusqueda.getText().trim();
                Conexion.procedure = Conexion.connection.prepareCall("{call buscarEmpleado('" + id + "','" + 1 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }else if (rbtnCedula.isSelected()){
                String cedula = txtBusqueda.getText().trim();
                Conexion.procedure = Conexion.connection.prepareCall("{call buscarEmpleado('" + cedula + "','" + 2 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }else if (rbtnNombres.isSelected()){
                String nombres = txtBusqueda.getText();
                Conexion.procedure = Conexion.connection.prepareCall("{call buscarEmpleado('" + nombres + "','" + 3 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }
            if(Conexion.result.next()){
                lviewID.getItems().add(Conexion.result.getString(1));
                lviewNombre.getItems().add(Conexion.result.getString(2));
                lviewNac.getItems().add(Conexion.result.getString(6));
                lviewGenero.getItems().add(Conexion.result.getString(4));
                lviewDir.getItems().add(Conexion.result.getString(7));
                lviewTelef.getItems().add(Conexion.result.getString(8));
                lviewDisca.getItems().add(Conexion.result.getString(9));
                lviewEstadoC.getItems().add(Conexion.result.getString(10));
                lviewEstudios.getItems().add(Conexion.result.getString(11));
                lviewTitulo.getItems().add(Conexion.result.getString(12));
                lviewCargo.getItems().add(Conexion.result.getString(15));
                lviewTrabaja.getItems().add("" + (Year.now().getValue() - Integer.parseInt(Conexion.result.getString(13))));
                lviewSueldo.getItems().add(Conexion.result.getString(16));
                lviewDepartamento.getItems().add(Conexion.result.getString(15));
                lviewJornada.getItems().add(Conexion.result.getString(14));
            }
            else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
