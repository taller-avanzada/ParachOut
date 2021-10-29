package state;


public class PersonajeNormal extends EstadoPersonaje{

	public PersonajeNormal()
	{
		this.velocidadY = 2.5;
	}
	
	public PersonajeNormal(double velocidadY)
	{
		this.velocidadY = velocidadY * 2;
	}
	
	@Override
	public EstadoPersonaje hit(int vidas, double velocidadY) 
	{
		if (vidas < 1) {			
			return new PersonajeMuerto(); // ver el tema de las vidas
		}
		
		return new PersonajeLento(velocidadY);
	}
}
