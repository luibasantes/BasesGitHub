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
public class Nota {
    private String idMateria, nomMateria, curso, fQui, sQui, prom, sup, matricula, nombres, estado;

    public Nota(String idMateria, String nomMateria, String fQui, String sQui, String prom, String sup, String curso, String matricula, String nombres, String estado) {
        this.idMateria = idMateria;
        this.nomMateria = nomMateria;
        this.curso = curso;
        this.fQui = fQui;
        this.sQui = sQui;
        this.prom = prom;
        this.sup = sup;
        this.matricula = matricula;
        this.nombres = nombres;
        this.estado = estado;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getFQui() {
        return fQui;
    }

    public void setfQui(String fQui) {
        this.fQui = fQui;
    }

    public String getSQui() {
        return sQui;
    }

    public void setsQui(String sQui) {
        this.sQui = sQui;
    }

    public String getProm() {
        return prom;
    }

    public void setProm(String prom) {
        this.prom = prom;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
