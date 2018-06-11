package net.xaviersala.rates.model;

import java.util.List;

import lombok.Data;
import net.xaviersala.rates.model.Posicio;

@Data
public class Cosa {
	int id;
	String objecte;
	Posicio posicio;
	List<String> list;
	private int menjats;
	
	public Cosa(int id, String objecte, Posicio posicio) {
		this.id = id;
		this.objecte = objecte;
		this.posicio = posicio;
		this.menjats = 0;
	}

	public List<String> getMoviments() {
		return list;
	}
	
	public void setMoviments(List<String> list) {
		this.list = list;		
	}
	
	public void incrementaMenjats() {
		menjats++;
	}
	
	public String toString() {
		return objecte.substring(0,1);
	}
	
}
