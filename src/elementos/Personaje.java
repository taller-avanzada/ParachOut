package elementos;

import java.util.Objects;

import state.EstadoPersonaje;
import state.PersonajeNormal;

public class Personaje implements Comparable<Personaje> {
	
	private Punto2D posicion;
	private int puntaje;
	private EstadoPersonaje estado; 
	private int vidas;
	private double velocidadX;
	
	public Personaje()
	{
		this.posicion = new Punto2D(0,0);
		this.setPuntaje(0);
		this.estado = new PersonajeNormal();
		this.velocidadX = 5;
		this.vidas = 3;
	}
	
	public Personaje(int x, int y)
	{
		this.posicion = new Punto2D(x,y);
		this.setPuntaje(0);
		this.estado = new PersonajeNormal();
	}
	
	public void recogerParacaidas() {
		if (0 < vidas && vidas < 3)
			vidas++;
	}
	
	public void hit() {
		this.vidas--;
		estado = estado.hit(this.vidas);
	}
	
	@Override
	public int compareTo(Personaje otro) {
		// TODO Auto-generated method stub
		return puntaje - otro.puntaje;
	}

	/**
	 * @return the estado
	 */
	public EstadoPersonaje getEstado() {
		return estado;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(puntaje);
	}

	// Esto no se si deberia comparar solo por puntaje o por todo, cuidado
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personaje other = (Personaje) obj;
		return puntaje == other.puntaje;
	}


	/**
	 * @return the vidas
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * @param puntaje the puntaje to set
	 */
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public void moverDerecha() {
		this.posicion.desplazarX(velocidadX);
	}
	
	public void moverIzquierda() {
		this.posicion.desplazarX(-velocidadX);
	}
	
	public void caer() {
		this.posicion.desplazarY(estado.getVelocidadY());
	}

	public Punto2D getPosicion() {
		return this.posicion;
	}
}


