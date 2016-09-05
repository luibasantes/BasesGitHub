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
import javafx.scene.control.ToggleGroup;
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
    
    ToggleGroup group;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        group = new ToggleGroup();
        rbtnID.setToggleGroup(group);
        rbtnCedula.setToggleGroup(group);
        rbtnNombres.setToggleGroup(group);
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
            String id;
            if(rbtnID.isSelected()){
                id = txtBusqueda.getText().trim();
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
                lviewID.getItems().add(Conexion.result.getString("ID_Empleado"));
                lviewNombre.getItems().add(Conexion.result.getString("NombreCompleto"));
                lviewNac.getItems().add(Conexion.result.getString("fecha_Nacimiento"));
                lviewGenero.getItems().add(Conexion.result.getString("genero"));
                lviewDir.getItems().add(Conexion.result.getString("direccion"));
                lviewDisca.getItems().add(Conexion.result.getString("discapacidad"));
                lviewEstadoC.getItems().add(Conexion.result.getString("estado_Civil"));
                lviewEstudios.getItems().add(Conexion.result.getString("nivel_Estudios"));
                lviewTitulo.getItems().add(Conexion.result.getString("titulo"));
                lviewCargo.getItems().add(Conexion.result.getString("descripcion"));
                lviewTrabaja.getItems().add("" + (Year.now().getValue() - Integer.parseInt(Conexion.result.getString("años_servicio"))));
                lviewSueldo.getItems().add(Conexion.result.getString("SUM(con.sueldo)"));
                lviewDepartamento.getItems().add(Conexion.result.getString("dep"));
                lviewJornada.getItems().add(Conexion.result.getString("jornada"));
                id=Conexion.result.getString("ID_Empleado");
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoEmpleado(?)}");
                Conexion.procedure.setString(1, id);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    lviewTelef.getItems().add(telefonos.substring(0, telefonos.length()-1));
                else
                    lviewTelef.getItems().add("");
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
