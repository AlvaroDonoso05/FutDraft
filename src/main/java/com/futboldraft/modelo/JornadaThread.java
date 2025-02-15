package com.futboldraft.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.futboldraft.controlador.BaseDatos;

public class JornadaThread extends Thread{
	
	private BaseDatos bbdd;
	private boolean imprimir;
	private Equipo equipoLoc;
	private Equipo equipoVis;
	
	
	public JornadaThread(boolean imprimir, Equipo equipoLoc, Equipo equipoVis) {
		super();
		this.bbdd = BaseDatos.getInstance();
		this.imprimir = imprimir;
		this.equipoLoc = equipoLoc;
		this.equipoVis = equipoVis;
	}
	
	public void run() {
		
		
		int statsTLoc = 0, statsTVis = 0, atkL = 8, defL = 8, atkV = 8, defV = 8;
		Set<Jugador> jugadoresSetLoc = equipoLoc.getJugadors();
		List<Jugador> lJugadoresLoc = new ArrayList<Jugador>();
		lJugadoresLoc.addAll(jugadoresSetLoc);
		
		Set<Jugador> jugadoresSetVis = equipoVis.getJugadors();
		List<Jugador> lJugadoresVis = new ArrayList<Jugador>();
		lJugadoresVis.addAll(jugadoresSetVis);
		
		for(int i = 0; i<lJugadoresLoc.size();i++) {
			Jugador jugadorL = lJugadoresLoc.get(i);
			Jugador jugadorV = lJugadoresVis.get(i);
			statsTLoc = statsTLoc + jugadorL.statsTotales();
			statsTVis = statsTVis + jugadorV.statsTotales();
		}
		
		if(statsTLoc<statsTVis) {
			atkL += 2;
			defL -= 2;
			atkV -= 2;
			defV +=2;
		}else if (statsTLoc>statsTVis){
			atkV += 2;
			defV -= 2;
			atkL -= 2;
			defL +=2;
		}
		
		for(int i = 0; i<16;i++) {
			//si al atacante le quedan ataques, ataca y defiende el otro
			if(atkL>0) {
				
				
			}else {
				
			}
			
			
			
		}
	
		
	}
	
	
	
	

}
