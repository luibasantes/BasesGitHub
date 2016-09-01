/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class ModificaAlumnoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ToggleGroup group=new ToggleGroup();
    @FXML
    TextField busquedaF,matriculaF,cedulaF,nombreF,fechaF,direccionF,telefonosF,discapacidadF,generoF,padreF,madreF,repF,repTelF,institucionF;
    @FXML
    ComboBox cursoBox;
    @FXML
    RadioButton nameRadio,idRadio,matRadio;
    @FXML
    Button modifyButton;
    String cedulaAnterior=null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            nameRadio.setToggleGroup(group);
            idRadio.setToggleGroup(group);
            matRadio.setToggleGroup(group);
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos(?)};");
            //Conexion.result=Conexion.statement.executeQuery("Select nombreC,paralelo FROM Curso WHERE estado=\"ACTIVO\"");
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
        modifyButton.setDisable(true);
    }    
    
    public void buscarAlumno(ActionEvent event) throws SQLException{
        if(nameRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call buscarAlumnos(?,?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 1);
            Conexion.procedure.setString(3, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
                cedulaF.setText(Conexion.result.getString("cedula"));
                nombreF.setText(Conexion.result.getString("nombreA"));
                fechaF.setText(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                institucionF.setText(Conexion.result.getString("institucion_Anterior"));
                padreF.setText(Conexion.result.getString("nombre_Padre"));
                madreF.setText(Conexion.result.getString("nombre_Madre"));
                repTelF.setText(Conexion.result.getString("telefono_Representante"));
                direccionF.setText(Conexion.result.getString("direccion"));
                generoF.setText(Conexion.result.getString("genero"));
                discapacidadF.setText(Conexion.result.getString("discapacidad"));
                repF.setText(Conexion.result.getString("nombre_Representante"));
                matriculaF.setText(Conexion.result.getString("NO_Matricula"));
                cursoBox.setValue(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
                modifyButton.setDisable(false);
                cedulaAnterior=cedulaF.getText();
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoAlumno(?)}");
                Conexion.procedure.setString(1, cedulaAnterior);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    telefonosF.setText(telefonos.substring(0, telefonos.length()-1));
                else
                    telefonosF.setText("");
            }
            else{
                Alert error=new Alert(AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
        else if(idRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call buscarAlumnos(?,?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 2);
            Conexion.procedure.setString(3, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
                cedulaF.setText(Conexion.result.getString("cedula"));
                nombreF.setText(Conexion.result.getString("nombreA"));
                fechaF.setText(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                institucionF.setText(Conexion.result.getString("institucion_Anterior"));
                padreF.setText(Conexion.result.getString("nombre_Padre"));
                madreF.setText(Conexion.result.getString("nombre_Madre"));
                repTelF.setText(Conexion.result.getString("telefono_Representante"));
                direccionF.setText(Conexion.result.getString("direccion"));
                generoF.setText(Conexion.result.getString("genero"));
                discapacidadF.setText(Conexion.result.getString("discapacidad"));
                repF.setText(Conexion.result.getString("nombre_Representante"));
                matriculaF.setText(Conexion.result.getString("NO_Matricula"));
                cursoBox.setValue(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
                modifyButton.setDisable(false);
                cedulaAnterior=cedulaF.getText();
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoAlumno(?)}");
                Conexion.procedure.setString(1, cedulaAnterior);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    telefonosF.setText(telefonos.substring(0, telefonos.length()-1));
                else
                    telefonosF.setText("");
            }
            else{
                Alert error=new Alert(AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
        else if(matRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call buscarAlumnos(?,?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 3);
            Conexion.procedure.setString(3, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
                cedulaF.setText(Conexion.result.getString("cedula"));
                nombreF.setText(Conexion.result.getString("nombreA"));
                fechaF.setText(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                institucionF.setText(Conexion.result.getString("institucion_Anterior"));
                padreF.setText(Conexion.result.getString("nombre_Padre"));
                madreF.setText(Conexion.result.getString("nombre_Madre"));
                repTelF.setText(Conexion.result.getString("telefono_Representante"));
                direccionF.setText(Conexion.result.getString("direccion"));
                generoF.setText(Conexion.result.getString("genero"));
                discapacidadF.setText(Conexion.result.getString("discapacidad"));
                repF.setText(Conexion.result.getString("nombre_Representante"));
                matriculaF.setText(Conexion.result.getString("NO_Matricula"));
                cursoBox.setValue(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
                modifyButton.setDisable(false);
                cedulaAnterior=cedulaF.getText();
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoAlumno(?)}");
                Conexion.procedure.setString(1, cedulaAnterior);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    telefonosF.setText(telefonos.substring(0, telefonos.length()-1));
                else
                    telefonosF.setText("");
            }
            else{
                Alert error=new Alert(AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
    }
    
    public void modificarAlumno(ActionEvent event) throws SQLException{
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
        String ca=""+cedulaAnterior;
        if(cedula.length()==10){
            Conexion.procedure=Conexion.connection.prepareCall("{call modificarAlumno(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
            Conexion.procedure.setString(15,nombre.replaceAll(" ",""));
            Conexion.procedure.setString(16,ca);
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
        }
        else{
            Alert error=new Alert(AlertType.ERROR);
            error.setTitle("Error de Ingreso");
            error.setHeaderText("Datos Ingresados Incorrectos");
            error.setContentText("Cedula Incorrecta");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
        
    }
}
