package fr.univamu.fr.commandes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;

import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;

import jakarta.ws.rs.core.Application;

/**
 * Classe représentant l'application des commandes.
 * Cette classe configure l'application JAX-RS et fournit une méthode pour produire une instance de CommandeRepositoryInterface.
 */
@ApplicationPath("/api")
@ApplicationScoped
public class CommandeApplication extends Application {

    /**
     * Produit une instance de CommandeRepositoryInterface en ouvrant une connexion à la base de données.
     * @return Une instance de CommandeRepositoryInterface connectée à la base de données.
     */
    @Produces
    private CommandeRepositoryInterface openDbConnection() {
        CommandeRepositoryMariadb db = null;

        try {
            // Connexion à la base de données MariaDB
            db = new CommandeRepositoryMariadb("jdbc:mariadb://mysql-r401rossi.alwaysdata.net/r401rossi_commandes_db", "r401rossi_comm", "password@123");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Ferme la connexion à la base de données lorsqu'une instance de CommandeRepositoryInterface est supprimée.
     * @param commandeRepo L'instance de CommandeRepositoryInterface à fermer.
     */
    private void closeDbConnection(@Disposes CommandeRepositoryInterface commandeRepo ) {
        commandeRepo.close();
    }
}
