package com.futboldraft.controlador;

import com.futboldraft.modelo.Draft;
import com.futboldraft.modelo.Equipo;
import com.futboldraft.modelo.Jugador;

import javafx.concurrent.Task;

public class CrearEquiposTask extends Task<Void> {

	private BaseDatos bbdd;
	private Draft draft;
	private Equipo equipo;

	public CrearEquiposTask(Equipo equipo){
		this.equipo = equipo;
	}

	@Override
	protected Void call() throws Exception {
		this.draft = Draft.getInstance();
		this.bbdd = BaseDatos.getInstance();

		Jugador jugador;
		try {
			jugador = draft.getPor();
			jugador.setEquipo(equipo);
			bbdd.insertarJugador(jugador);

			for (int i = 0; i < 4; i++) {
				jugador = draft.getDef();
				jugador.setEquipo(equipo);
				bbdd.insertarJugador(jugador);
			}

			for (int i = 0; i < 4; i++) {
				jugador = draft.getMed();
				jugador.setEquipo(equipo);
				bbdd.insertarJugador(jugador);
			}

			for (int i = 0; i < 2; i++) {
				jugador = draft.getDel();
				jugador.setEquipo(equipo);
				bbdd.insertarJugador(jugador);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
