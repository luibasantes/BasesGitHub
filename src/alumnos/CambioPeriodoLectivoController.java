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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class CambioPeriodoLectivoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label periodLabel;
    @FXML
    TextField ageF;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        periodLabel.setText("Periodo Actual: "+PeriodoLectivo.periodo);
    }    
    
    public void cambiarPeriodo(ActionEvent e){
        if(ageF.getText().length()==9){
            PeriodoLectivo.periodo=ageF.getText();
            PeriodoLectivo.year=ageF.getText().substring(0, 3);
            Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("PROCESO COMPLETADO");
            suceed.setContentText("El periodo actual ahora es: "+PeriodoLectivo.periodo+"\nTENGA EN CUENTA QUE LOS CAMBIOS REALIZADOS EN EL PERIODO ESCOGIDO PUEDEN SER IRREVERSIBLES");
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();
        }
        else{
                Alert error=new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Ingreso");
                error.setHeaderText("Informacion no consistente");
                error.setContentText("Debe ingresar el periodo como lo especifica el campo");
                error.initStyle(StageStyle.UTILITY);
                error.showAndWait();
        }
        
    }
    
    
    
    
}
