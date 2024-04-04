package fr.univamu.fr.commandes;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

/**
 * Service pour gérer les commandes.
 */
public class CommandeService {

    /** Interface de repository pour les commandes */
    protected CommandeRepositoryInterface commandeRepo;

    /**
     * Constructeur de la classe CommandeService.
     *
     * @param commandeRepo Le repository des commandes
     */
    public CommandeService(CommandeRepositoryInterface commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    /**
     * Récupère toutes les commandes sous forme de JSON.
     *
     * @return Toutes les commandes au format JSON
     */
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

    /**
     * Récupère une commande spécifique sous forme de JSON.
     *
     * @param IdCommande L'identifiant de la commande à récupérer
     * @return La commande au format JSON
     */
    public String getCommandeJSON(int IdCommande) {
        String result = null;
        Commande myCommande = commandeRepo.getCommande(IdCommande);

        // si la commande a été trouvée
        if (myCommande != null) {
            // création du JSON et conversion de la commande
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Met à jour une commande existante.
     *
     * @param IdCommande L'identifiant de la commande à mettre à jour
     * @param commande   La nouvelle commande
     * @return true si la mise à jour est réussie, false sinon
     */
    public boolean updateCommande(int IdCommande, Commande commande) {
        return commandeRepo.updateCommande(IdCommande, commande.PrixCommande, commande.AdresseLivraison,
                commande.DateCommande, commande.DateLivraison, commande.IdUtilisateur);
    }

    /**
     * Supprime une commande existante.
     *
     * @param IdCommande L'identifiant de la commande à supprimer
     * @return true si la suppression est réussie, false sinon
     */
    public boolean removeCommande(int IdCommande) {
        boolean result = false;

        Commande commande = commandeRepo.getCommande(IdCommande);

        if (commande == null)
            throw new NotFoundException("La commande n'existe pas");
        else {
            result = commandeRepo.removeCommande(IdCommande);
        }
        return result;
    }

    /**
     * Enregistre une nouvelle commande à partir d'un objet JSON.
     *
     * @param nouvelleCommande L'objet JSON représentant la nouvelle commande
     * @return true si l'enregistrement est réussi, false sinon
     */
    public boolean registerCommande(JsonObject nouvelleCommande) {
        return commandeRepo.registerCommande(nouvelleCommande);
    }
}
