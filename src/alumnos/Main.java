/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author HOME
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws SQLException, IOException {
        
        final BorderPane pane=new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 100, 50, 50));
        grid.setVgap(10);
        grid.setHgap(10);
        
        //Error Label
        Label lbl = new Label("ERROR");
        lbl.setStyle("-fx-font: 20 tahoma; -fx-base: #DC143C;");
        GridPane.setConstraints(lbl, 0, 4);
        lbl.setVisible(false);
        //Name Label
        Label lblUser = new Label("Username: ");
        lblUser.setStyle("-fx-font: 14 tahoma; -fx-base: #002BFF;");
        GridPane.setConstraints(lblUser, 0, 0);
        
        //Name input
        TextField nameInput = new TextField("Usuario");
        GridPane.setConstraints(nameInput, 2, 0);
        
        //Password label
        Label lblPass = new Label("Password: ");
        lblPass.setStyle("-fx-font: 14 tahoma; -fx-base: #002BFF;");
        GridPane.setConstraints(lblPass, 0, 2);
        
        //Password input
        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Your password");
        GridPane.setConstraints(passInput, 2, 2);
        
        Button btnLogin = new Button("Login");
        btnLogin.setStyle("-fx-font: 22 tahoma; -fx-base:#002BFF;");
        GridPane.setConstraints(btnLogin, 2, 4);
        
        grid.getChildren().addAll(lblUser, lblPass, nameInput, passInput, btnLogin,lbl);
        pane.setCenter(grid);
        pane.setStyle("-fx-background-image: url('logo.png')");
        Scene scene1 = new Scene(pane, 530, 350);
        stage.setScene(scene1);
        stage.setTitle("BASE DE DATOS");
        stage.setResizable(false);
        stage.show();
        Usuario usuario = new Usuario();
        
        //AlPresionar LOGIN
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e){
            Connection myConn = null;
            Statement myStmt = null;
            ResultSet myRs = null;

            String user = "root";
            String pass = "mialagata23";

            try {
                //Esta clase sirve para generar la conexion en SQL
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_Colegio", user, pass);
                               
                myStmt = myConn.createStatement();
                //Esta clase almacena los datos de los querys, funciona parecido a un scanner
                myRs = myStmt.executeQuery("select * from Cuenta");


                while (myRs.next()) {
                    String userName=myRs.getString("Usuario").toUpperCase();
                    if(userName!=null){
                        userName = userName.replaceAll(" ","");
                    }
                    String passWord=myRs.getString("clave");
                    String cedula=myRs.getString("cedula");
                    String ID_E=myRs.getString("ID_Empleado");
                    if(nameInput.getText().toUpperCase().equals(userName)){
                        if(passInput.getText().equals(passWord)){
                            usuario.setUsername(userName);
                            usuario.setPassword(passWord);
                            if(cedula!=null){
                                usuario.setTipo(0);
                                usuario.setId(cedula);
                            }else{
                                usuario.setId(ID_E);
                                ResultSet myRs1 = null;
                                myRs1=myStmt.executeQuery("select con.Cargo from Empleado e,Contrato con where "
                                        + "con.Empleado=e.ID_Empleado and con.Empleado="+usuario.getId());
                                LinkedList <String> s=new LinkedList<>();
                                while (myRs1.next()){
                                    s.addLast(myRs1.getString("Cargo"));
                                }
                                int contador=0;
                                for(String A:s){
                                    if(A.startsWith("ADMN")){
                                        contador++;
                                    }
                                }
                                if(contador==0){
                                    //Tipo1 Profesor
                                    usuario.setTipo(1);
                                }else{
                                    //Tipo2 ADMINISTRADOR
                                    usuario.setTipo(2);
                                }
                                myRs1.close();
                            }
                        }else{
                            lbl.setText("ERROR");
                            lbl.setVisible(true);
                        }
                        break;
                    }
                }
                myRs.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            } 
            finally {
                if (myRs != null) {
                    try {
                        myRs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (myStmt != null) {
                    try {
                        myStmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (myConn != null) {
                    try {
                        myConn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
                if(usuario.getTipo()==2){
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("Administrador.fxml"));
                        root.setStyle("-fx-background-image: url('cielo.jpg')");
                        Scene scene = new Scene(root);
                        stage.hide();
                        stage.setScene(scene);
                        stage.setMaximized(false);
                        stage.setResizable(false);
                        stage.setTitle("Admin");
                        Calendar fecha = Calendar.getInstance();
                        PeriodoLectivo.periodo=""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.YEAR)+1);
                        PeriodoLectivo.year=""+fecha.get(Calendar.YEAR);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("ERROR");
                    }
                }else if(usuario.getTipo()==1){
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("Profesor.fxml"));
                        root.setStyle("-fx-background-image: url('cielo.jpg')");
                        Scene scene = new Scene(root);
                        stage.hide();
                        stage.setScene(scene);
                        stage.setMaximized(false);
                        stage.setResizable(false);
                        stage.setTitle("Profesor");
                        Calendar fecha = Calendar.getInstance();
                        PeriodoLectivo.periodo=""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.YEAR)+1);
                        PeriodoLectivo.year=""+fecha.get(Calendar.YEAR);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("ERROR");
                        ex.printStackTrace();
                    }
                }else if(usuario.getTipo()==0){
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("Alumno.fxml"));
                        root.setStyle("-fx-background-image: url('fondo123.jpg')");
                        Scene scene = new Scene(root);
                        stage.hide();
                        stage.setScene(scene);
                        stage.setMaximized(false);
                        stage.setResizable(false);
                        stage.setTitle("Alumno");
                        Calendar fecha = Calendar.getInstance();
                        PeriodoLectivo.periodo=""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.YEAR)+1);
                        PeriodoLectivo.year=""+fecha.get(Calendar.YEAR);
                        stage.show();
                        
                    } catch (IOException ex) {
                        System.out.println("Algo fallo");
                        ex.printStackTrace();
                    }
                }else{
                    lbl.setText("ERROR");
                    lbl.setVisible(true);
                }
            }
        });
        /*
        Parent root = FXMLLoader.load(getClass().getResource("Administrador.fxml"));
        root.setStyle("-fx-background-image: url('cielo.jpg')");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        /*
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String user = "root";
        String pass = "mialagata23";
        
        try {
            
            //Esta clase sirve para generar la conexion en SQL
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_Colegio", user, pass);

            //Esta clase sirve para crear los querys y demas acciones para sql
            
            Statement s= (Statement) myConn.createStatement();
            String name="Sociales";
            String mat="491284124";
            String insert="INSERT INTO BD_Colegio.Materia(ID_Materia,nombreM) VALUES ('"+mat+"','"+name+"')";
            s.executeUpdate(insert);
            
            myStmt = myConn.createStatement();

            //Esta clase almacena los datos de los querys, funciona parecido a un scanner
            myRs = myStmt.executeQuery("select * from Materia");

            
            while (myRs.next()) {
                System.out.println("MY QUERY");
                System.out.println(myRs.getString("ID_Materia") + ", " + myRs.getString("nombreM"));
            }
            

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }*/
        launch(args);
    }
    
}
