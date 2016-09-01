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
public class AdministradorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public Pane loaderPane;
        
    @FXML
    public void mostrarIngresoAlumnos(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("IngresoAlumnos.fxml")));
    }
    
    @FXML
    public void mostrarInfoDepartamento(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("InfoDepartamento.fxml")));
    }
    
    @FXML
    public void mostrarConsultaNotaCurso(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ConsultaNotaCurso.fxml")));
    }
    
    @FXML
    public void mostrarConsultaMateriaCurso(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ConsultaMateriaCurso.fxml")));
    }
    
    @FXML
    public void mostrarInfoEmpleado(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("InfoEmpleado.fxml")));
    }
    
    @FXML
    public void mostrarInfoAlumno(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("InfoAlumno.fxml")));
    }
    
    @FXML
    public void mostrarInfoUsuario(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("InfoUsuario.fxml")));
    }
    
    @FXML
    public void mostrarInfoGen(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("InfoGeneral.fxml")));
    }
    
    @FXML
    public void mostrarConsultaNotaPeriodo(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ConsultaNotaPeriodo.fxml")));
    }
    
    @FXML
    public void mostrarCambiarPeriodo(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("CambioPeriodoLectivo.fxml")));
    }
    
    @FXML
    public void mostrarModificaAlumno(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ModificaAlumno.fxml")));
    }
    
    @FXML
    public void mostrarModificaIngresaNotas(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ModificaIngresaNotas.fxml")));
    }
    
    @FXML
    public void mostrarCambioEstadoMatricula(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("CambioEstadoMatricula.fxml")));
    }
    
    @FXML
    public void mostrarIngresoEmpleadoSistema(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("IngresoEmpleadoSistema.fxml")));
    }
    
    @FXML
    public void mostrarModificacionEmpleado(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("ModificacionEmpleado.fxml")));
    }
    
    @FXML
    public void mostrarAgregaNombramiento(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("AgregarNombramiento.fxml")));
    }
    
    @FXML
    public void mostrarAgregarMateria(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("AgregarMateria.fxml")));
    }
    
    @FXML
    public void mostrarEliminarMateria(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("EliminarMateria.fxml")));
    }
    
    @FXML
    public void mostrarAgregarCurso(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("AgregarCurso.fxml")));
    }
    
    @FXML
    public void mostrarEliminarCurso(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("EliminarCurso.fxml")));
    }
    
    @FXML
    public void mostrarAsignarDirigente(ActionEvent event) throws IOException{
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(FXMLLoader.load(getClass().getResource("AsignarDirigente.fxml")));
    }
    
    @FXML
    private void logout(ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Conexion.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_Colegio", "root", "mialagata23");
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
