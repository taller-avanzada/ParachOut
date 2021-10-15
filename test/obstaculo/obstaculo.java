package obstaculo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import elementos.Obstaculo;
import elementos.Personaje;
import elementos.Punto2D;

public class obstaculo {

	Personaje personaje;
	Obstaculo obstaculo;

	@Before
	public void setup() {
		personaje = new Personaje(); // posicion inicial ( 0 , 0 )
		obstaculo = new Obstaculo(new Punto2D(5, 5), 10, 10);
	}

	@After
	public void teardown() {
		personaje = null;
		obstaculo = null;
	}

	@Test
	public void testGolpeaObstaculo() {

		for (int i = 0; i < 1; i++) {

			personaje.moverDerecha(); // da 5 porque se mueve con velocidad 5

		}

		// se mueve 5 para la derecha y mide 10x10 y se encuentra con el obstaculo que
		// esta a (5,5)
		// y mide 10x10

		assertTrue(obstaculo.colision(personaje));

	}

	public void evadeObstaculo() {

		for (int i = 0; i < 8; i++) {

			personaje.moverDerecha(); // da 40 porque se mueve con velocidad 5 --> 5 * 8 = 40

		}

		// se mueve 40 para la derecha y mide 10x10 y NO se encuentra con el obstaculo que
		// esta a (5,5)
		// y mide 10x10

		assertFalse(obstaculo.colision(personaje));

	}

}
