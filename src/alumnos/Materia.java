/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

/**
 *
 * @author HOME
 */
public class Materia {
    private String idMateria, nomMateria, profesor;

    public Materia(String idMateria, String nomMateria, String profesor) {
        this.idMateria = idMateria;
        this.nomMateria = nomMateria;
        this.profesor = profesor;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getNomMateria() {
        return nomMateria;
    }

    public void setNomMateria(String nomMateria) {
        this.nomMateria = nomMateria;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    
}
