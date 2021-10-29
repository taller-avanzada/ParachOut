package state;

public abstract class EstadoPersonaje {
	protected double velocidadY;
	
	public EstadoPersonaje hit(int vidas, double velocidadY) 
	{
		if (vidas > 1)
			return new PersonajeLento(velocidadY);
		else return new PersonajeMuerto();
	}
	
	public double getVelocidadY() {
		return velocidadY;
	}

	public void acelerar()
	{
		velocidadY += 0.0005;
	}
	
}
