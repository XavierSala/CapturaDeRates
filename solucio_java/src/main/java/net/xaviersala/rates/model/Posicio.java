package net.xaviersala.rates.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Posicio {
	int x;
	int y;
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public void mou(char c) {
		switch(c) {
		case '>': 
			x = x + 1;
			break;
		case '<': 
			x = x - 1;
			break;
		case '^':
			y = y - 1;
			break;
		case 'v':
			y = y + 1;
			break;
		}
		
	}

}
