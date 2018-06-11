package net.xaviersala.rates.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.xaviersala.rates.model.Cosa;
import net.xaviersala.rates.model.Posicio;

public class RatesDAOMongo implements RatesDAO {
	
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> coleccio;
	
	public RatesDAOMongo(String server, String bdd, String col) {
		mongo = new MongoClient(server);
		database = mongo.getDatabase(bdd);
		coleccio = database.getCollection(col);
	}

	public int getDimensio(String quina) {
		
		Document doc = coleccio.find()
				               .sort(new Document("posicio." + quina, -1)).limit(1).first();
		return ((Document) doc.get("posicio")).getInteger(quina);
	}
	
	public List<Cosa> getCoses(String que) {
			
		List<Document> docs = coleccio.find(new Document("objecte", que)).into(new ArrayList<Document>());
		List<Cosa> coses = new ArrayList<>(docs.size());
		for(Document doc: docs) {
			
			Document pos = (Document) doc.get("posicio");
			
			Cosa cosa = new Cosa((int)doc.get("id"),
			                     (String)doc.get("objecte"),
			                     new Posicio(pos.getInteger("x", 0), pos.getInteger("y", 0)));
			if (que.equals("rata")) {
				cosa.setMoviments((List<String>) doc.get("previsioMoviments"));
			}
			coses.add( cosa ); 
		}
		return coses;
	}

	@Override
	public long quantesCoses(String tipus) {
		return coleccio.count(new Document("objecte", tipus));
	}
	

}
