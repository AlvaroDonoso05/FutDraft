package com.futboldraft.controlador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.futboldraft.modelo.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class JornadaController {

	
	private BaseDatos bbdd = BaseDatos.getInstance();
	private List<Equipo> equipos, equipos2;
	private Equipo equipoJug;
	private int contJorn, jorn;
	private Equipo [][] eqEnfr;
	private List<JornadaThread> lJornTh;
	private List<Clasificacion> clasificaciones;
	private DraftController draftC;
	private boolean cambiarJug;
	
	@FXML
    private ChoiceBox<String> choiceBoxJornadas;

    @FXML
    private ListView<String> partidosListView;

    @FXML
    private ListView<String> eventosListView;
	
	@FXML
	private ImageView btnSiguienteJornada, btnSalir;
	
	private Image btnSJornada_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSJornada.png.png").toExternalForm());
	private Image btnSJornada_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSJornada_action.png").toExternalForm());
	private Image btnSalir_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSalir.png").toExternalForm());
	private Image btnSalir_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSalir_action.png").toExternalForm());
	
	private MainController mc;
	
	private ObservableList<String> listaJornadas = FXCollections.observableArrayList();
    private Map<Integer, List<String>> jornadasPartidos = new HashMap<>();
    private Map<String, List<String>> partidoEventos = new HashMap<>();
	
	@FXML
	private void intialize() {
		mc = MainController.getInstance();
		contJorn = 0;
		jorn = 1;
		eqEnfr = new Equipo[10][2];
		lJornTh = new ArrayList<JornadaThread>();
		equipos = bbdd.selectEquiposByNombre("%");
		Collections.shuffle(equipos);
		draftC = new DraftController();
		List<Equipo> equipos = bbdd.selectEquiposByNombre("NombreEqJugador");//Cambiar a get de la otra clase
		equipoJug = draftC.getEquipoJugador();
		Equipo eqTemp = equipos.set(0, equipoJug);
		equipos.add(eqTemp);
		cambiarJug = false;
		
		for(int i = 0; i<equipos.size(); i++) {
			Clasificacion clas = new Clasificacion(equipos.get(i));
			bbdd.insertarClasificacion(clas);
		}
		for(int i = 0; i<10; i++){
			equipos2.add(equipos.remove(equipos.size() - 1));
		}
		
	}
	
	public void configurarListeners() {
		 choiceBoxJornadas.getSelectionModel().selectedItemProperty().addListener((obs, oldJornada, newJornada) -> {
	            if (newJornada != null) {
	                partidosListView.setItems(FXCollections.observableArrayList(jornadasPartidos.get(newJornada)));
	                eventosListView.getItems().clear();
	            }
	        });

		 partidosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldPartido, newPartido) -> {
	            if (newPartido != null) {
	                eventosListView.setItems(FXCollections.observableArrayList(partidoEventos.get(newPartido)));
	            }
	        });
	}
	
	//botonSiguiente Jornada, inicializar jornada a 0 a 1, decidir despues
	@FXML
	private void enfrentamientosJornada() {
		cambiarJug = false;
		if(jorn < 20) {
			listaJornadas.add("Jornada " + jorn);
			choiceBoxJornadas.setItems(listaJornadas);
			choiceBoxJornadas.setItems(listaJornadas);
            choiceBoxJornadas.setValue("Jornada " + jorn);
			
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
		}	
		if(jorn%5 == 0) {
			clasificaciones = bbdd.selectClasificacionOrdenada("DESC");
			for(int i = 0; i < 3; i ++) {
				if(clasificaciones.get(i).getEquipo().equals(equipoJug)) {
					cambiarJug = true;
				}
			}
		}
	}
	
	public void hacerFichajes() {
		Jugador jPor, jDef, jMed, jDel, jPor1, jDef1, jMed1, jDel1;
		Set<Jugador> sJugadores = equipoJug.getJugadors();
		List<Jugador> lJugadores = new ArrayList<Jugador>();
		lJugadores.addAll(sJugadores);
		List<Jugador> lJugadoresP = bbdd.jugadorPosicionLibre("POR");
		List<Jugador> lJugadoresD = bbdd.jugadorPosicionLibre("DEF");
		List<Jugador> lJugadoresM = bbdd.jugadorPosicionLibre("MED");
		List<Jugador> lJugadoresDel = bbdd.jugadorPosicionLibre("DEL");
		
		jPor1 = lJugadoresP.get((int)(Math.random()*lJugadoresP.size()));
		jDef1 = lJugadoresD.get((int)(Math.random()*lJugadoresD.size()));
		jMed1 = lJugadoresM.get((int)(Math.random()*lJugadoresM.size()));
		jDel = lJugadoresDel.get((int)(Math.random()*lJugadoresDel.size()));
		
		do {
			jPor = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jPor.getPosicion().equalsIgnoreCase("POR"));
		do {
			jDef = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jDef.getPosicion().equalsIgnoreCase("DEF"));
		do {
			jMed = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jMed.getPosicion().equalsIgnoreCase("MED"));
		do {
			jDel = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jDel.getPosicion().equalsIgnoreCase("DEL"));	
	}
	
	public void cambiarJugador(String nombre, String posicion, String nombre1, String posicion1) {
		Jugador jug1 = bbdd.selectJugador(nombre, posicion);
		Jugador jug2 = bbdd.selectJugador(nombre1, posicion1);
		jug1.setEquipo(new Equipo(null));
		jug2.setEquipo(equipoJug);
		bbdd.insertarJugador(jug1);
		bbdd.insertarJugador(jug2);		
	}
	
	public void mostrarClasificacion(){
	List<Clasificacion> clasificacion = bbdd.selectClasificacionOrdenada("DESC");
	}
	

	@FXML
	public void clickBoton(MouseEvent event) {
		if(event.getSource() == btnSiguienteJornada) {
				if (btnSiguienteJornada.getImage().equals(btnSJornada_Estado1)) {
					btnSiguienteJornada.setImage(btnSJornada_Estado2);
				} else {
					btnSiguienteJornada.setImage(btnSJornada_Estado1);
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
			if (event.getSource() == btnSiguienteJornada) {
				btnSiguienteJornada.setImage(btnSJornada_Estado1);
				enfrentamientosJornada();
			} else if (event.getSource() == btnSalir) {
				btnSalir.setImage(btnSalir_Estado1);
				try {
					mc.setSiguienteVista(MainController.LOGIN);
					mc.cargarVista(MainController.LOADING);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}
}
