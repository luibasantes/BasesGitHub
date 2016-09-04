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
public class InfoAlumnoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public Button btnBuscar;
    @FXML
    public RadioButton rbtnNombres, rbtnCedula, rbtnMatricula;
    @FXML
    public TextField txtBusqueda;
    @FXML
    public ListView lviewMatricula, lviewCedula, lviewNombres, lviewFechaNac, lviewDirec, lviewTelef, lviewDiscapacidad, lviewGenero, lviewPadre, lviewMadre, lviewNomRep, lviewTelefRep, lviewCursoReg, lviewEstadoMat, lviewInstitucion;
    
    @FXML
    public void buscarAlumno(){
        lviewMatricula.getItems().clear();
        lviewCedula.getItems().clear();
        lviewNombres.getItems().clear();
        lviewFechaNac.getItems().clear();
        lviewDirec.getItems().clear();
        lviewTelef.getItems().clear();
        lviewDiscapacidad.getItems().clear();
        lviewGenero.getItems().clear();
        lviewPadre.getItems().clear();
        lviewMadre.getItems().clear();
        lviewNomRep.getItems().clear();
        lviewTelefRep.getItems().clear();
        lviewCursoReg.getItems().clear();
        lviewEstadoMat.getItems().clear();
        lviewInstitucion.getItems().clear();
        
        try{
            if(rbtnNombres.isSelected()){
                String nomAlumno = txtBusqueda.getText().trim();
                Conexion.procedure = Conexion.connection.prepareCall("{call buscarAlumno('" + nomAlumno + "','" + 1 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }else if (rbtnCedula.isSelected()){
                String cedula = txtBusqueda.getText().trim();
                Conexion.procedure = Conexion.connection.prepareCall("{call buscarAlumno('" + cedula + "','" + 2 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }else if (rbtnMatricula.isSelected()){
                String matricula = txtBusqueda.getText();
                Conexion.procedure = Conexion.connection.prepareCall("{call buscarAlumno('" + matricula + "','" + 3 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }
            if(Conexion.result.next()){
                String cedula=Conexion.result.getString("cedula");
                lviewMatricula.getItems().add(Conexion.result.getString("NO_Matricula"));
                lviewCedula.getItems().add(Conexion.result.getString("cedula"));
                lviewNombres.getItems().add(Conexion.result.getString("nombreA"));
                lviewFechaNac.getItems().add(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                lviewDirec.getItems().add(Conexion.result.getString("direccion"));
                lviewDiscapacidad.getItems().add(Conexion.result.getString("discapacidad"));
                lviewGenero.getItems().add(Conexion.result.getString("genero"));
                lviewPadre.getItems().add(Conexion.result.getString("nombre_Padre"));
                lviewMadre.getItems().add(Conexion.result.getString("nombre_Madre"));
                lviewNomRep.getItems().add(Conexion.result.getString("nombre_Representante"));
                lviewTelefRep.getItems().add(Conexion.result.getString("telefono_Representante"));
                lviewCursoReg.getItems().add(Conexion.result.getString("nombreC")+"-"+Conexion.result.getString("paralelo"));
                lviewEstadoMat.getItems().add(Conexion.result.getString("estado"));
                lviewInstitucion.getItems().add(Conexion.result.getString("institucion_Anterior"));
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoAlumno('" + cedula + "')}");
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
