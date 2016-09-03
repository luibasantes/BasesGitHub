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
public class ConsultaNotaPeriodoAlumnoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ToggleGroup group=new ToggleGroup();
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
            Conexion.procedure = Conexion.connection.prepareCall("{call getNombre('" + Usuario.Id + "')}");
            Conexion.result = Conexion.procedure.executeQuery();
            if (Conexion.result.next()){
                String nombre = Conexion.result.getString("nombreA");
                System.out.println(Usuario.tipo);
                Conexion.procedure = Conexion.connection.prepareCall("{call mostrarNotasPeriodo('" + nombre + "','" + PeriodoLectivo.periodo + "','" + (Usuario.tipo + 1)  + "')}");
                Conexion.result = Conexion.procedure.executeQuery();
                while (Conexion.result.next()){
                    Nota n = new Nota(Conexion.result.getString("ID_Materia"), Conexion.result.getString("nombreM"), Conexion.result.getString("nota1"), Conexion.result.getString("nota2"), Conexion.result.getString("promedio"), Conexion.result.getString("notaSup"),Conexion.result.getString("nombreC")+"-"+Conexion.result.getString("paralelo"), "", "", "");
                    tableNotas.getItems().add(n);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
