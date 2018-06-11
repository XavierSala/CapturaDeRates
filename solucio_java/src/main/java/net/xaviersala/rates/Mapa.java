package net.xaviersala.rates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import net.xaviersala.rates.model.Cosa;
import net.xaviersala.rates.model.Posicio;

/**
 * Classe que permet simular el comportament de les rates dins
 * de la fàbrica.
 * 
 * Es posen els objectes (separant les rates) i es mouen per 
 * veure què passa. 
 * 
 * Les caselles per on passa una rata queden marcades amb la 
 * rata que hi ha passat per darrer cop.
 * 
 * - Si una rata troba un gat desapareix
 * - Si una rata troba un formatge se'l menja
 * 
 * @author xavier
 *
 */
public class Mapa {
	static Cosa[][] mapa;
	private int formatgesMenjats;
	private int ratesMenjades;

	private final static Logger LOG = Logger.getLogger(Mapa.class.getName());
	
	/**
	 * Crea un objecte de tipus mapa.
	 * @param x posició màxima
	 * @param y posició mínima
	 */
	public Mapa(int x, int y) {
		LOG.info("Creat el mapa de " + (x+1) + "," + (y+1) + ")");
		mapa = new Cosa[x+1][y+1];
	}
	
	/**
	 * Posa objectes de tipus cosa dins de l'array bidimensional que representa
	 * que fa de mapa (i es diu mapa).
	 * @param coses coses a posar a dins
	 */
	public void ompleMapa(List<Cosa> coses) {
		for(Cosa cosa: coses) {
			LOG.fine( cosa.getObjecte() + " a (" + cosa.getPosicio().getX() + "," + cosa.getPosicio().getY() + ")");
			mapa[cosa.getPosicio().getX()][cosa.getPosicio().getY()] = cosa;
		}		
	}

	/**
	 * Mou les rates segons les posicions on han d'anar.
	 * @param rates rates que s'han de moure
	 */
	public void mouRates(List<Cosa> rates) {
		for(Cosa rata: rates) {
			
			mouRata(rata);	
		}
	}

	/**
	 * Mou una rata segons les ordres rebudes.
	 * @param rata rata que es vol moure
	 */
	private void mouRata(Cosa rata) {
		if (rata == null) {
			return;
		}
		
		boolean rataMorta = false;
		
		Posicio pos = rata.getPosicio();

		if (estaDins(pos)) {
			mapa[rata.getPosicio().getX()][rata.getPosicio().getY()] = rata;
		}

		for(String moviment : rata.getMoviments()) {

			pos.mou(moviment.charAt(0));
			// Si la rata no està dins del mapa no cal fer res.
			if (estaDins(pos)) {
				
				if (mapa[pos.getX()][pos.getY()] == null) {
					mapa[pos.getX()][pos.getY()] = rata;
				} else {
					switch(mapa[pos.getX()][pos.getY()].getObjecte()) {
					case "formatge": 
						formatgesMenjats ++;
						mapa[pos.getX()][pos.getY()] = rata;
						rata.incrementaMenjats();
						LOG.info("Rata " + rata.getId() + " menja formatge de (" 
						                 + rata.getPosicio().getX() + "," + rata.getPosicio().getY() + ") " 
								         + "->" + formatgesMenjats);
						break;
					case "gat":
						mapa[pos.getX()][pos.getY()].incrementaMenjats();
						ratesMenjades++;
						rataMorta = true;
						LOG.info("Gat " + mapa[pos.getX()][pos.getY()].getId() + " menja la Rata " + rata.getId() 
						         + "->" + ratesMenjades);
						break;
					default:
						break;
					}
					
				}
			}
			// Si la rata està morta no cal seguir
			if (rataMorta) break;
		}
		
	}

	/**
	 * Determina si una coordenada està dins del mapa o no.
	 * @param x Posició x
	 * @param y Posició y
	 * @return retorna si les coordenades estan dins.
	 */
	private boolean estaDins(Posicio pos) {		
		return (pos.getX() < mapa.length && pos.getX() >= 0) && (pos.getY() < mapa.length && pos.getY() >= 0);
	}
	
	/**
	 * Imprimeix el mapa d'objectes amb la inicial del tipus
	 * d'objecte en cada posició.
	 */
	public void imprimeixMapa() {
		
		int alt = mapa[0].length;
		int ample = mapa.length;
		
		for(int fila=0; fila < alt; fila++) {
			for(int columna = 0; columna < ample; columna++) {
				if (mapa[columna][fila]== null) {
					System.out.print(".");
				} else {
					System.out.print(mapa[columna][fila]);
				}
			}
			System.out.println("");
		}
	}
	
	/**
	 * Retorna els tipus d'objectes que han fet més feina (menjat)
	 * @param tipus Tipus d'objecte (gat, rata, formatge)
	 * @param quants quants resultats volem com a màxim
	 * @return Llista amb els resultats
	 */
	public List<Cosa> getObjectes(String tipus, int quants) {
		Set<Cosa> elements = new HashSet<>();
		
		int alt = mapa[0].length;
		int ample = mapa.length;
		for(int fila=0; fila < alt; fila++) {
			for(int columna = 0; columna < ample; columna++) {
				if (mapa[columna][fila]!= null && mapa[columna][fila].getObjecte().equals(tipus)) {
					elements.add(mapa[columna][fila]);
				} 
			}
		}
		List<Cosa> llista = new ArrayList<>(elements);
		Collections.sort(llista, (un, dos) -> dos.getMenjats() - un.getMenjats() );
		
		return llista.stream()
        .limit(quants)
        .collect(Collectors.toList());
		// return llista;
	}

	public int getFormatgesMenjats() {
		return formatgesMenjats;
	}

	public int getRatesMenjades() {
		return ratesMenjades;
	}
	
}
