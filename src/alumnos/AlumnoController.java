/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ADMIN-MINEDUC
 */
public class AlumnoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public Pane loaderPane;
    
    
    @FXML
    public void mostrarInfoGeneral(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("InfoGeneral.fxml")));
    }
    
    @FXML
    public void mostrarInfoAlumno(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("MostrarInfoAlumno.fxml")));
    }
    
    @FXML
    public void ConsultarNotasPeriodo(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ConsultaNotaPeriodoAlumno.fxml")));
    }
    
    @FXML
    private void logout(ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
