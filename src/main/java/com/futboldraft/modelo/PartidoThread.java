package com.futboldraft.modelo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.futboldraft.controlador.BaseDatos;
import com.futboldraft.modelo.EventosPartido.tipoEventoEnum;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class PartidoThread extends Thread{

	private BaseDatos bbdd;
	private boolean imprimir;
	private Equipo equipoLoc;
	private Equipo equipoVis;
	private int idJornada;
	private int golesLoc;
	private int golesVis;
	private List<Integer> tiempos;
	private Map<String, List<String>> eventosPartidos;
	private final String nombrePartido;
	private ListView<String> listViewPartidos;
	private ListView<String> listViewEventos;
	private Text txtContador;
	private List<int[]> goles;


	public PartidoThread(String nombrePartido, boolean imprimir, Equipo equipoLoc, Equipo equipoVis, int idJornada, 
			Map<String, List<String>> eventosPartidos, ListView<String> listViewPartidos, ListView<String> listViewEventos, Text txtContador, List<int[]> goles) {
		this.bbdd = BaseDatos.getInstance();
		this.imprimir = imprimir;
		this.equipoLoc = equipoLoc;
		this.equipoVis = equipoVis;
		this.idJornada = idJornada;
		this.golesLoc = 0;
		this.golesVis = 0;
		this.tiempos = new ArrayList<>();
		this.nombrePartido = nombrePartido;
		this.eventosPartidos = eventosPartidos;
		this.listViewPartidos = listViewPartidos;
		this.listViewEventos = listViewEventos;
		this.txtContador = txtContador;
		this.goles = goles;
	}

	public void run() {
		//jornada se genera en el controlador

		tipoEventoEnum[] tipoEvento = tipoEventoEnum.values();
		int statsTLoc = 0, statsTVis = 0, atkL = 8, defL = 8, atkV = 8, defV = 8, atk = 0, def = 0, random, idEquipo;
		Jugador jugAtk = null, jugDef = null;
		String frase="";

		idEquipo = equipoLoc.getIdEquipo();
		List<Jugador> lJugadoresLoc = new ArrayList<Jugador>();
		lJugadoresLoc = bbdd.selectJugadoresEquipo(idEquipo);

		idEquipo = equipoVis.getIdEquipo();
		List<Jugador> lJugadoresVis = new ArrayList<Jugador>();
		lJugadoresVis = bbdd.selectJugadoresEquipo(idEquipo);

		//crear timestamps aleatorios
		for(int i=0; i<16; i++) {
			tiempos.add((int)(Math.random()*91));
		}
		Collections.sort(tiempos);

		//insertar partido
		Partido partido = new Partido();
		partido.setEquipoByIdEquipoLocal(equipoLoc);
		partido.setEquipoByIdEquipoVisitante(equipoVis);
		partido.setGolesLocal(golesLoc);
		partido.setGolesVisitante(golesVis);
		Jornada jorn = bbdd.selectJornada(idJornada);
		partido.setJornada(jorn);
		

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
		if(imprimir) {
		listViewEventos.getItems().clear();
		}
		
		//bucle simulacion partido
		for(int i = 0; i<16;i++) {
			//if alternar ataque/efensa
			if(i%2==0) {
				//if ataques restantes local
				if(atkL>0) {
					do {
						jugAtk = lJugadoresLoc.get((int)(Math.random() * lJugadoresLoc.size()));
					}while(jugAtk.getPosicion().equalsIgnoreCase("POR"));
					atk = equipoLoc.atacar(jugAtk);
					def = equipoVis.defender();	
					do {
						jugDef = lJugadoresVis.get((int)(Math.random() * lJugadoresVis.size()));
					}while(!jugDef.getPosicion().equalsIgnoreCase("DEF") &&
							!jugDef.getPosicion().equalsIgnoreCase("POR"));
					//else si no ataquesL, ataca V
				}else {
					do {
						jugAtk = lJugadoresVis.get((int)(Math.random() * lJugadoresVis.size()));
					}while(jugAtk.getPosicion().equalsIgnoreCase("POR"));
					atk = equipoVis.atacar(jugAtk);
					def = equipoLoc.defender();					
					do {
						jugDef = lJugadoresLoc.get((int)(Math.random() * lJugadoresLoc.size()));
					}while(!jugDef.getPosicion().equalsIgnoreCase("DEF") &&
							!jugDef.getPosicion().equalsIgnoreCase("POR"));
				}
				//else alternar ataque/defensa
			}else {
				//si el eqV tiene ataques
				if(atkV>0) {
					do {
						jugAtk = lJugadoresVis.get((int)(Math.random() * lJugadoresVis.size()));
					}while(jugAtk.getPosicion().equalsIgnoreCase("POR"));
					atk = equipoVis.atacar(jugAtk);
					def = equipoLoc.defender();	
					do {
						jugDef = lJugadoresLoc.get((int)(Math.random() * lJugadoresLoc.size()));
					}while(!jugDef.getPosicion().equalsIgnoreCase("DEF") &&
							!jugDef.getPosicion().equalsIgnoreCase("POR"));
					//else si no ataquesV, ataca L
				}else {
					do {
						jugAtk = lJugadoresLoc.get((int)(Math.random() * lJugadoresLoc.size()));
					}while(jugAtk.getPosicion().equalsIgnoreCase("POR"));
					atk = equipoLoc.atacar(jugAtk);
					def = equipoVis.defender();					
					do {
						jugDef = lJugadoresVis.get((int)(Math.random() * lJugadoresVis.size()));
					}while(!jugDef.getPosicion().equalsIgnoreCase("DEF") &&
							!jugDef.getPosicion().equalsIgnoreCase("POR"));
				}

			}

			//una vez ha atacado Local o visitante, se comprueba quien gana
			EventosPartido evPart;
			if(atk>def) {		
				frase = seleccionarResultado(tipoEvento[0].toString(), jugAtk);
				if(jugAtk.getEquipo().getIdEquipo() == equipoLoc.getIdEquipo()) {
					partido.setGolesLocal(partido.getGolesLocal() + 1);
					golesLoc = golesLoc + 1;
					bbdd.insertarPartido(partido);
					evPart = new EventosPartido(jugAtk, partido, 90, tipoEvento[0].toString(), frase);
					bbdd.insertarEventoPartido(evPart);
				}else {
					partido.setGolesVisitante(partido.getGolesVisitante() + 1);
					golesVis = golesVis +1;
					bbdd.insertarPartido(partido);
					evPart = new EventosPartido(jugAtk, partido, tiempos.get(i), tipoEvento[0].toString(), frase);
					bbdd.insertarEventoPartido(evPart);
				}

			}else {
				random = (int)(1 + Math.random()*4);
				frase = seleccionarResultado(tipoEvento[random].toString(), jugDef);
				evPart = new EventosPartido(jugDef, partido, tiempos.get(i), tipoEvento[random].toString(), frase);
				bbdd.insertarEventoPartido(evPart);
			}

			String evento = "Minuto " + (tiempos.get(i)) + ": " + evPart.getDescripcion();
			eventosPartidos.get(nombrePartido).add(evento);
			
			
			Platform.runLater(() -> {
				if(imprimir) {
					listViewEventos.getItems().add(evento);
					txtContador.setText(golesLoc + " - " + golesVis);
				}
				
			});

		
		}//fin bucle partido
		
		bbdd.insertarPartido(partido);
	
		int[] golesP = {golesLoc, golesVis};
		goles.add(golesP);

		Clasificacion clas1 = bbdd.selectClasificacion(equipoLoc.getIdEquipo());
		clas1.setGolesContra(clas1.getGolesContra() + golesVis);
		clas1.setGolesFavor(clas1.getGolesFavor() + golesLoc);

		Clasificacion clas2 = bbdd.selectClasificacion(equipoVis.getIdEquipo());
		clas2.setGolesContra(clas1.getGolesContra() + golesLoc);
		clas2.setGolesFavor(clas1.getGolesFavor() + golesVis);

		if(golesLoc>golesVis) {
			clas1.setPuntos(clas1.getPuntos() + 3);
		}else if(golesVis>golesLoc) {
			clas2.setPuntos(clas2.getPuntos() + 3);
		}else {
			clas1.setPuntos(clas1.getPuntos() + 1);
			clas2.setPuntos(clas2.getPuntos() + 1);
		}

		bbdd.insertarClasificacion(clas1);
		bbdd.insertarClasificacion(clas2);




	}

	public String seleccionarResultado(String tipoOcasion, Jugador jugador) {
		String frase="";
		List<String> fGol = new ArrayList<String>();
		List<String> fPar = new ArrayList<String>();
		List<String> fFue = new ArrayList<String>();
		List<String> fVar = new ArrayList<String>();
		List<String> fDes = new ArrayList<String>();
		List<String> fOtro = new ArrayList<String>();

		fGol.add("¡GOOOOL! ¡Qué maravilla de jugada de "+ jugador.getNombre()+ "!");
		fGol.add("¡GOLAZO de "+ jugador.getNombre()+ "!");
		fGol.add("¡Vaselina y GOL de "+ jugador.getNombre()+ "!");
		fGol.add("¡Cabezazo y GOL de "+ jugador.getNombre()+ "!");

		fPar.add(("¡PARADON de "+ jugador.getNombre()+ " evitando la ocasion"));
		fPar.add(("¡PARADA sencilla de "+ jugador.getNombre()));

		fFue.add(("FUERA DE JUEGO por parte de "+ jugador.getNombre()));
		fFue.add(("El jugador "+ jugador.getNombre() + "se encontraba FUERA DE JUEGO"));
		
		fVar.add("El VAR ha determinado que se ANULA el GOL porque le apetece");
		fVar.add("El VAR ha vuelto a hacer una de las suyas, GOL ANULADO");

		fDes.add(("Balon DESPEJADO gracias a  "+ jugador.getNombre()));
		fDes.add(("Increible DESPEJE de  "+ jugador.getNombre()));
		
		fOtro.add("El fin del mundo se aproxima, GOL ANULADO");
		fOtro.add("Una gaviota ha aparecido y ha PARADO  la pelota");


		switch(tipoOcasion) {
		case "GOL":
			frase = fGol.get((int)(Math.random()*fGol.size()));
			break;
		case "PARADA":
			frase = fPar.get((int)(Math.random()*fPar.size()));
			break;
		case "FUERA_DE_JUEGO":
			frase = fFue.get((int)(Math.random()*fFue.size()));
			break;
		case "VAR":
			frase = fVar.get((int)(Math.random()*fVar.size()));
			break;
		case "DESPEJE":
			frase = fDes.get((int)(Math.random()*fDes.size()));
			break;
		case "OTRO":
			frase = fOtro.get((int)(Math.random()*fOtro.size()));
			break;
		default:
			break;
		}

		return frase;
	}

	public String getNombrePartido() {
		return nombrePartido;
	}

	public int getGolesLoc() {
		return golesLoc;
	}

	public int getGolesVis() {
		return golesVis;
	}

	public void setImprimir(boolean imprimir) {
		this.imprimir = imprimir;
	}
	
	
	






}//fin class
