package fr.univamu.fr.commandes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;

import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;

import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class CommandeApplication extends Application {

    @Produces
    private CommandeRepositoryInterface openDbConnection() {
        CommandeRepositoryMariadb db = null;

        try {
            db = new CommandeRepositoryMariadb("jdbc:mariadb://mysql-r401rossi.alwaysdata.net/r401rossi_commandes_db", "r401rossi_comm", "password@123");
            }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes CommandeRepositoryInterface commandeRepo ) {
        commandeRepo.close();
    }
}