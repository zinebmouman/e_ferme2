package com.JAVA.DAO;

import java.sql.SQLException;
import java.util.List;

import com.JAVA.Beans.Offre;
import com.JAVA.Beans.OffreProduit;

public interface OffreDAO {
	Long ajouterOffre(Offre offre) throws SQLException;
	void ajouterProduitsAOffre(Long offreId, List<Long> produitIds);
	void ajouterOffreProduit(OffreProduit offreProduit);
	List<Long> getProduitsParOffre(Long offreId) throws SQLException;
	List<Offre> getToutesLesOffres(Long fermierId) throws SQLException;
}