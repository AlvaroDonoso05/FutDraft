package com.futboldraft.controlador;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.futboldraft.modelo.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseDatos {
	private SessionFactory sessionFactory;
	private static BaseDatos instance;

	
	public boolean isDBEmpty() {
		boolean empty = true;
		Session session = null;
		long result;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			String hql = "SELECT COUNT(*) FROM Equipo";
	        Query<Long> query = session.createQuery(hql, Long.class);
			result = query.uniqueResult();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}	
		if(result > 0) {
			empty = false;
		}		
		return empty;
	}
	
	
	
	public List<Clasificacion> selectClasificacionOrdenada(String orden){
		List<Clasificacion> clasificaciones = null;
		
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Clasificacion> query = session.createQuery("FROM Clasificacion ORDER BY PUNTOS :orden");
			query.setParameter("orden", orden);
			clasificaciones = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}		
		
		return clasificaciones;
	}
	
	public ObservableList<ClasificacionTabla> obtenerClasificaciones() {
	    ObservableList<ClasificacionTabla> jugadoresClasificacion = FXCollections.observableArrayList();
	    List<Jugador> jugadores;

	    Session session = null;

	    try {
	        session = sessionFactory.getCurrentSession();
	        session.beginTransaction();

	        // Usar JOIN FETCH para asegurarse de que el equipo se cargue con el jugador
	        String hql = "FROM Jugador j LEFT JOIN FETCH j.equipo";
	        Query<Jugador> query = session.createQuery(hql, Jugador.class);
	        jugadores = query.list();

	        session.getTransaction().commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (null != session) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    } finally {
	        if (null != session) {
	            session.close();
	        }
	    }

	    for (Jugador jugador : jugadores) {
	        ClasificacionTabla clasificacion = new ClasificacionTabla(
	            jugador.getIdJugador(),
	            jugador.getEquipo() != null ? jugador.getEquipo().getNombre() : "Desconocido",
	            jugador.getNombre(),
	            jugador.getPosicion(),
	            jugador.getFuerzaAtaque(),
	            jugador.getFuerzaTecnica(),
	            jugador.getFuerzaDefensa(),
	            jugador.getFuerzaPortero()
	        );
	        jugadoresClasificacion.add(clasificacion);
	    }

	    return jugadoresClasificacion;
	}
	
	public Clasificacion selectClasificacion(int idEquipo) {
		Session session = null;
		Clasificacion clasif;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Clasificacion> query = session.createQuery("FROM Clasificacion WHERE idEquipo LIKE :idEquipo");
			query.setParameter("idEquipo", idEquipo);
			clasif = query.getSingleResult();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}	
		return clasif;
	}
	
	public void insertarEventoPartido(EventosPartido eventoPartido) {
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(eventoPartido);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}	
	}
	
	public void insertarJornada(Jornada jornada) {
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(jornada);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}	
	}
	
	public void insertarClasificacion(Clasificacion clasif) {
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(clasif);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}	
	}
	
	public void insertarPartido(Partido partido) {
	Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(partido);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}	
	
		
	}
	
	public Jugador selectJugador(String nombre, String posicion) {
		Jugador jugador = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador WHERE nombre = :nombre AND posicion LIKE :posicion");
			query.setParameter("nombre", nombre);
			query.setParameter("posicion", posicion);
			jugador = query.getSingleResult();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}		
		
		
		return jugador;
	}
	
	public synchronized void insertarEquipo(Equipo equipo) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(equipo);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}		
	}
	public List<Jugador> jugadorPosicion(String posicion) {
		Session session = null;
		List<Jugador> jugadoresP = null;

		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador a WHERE posicion = :posicion");
			query.setParameter("posicion", posicion);
			jugadoresP = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
		return jugadoresP;
	}
	
	public List<Jugador> jugadorPosicionRand(String posicion) {
		Session session = null;
		List<Jugador> jugadoresP = null;

		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador a WHERE posicion = :posicion ORDER BY RAND()");
			query.setParameter("posicion", posicion);
			query.setMaxResults(5);
			jugadoresP = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
		return jugadoresP;
	}
	
	public  List<Jugador> jugadorPosicionLibre(String posicion) {
		Session session = null;
		List<Jugador> jugadoresP = null;

		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador a WHERE equipo.nombre IS NULL AND posicion = :posicion" );
			query.setParameter("posicion", posicion);
			jugadoresP = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
		return jugadoresP;
	}

	public void insertarJugador(Jugador jugador) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(jugador);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
	}
	
	public List<Equipo> selectEquiposByNombre(String criterio) {
		List<Equipo> equipos;	
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Equipo> query = session.createQuery("FROM Equipo WHERE nombre LIKE :criterio");
			query.setParameter("criterio", criterio);
			equipos = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}		
		
		
		return equipos;
	}
	
	public List<String> selectEventosByPartido(Partido criterio) {
		List<EventosPartido> eventos;
		List<String> descripcion = new ArrayList<String>();
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<EventosPartido> query = session.createQuery("FROM EventosPartido WHERE partido = :criterio");
			query.setParameter("criterio", criterio);
			eventos = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
		
		for(EventosPartido evento: eventos) {
			descripcion.add(evento.getDescripcion());
		}
		
		
		return descripcion;
	}
	
	public void resetearJugadores() {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador WHERE equipo IS NOT NULL");
			List<Jugador> jugadores = query.list();
			for(Jugador jugador : jugadores) {
				jugador.setEquipo(null);
				session.saveOrUpdate(jugador);				
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
	}

	public void cerrarSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	public static BaseDatos getInstance(){
		if(instance == null) {
			instance = new BaseDatos();		
		}
		return instance;
	}


	private BaseDatos(){
		try {
			sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void metodoEjemplo(Jugador jugador, Equipo equipo) {
		Session session = null;
		
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (null != session) {
				session.getTransaction().rollback();
			}
			throw e;
		}
		finally {
			if (null != session) {
				session.close();
			}
		}		
	}


}
