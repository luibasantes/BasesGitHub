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
public class ConsultaMateriaCursoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public ComboBox cBoxCursos;
    @FXML
    public Button btnMostrar;
    @FXML
    public TableView<Materia> tableMateria;
    @FXML
    public TableColumn<Materia, String> codMateria, nomMateria, profesor;
   
    
    @FXML
    public void mostrarMateria(){
        tableMateria.getItems().clear();
        String cadena = (String)cBoxCursos.getValue();
        String cadenas[] = cadena.split(",");
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call mostrarMateriaCurso('" + cadenas[0] + "','" + cadenas[1] + "')}");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                Materia m = new Materia(Conexion.result.getString("ID_Materia"), Conexion.result.getString("nombreM"), Conexion.result.getString("NombreCompleto"));
                tableMateria.getItems().add(m);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        codMateria.setCellValueFactory(new PropertyValueFactory<>("idMateria"));
        nomMateria.setCellValueFactory(new PropertyValueFactory<>("nomMateria"));
        profesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));
        
        try{
            
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos(?)}");
            Conexion.procedure.setString(1, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            while(Conexion.result.next()){
                cBoxCursos.getItems().add(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
            }  
        }catch (Exception e){
            e.printStackTrace();
        }
    }    
    
}
