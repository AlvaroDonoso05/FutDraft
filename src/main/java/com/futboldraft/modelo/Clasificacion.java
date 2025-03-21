package com.futboldraft.modelo;
// Generated 13 feb 2025, 10:28:25 by Hibernate Tools 6.5.1.Final



/**
 * Clasificacion generated by hbm2java
 */
public class Clasificacion implements java.io.Serializable {


     private int idEquipo;
     private Equipo equipo;
     private Integer puntos;
     private Integer golesFavor;
     private Integer golesContra;
     private Integer partidosJugados;

    public Clasificacion() {
    }

	
    public Clasificacion(Equipo equipo) {
        this.equipo = equipo;
    }
    public Clasificacion(Equipo equipo, Integer puntos, Integer golesFavor, Integer golesContra, Integer partidosJugados) {
       this.equipo = equipo;
       this.puntos = puntos;
       this.golesFavor = golesFavor;
       this.golesContra = golesContra;
       this.partidosJugados = partidosJugados;
    }
   
    public int getIdEquipo() {
        return this.idEquipo;
    }
    
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    public Equipo getEquipo() {
        return this.equipo;
    }
    
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
    public Integer getPuntos() {
        return this.puntos;
    }
    
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    public Integer getGolesFavor() {
        return this.golesFavor;
    }
    
    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }
    public Integer getGolesContra() {
        return this.golesContra;
    }
    
    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }
    public Integer getPartidosJugados() {
        return this.partidosJugados;
    }
    
    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }




}