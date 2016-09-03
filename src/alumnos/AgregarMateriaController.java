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

public class AgregarMateriaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField codigoF,nombreF;
    @FXML
    ComboBox cursoBox;
    @FXML
    Button asignarButton;
    @FXML
    TableView<Curso> tablaCursos;
    @FXML
    TableColumn<Curso,String> nameCol,parCol,periodoCol;
    ArrayList<Curso> cursos=new ArrayList<Curso>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos(?)};");
            Conexion.procedure.setString(1, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            while(Conexion.result.next()){
                cursoBox.getItems().add(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Conexion.result.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngresoAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.procedure.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngresoAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        periodoCol.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        parCol.setCellValueFactory(new PropertyValueFactory<>("paralelo"));
        asignarButton.setDisable(true);
    }    
    
    public void enableButton(ActionEvent e) throws SQLException{
        Conexion.procedure=Conexion.connection.prepareCall("{call verificarCodigoMateria('"+codigoF.getText()+"','"+PeriodoLectivo.year+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        Conexion.result.next();
        if(!codigoF.getText().equals("") && !nombreF.getText().equals("") && Conexion.result.getInt(1)==0)
            asignarButton.setDisable(false);
        else{
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de Ingreso");
            error.setHeaderText("CODIGO INCORRECTO");
            error.setContentText("Ha ingresado de manera erronea el codigo o los datos ingresados son insuficientes");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
    }
    
    public void asignarCurso(ActionEvent e){
        String[] cursoArray=((String) cursoBox.getValue()).split(",");
        Curso c=new Curso(cursoArray[0],cursoArray[1],PeriodoLectivo.periodo);
        tablaCursos.getItems().add(c);
        cursos.add(c);
        asignarButton.setDisable(true);
    }
    
    public void crearMateria(ActionEvent e) throws SQLException{
        if(!cursos.isEmpty()){
            for(Curso c:cursos){
                Conexion.procedure=Conexion.connection.prepareCall("{call asignarMaterias('"+codigoF.getText()+"','"+nombreF.getText()+"','"+c.getNombreCurso()+"','"+c.getParalelo()+"','"+c.getPeriodo()+"')}");
                Conexion.procedure.execute();
            }
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("CREACION DE MATERIA CON EXITO");
            suceed.setContentText("La materia ha sido creada y asignada a los cursos seleccionados");
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();    
        }
        else{
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de Ingreso");
            error.setHeaderText("ERROR DE ASIGNACION");
            error.setContentText("No hay cursos seleccionados");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
    }
    
}
