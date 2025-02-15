package com.futboldraft.controlador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.futboldraft.modelo.Jugador;
import com.futboldraft.modelo.JugadorCsv;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvController {
	
	public  List<JugadorCsv> abrirCSV() {
 		List<JugadorCsv> jugadores = null;
 		try {
 			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jugadores_ligaFantasy.csv");

 			if (inputStream == null) {
 			    throw new FileNotFoundException("El archivo no se pudo encontrar en resources");
 			}

 			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

 			CsvToBeanBuilder<JugadorCsv> csvBuilder = new CsvToBeanBuilder<>(reader);
 			CsvToBean<JugadorCsv> csv = csvBuilder.withType(JugadorCsv.class).withIgnoreLeadingWhiteSpace(true).build();

 			jugadores = csv.parse();

 		} catch (Exception e) {
 			e.printStackTrace();
 		}

 		return jugadores;
 	}
}
