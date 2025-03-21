package com.futboldraft.modelo;
// Generated 13 feb 2025, 10:28:25 by Hibernate Tools 6.5.1.Final


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.futboldraft.controlador.BaseDatos;

/**
 * Equipo generated by hbm2java
 */
public class Equipo  implements java.io.Serializable {


     private Integer idEquipo;
     private String nombre;
     private Set<Jugador> jugadors = new HashSet<Jugador>(0);
     private Set partidosForIdEquipoVisitante = new HashSet(0);
     private Clasificacion clasificacion;
     private Set partidosForIdEquipoLocal = new HashSet(0);
     private BaseDatos bbdd;

    public Equipo() {
    }

	
    public Equipo(String nombre) {
    	this.bbdd = BaseDatos.getInstance();
        this.nombre = nombre;
    }
    public Equipo(String nombre, Set jugadors, Set partidosForIdEquipoVisitante, Clasificacion clasificacion, Set partidosForIdEquipoLocal) {
       this.nombre = nombre;
       this.jugadors = jugadors;
       this.partidosForIdEquipoVisitante = partidosForIdEquipoVisitante;
       this.clasificacion = clasificacion;
       this.partidosForIdEquipoLocal = partidosForIdEquipoLocal;
       this.bbdd = BaseDatos.getInstance();
 
    }
   
    public Integer getIdEquipo() {
        return this.idEquipo;
    }
    
    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getJugadors() {
        return this.jugadors;
    }
    
    public void setJugadors(Set jugadors) {
        this.jugadors = jugadors;
    }
    public Set getPartidosForIdEquipoVisitante() {
        return this.partidosForIdEquipoVisitante;
    }
    
    public void setPartidosForIdEquipoVisitante(Set partidosForIdEquipoVisitante) {
        this.partidosForIdEquipoVisitante = partidosForIdEquipoVisitante;
    }
    public Clasificacion getClasificacion() {
        return this.clasificacion;
    }
    
    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }
    public Set getPartidosForIdEquipoLocal() {
        return this.partidosForIdEquipoLocal;
    }
    
    public void setPartidosForIdEquipoLocal(Set partidosForIdEquipoLocal) {
        this.partidosForIdEquipoLocal = partidosForIdEquipoLocal;
    }
    
    public int atacar(Jugador jugAt) {
    	inicializarBBDD();
    	int ataque = 0, fTecn = 0;
    	List<Jugador> jugadores = bbdd.selectJugadoresEquipo(idEquipo);
    		
    	  		
    	for(Jugador jugador : jugadores) {
    		if(jugador.getPosicion().equalsIgnoreCase("MED")) {
    			fTecn = fTecn + jugador.getFuerzaTecnica();
    		}
    	}
    	ataque = jugAt.getFuerzaAtaque() * 2 + fTecn;
    	
    	return (int)(Math.random()*ataque);
    }
    
    public int defender() {
    	inicializarBBDD();
    	int defensa = 0;
    	List<Jugador> jugadores = bbdd.selectJugadoresEquipo(idEquipo);
    	
    	for(Jugador jugador : jugadores) {
    		if(jugador.getPosicion().equalsIgnoreCase("POR")) {
    			defensa = defensa + (jugador.getFuerzaPortero()*3);   			
    		}else if(jugador.getPosicion().equalsIgnoreCase("DEF")) {
    			defensa = defensa + jugador.getFuerzaDefensa();  			
    		}    		
    	}
    	
    	return (int)(Math.random()*defensa);
    }

    private void inicializarBBDD() {
    	this.bbdd = BaseDatos.getInstance();
    }



}


