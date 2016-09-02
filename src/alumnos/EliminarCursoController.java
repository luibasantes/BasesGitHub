/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
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
public class EliminarCursoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox cursoBox;
    @FXML
    TextField cursoF,nombreF;
    @FXML
    TableView<Alumno> tablaAlumnos;
    @FXML
    TableColumn<Alumno,String> matCol,nameCol,statusCol;
    ArrayList<Alumno> alumnos=new ArrayList<Alumno>();
    String curso,paralelo,idCurso;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        matCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
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
    }    
    
    public void mostrarInfoCurso(ActionEvent e) throws SQLException{
        String[] cursoArray=((String)cursoBox.getValue()).split(",");
        curso=cursoArray[0];
        paralelo=cursoArray[1];
        Conexion.procedure=Conexion.connection.prepareCall("{call getCurso('"+curso+"','" + paralelo+"','" + PeriodoLectivo.periodo+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        Conexion.result.next();
        idCurso=Conexion.result.getString(1);
        cursoF.setText(idCurso);
        nombreF.setText(PeriodoLectivo.periodo);
        Conexion.procedure=Conexion.connection.prepareCall("{call mostrarAlumnosRegistrados('"+curso+"','" + paralelo+"','" + PeriodoLectivo.periodo+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        while(Conexion.result.next()){
            Alumno a=new Alumno(Conexion.result.getString(3),Conexion.result.getString(2),Conexion.result.getString(1),Conexion.result.getString(4),null);
            tablaAlumnos.getItems().add(a);
            alumnos.add(a);
        
        }
        System.out.println("id: "+idCurso);
    }
    
    public void eliminarCurso(ActionEvent e) throws SQLException{
        if(!alumnos.isEmpty()){
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursosDisponibles('"+curso+"','" + PeriodoLectivo.periodo+"','" + alumnos.size()+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            List<String> choices = new ArrayList<>();
            while(Conexion.result.next())
                choices.add(Conexion.result.getString(1)+"-"+Conexion.result.getString(2)+"-"+Conexion.result.getString(3));
            ChoiceDialog<String> dialog = new ChoiceDialog<>(null,choices);
            dialog.setTitle("ADVERTENCIA DE ELIMINAR");
            dialog.setHeaderText("Usted va a eliminar con estudiantes registrados,por lo que debe traspasarlos a otro paralelo");
            dialog.setContentText("Seleccione el curso para el traspaso: ");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                String[] resultado= result.get().split("-");
                for(Alumno a: alumnos){
                    Conexion.procedure=Conexion.connection.prepareCall("{call traspasarAlumno('"+a.getCedula()+"','" + resultado[0]+"')}");
                    Conexion.procedure.execute();
                }
                Conexion.procedure=Conexion.connection.prepareCall("{call inactivarCurso('"+idCurso+"')}");
                Conexion.procedure.execute();
                Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
                suceed.setTitle("EXITO!");
                suceed.setHeaderText("PROCESO COMPLETADO");
                suceed.setContentText("El curso ha sido eliminado y los estudiantes han sido traspasados al paralelo seleccionado");
                suceed.initStyle(StageStyle.UTILITY);
                suceed.showAndWait();
                Conexion.procedure.close();
            }
        }
        else{
            Conexion.procedure=Conexion.connection.prepareCall("{call inactivarCurso('"+idCurso+"')}");
            Conexion.procedure.execute();
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
                suceed.setTitle("EXITO!");
                suceed.setHeaderText("PROCESO COMPLETADO");
                suceed.setContentText("El curso ha sido eliminado");
                suceed.initStyle(StageStyle.UTILITY);
                suceed.showAndWait();
                Conexion.procedure.close();
        }
    }
}
