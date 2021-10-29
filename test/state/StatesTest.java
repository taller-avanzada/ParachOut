package state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import elementos.Personaje;

public class StatesTest {

	@Test
	public void deNormalALento() {
		Personaje p = new Personaje(0,0);
		p.hit(p.getVelocidadY());
		assertTrue(p.getEstado() instanceof PersonajeLento);
	}
	
	@Test
	public void deNormalAMuerto() {
		Personaje p = new Personaje(0,0);

		p.hit(p.getVelocidadY());
		p.hit(p.getVelocidadY());
		p.hit(p.getVelocidadY());
		
		assertTrue(p.getEstado() instanceof PersonajeMuerto);
	}
	
	@Test
	public void deMuertoNoSale() {
		Personaje p = new Personaje(0,0);

		p.hit(p.getVelocidadY());
		p.hit(p.getVelocidadY());
		p.hit(p.getVelocidadY());
		// aca ya esta muerto
		
		p.recogerParacaidas();
		p.recogerParacaidas();
		
		assertTrue(p.getEstado() instanceof PersonajeMuerto);
	}
	
	@Test
	public void noPasarseDe3Vidas() {
		Personaje p = new Personaje(0,0);
		p.recogerParacaidas();
		p.hit(p.getVelocidadY());
		p.recogerParacaidas();
		p.recogerParacaidas();
		p.recogerParacaidas();
		
		assertEquals(3, p.getVidas());
		
	}
	
	
	@Test
	public void noBajarDe0Vidas() {
		Personaje p = new Personaje(0,0);
		p.hit(p.getVelocidadY());
		p.hit(p.getVelocidadY());
		p.hit(p.getVelocidadY());
		p.recogerParacaidas();
		p.recogerParacaidas();
		
		assertEquals(0, p.getVidas());
	}
	
		
	/**
	@Test
	public void deNormalAMuerto() {
		
	}	
	@Test
	public void deNormalAMuerto() {
		
	}	
	@Test
	public void deNormalAMuerto() {
		
	}
 */
}
