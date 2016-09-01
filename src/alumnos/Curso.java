/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hawk
 */
public class Curso {
    private String idCurso,nombreCurso,paralelo,periodo,estado,dirigente;

    public Curso(String idCurso, String nombreCurso, String paralelo, String periodo, String estado, String dirigente) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.paralelo = paralelo;
        this.periodo = periodo;
        this.estado = estado;
        this.dirigente = dirigente;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDirigente() {
        return dirigente;
    }

    public void setDirigente(String dirigente) {
        this.dirigente = dirigente;
    }

    
    
}
