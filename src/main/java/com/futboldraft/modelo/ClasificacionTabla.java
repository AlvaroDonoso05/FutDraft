package com.futboldraft.modelo;

public class ClasificacionTabla {
	private int idJugador;
	private String equipo;
	private String nombre;
	private String posicion;
	private int fAtaque;
	private int fTecnica;
	private int fDefensa;
	private int fPortero;
	
	public ClasificacionTabla(int idJugador, String equipo, String nombre, String posicion, int fAtaque,
			int fTecnica, int fDefensa, int fPortero) {
		this.idJugador = idJugador;
		this.equipo = equipo;
		this.nombre = nombre;
		this.posicion = posicion;
		this.fAtaque = fAtaque;
		this.fTecnica = fTecnica;
		this.fDefensa = fDefensa;
		this.fPortero = fPortero;
	}
	
	public int getIdJugador() {
		return idJugador;
	}
	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public void setfAtaque(int fAtaque) {
		this.fAtaque = fAtaque;
	}
	public void setfTecnica(int fTecnica) {
		this.fTecnica = fTecnica;
	}
	public void setfDefensa(int fDefensa) {
		this.fDefensa = fDefensa;
	}
	public void setfPortero(int fPortero) {
		this.fPortero = fPortero;
	}
	public int getFAtaque() {
	    return fAtaque;
	}

	public int getFTecnica() {
	    return fTecnica;
	}

	public int getFDefensa() {
	    return fDefensa;
	}

	public int getFPortero() {
	    return fPortero;
	}
	
	@Override
	public String toString() {
		return "ClasificacionTabla [idJugador=" + idJugador + ", equipo=" + equipo + ", nombre=" + nombre
				+ ", posicion=" + posicion + ", fAtaque=" + fAtaque + ", fTecnica=" + fTecnica + ", fDefensa="
				+ fDefensa + ", fPortero=" + fPortero + "]";
	}
	
	
}
