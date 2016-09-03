/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

/**
 *
 * @author Luigi
 */
public class Usuario {
    public static String username,password,Id;
    public static int tipo;

    public Usuario() {
        this.Id = null;
        this.username = null;
        this.password = null;
        this.tipo = -1;
    }

    public String getId() {
        return Id;
    }
    
    public void setId(String id) {
        this.Id= id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
