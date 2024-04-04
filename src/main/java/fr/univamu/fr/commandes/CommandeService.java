package fr.univamu.fr.commandes;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class CommandeService {

    protected CommandeRepositoryInterface commandeRepo;

    public CommandeService(CommandeRepositoryInterface commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    public String getAllCommandesJSON() {

        ArrayList<Commande> allCommandes = commandeRepo.getAllCommandes();

        String result = null;
        try(Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCommandes);
        } catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public String getCommandeJSON (int IdCommande){
        String result = null;
        Commande myCommande = commandeRepo.getCommande(IdCommande);

        // si le livre a été trouvé
        if( myCommande != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public boolean updateCommande(int IdCommande, Commande commande){
        return commandeRepo.updateCommande(IdCommande, commande.PrixCommande, commande.AdresseLivraison,
                commande.DateCommande, commande.DateLivraison, commande.IdUtilisateur);
    }

    boolean removeCommande(int IdCommande){
        boolean result = false;

        // récupération des informations du livre
        Commande commande = commandeRepo.getCommande( IdCommande );

        //si le livre n'est pas trouvé
        if( commande == null )
            throw  new NotFoundException("La commande n'existe pas");
        else
        {
            // supprimer la réservation
            result = commandeRepo.removeCommande( IdCommande );
        }
        return result;
    }
}
