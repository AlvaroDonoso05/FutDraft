package com.futboldraft.controlador;
import java.util.Collections;
import java.util.List;

import com.futboldraft.modelo.*;

import javafx.fxml.FXML;

public class JornadaController {

	
	private BaseDatos bbdd = BaseDatos.getInstance();
	private List<Equipo> equipos;
	private Equipo equipoJug;
	private int contJorn;
	private Equipo [][] eqEnfr;
	
	@FXML
	private void intialize() {
		contJorn = 1;
		eqEnfr = new Equipo[20][2];
		equipos = bbdd.selectEquiposByNombre("%");
		Collections.shuffle(equipos);
		List<Equipo> equipos = bbdd.selectEquiposByNombre("NombreEqJugador");//Cambiar a get de la otra clase
		equipoJug = equipos.get(0);
		Equipo eqTemp = equipos.set(0, equipoJug);
		equipos.add(eqTemp);
	}
	
	private void enfrentamientosJornada() {
		if(contJorn%2==0) {
			for(int i = 0; i<equipos.size()/2;i++) {
				eqEnfr[i][0] = equipos.get(i);
				eqEnfr[i][1] = equipos.get(i + contJorn);
			}
		}else {
			for(int i = 0; i<equipos.size()/2;i++) {
				eqEnfr[i][1] = equipos.get(i);
				eqEnfr[i][0] = equipos.get(i + contJorn);
			}
		}
	}
	
}
