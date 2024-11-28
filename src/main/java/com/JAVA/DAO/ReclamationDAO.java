package com.JAVA.DAO;

import java.sql.SQLException;

public interface ReclamationDAO { // Remplacez `class` par `interface`
    boolean ajouterReclamation(String contenu, int consommateurId) throws SQLException;
}
