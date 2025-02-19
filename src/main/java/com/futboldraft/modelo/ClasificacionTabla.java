package com.futboldraft.modelo;

public class ClasificacionTabla {
	private int idEquipo;
    private String equipo;
    private Integer puntos;
    private Integer golesFavor;
    private Integer golesContra;
    private Integer partidosJugados;  
    
	public ClasificacionTabla(int idEquipo, String equipo, Integer puntos, Integer golesFavor, Integer golesContra,
			Integer partidosJugados) {
		this.idEquipo = idEquipo;
		this.equipo = equipo;
		this.puntos = puntos;
		this.golesFavor = golesFavor;
		this.golesContra = golesContra;
		this.partidosJugados = partidosJugados;
	}
	
	public int getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public Integer getPuntos() {
		return puntos;
	}
	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	public Integer getGolesFavor() {
		return golesFavor;
	}
	public void setGolesFavor(Integer golesFavor) {
		this.golesFavor = golesFavor;
	}
	public Integer getGolesContra() {
		return golesContra;
	}
	public void setGolesContra(Integer golesContra) {
		this.golesContra = golesContra;
	}
	public Integer getPartidosJugados() {
		return partidosJugados;
	}
	public void setPartidosJugados(Integer partidosJugados) {
		this.partidosJugados = partidosJugados;
	}
    
    
}
