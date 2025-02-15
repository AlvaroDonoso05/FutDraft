package com.futboldraft.controlador;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.futboldraft.modelo.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DraftController {
	private BaseDatos bbdd = BaseDatos.getInstance();
	
	@FXML
	private ImageView del1, del2, cen1, cen2, cen3, cen4, def1, def2, def3, def4, por1;
	
	@FXML
	private ImageView btnEmpezar, btnSalir;
	
	@FXML
	private BorderPane seleccionJugador;

	@FXML
	private Text tNombre1, tMedia1, tAtaq1, tDef1, tTec1, tPor1, tPos1;
	@FXML
	private Text tNombre2, tMedia2, tAtaq2, tDef2, tTec2, tPor2, tPos2;
	@FXML
	private Text tNombre3, tMedia3, tAtaq3, tDef3, tTec3, tPor3, tPos3;
	@FXML
	private Text tNombre4, tMedia4, tAtaq4, tDef4, tTec4, tPor4, tPos4;
	@FXML
	private Text tNombre5, tMedia5, tAtaq5, tDef5, tTec5, tPor5, tPos5;
	
	@FXML
	private ImageView sel1, sel2, sel3, sel4, sel5;

	private Image btnEmpezar_Estado1 = new Image(getClass().getResource("/imagenes/Botones/start.png").toExternalForm());
	private Image btnEmpezar_Estado2 = new Image(getClass().getResource("/imagenes/Botones/start_action.png").toExternalForm());
	private Image btnEmpezar_dis = new Image(getClass().getResource("/imagenes/Botones/start_dis.png").toExternalForm());
	private Image btnSalir_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSalir.png").toExternalForm());
	private Image btnSalir_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSalir_action.png").toExternalForm());
	
	private Image pasarEncimaImg_Estado1 = new Image(getClass().getResource("/imagenes/rotating_card_loop.gif").toExternalForm());
	private Image pasarEncimaImg_Estado2 = new Image(getClass().getResource("/imagenes/default_selected.gif").toExternalForm());
	
	private Image bronze_Estado1 = new Image(getClass().getResource("/imagenes/cards/cardBronzeP.png").toExternalForm());
	private Image plata_Estado1 = new Image(getClass().getResource("/imagenes/cards/cardSilverP.png").toExternalForm());
	private Image oro_Estado1 = new Image(getClass().getResource("/imagenes/cards/cardGoldP.png").toExternalForm());
	private Image plus_Estado1 = new Image(getClass().getResource("/imagenes/cards/card+85P.png").toExternalForm());
	private Image mega_Estado1 = new Image(getClass().getResource("/imagenes/cards/card+90P.png").toExternalForm());
	private Image bronze_Estado2 = new Image(getClass().getResource("/imagenes/cards/cardBronzeP.gif").toExternalForm());
	private Image plata_Estado2 = new Image(getClass().getResource("/imagenes/cards/cardSilverP.gif").toExternalForm());
	private Image oro_Estado2 = new Image(getClass().getResource("/imagenes/cards/cardGoldP.gif").toExternalForm());
	private Image plus_Estado2 = new Image(getClass().getResource("/imagenes/cards/card+85P.gif").toExternalForm());
	private Image mega_Estado2 = new Image(getClass().getResource("/imagenes/cards/card+90P.gif").toExternalForm());
	
	private List<List<Text>> listaEstadisticas;
	private List<ImageView> listaImgSeleccion;
	
	private Equipo equipoJugador;


	@FXML
	public void initialize() {
		
bbdd = BaseDatos.getInstance();
		
		if(bbdd.isDBEmpty()) {
			CsvController csvContr = new CsvController();

			List<JugadorCsv> jugadoresC = csvContr.abrirCSV();
			List<Equipo> equipos = bbdd.selectEquiposByNombre("%");
			List<String> equiposString = new ArrayList<String>();
			
			for(JugadorCsv jugadorC : jugadoresC) {
				Jugador jugador = new Jugador(null, jugadorC.getNombre(), jugadorC.getPosicion(), jugadorC.getFuerzaAtaque(),
						jugadorC.getFuerzaTecnica(), jugadorC.getFuerzaDefensa(), jugadorC.getFuerzaPortero());
				bbdd.insertarJugador(jugador);
				
				for(Equipo equipoB: equipos) {
					equiposString.add(equipoB.getNombre());
				}
				
				if(!equiposString.contains(jugadorC.getEquipo())) {
					equiposString.add(jugadorC.getEquipo());
					bbdd.insertarEquipo(new Equipo(jugadorC.getEquipo()));
				}
			}
		}
		
		bbdd.resetearJugadores();
		
		
		
		btnEmpezar.setImage(btnEmpezar_dis);
		btnSalir.setImage(btnSalir_Estado1);
		del1.setImage(pasarEncimaImg_Estado1);
		del2.setImage(pasarEncimaImg_Estado1);
		cen1.setImage(pasarEncimaImg_Estado1);
		cen2.setImage(pasarEncimaImg_Estado1);
		cen3.setImage(pasarEncimaImg_Estado1);
		cen4.setImage(pasarEncimaImg_Estado1);
		def1.setImage(pasarEncimaImg_Estado1);
		def2.setImage(pasarEncimaImg_Estado1);
		def3.setImage(pasarEncimaImg_Estado1);
		def4.setImage(pasarEncimaImg_Estado1);
		por1.setImage(pasarEncimaImg_Estado1);
		
		List<Equipo> listaEquipos = bbdd.selectEquiposByNombre("%");
		
		// Obtener Equipo del Jugador
		
		int random = (int) (Math.random() * listaEquipos.size());
		equipoJugador = listaEquipos.get(random);
		
		listaEstadisticas = new ArrayList<>();
        listaEstadisticas.add(List.of(tNombre1, tMedia1, tAtaq1, tDef1, tTec1, tPor1, tPos1));
        listaEstadisticas.add(List.of(tNombre2, tMedia2, tAtaq2, tDef2, tTec2, tPor2, tPos2));
        listaEstadisticas.add(List.of(tNombre3, tMedia3, tAtaq3, tDef3, tTec3, tPor3, tPos3));
        listaEstadisticas.add(List.of(tNombre4, tMedia4, tAtaq4, tDef4, tTec4, tPor4, tPos4));
        listaEstadisticas.add(List.of(tNombre5, tMedia5, tAtaq5, tDef5, tTec5, tPor5, tPos5));
		
		listaImgSeleccion = List.of(sel1, sel2, sel3, sel4, sel5);
		
	}
	
	public List<Jugador> sacarDraft(String posicion) {
		List<Jugador> jugadoresD = new ArrayList<Jugador>();

		int cont = 0, random;

		List<Jugador> jugadoresP = bbdd.jugadorPosicion(posicion);
		while(cont <5) {
			random = (int)(Math.random()*jugadoresP.size());
			if(jugadoresP.get(random).getEquipo() == null) {
				jugadoresD.add(jugadoresP.remove(random));
				cont++;
			}
		}
		return jugadoresD;
	}
	
	
	public void anadirJugadorEquipo(String nEquipo) {
		String nJugador= "", posicion="";
		
		Jugador jugador = bbdd.selectJugador(nJugador, posicion);
		jugador.setEquipo(new Equipo(nEquipo));
		
		bbdd.insertarJugador(null);
	}
	
	@FXML
	public void clickBoton(MouseEvent event) {
		if(event.getSource() == btnEmpezar) {
			if(!btnEmpezar.getImage().equals(btnEmpezar_dis)) {
				if (btnEmpezar.getImage().equals(btnEmpezar_Estado1)) {
					btnEmpezar.setImage(btnEmpezar_Estado2);
				} else {
					btnEmpezar.setImage(btnEmpezar_Estado1);
				}
			}
		} else if(event.getSource() == btnSalir) {
			if (btnSalir.getImage().equals(btnSalir_Estado1)) {
				btnSalir.setImage(btnSalir_Estado2);
			} else {
				btnSalir.setImage(btnSalir_Estado1);
			}
		}
	}
	
	@FXML
	public void soltarBoton(MouseEvent event) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
			if (event.getSource() == btnEmpezar) {
				if(!btnEmpezar.getImage().equals(btnEmpezar_dis)) {
					btnEmpezar.setImage(btnEmpezar_Estado1);
				}
			} else if (event.getSource() == btnSalir) {
				btnSalir.setImage(btnSalir_Estado1);
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	@FXML
	public void pasarEncima(MouseEvent event) {
		ImageView imageView = (ImageView) event.getSource();
		
		if(imageView.getImage() == pasarEncimaImg_Estado1) {
			imageView.setImage(pasarEncimaImg_Estado2);
		} else if(imageView.getImage() == pasarEncimaImg_Estado2) {
			imageView.setImage(pasarEncimaImg_Estado1);
		}
		
		if(imageView.getImage() == bronze_Estado1) {
			imageView.setImage(bronze_Estado2);
		} else if(imageView.getImage() == bronze_Estado2) {
			imageView.setImage(bronze_Estado1);
		} else if(imageView.getImage() == plata_Estado1) {
			imageView.setImage(plata_Estado2);
		} else if(imageView.getImage() == plata_Estado2) {
			imageView.setImage(plata_Estado1);
		} else if(imageView.getImage() == oro_Estado1) {
			imageView.setImage(oro_Estado2);
		} else if(imageView.getImage() == oro_Estado2) {
			imageView.setImage(oro_Estado1);
		} else if(imageView.getImage() == plus_Estado1) {
			imageView.setImage(plus_Estado2);
		} else if(imageView.getImage() == plus_Estado2) {
			imageView.setImage(plus_Estado1);
		} else if(imageView.getImage() == mega_Estado1) {
			imageView.setImage(mega_Estado2);
		} else if(imageView.getImage() == mega_Estado2) {
			imageView.setImage(mega_Estado1);
		}
	}
	
	public void clickSelec(MouseEvent event) {
		ImageView imageView = (ImageView) event.getSource();
		List<Jugador> listaJugadores;
		
		if(event.getSource() == del1 || event.getSource() == del2) {
			listaJugadores = sacarDraft("DEL");
			rellenarJugadoresSeleccion(listaJugadores);
						
		} else if(event.getSource() == cen1 || event.getSource() == cen2 || event.getSource() == cen3 || 
				event.getSource() == cen4) {
			listaJugadores = sacarDraft("MED");
			rellenarJugadoresSeleccion(listaJugadores);
			
		} else if(event.getSource() == def1 || event.getSource() == def2 || event.getSource() == def3 || 
				event.getSource() == def4) {
			listaJugadores = sacarDraft("DEF");
			rellenarJugadoresSeleccion(listaJugadores);
			
		} else if(event.getSource() == por1) {
			listaJugadores = sacarDraft("POR");
			rellenarJugadoresSeleccion(listaJugadores);
			
		}
	}

	private void rellenarJugadoresSeleccion(List<Jugador> listaJugadores) {
		for(int i = 0; i < listaJugadores.size(); i++) {
			List<Text> estadistica = listaEstadisticas.get(i);
			Jugador jugador = listaJugadores.get(i);
			estadistica.get(0).setText(jugador.getNombre());
			estadistica.get(1).setText("Media");
			estadistica.get(2).setText(String.valueOf(jugador.getFuerzaAtaque()));
			estadistica.get(3).setText(String.valueOf(jugador.getFuerzaDefensa()));
			estadistica.get(4).setText(String.valueOf(jugador.getFuerzaTecnica()));
			estadistica.get(5).setText(String.valueOf(jugador.getFuerzaPortero()));
			estadistica.get(6).setText(jugador.getPosicion());
			
			ImageView imagen = listaImgSeleccion.get(i);
			double media = 70;
			if(media >= 90) {
				imagen.setImage(mega_Estado1);
			} else if(media >= 85) {
				imagen.setImage(plus_Estado1);
			} else if(media >= 80) {
				imagen.setImage(oro_Estado1);
			} else if(media >= 70) {
				imagen.setImage(plata_Estado1);
			} else {
				imagen.setImage(bronze_Estado1);
			}
		}
		
		seleccionJugador.setVisible(true);
		
	}

	public Equipo getEquipoJugador() {
		return equipoJugador;
	}

	public void setEquipoJugador(Equipo equipoJugador) {
		this.equipoJugador = equipoJugador;
	}
	
}//fin class