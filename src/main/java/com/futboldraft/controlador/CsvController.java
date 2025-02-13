package com.futboldraft.controlador;

import java.io.FileReader;
import java.util.List;

import com.futboldraft.modelo.Jugador;
import com.futboldraft.modelo.JugadorCsv;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvController {
	public static List<JugadorCsv> abrirCSV() {
 		List<JugadorCsv> jugadores = null;
 		try {
 			FileReader reader = new FileReader("main/resources/jugadores_ligaFantasy.csv");

 			CsvToBeanBuilder<JugadorCsv> csvBuilder = new CsvToBeanBuilder<>(reader);
 			CsvToBean<JugadorCsv> csv = csvBuilder.withType(JugadorCsv.class).withIgnoreLeadingWhiteSpace(true).build();

 			jugadores = csv.parse();

 		} catch (Exception e) {
 			e.printStackTrace();
 		}

 		return jugadores;
 	}
}
