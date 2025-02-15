package com.futboldraft.modelo;
// Generated 13 feb 2025, 10:28:25 by Hibernate Tools 6.5.1.Final


import java.util.HashSet;
import java.util.Set;

/**
 * Partido generated by hbm2java
 */
public class Partido  implements java.io.Serializable {


     private Integer idPartido;
     private Jornada jornada;
     private Equipo equipoByIdEquipoVisitante;
     private Equipo equipoByIdEquipoLocal;
     private Integer golesLocal;
     private Integer golesVisitante;
     private Set<EventosPartido> eventosPartidos = new HashSet<EventosPartido>(0);

    public Partido() {
    }

	
    public Partido(Jornada jornada, Equipo equipoByIdEquipoVisitante, Equipo equipoByIdEquipoLocal) {
        this.jornada = jornada;
        this.equipoByIdEquipoVisitante = equipoByIdEquipoVisitante;
        this.equipoByIdEquipoLocal = equipoByIdEquipoLocal;
    }
    public Partido(Jornada jornada, Equipo equipoByIdEquipoVisitante, Equipo equipoByIdEquipoLocal, Integer golesLocal, Integer golesVisitante, Set eventosPartidos) {
       this.jornada = jornada;
       this.equipoByIdEquipoVisitante = equipoByIdEquipoVisitante;
       this.equipoByIdEquipoLocal = equipoByIdEquipoLocal;
       this.golesLocal = golesLocal;
       this.golesVisitante = golesVisitante;
       this.eventosPartidos = eventosPartidos;
    }
   
    public Integer getIdPartido() {
        return this.idPartido;
    }
    
    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }
    public Jornada getJornada() {
        return this.jornada;
    }
    
    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
    public Equipo getEquipoByIdEquipoVisitante() {
        return this.equipoByIdEquipoVisitante;
    }
    
    public void setEquipoByIdEquipoVisitante(Equipo equipoByIdEquipoVisitante) {
        this.equipoByIdEquipoVisitante = equipoByIdEquipoVisitante;
    }
    public Equipo getEquipoByIdEquipoLocal() {
        return this.equipoByIdEquipoLocal;
    }
    
    public void setEquipoByIdEquipoLocal(Equipo equipoByIdEquipoLocal) {
        this.equipoByIdEquipoLocal = equipoByIdEquipoLocal;
    }
    public Integer getGolesLocal() {
        return this.golesLocal;
    }
    
    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }
    public Integer getGolesVisitante() {
        return this.golesVisitante;
    }
    
    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
    public Set getEventosPartidos() {
        return this.eventosPartidos;
    }
    
    public void setEventosPartidos(Set eventosPartidos) {
        this.eventosPartidos = eventosPartidos;
    }




}


