package com.futboldraft.controlador;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.futboldraft.modelo.*;

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
	
	public Jugador selectJugador(String nombre, String posicion) {
		Jugador jugador = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador WHERE nombre = :nombre AND posicion = :posicion");
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
	
	public void resetearJugadores() {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query<Jugador> query = session.createQuery("FROM Jugador");
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
