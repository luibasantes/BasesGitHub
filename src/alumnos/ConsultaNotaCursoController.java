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
public class ConsultaNotaCursoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public ComboBox cBoxCurso, cBoxParalelo, cBoxMateria;
    @FXML
    public Button btnMostrar;
    @FXML
    public TableView<Nota> tableNotas;
    @FXML
    public TableColumn<Nota, String> matricula, nombres, firstQui, secondQui, prom, sup, estado;
    @FXML
    
    public void mostrarNotas(){
        tableNotas.getItems().clear();
        String nomCurso = (String) cBoxCurso.getValue();
        String paralelo = (String) cBoxParalelo.getValue();
        String nomMateria = (String) cBoxMateria.getValue();
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call mostrarNotasCurso('"+ nomCurso + "','" + paralelo + "','" + nomMateria +"','" + PeriodoLectivo.periodo +"')}");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                Nota n = new Nota("", "", Conexion.result.getString("nota1"), Conexion.result.getString("nota2"), Conexion.result.getString("promedio"), Conexion.result.getString("notaSup"), "", Conexion.result.getString("NO_Matricula"), Conexion.result.getString("nombreA"), Conexion.result.getString("estado"));
                tableNotas.getItems().add(n);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void llenarComboBox(){
        cBoxParalelo.getItems().clear();
        cBoxMateria.getItems().clear();
        tableNotas.getItems().clear();
        String nomCurso = (String) cBoxCurso.getValue();
        try{
            Conexion.procedure = Conexion.connection.prepareCall("call getParalelos('" + nomCurso + "','" + PeriodoLectivo.periodo + "')");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next())
                cBoxParalelo.getItems().add(Conexion.result.getString("paralelo"));
            Conexion.procedure = Conexion.connection.prepareCall("call getMaterias('" + nomCurso + "','" + PeriodoLectivo.periodo+ "')");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next())
                cBoxMateria.getItems().add(Conexion.result.getString("nombreM"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        firstQui.setCellValueFactory(new PropertyValueFactory<>("fQui"));
        secondQui.setCellValueFactory(new PropertyValueFactory<>("sQui"));
        prom.setCellValueFactory(new PropertyValueFactory<>("prom"));
        sup.setCellValueFactory(new PropertyValueFactory<>("sup"));
        estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        try{
            Conexion.procedure = Conexion.connection.prepareCall("{call getCursos('" + PeriodoLectivo.periodo + "')};");
            Conexion.result = Conexion.procedure.executeQuery();
            while (Conexion.result.next()){
                cBoxCurso.getItems().add(Conexion.result.getString("nombreC"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
