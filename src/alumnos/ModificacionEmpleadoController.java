/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class ModificacionEmpleadoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ToggleGroup group=new ToggleGroup();
    @FXML
    TextField busquedaF,cedulaF,nombreF,fechaF,generoF,dirF,teleF,discapacidadF,estadoF,nivelF,tituloF,ageF;
    @FXML
    ComboBox jornadaBox;
    @FXML
    Button modificarButton;
    @FXML
    RadioButton nameRadio,idRadio,cedulaRadio;
    String id=null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameRadio.setToggleGroup(group);
        idRadio.setToggleGroup(group);
        cedulaRadio.setToggleGroup(group);
        jornadaBox.getItems().add("MATUTINA");
        jornadaBox.getItems().add("VESPERTINA");
        jornadaBox.getItems().add("COMPLETA");
    }    
    
    public void mostrarInfoEmpleado(ActionEvent e) throws SQLException{
        if(nameRadio.isSelected()){
            Conexion.procedure= Conexion.connection.prepareCall("{call busquedaEmpleado(?,?)}");
            Conexion.procedure.setString(1,busquedaF.getText());
            Conexion.procedure.setInt(2, 1);
            Conexion.result=Conexion.procedure.executeQuery();
            if(Conexion.result.next()){
                cedulaF.setText(Conexion.result.getString("CIPasaporte"));
                nombreF.setText(Conexion.result.getString("NombreCompleto"));
                fechaF.setText(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                generoF.setText(Conexion.result.getString("genero"));
                dirF.setText(Conexion.result.getString("direccion"));
                discapacidadF.setText(Conexion.result.getString("discapacidad"));
                estadoF.setText(Conexion.result.getString("estado_Civil"));
                nivelF.setText(Conexion.result.getString("nivel_EStudios"));
                tituloF.setText(Conexion.result.getString("titulo"));
                ageF.setText(Conexion.result.getString("años_servicio"));
                jornadaBox.setValue(Conexion.result.getString("jornada"));
                id=Conexion.result.getString("ID_Empleado");
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoEmpleado(?)}");
                Conexion.procedure.setString(1, id);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    teleF.setText(telefonos.substring(0, telefonos.length()-1));
                else
                    teleF.setText("");
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
                cedulaF.setText(Conexion.result.getString("CIPasaporte"));
                nombreF.setText(Conexion.result.getString("NombreCompleto"));
                fechaF.setText(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                generoF.setText(Conexion.result.getString("genero"));
                dirF.setText(Conexion.result.getString("direccion"));
                discapacidadF.setText(Conexion.result.getString("discapacidad"));
                estadoF.setText(Conexion.result.getString("estado_Civil"));
                nivelF.setText(Conexion.result.getString("nivel_EStudios"));
                tituloF.setText(Conexion.result.getString("titulo"));
                ageF.setText(Conexion.result.getString("años_servicio"));
                jornadaBox.setValue(Conexion.result.getString("jornada"));
                id=Conexion.result.getString("ID_Empleado");
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoEmpleado(?)}");
                Conexion.procedure.setString(1, id);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    teleF.setText(telefonos.substring(0, telefonos.length()-1));
                else
                    teleF.setText("");
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
                cedulaF.setText(Conexion.result.getString("CIPasaporte"));
                nombreF.setText(Conexion.result.getString("NombreCompleto"));
                fechaF.setText(Conexion.result.getString("lugar_Nacimiento")+","+Conexion.result.getString("fecha_Nacimiento"));
                generoF.setText(Conexion.result.getString("genero"));
                dirF.setText(Conexion.result.getString("direccion"));
                discapacidadF.setText(Conexion.result.getString("discapacidad"));
                estadoF.setText(Conexion.result.getString("estado_Civil"));
                nivelF.setText(Conexion.result.getString("nivel_EStudios"));
                tituloF.setText(Conexion.result.getString("titulo"));
                ageF.setText(Conexion.result.getString("años_servicio"));
                jornadaBox.setValue(Conexion.result.getString("jornada"));
                id=Conexion.result.getString("ID_Empleado");
                Conexion.procedure=Conexion.connection.prepareCall("{call mostrarTelefonoEmpleado(?)}");
                Conexion.procedure.setString(1, id);
                Conexion.result=Conexion.procedure.executeQuery();
                String telefonos="";
                while(Conexion.result.next()){
                    telefonos+=Conexion.result.getString(1)+",";
                }
                if(telefonos.length()>0)
                    teleF.setText(telefonos.substring(0, telefonos.length()-1));
                else
                    teleF.setText("");
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
    
    public void modificarEmpleado(ActionEvent e) throws SQLException{
        if(!cedulaF.getText().equals("") && !nombreF.getText().equals("") && !fechaF.getText().equals("") && !generoF.getText().equals("") && !dirF.getText().equals("") && !teleF.getText().equals("") && !discapacidadF.getText().equals("")
           && !estadoF.getText().equals("") && !nivelF.getText().equals("") && !tituloF.getText().equals("") && !ageF.getText().equals("") && cedulaF.getText().length()==10){
            String arrayFecha[]=fechaF.getText().split(",");
            String arrayNombre[]=nombreF.getText().split(" ");
            Conexion.procedure=Conexion.connection.prepareCall("{call modificarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            Conexion.procedure.setString(1,cedulaF.getText());
            Conexion.procedure.setString(2,nombreF.getText());
            Conexion.procedure.setString(3,arrayFecha[0]);
            Conexion.procedure.setString(4,arrayFecha[1]);
            Conexion.procedure.setString(5,generoF.getText());
            Conexion.procedure.setString(6,dirF.getText());
            Conexion.procedure.setString(7,discapacidadF.getText());
            Conexion.procedure.setString(8,estadoF.getText());
            Conexion.procedure.setString(9,nivelF.getText());
            Conexion.procedure.setString(10,tituloF.getText());
            Conexion.procedure.setString(11,ageF.getText());
            Conexion.procedure.setString(12,(String) jornadaBox.getValue());
            Conexion.procedure.setString(13,(arrayNombre[0]+arrayNombre[2])); 
            Conexion.procedure.setString(14,id); 
            Conexion.procedure.execute();
            String arrayTelefono[]=teleF.getText().split(",");
            for(int i=0;i<arrayTelefono.length;i++){
                Conexion.procedure=Conexion.connection.prepareCall("{call ingresarTelefonoEmpleado(?,?)}");
                Conexion.procedure.setString(1,id);
                Conexion.procedure.setString(2,arrayTelefono[i]);
                Conexion.procedure.execute();
            }
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("El empleado fue ingresado correctamene a la Base de Datos");
            suceed.setContentText("La cuenta creada es:\t"+(arrayNombre[0]+arrayNombre[2])+"\n"+"El password es:\t"+cedulaF.getText());
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
        }
        else{
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de Ingreso");
            error.setHeaderText("Datos Ingresados Incorrectos");
            error.setContentText("Cedula Incorrecta o campos vacios");
            error.initStyle(StageStyle.UTILITY);
            error.showAndWait();
        }
    }
}
