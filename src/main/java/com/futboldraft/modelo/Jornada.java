package com.futboldraft.modelo;
// Generated 12 feb 2025, 18:13:57 by Hibernate Tools 6.5.1.Final


import java.util.HashSet;
import java.util.Set;

/**
 * Jornada generated by hbm2java
 */
public class Jornada  implements java.io.Serializable {


     private Integer idJornada;
     private int numero;
     private Set partidos = new HashSet(0);

    public Jornada() {
    }

	
    public Jornada(int numero) {
        this.numero = numero;
    }
    public Jornada(int numero, Set partidos) {
       this.numero = numero;
       this.partidos = partidos;
    }
   
    public Integer getIdJornada() {
        return this.idJornada;
    }
    
    public void setIdJornada(Integer idJornada) {
        this.idJornada = idJornada;
    }
    public int getNumero() {
        return this.numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public Set getPartidos() {
        return this.partidos;
    }
    
    public void setPartidos(Set partidos) {
        this.partidos = partidos;
    }




}


