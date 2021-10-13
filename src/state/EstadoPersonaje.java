package state;

public abstract class EstadoPersonaje {
	protected double velocidadY;
	
	public EstadoPersonaje hit(int vidas) 
	{
		return this;
	}
	
	public double getVelocidadY() {
		return velocidadY;
	}
	
}
