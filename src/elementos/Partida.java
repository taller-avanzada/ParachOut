package elementos;

import java.util.ArrayList;

public class Partida
{
	private int id = 0;
	private ArrayList<Personaje> participantes;
	private ArrayList<Personaje> ganadores; // Set?
	 
	
	public Partida(ArrayList<Personaje> participantes)
	{
		this.id = (int)(Math.random()*1000);
		this.participantes = participantes;
		this.ganadores = new ArrayList<Personaje>();
	}
	
	public void iniciarPartida()
	{
		//Patente pendiente
	}
	
	public void finalizarPartida()
	{
		//algo mas aca capaz en un futuro
		determinarGanadores();
	}

	private void determinarGanadores()
	{
		Personaje maxPer = participantes.get(0);
		//Obtener ganadores de participantes y cargarlo en ganadores
		for(int i = 1 ; i < participantes.size(); i++)
		{
			if (maxPer.compareTo(participantes.get(i)) < 0)
			{
				maxPer = participantes.get(i);
			}
		}
		
		for(int i = 0 ; i < participantes.size(); i++)
		{
			if (maxPer.compareTo(participantes.get(i)) == 0) 
			{
				ganadores.add(participantes.get(i));
			}
		}
	}
	
	
	public ArrayList<Personaje> getGanadores() {
		return this.ganadores;
	}
}

