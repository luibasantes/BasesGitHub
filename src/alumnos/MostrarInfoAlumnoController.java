/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author HOME
 */
public class MostrarInfoAlumnoController implements Initializable {
    @FXML
    private ListView matricula;
    @FXML
    private ListView cedula;
    @FXML
    private ListView nombresC;
    @FXML
    private ListView fechaN;
    @FXML
    private ListView dir;
    @FXML
    private ListView tele;
    @FXML
    private ListView disca;
    @FXML
    private ListView genero;
    @FXML
    private ListView nombresP;
    @FXML
    private ListView nombresM;
    @FXML
    private ListView NombresR;
    @FXML
    private ListView teleR;
    @FXML
    private ListView curso;
    @FXML
    private ListView estado;
    @FXML
    private ListView institucion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            String telefonos = "";
            Conexion.procedure=Conexion.connection.prepareCall("{call getInfoAlumnos('"+Usuario.Id+"')}");
            Conexion.result=Conexion.procedure.executeQuery();
            Conexion.result.next();
            matricula.getItems().add(Conexion.result.getString(1));
            cedula.getItems().add(Conexion.result.getString(2));
            nombresC.getItems().add(Conexion.result.getString(3));
            fechaN.getItems().add(Conexion.result.getString(4));
            dir.getItems().add(Conexion.result.getString(5));
            telefonos+=Conexion.result.getString(6)+"-";
            disca.getItems().add(Conexion.result.getString(7));
            genero.getItems().add(Conexion.result.getString(8));
            nombresP.getItems().add(Conexion.result.getString(9));
            nombresM.getItems().add(Conexion.result.getString(10));
            NombresR.getItems().add(Conexion.result.getString(11));
            teleR.getItems().add(Conexion.result.getString(12));
            curso.getItems().add(Conexion.result.getString(13));
            estado.getItems().add(Conexion.result.getString(14));
            institucion.getItems().add(Conexion.result.getString(15));
            while(Conexion.result.next()){
                telefonos+=Conexion.result.getString(6)+"-";
            }
            tele.getItems().add(telefonos.substring(0, telefonos.length()-1));
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }    
    
}
