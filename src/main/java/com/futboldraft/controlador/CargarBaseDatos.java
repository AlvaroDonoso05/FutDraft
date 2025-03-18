package com.futboldraft.controlador;

import java.util.ArrayList;
import java.util.List;

import com.futboldraft.modelo.Equipo;
import com.futboldraft.modelo.Jugador;
import com.futboldraft.modelo.JugadorCsv;

import javafx.concurrent.Task;

public class CargarBaseDatos extends Task<Void> {
	private BaseDatos bbdd = BaseDatos.getInstance();
	
	public Void call() {
		try {
			if(bbdd.isDBEmpty()) {
				CsvController csvContr = new CsvController();

				List<JugadorCsv> jugadoresC = csvContr.abrirCSV();
				List<Equipo> equipos = bbdd.selectEquiposByNombre("%");
				List<String> equiposString = new ArrayList<String>();
				
				for(JugadorCsv jugadorC : jugadoresC) {
					Jugador jugador = new Jugador(null, jugadorC.getNombre(), jugadorC.getPosicion(), jugadorC.getFuerzaAtaque(),
							jugadorC.getFuerzaTecnica(), jugadorC.getFuerzaDefensa(), jugadorC.getFuerzaPortero());
					bbdd.insertarJugador(jugador);
					
					for(Equipo equipoB: equipos) {
						equiposString.add(equipoB.getNombre());
					}
					
					if(!equiposString.contains(jugadorC.getEquipo())) {
						equiposString.add(jugadorC.getEquipo());
						bbdd.insertarEquipo(new Equipo(jugadorC.getEquipo()));
					}
				}
			}
			
			bbdd.resetearJugadores();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}