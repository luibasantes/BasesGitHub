/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    Button asignar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        asignar.setDisable(true);
    }    
    
    public void enableButton(ActionEvent e){
        asignar.setDisable(false);
    }
    
    public void asignarCurso(ActionEvent e){}
    
    public void crearMateria(ActionEvent e){}
    
}
