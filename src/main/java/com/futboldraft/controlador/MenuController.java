package com.futboldraft.controlador;

import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.futboldraft.modelo.*;

public class MenuController {
	private BaseDatos bbdd;

	@FXML
	public void initialize() {
		
		
		bbdd = BaseDatos.getInstance();

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


	}


	
}
