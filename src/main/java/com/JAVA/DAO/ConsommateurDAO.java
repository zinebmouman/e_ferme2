package com.JAVA.DAO;

import com.JAVA.Beans.Consommateur;
import java.sql.SQLException;

public interface ConsommateurDAO {
    void ajouterConsommateur(Consommateur consommateur) throws SQLException;
}
