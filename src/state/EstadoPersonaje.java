package state;

public abstract class EstadoPersonaje {
	protected double velocidadY;
	
	public EstadoPersonaje hit(int vidas) 
	{
		if (vidas > 1)
			return new PersonajeLento();
		else return new PersonajeMuerto();
	}
	
	public double getVelocidadY() {
		return velocidadY;
	}
	
}
