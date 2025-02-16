package com.futboldraft.modelo;

import javafx.scene.image.ImageView;

public class JugadorSeleccionado {
	private ImageView calidad;
	private Jugador jugador;
	
	public JugadorSeleccionado(ImageView calidad, Jugador jugador) {
		this.calidad = calidad;
		this.jugador = jugador;
	}
	
	public ImageView getCalidad() {
		return calidad;
	}
	public void setCalidad(ImageView calidad) {
		this.calidad = calidad;
	}
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
	
}
