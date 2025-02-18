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
import javafx.scene.text.Text;
import javafx.util.Duration;

public class JornadaController {

	
	private BaseDatos bbdd = BaseDatos.getInstance();
	private List<Equipo> equipos, equipos2;
	private Equipo equipoJug;
	private int contJorn, jorn;
	private Equipo [][] eqEnfr;
	private List<Clasificacion> clasificaciones;
	private DraftController draftC;
	private boolean cambiarJug;
	private List<PartidoThread> partidos;
	
	@FXML
    private ChoiceBox<String> choiceBoxJornadas;
    @FXML
    private ListView<String> listViewPartidos;
    @FXML
    private ListView<String> listViewEventos;
    
    @FXML
    private Text txtTiempo, txtContador;
	
	@FXML
	private ImageView btnSiguienteJornada, btnSalir;
	
	private Image btnSJornada_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSJornada.png").toExternalForm());
	private Image btnSJornada_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSJornada_action.png").toExternalForm());
	private Image btnSalir_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSalir.png").toExternalForm());
	private Image btnSalir_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSalir_action.png").toExternalForm());
	
	private MainController mc;
	
	private final Map<Integer, List<PartidoThread>> jornadas = new HashMap<>();
    private final Map<String, List<String>> eventosPartidos = new HashMap<>();
	
	@FXML
	private void initialize() {
		mc = MainController.getInstance();
		contJorn = 0;
		jorn = 1;
		eqEnfr = new Equipo[190][2];
		equipos = bbdd.selectEquiposByNombre("%");
		equipoJug = mc.getEquipoJug();
		List<Equipo> equiposSinJug = bbdd.selectEquiposSinEqJug(equipoJug.getIdEquipo());
		
		Collections.shuffle(equipos);
		Equipo eqEl = null;
		for(Equipo equipo2 : equipos) {
			if(equipo2.getIdEquipo() == equipoJug.getIdEquipo()) {
				eqEl = equipo2;
			}
		}
		equipos.remove(eqEl);
		
		Equipo eqTemp = equipos.set(0, equipoJug);
		equipos.add(eqTemp);
		cambiarJug = false;
		
		bbdd.borrarClasificacion();
		for(int i = 0; i<equipos.size(); i++) {
			Clasificacion clas = new Clasificacion(equipos.get(i));
			clas.setIdEquipo(equipos.get(i).getIdEquipo());
			clas.setGolesContra(0);
			clas.setGolesFavor(0);
			clas.setPartidosJugados(0);
			clas.setPuntos(0);
			bbdd.insertarClasificacion(clas);
		}
		
		listViewPartidos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> mostrarEventos(newVal));
		
		choiceBoxJornadas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
	        if (newVal != null) {
	            int jornadaSeleccionada = Integer.parseInt(newVal.replace("Jornada ", ""));
	            mostrarJornada(jornadaSeleccionada);
	            int golesLoc = partidos.get(choiceBoxJornadas.getSelectionModel().getSelectedIndex()).getGolesLoc();
	            int golesVis = partidos.get(choiceBoxJornadas.getSelectionModel().getSelectedIndex()).getGolesVis();
	            txtContador.setText(golesLoc + " - "+ golesVis);
	            
	        }
	    });
		
	}
	
	private void mostrarJornada(int jornada) {
	    if (jornadas.containsKey(jornada)) {
	        List<PartidoThread> partidos = jornadas.get(jornada);
	        List<String> nombresPartidos = new ArrayList<>();

	        for (PartidoThread partido : partidos) {
	            nombresPartidos.add(partido.getNombrePartido());
	        }

	        listViewPartidos.getItems().setAll(nombresPartidos);
	        listViewEventos.getItems().clear();
	    }
	}
	
	//botonSiguiente Jornada, inicializar jornada a 0 a 1, decidir despues
	@FXML
	private void enfrentamientosJornada() {
		cambiarJug = false;
		if(jorn < 20) {
			choiceBoxJornadas.getItems().add("Jornada " + jorn);
	        choiceBoxJornadas.setValue("Jornada " + jorn);
	        
	        partidos = new ArrayList<>();
	        List<String> nombresPartidos = new ArrayList<>();
	        for (int i = 0; i < 19; i++) {
	        	//local
				if(i%2==0) {
					for (int  j = 0; j < equipos.size()/2; j++) {
		                eqEnfr[(i*equipos.size()/2)+j][0] = equipos.get(j);
		                eqEnfr[(i*equipos.size()/2)+j][1] = equipos.get(equipos.size() - 1 - j);
		                                     
		            }
					//visitante
				}else {
					for (int  j = 0; j < equipos.size()/2; j++) {
		            	eqEnfr[(i*equipos.size()/2)+j][1] = equipos.get(j);
		            	eqEnfr[(i*equipos.size()/2)+j][0] = equipos.get(equipos.size() - 1 - j);
		                      		                                  
		            }
					
				}
	            
	            // Rotar los equipos para la próxima jornada
	            Collections.rotate(equipos.subList(1, equipos.size()), 1);            
	        }
			
			for (int i = 0; i <equipos.size()/2; i++) {
				System.out.println(eqEnfr[i][0].getNombre() + " " + eqEnfr[i][1].getNombre());
	            String partidoNombre = eqEnfr[i+(10*contJorn)][0].getNombre() + " vs " + eqEnfr[i+(10*contJorn)][1].getNombre();
	            nombresPartidos.add(partidoNombre);
	            PartidoThread partido = new PartidoThread(partidoNombre, false, eqEnfr[i+(10*contJorn)][0], eqEnfr[i+(10*contJorn)][1], jorn, eventosPartidos, listViewPartidos, listViewEventos, txtContador);
	            partidos.add(partido);
	            eventosPartidos.put(partidoNombre, new ArrayList<>());
	        }
			
			jornadas.put(jorn, partidos);
			listViewPartidos.getItems().setAll(nombresPartidos);
			
			bbdd.insertarJornada(new Jornada(jorn));
			
			for (PartidoThread partido : partidos) {
	            partido.start();
	        }
			
			new Thread(() -> {
	            for (PartidoThread partido : partidos) {
	                try {
	                    partido.join();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();
			
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
	
	private void mostrarEventos(String partido) {
        if (partido != null) {
            listViewEventos.getItems().setAll(eventosPartidos.get(partido));
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
