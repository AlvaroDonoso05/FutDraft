package com.futboldraft.modelo;

import java.util.List;

import org.hibernate.cfg.Configuration;

import com.futboldraft.controlador.BaseDatos;

public class DraftTeamThread {

	private BaseDatos bbdd;
	private Draft draft;
	private Equipo equipo;

	public DraftTeamThread(Equipo equipo) {
		this.draft = Draft.getInstance();
		this.bbdd = BaseDatos.getInstance();
		this.equipo = equipo;
	}


	public void run() {
		Jugador jugador;
		
		jugador = draft.getPor();
		jugador.setEquipo(equipo);
		bbdd.insertarJugador(jugador);

		for(int i = 0;i < 4;i++) {				
			jugador = draft.getDef();
			jugador.setEquipo(equipo);
			bbdd.insertarJugador(jugador);
		}

		for(int i = 0;i < 4;i++) {				
			jugador = draft.getMed();
			jugador.setEquipo(equipo);
			bbdd.insertarJugador(jugador);
		}

		for(int i = 0;i < 2;i++) {				
			jugador = draft.getDel();
			jugador.setEquipo(equipo);
			bbdd.insertarJugador(jugador);		
		}

	}

}
