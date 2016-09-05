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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class ConsultaNotaPeriodoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ToggleGroup group=new ToggleGroup();
    @FXML
    public RadioButton rbtnNombres, rbtnCedula, rbtnMatricula;
    @FXML
    public TextField txtBusqueda;
    @FXML
    public Button btnBuscar;
    @FXML
    public TableView<Nota> tableNotas;
    @FXML
    public TableColumn<Nota, String> codMateria, nomMateria, firstQui, secondQui, prom, sup, curso;
    @FXML
    public ComboBox cBoxPeriodos;
    @FXML
    private VBox consultaNotaPeriodoBox;

    
    
    @FXML
    public void mostrarNotas(){
        tableNotas.getItems().clear();
        try{
            String periodo = (String)cBoxPeriodos.getValue();
            if(rbtnNombres.isSelected()){
                String nomAlumno = txtBusqueda.getText().trim();
                Conexion.procedure = Conexion.connection.prepareCall("{call mostrarNotasPeriodo('" + nomAlumno + "','" + periodo + "','" + 1 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }else if (rbtnCedula.isSelected()){
                String cedula = txtBusqueda.getText().trim();
                Conexion.procedure = Conexion.connection.prepareCall("{call mostrarNotasPeriodo('" + cedula + "','" + periodo + "','" + 2 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }else if (rbtnMatricula.isSelected()){
                String matricula = txtBusqueda.getText();
                Conexion.procedure = Conexion.connection.prepareCall("{call mostrarNotasPeriodo('" + matricula + "','" + periodo + "','" + 3 + "')};");
                Conexion.result = Conexion.procedure.executeQuery();
            }
            if(Conexion.result.next()){
                Conexion.result.beforeFirst();
                while (Conexion.result.next()){
                    Nota n = new Nota(Conexion.result.getString("ID_Materia"), Conexion.result.getString("nombreM"), Conexion.result.getString("nota1"), Conexion.result.getString("nota2"), Conexion.result.getString("promedio"), Conexion.result.getString("notaSup"),Conexion.result.getString("nombreC")+"-"+Conexion.result.getString("paralelo"), "", "", "");
                    tableNotas.getItems().add(n);
                }
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
        rbtnNombres.setToggleGroup(group);
        rbtnCedula.setToggleGroup(group);
        rbtnMatricula.setToggleGroup(group);
        
        codMateria.setCellValueFactory(new PropertyValueFactory<>("idMateria"));
        nomMateria.setCellValueFactory(new PropertyValueFactory<>("nomMateria"));
        firstQui.setCellValueFactory(new PropertyValueFactory<>("fQui"));
        secondQui.setCellValueFactory(new PropertyValueFactory<>("sQui"));
        prom.setCellValueFactory(new PropertyValueFactory<>("prom"));
        sup.setCellValueFactory(new PropertyValueFactory<>("sup"));
        curso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call getPeriodos()}");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                cBoxPeriodos.getItems().add(Conexion.result.getString("periodo_Electivo"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }    
    
}
