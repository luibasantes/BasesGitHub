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

    public void setProm() {
        double n1=Double.parseDouble(this.fQui);
        double n2=Double.parseDouble(this.sQui);
        double sup=Double.parseDouble(this.sup);
        if(sup==0){
            double prom=(n1+n2)/2;
            prom=Math.round(prom*100);
            prom=prom/100;
            this.prom =""+prom;
        }
        else{
            if(sup==7)
                this.prom="7.00";
            else if(sup>n1 || sup>n2){
                double prom=(Math.max(n1, n2)+sup)/2;
                this.prom=""+prom;
            }
            else{
                double prom=(n1+n2)/2;
                prom=Math.round(prom*100);
                prom=prom/100;
                this.prom =""+prom;
            }
        }
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        if(Double.parseDouble(sup)>=7)
            this.sup = "7";
        else
            this.sup=sup;
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

    public void setEstado() {
        if(Double.parseDouble(this.prom)>=7)
            this.estado="APROBADO";
        else if(Double.parseDouble(this.sup)==7){
            this.estado="APROBADO";
        }
        else
            this.estado="REPROBADO";
    }
    
    
    
}
