/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luiscruz
 */
public class ModificaIngresaNotasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox cursoBox,MateriaBox;
    @FXML
    TableView<Nota> tablaNotas;
    @FXML
    TableColumn<Nota,String> matCol,nameCol,firstCol,secondCol,supCol,avgCol,statusCol;
    ArrayList<Nota> notas=new ArrayList<Nota>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        firstCol.setCellValueFactory(new PropertyValueFactory<>("fQui"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("SQui"));
        supCol.setCellValueFactory(new PropertyValueFactory<>("sup"));
        avgCol.setCellValueFactory(new PropertyValueFactory<>("prom"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
        firstCol.setCellFactory(TextFieldTableCell.<Nota>forTableColumn());
        secondCol.setCellFactory(TextFieldTableCell.<Nota>forTableColumn());
        supCol.setCellFactory(TextFieldTableCell.<Nota>forTableColumn());
        try {
            // TODO
            Conexion.procedure=Conexion.connection.prepareCall("{call mostrarCursos(?)}");
            Conexion.procedure.setString(1, PeriodoLectivo.periodo);
            Conexion.result=Conexion.procedure.executeQuery();
            while(Conexion.result.next()){
                cursoBox.getItems().add(Conexion.result.getString("nombreC")+","+Conexion.result.getString("paralelo"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ModificaIngresaNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.procedure.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModificaIngresaNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.result.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModificaIngresaNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    public void cargarMaterias(ActionEvent e) throws SQLException{
       MateriaBox.getItems().clear();
       String[] curso=((String) cursoBox.getValue()).split(",");
       Conexion.procedure=Conexion.connection.prepareCall("{call mostrarPensum(?,?,?)}");
       Conexion.procedure.setString(1, curso[0]);
       Conexion.procedure.setString(2, curso[1]);
       Conexion.procedure.setString(3, PeriodoLectivo.periodo);
       Conexion.result=Conexion.procedure.executeQuery();
       while(Conexion.result.next())
           MateriaBox.getItems().add(Conexion.result.getString("nombreM"));
    }
    
    @FXML
    public void mostrarNotas(ActionEvent e) throws SQLException{
        tablaNotas.getItems().clear();
        String arrayCurso[]=((String) cursoBox.getValue()).split(",");
        String materia=(String) MateriaBox.getValue();
        Conexion.procedure=Conexion.connection.prepareCall("{call mostrarNotasCurso('"+ arrayCurso[0] + "','" + arrayCurso[1] + "','" + materia +"','" + PeriodoLectivo.periodo +"')}");
        Conexion.result = Conexion.procedure.executeQuery();
        while (Conexion.result.next()){
                Nota n = new Nota(Conexion.result.getString("ID_Materia"),Conexion.result.getString("nombreM"), Conexion.result.getString("nota1"), Conexion.result.getString("nota2"), Conexion.result.getString("promedio"), Conexion.result.getString("notaSup"), "", Conexion.result.getString("NO_Matricula"), Conexion.result.getString("nombreA"), null);
                n.setEstado();
                tablaNotas.getItems().add(n);
                notas.add(n);
        }
    }
    
    @FXML
    public void cambiarPromedio(CellEditEvent<Nota,String> e){
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setfQui(e.getNewValue());
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setProm();
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setEstado();
        
    }
    
    @FXML
    public void cambiarPromedio2(CellEditEvent<Nota,String> e){
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setsQui(e.getNewValue());
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setProm();
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setEstado();
        
    }
    
    @FXML
    public void cambiarProm(CellEditEvent<Nota,String> e){
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setSup(e.getNewValue());
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setProm();
        ((Nota) e.getTableView().getItems().get(
                e.getTablePosition().getRow())
                ).setEstado();
        
    }
    
    @FXML
    public void modificarNotas(ActionEvent e) throws SQLException{
        String arrayCurso[]=((String) cursoBox.getValue()).split(",");
        for(Nota n: notas){
           Conexion.procedure=Conexion.connection.prepareCall("{call modificarNotas('"+ n.getMatricula() + "','" + n.getIdMateria() + "','" + arrayCurso[0] +"','" + arrayCurso[1] +"','"+Double.parseDouble(n.getFQui())+"','"+Double.parseDouble(n.getSQui())+"','"+Double.parseDouble(n.getSup())+"','"+Double.parseDouble(n.getProm())+"','"+PeriodoLectivo.periodo+"')}");
           Conexion.procedure.execute();
        }
        Alert suceed=new Alert(Alert.AlertType.CONFIRMATION);
            suceed.setTitle("EXITO!");
            suceed.setHeaderText("NOTAS MODIFICADAS CON EXITO");
            suceed.setContentText("Las notas han sido ingresadas a la base de Datos");
            suceed.initStyle(StageStyle.UTILITY);
            suceed.showAndWait();  
    }
}
