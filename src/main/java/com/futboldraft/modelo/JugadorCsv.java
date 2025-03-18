package com.futboldraft.modelo;

import java.io.FileReader;
import java.util.List;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class JugadorCsv {
	@CsvBindByName(column = "Equipo")
	private String equipo;
	
	@CsvBindByName(column = "Nombre")
	private String nombre;
	
	@CsvBindByName(column = "Posicion")
	private String posicion;
	
	@CsvBindByName(column = "FuerzaAtaque")
	private int fuerzaAtaque;
	
	@CsvBindByName(column = "FuerzaTecnica")
	private int fuerzaTecnica;
	
	@CsvBindByName(column = "FuerzaDefensa")
	private int fuerzaDefensa;
	
	@CsvBindByName(column = "FuerzaPortero")
	private int fuerzaPortero;

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

	public int getFuerzaAtaque() {
		return fuerzaAtaque;
	}

	public void setFuerzaAtaque(int fuerzaAtaque) {
		this.fuerzaAtaque = fuerzaAtaque;
	}

	public int getFuerzaTecnica() {
		return fuerzaTecnica;
	}

	public void setFuerzaTecnica(int fuerzaTecnica) {
		this.fuerzaTecnica = fuerzaTecnica;
	}

	public int getFuerzaDefensa() {
		return fuerzaDefensa;
	}

	public void setFuerzaDefensa(int fuerzaDefensa) {
		this.fuerzaDefensa = fuerzaDefensa;
	}

	public int getFuerzaPortero() {
		return fuerzaPortero;
	}

	public void setFuerzaPortero(int fuerzaPortero) {
		this.fuerzaPortero = fuerzaPortero;
	}

	@Override
	public String toString() {
		return "JugadorCsv [equipo=" + equipo + ", nombre=" + nombre + ", posicion=" + posicion + ", fuerzaAtaque="
				+ fuerzaAtaque + ", fuerzaTecnica=" + fuerzaTecnica + ", fuerzaDefensa=" + fuerzaDefensa
				+ ", fuerzaPortero=" + fuerzaPortero + "]";
	}
	
	
}
