/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Hawk
 */



public class AgregarCursoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TextField codeF,nameF,capF;
    @FXML
    ComboBox parBox;
    @FXML
    Button crearButton;
    @FXML
    TableView<Curso> tablaCursos;
    @FXML
    TableColumn<Curso,String> codeCol,nameCol,parCol,periodCol,capCol;
    ArrayList<Curso> cursos=new ArrayList<Curso>();
    int ultimo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        parBox.getItems().add("A");
        parBox.getItems().add("B");
        parBox.getItems().add("C");
        parBox.getItems().add("D");
        parBox.getItems().add("E");
        parBox.getItems().add("F");
        parBox.getItems().add("G");
        parBox.getItems().add("H");
        parBox.getItems().add("I");
        crearButton.setDisable(true);
        codeCol.setCellValueFactory(new PropertyValueFactory<>("idCurso"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        parCol.setCellValueFactory(new PropertyValueFactory<>("paralelo"));
        periodCol.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        try{
        Conexion.procedure=Conexion.connection.prepareCall("{call verificarCodigoCurso()}");
        Conexion.result=Conexion.procedure.executeQuery();
        Conexion.result.next();
        ultimo=Conexion.result.getInt(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }    
    
    public void enableButton(ActionEvent e){
        if(!codeF.getText().equals("") && !nameF.getText().equals(""))
            crearButton.setDisable(false);
    }
    
    public void crearCurso(ActionEvent e) throws SQLException{
        if(Integer.parseInt(codeF.getText())<=ultimo){
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("CODIGO INCORRECTO");
                error.setContentText("Ha ingresado de manera erronea el codigo o este codigo ya existe\nEl último código ingresado fue: "+ultimo);
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
        }
        else{
            Curso c=new Curso(PeriodoLectivo.year+"CUR"+codeF.getText(),nameF.getText(),(String) parBox.getValue(),PeriodoLectivo.periodo,"ACTIVO",capF.getText(),null);
            tablaCursos.getItems().add(c);
            ultimo=Integer.parseInt(codeF.getText());
            cursos.add(c);
            codeF.clear();
            nameF.clear();
            capF.clear();
        }
        crearButton.setDisable(true);
    }
    
    public void generarCursos(ActionEvent e) throws SQLException{
        for(Curso c:cursos){
            Conexion.procedure=Conexion.connection.prepareCall("{call ingresarCursos('"+c.getIdCurso()+"','" + c.getNombreCurso()+"','"+c.getParalelo()+"','"+c.getPeriodo()+"','"+c.getEstado()+"','"+c.getCapacidad()+"')}");
            Conexion.procedure.execute();
        }
        Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
        suceed.setTitle("EXITO!");
        suceed.setHeaderText("CREACION DE CURSOS CON EXITO");
        suceed.setContentText("Los cursos han sido creados correctamente en el periodo lectivo seleccionado");
        suceed.initStyle(StageStyle.UTILITY);
        suceed.showAndWait();    
        Conexion.procedure.close();
    }
    
}
