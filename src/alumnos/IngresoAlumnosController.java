/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class IngresoAlumnosController implements Initializable {
    
    @FXML
    TextField matriculaF,cedulaF,nombreF,fechaF,direccionF,telefonosF,discapacidadF,generoF,padreF,madreF,repF,repTelF,institucionF;
    @FXML
    ComboBox cursoBox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    
    @FXML
    public void ingresarAlumno(ActionEvent event) throws SQLException{
        Conexion.procedure=Conexion.connection.prepareCall("{call mostrarMatricula(?)};");
        Conexion.procedure.setString(1, PeriodoLectivo.periodo);
        String matricula=matriculaF.getText();
        String cedula=cedulaF.getText();
        String nombre=nombreF.getText();
        String fecha[]=fechaF.getText().split(",");
        String direccion=direccionF.getText();
        String telefonos[]=telefonosF.getText().split(",");
        String discapacidad=discapacidadF.getText();
        String genero=generoF.getText();
        String padre=padreF.getText();
        String madre=madreF.getText();
        String rep=repF.getText();
        String repTel=repTelF.getText();
        String institucion=institucionF.getText();
        String curso=(String) cursoBox.getValue();
        String cursoArray[]=curso.split(",");
        Conexion.result=Conexion.procedure.executeQuery();
        boolean matRepetida=false;
        while(Conexion.result.next()){
            if(matricula.equals(Conexion.result.getString(1)))
               matRepetida=true;
        }
        if(!matRepetida && matricula.startsWith("MAT") && cedula.length()==10){
            Conexion.procedure=Conexion.connection.prepareCall("{call ingresarAlumno(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            Conexion.procedure.setString(1,cedula);
            Conexion.procedure.setString(2,nombre);
            Conexion.procedure.setString(3,fecha[0]);
            Conexion.procedure.setString(4,fecha[1]);
            Conexion.procedure.setString(5,institucion);
            Conexion.procedure.setString(6,padre);
            Conexion.procedure.setString(7,madre);
            Conexion.procedure.setString(8,rep);
            Conexion.procedure.setString(9,repTel);
            Conexion.procedure.setString(10,direccion);
            Conexion.procedure.setString(11,genero);
            Conexion.procedure.setString(12,discapacidad);
            Conexion.procedure.setString(13,cursoArray[0]);
            Conexion.procedure.setString(14,cursoArray[1]);
            Conexion.procedure.setString(15,matricula);
            Conexion.procedure.setString(16,nombre.replaceAll(" ",""));
            Conexion.procedure.setString(17, PeriodoLectivo.periodo);
            Conexion.procedure.execute();
            for(int i=0;i<telefonos.length;i++){
                Conexion.procedure=Conexion.connection.prepareCall("{call insertarTelefonoEstudiante(?,?)}");
                Conexion.procedure.setString(1,telefonos[i]);
                Conexion.procedure.setString(2,cedula);
                Conexion.procedure.execute();
            }
            Alert suceed=new Alert(AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("El alumno fue ingresado correctamene a la Base de Datos");
            suceed.setContentText("La cuenta creada es:\t"+nombre.replaceAll(" ","")+"\n"+"El password es:\t"+cedula);
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
            Conexion.procedure.close();
            Conexion.result.close();
        }
        
        else{
            Alert error=new Alert(AlertType.ERROR);
            error.setTitle("Error de Ingreso");
            error.setHeaderText("Datos Ingresados Incorrectos");
            error.setContentText("La matrícula ingresada ya existe.");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
            
        
    }
}
