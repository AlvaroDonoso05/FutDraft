package com.futboldraft.modelo;

import java.util.List;

import com.futboldraft.controlador.BaseDatos;

public class Draft {

	private static Draft instance;
	private List<Jugador> jugadoresPor;
	private List<Jugador> jugadoresDef;
	private List<Jugador> jugadoresMed;
	private List<Jugador> jugadoresDel;
	private BaseDatos bbdd;

	
	public synchronized Jugador getPor() {
		return jugadoresPor.remove((int)(Math.random()*jugadoresPor.size()));
	}
	
	public synchronized Jugador getDef() {
		return jugadoresDef.remove((int)(Math.random()*jugadoresDef.size()));
	}
	
	public synchronized Jugador getMed() {
		return jugadoresMed.remove((int)(Math.random()*jugadoresMed.size()));
	}

	public synchronized Jugador getDel() {
		return jugadoresDel.remove((int)(Math.random()*jugadoresDel.size()));
	}
	
	public static Draft getInstance(){
		if(instance == null) {
			instance = new Draft();		
		}
		return instance;
	}


	private Draft(){
		this.bbdd = BaseDatos.getInstance();
		jugadoresPor = bbdd.jugadorPosicionLibre("POR");
		jugadoresDef = bbdd.jugadorPosicionLibre("DEF");
		jugadoresMed = bbdd.jugadorPosicionLibre("MED");
		jugadoresDel = bbdd.jugadorPosicionLibre("DEL");
	}
}
