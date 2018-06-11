package net.xaviersala.rates;

import net.xaviersala.rates.dao.RatesDAO;
import net.xaviersala.rates.dao.RatesDAOMongo;

/**
 * Comprova quantes rates i quants formatges són menjats segons la base
 * de dades MongoDB.
 *
 */
public class App 
{
    public static void main( String[] args )
    {    	
        RatesDAO mongo = new RatesDAOMongo("localhost", "fabrica", "rates7");
    	
        Mapa mapa = new Mapa(mongo.getDimensio("x"), mongo.getDimensio("y"));
            	  	
        mapa.ompleMapa(mongo.getCoses("formatge"));
        mapa.ompleMapa(mongo.getCoses("gat"));  
        mapa.ompleMapa(mongo.getCoses("rata"));
        mapa.mouRates(mongo.getCoses("rata")); 

        // mapa.imprimeixMapa();

		mapa.getObjectes("gat",5).forEach( it -> {
		                        	System.out.printf("%s %d : ha menjat %d rates\n", 
		                        			it.getObjecte(), it.getId(), it.getMenjats());
		                        });;

        mapa.getObjectes("rata",5).forEach( it -> {
        	System.out.printf("%s %d : ha menjat %d formatges\n", 
        			it.getObjecte(), it.getId(), it.getMenjats());
        });

        System.out.println("----------------------");    
        
        System.out.printf("Amb %d gats s'han menjat:\n %d rates de %d i %d formatges de %d\n", 
        		mongo.quantesCoses("gat"), 
        		mapa.getRatesMenjades(), mongo.quantesCoses("rata"),
        		mapa.getFormatgesMenjats(), mongo.quantesCoses("formatge"));
        
        
    }
}
