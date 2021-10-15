package movimiento;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import elementos.Personaje;

public class testMovimiento {

	Personaje personaje;
	
	@Before
	public void setup() {
		personaje = new Personaje(); // posicion inicial ( 0 , 0 )
	}
	
	@After
	public void teardown() {
		personaje = null;
	}
	
	@Test
	public void testCaida() {
		
		for ( int i = 0 ; i < 100 ; i++ ) {
			personaje.caer();
		}
		
		assertEquals(1000,personaje.getPosicion().getY(),0); //velocidadY  = 10 * 100 = 1000
		
	}
	
	@Test
	public void testCaidaLenta() {		
		
		personaje.hit(); //reduce velocidad a 5
		
		
		for ( int i = 0 ; i < 100 ; i++ ) {
			personaje.caer();
		}
		
		assertEquals(500,personaje.getPosicion().getY(),0); //velocidadY  = 5 --> 5 * 100 = 1000
		
	}
	
	@Test
	public void testMoverDerecha() {
		
		for( int i = 0 ; i < 10 ; i++) {
			
			personaje.moverDerecha();
			
		}
		
		//ver tema velocidad
		assertEquals(50,personaje.getPosicion().getX(),0); //velocidadX = 5 --> 5 * 10 = 50 
		
	}
	
	/*public void testMoverDerechaFueraDePantalla() {
		
		for( int i = 0 ; i < 105 ; i++) {
			
			personaje.moverDerecha();
			
		}
		
		assertEquals(50,personaje.getPosicion().getX(),0); //velocidadX = 5 --> 5 * 10 = 50 
		
	}*/
	
	@Test
	public void testMoverIzquierda() {
		
		for( int i = 0 ; i < 15 ; i++) {
			
			personaje.moverIzquierda();
			
		}
		
		//ver tema velocidad
		assertEquals(-75,personaje.getPosicion().getX(),0); //velocidadX = 5 --> 5 * 15 = 75 
		
	}
	
	

}
