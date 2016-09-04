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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class AgregarNombramientoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ToggleGroup group=new ToggleGroup();
    @FXML
    TextField busquedaF,cargoF,nombramientoF,fechainicioF,fechafinF,sueldoF;
    @FXML
    ComboBox cargoBox,categoriaBox,depBox,nombramientoBox;
    @FXML
    RadioButton nameRadio,idRadio,cedulaRadio;
    @FXML
    Label nameLabel,idLabel;
    String id=null;
    String titulo=null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameRadio.setToggleGroup(group);
        idRadio.setToggleGroup(group);
        cedulaRadio.setToggleGroup(group);
        cargoBox.getItems().add("PROFESOR");
        cargoBox.getItems().add("ADMINISTRATIVO");
        nombramientoBox.getItems().add("DEFINITIVO");
        nombramientoBox.getItems().add("PROVISIONAL");
        
    }    
    
    @FXML
    public void mostrarInfo(ActionEvent e) throws SQLException{
        if(nameRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call busquedaEmpleado(?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 1);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
                nameLabel.setText("Nombre: "+Conexion.result.getString("NombreCompleto"));
                idLabel.setText("Identificacion: "+Conexion.result.getString("ID_Empleado"));
                id=Conexion.result.getString("ID_Empleado");
                titulo=Conexion.result.getString("nivel_EStudios");
            }
            else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
        else if(idRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call busquedaEmpleados(?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 2);
            Conexion.result=Conexion.procedure.executeQuery();
             if(Conexion.result.next()){
                nameLabel.setText("Nombre: "+Conexion.result.getString("NombreCompleto"));
                idLabel.setText("Identificacion: "+Conexion.result.getString("ID_Empleado"));
                id=Conexion.result.getString("ID_Empleado");
                titulo=Conexion.result.getString("nivel_EStudios");
                
            }
            else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
        else if(cedulaRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call busquedaEmpleados(?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 3);
            Conexion.result=Conexion.procedure.executeQuery();
             if(Conexion.result.next()){
                nameLabel.setText("Nombre: "+Conexion.result.getString("NombreCompleto"));
                idLabel.setText("Identificacion: "+Conexion.result.getString("ID_Empleado"));
                id=Conexion.result.getString("ID_Empleado");
                titulo=Conexion.result.getString("nivel_EStudios");
                
            }
            else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no encontrada");
                error.setContentText("No se ha encontrado ningun registro con la informacion ingresada");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
            }
        }
    }
    
    @FXML
    public void cargarCategoria(ActionEvent e) throws SQLException{
       depBox.getItems().clear();
        String cargo=(String) cargoBox.getValue();
       if(cargo.equals("PROFESOR")){
           categoriaBox.getItems().add("Docente de primer Nivel");
           categoriaBox.getItems().add("Docente de segundo Nivel");
           categoriaBox.getItems().add("Docente de tercer Nivel");
           categoriaBox.getItems().add("Docente de cuarto Nivel");
           depBox.getItems().add("Docencia");
       }
       else if(cargo.equals("ADMINISTRATIVO")){
           categoriaBox.getItems().add("Administrativo de primer Nivel");
           categoriaBox.getItems().add("Administrativo de segundo Nivel");
           categoriaBox.getItems().add("Administrativo de tercer Nivel");
           categoriaBox.getItems().add("Administrativo de cuarto Nivel");
           Conexion.procedure=Conexion.connection.prepareCall("{call getDepartamentos()}");
           Conexion.result=Conexion.procedure.executeQuery();
           while(Conexion.result.next())
               if(!Conexion.result.getString(1).equals("DEP001"))
                depBox.getItems().add(Conexion.result.getString(1));
        }
    }
    
    @FXML
    public void asignarSueldo(ActionEvent e){
        sueldoF.clear();
        String cat=(String) categoriaBox.getValue();
        if(cat.equals("Docente de primer Nivel"))
            sueldoF.setText("800");
        else if(cat.equals("Docente de segundo Nivel")){
            sueldoF.setText("1200");
        }
        else if(cat.equals("Docente de tercer Nivel")){
            sueldoF.setText("1800");
        }
        else if(cat.equals("Docente de cuarto Nivel")){
            sueldoF.setText("2500");
        }
        else if(cat.equals("Administrativo de primer Nivel")){
            sueldoF.setText("1000");
        }
        else if(cat.equals("Administrativo de segundo Nivel")){
            sueldoF.setText("1800");
        }
        else if(cat.equals("Administrativo de tercer Nivel")){
            sueldoF.setText("2500");
        }
        else if(cat.equals("Administrativo de cuarto Nivel")){
            sueldoF.setText("5000");
        }
    }
    
    @FXML
    public void asignarNombramiento(ActionEvent e) throws SQLException{
        int tipoCargo;
        if(((String)cargoBox.getValue()).equals("PROFESOR"))
            tipoCargo=1;
        else if(((String)cargoBox.getValue()).equals("ADMINISTRATIVO"))
            tipoCargo=2;
        else
            tipoCargo=0;
        String categoria=(String) categoriaBox.getValue();
        String departamento=(String) depBox.getValue();
        String tipoNombramiento=(String) nombramientoBox.getValue();
        Conexion.procedure=Conexion.connection.prepareCall("{call verificarNombramiento('"+nombramientoF.getText()+"')}");
        Conexion.result=Conexion.procedure.executeQuery();
        Conexion.result.next();
        if((cargoF.getText().equals("Rector") && !titulo.toUpperCase().equals("CUARTO NIVEL")) || (cargoF.getText().equals("Vicerrector") && !titulo.toUpperCase().equals("CUARTO NIVEL")) ){
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de ingreso");
            error.setHeaderText("CARGO NO ASIGNABLE");
            error.setContentText("El cargo no se puede asignar a una persona con un titulo inferior a cuarto nivel");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
        else if(Conexion.result.getInt(1)==0){
            //Conexion.procedure=Conexion.connection.prepareCall("{call existeAutoridad()}");
            Conexion.procedure=Conexion.connection.prepareCall("{call asignarNombramiento('"+id+"','"+((String) depBox.getValue())+"','"+cargoF.getText()+"','"+fechainicioF.getText()+"','"+fechafinF.getText()+"','"+nombramientoF.getText()+"','"+((String) nombramientoBox.getValue())+"','"+((String) categoriaBox.getValue())+"','"+Double.parseDouble(sueldoF.getText())+"','"+tipoCargo+"')}");
            Conexion.procedure.execute();
            if(tipoCargo==1){
                while(true){
                    Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos('"+PeriodoLectivo.periodo+"')}");
                    Conexion.result=Conexion.procedure.executeQuery();
                    List<String> choices = new ArrayList<>();
                    while(Conexion.result.next())
                        choices.add(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);
                    dialog.setTitle("Elegir Curso");
                    dialog.setContentText("Elija el curso donde dictara clase: ");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        String[] arrayCurso=result.get().split(",");
                        Conexion.procedure=Conexion.connection.prepareCall("{call getMaterias('"+arrayCurso[0]+"','"+PeriodoLectivo.periodo+"')}");
                        Conexion.result=Conexion.procedure.executeQuery();
                        List<String> choices2 = new ArrayList<>();
                        while(Conexion.result.next())
                            choices2.add(Conexion.result.getString("nombreM"));
                        ChoiceDialog<String> dialog2 = new ChoiceDialog<>(null, choices2);
                        dialog2.setTitle("Elegir Materia");
                        dialog2.setContentText("Elija la materia que dictara: ");
                        Optional<String> result2 = dialog.showAndWait();
                        if (result2.isPresent()){
                            String materia=result2.get();
                            Conexion.procedure=Conexion.connection.prepareCall("{insertarAsignacion('"+id+"','"+arrayCurso[0]+"','"+arrayCurso[1]+"','"+materia+"','"+PeriodoLectivo.periodo+"')}");
                            Conexion.procedure.execute();
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("El nombramiento fue ingresado correctamene a la Base de Datos");
            suceed.setContentText("El empleado ahora posee el cargo designado");
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
        }
        else{
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de ingreso");
            error.setHeaderText("NOMBRAMIENTO EXISTENTE");
            error.setContentText("El cargo no se puede asignar a una persona con un nombramiento existente en la base de Datos");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
    }
}
