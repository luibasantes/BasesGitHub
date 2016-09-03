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
public class EliminarMateriaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField codigoF,nombreF;
    @FXML
    ComboBox materiasBox;
    @FXML
    TableView<Curso> tablaCursos;
    @FXML
    TableColumn<Curso,String> nameCol,parCol,periodoCol;
    ArrayList<Curso> cursos=new ArrayList<Curso>();
    String codigo,nombre;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarMaterias('"+PeriodoLectivo.year+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            while(Conexion.result.next())
                materiasBox.getItems().add(Conexion.result.getString("nombreM"));
        } catch (SQLException ex) {
            Logger.getLogger(EliminarMateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        periodoCol.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        parCol.setCellValueFactory(new PropertyValueFactory<>("paralelo"));
    }
    
    public void mostrarCursosAsignados(ActionEvent e) throws SQLException{
        cursos.clear();
        tablaCursos.getItems().clear();
        Conexion.procedure=Conexion.connection.prepareCall("{call getMateria('"+((String) materiasBox.getValue())+"','"+PeriodoLectivo.year+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        Conexion.result.next();
        codigo=Conexion.result.getString(1);
        nombre=(String) materiasBox.getValue();
        nombreF.setText(nombre);
        codigoF.setText(codigo);
        Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursosAsignados('"+codigo+"','"+PeriodoLectivo.year+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        while(Conexion.result.next()){
            Curso c=new Curso(Conexion.result.getString(1),Conexion.result.getString(2),Conexion.result.getString(3),Conexion.result.getString(4));
            cursos.add(c);
            tablaCursos.getItems().add(c);
        }
    }
    
    public void eliminarMateria(ActionEvent e) throws SQLException{
        System.out.println(codigo);
        Conexion.procedure=Conexion.connection.prepareCall("{call inactivarMateria('"+codigo+"')}");
        Conexion.procedure.execute();
        Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
        suceed.setTitle("EXITO!");
        suceed.setHeaderText("MATERIA ELIMINADA CON EXITO");
        suceed.setContentText("La materia ha sido eliminada y se han borrado las asignaciones para los profesores que la dictaban");
        suceed.initStyle(StageStyle.UTILITY);
        suceed.showAndWait();    
    }
    
}
