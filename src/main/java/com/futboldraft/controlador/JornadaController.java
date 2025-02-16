package com.futboldraft.controlador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.futboldraft.modelo.*;

import javafx.fxml.FXML;

public class JornadaController {

	
	private BaseDatos bbdd = BaseDatos.getInstance();
	private List<Equipo> equipos, equipos2;
	private Equipo equipoJug;
	private int contJorn, jorn;
	private Equipo [][] eqEnfr;
	private List<JornadaThread> lJornTh;
	
	@FXML
	private void intialize() {
		contJorn = 0;
		jorn = 1;
		eqEnfr = new Equipo[10][2];
		lJornTh = new ArrayList<JornadaThread>();
		equipos = bbdd.selectEquiposByNombre("%");
		Collections.shuffle(equipos);
		List<Equipo> equipos = bbdd.selectEquiposByNombre("NombreEqJugador");//Cambiar a get de la otra clase
		equipoJug = equipos.get(0);
		Equipo eqTemp = equipos.set(0, equipoJug);
		equipos.add(eqTemp);
		
		for(int i = 0; i<equipos.size(); i++) {
			Clasificacion clas = new Clasificacion(equipos.get(i));
			bbdd.insertarClasificacion(clas);
		}
		for(int i = 0; i<10; i++){
			equipos2.add(equipos.remove(equipos.size() - 1));
		}
		
	}
	
	//botonSiguiente Jornada, inicializar jornada a 0 a 1, decidir despues
	private void enfrentamientosJornada() {
		if(contJorn%2==0) {
			for(int i = 0; i<equipos.size();i++) {	
				eqEnfr[i][0] = equipos.get(i);
				eqEnfr[i][1] = equipos2.get(i + contJorn);			
			}
		}else {	
			for(int i = 0; i<equipos.size();i++) {
				eqEnfr[i][1] = equipos.get(i);
				eqEnfr[i][0] = equipos2.get(i + contJorn);
			}
		}
		bbdd.insertarJornada(new Jornada(jorn));
		for(int i = 0; i<eqEnfr[0].length;i++) {
			JornadaThread jornTh = new JornadaThread(false, eqEnfr[0][i], eqEnfr[1][i], jorn);
			lJornTh.add(jornTh);
			jornTh.start();
	
		}
		contJorn++;
		jorn++;
	
		bbdd.selectClasificacion(equipos.get(0).getIdEquipo());
		
		
	}
}
