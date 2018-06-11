package net.xaviersala.rates.dao;

import java.util.List;

import net.xaviersala.rates.model.Cosa;

public interface RatesDAO {
	int getDimensio(String x);
	long quantesCoses(String tipus);
	List<Cosa> getCoses(String que);
}
