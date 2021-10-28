package state;


public class PersonajeNormal extends EstadoPersonaje{

	public PersonajeNormal()
	{
		this.velocidadY = 2.5;
	}
	
	@Override
	public EstadoPersonaje hit(int vidas) 
	{
		if (vidas < 1) {			
			return new PersonajeMuerto(); // ver el tema de las vidas
		}
		
		return new PersonajeLento();
	}
	
}
