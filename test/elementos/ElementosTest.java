package elementos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ElementosTest {

	@Test
	public void ganadorCorrecto() {
		Personaje p1 = new Personaje();
		Personaje p2 = new Personaje();
		Personaje p3 = new Personaje();
		
		ArrayList<Personaje> listaParticipantes = new ArrayList<>();
		
		listaParticipantes.add(p1);
		listaParticipantes.add(p2);
		listaParticipantes.add(p3);
		
		p1.setPuntaje(5);
		p2.setPuntaje(6);
		p3.setPuntaje(10);
		
		Partida partida = new Partida(listaParticipantes);
		
		partida.finalizarPartida();
		
		ArrayList<Personaje> resultado = new ArrayList<>();
		resultado.add(p3);

		assertEquals(resultado, partida.getGanadores());
	}
	
	@Test
	public void empateCorrecto() {
		Personaje p1 = new Personaje();
		Personaje p2 = new Personaje();
		Personaje p3 = new Personaje();
		
		ArrayList<Personaje> listaParticipantes = new ArrayList<>();
		
		listaParticipantes.add(p1);
		listaParticipantes.add(p2);
		listaParticipantes.add(p3);
		
		p1.setPuntaje(5);
		p2.setPuntaje(10);
		p3.setPuntaje(10);
		
		Partida partida = new Partida(listaParticipantes);
		
		partida.finalizarPartida();
		
		ArrayList<Personaje> resultado = new ArrayList<>();
		resultado.add(p2);
		resultado.add(p3);
		
		// el equals del personaje chequea por puntaje nada mas Y NO POR TODOS LOS ELEMENTOS. 
		// En este caso sirve pero capaz despues se rompa, con cuidadito!
		assertEquals(resultado, partida.getGanadores());
	}
	
	@Test
	public void movimientoCorrecto() {
		Personaje personaje = new Personaje();
		
		personaje.caer();
		personaje.moverDerecha();
		personaje.moverDerecha();
		
		assertEquals(personaje.getPosicion(),new Punto2D(10,10));
	}
	
	@Test
	public void lentoFunciona() {
		Personaje personaje = new Personaje();
		personaje.hit();
		
		personaje.moverDerecha();
		personaje.caer();
		
		assertEquals(personaje.getPosicion(),new Punto2D(5,5));
	}
	
	
}
