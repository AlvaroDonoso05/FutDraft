package com.futboldraft.controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.futboldraft.modelo.*;

public class DraftController {
	BaseDatos bbdd = BaseDatos.getInstance();


	public List<Jugador> sacarDraft(String posicion) {
		List<Jugador> jugadoresD = new ArrayList<Jugador>();

		int cont = 0, random;

		List<Jugador> jugadoresP = bbdd.jugadorPosicion(posicion);
		while(cont <5) {
			random = (int)(Math.random()*jugadoresP.size());
			if(jugadoresP.get(random).getEquipo().getNombre().equalsIgnoreCase("Agente Libre")) {
				jugadoresD.add(jugadoresP.remove(random));
				cont++;
			}
		}
		return jugadoresD;
	}
	
	public void anadirJugadorEquipo(String nEquipo) {
		String nJugador= "", posicion="";
		
		Jugador jugador = bbdd.selectJugador(nJugador, posicion);
		jugador.setEquipo(new Equipo(nEquipo));
		
		
		
		bbdd.insertarJugador(null);
		
		
	}
	
	
}//fin class