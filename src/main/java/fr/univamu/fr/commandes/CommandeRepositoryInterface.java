package fr.univamu.fr.commandes;

import jakarta.json.JsonObject;

import java.util.ArrayList;

public interface CommandeRepositoryInterface {

    /**
     * Ferme la connexion au dépôt de commandes.
     */
    public void close();

    /**
     * Récupère une commande à partir de son identifiant.
     * @param IdCommande L'identifiant de la commande à récupérer.
     * @return L'objet Commande correspondant à l'identifiant spécifié, ou null si la commande n'est pas trouvée.
     */
    public Commande getCommande(int IdCommande);

    /**
     * Récupère toutes les commandes du dépôt.
     * @return Une liste contenant toutes les commandes présentes dans le dépôt.
     */
    public ArrayList<Commande> getAllCommandes();

    /**
     * Met à jour une commande existante dans le dépôt.
     * @param idCommande L'identifiant de la commande à mettre à jour.
     * @param prixCommande Le nouveau prix de la commande.
     * @param adresseLivraison La nouvelle adresse de livraison de la commande.
     * @param dateCommande La nouvelle date de commande de la commande.
     * @param dateLivraison La nouvelle date de livraison de la commande.
     * @param idUtilisateur Le nouvel identifiant de l'utilisateur associé à la commande.
     * @return true si la mise à jour est réussie, sinon false.
     */
    public boolean updateCommande(int idCommande, int prixCommande, String adresseLivraison,
                                  String dateCommande, String dateLivraison, int idUtilisateur);

    /**
     * Supprime une commande du dépôt.
     * @param idCommande L'identifiant de la commande à supprimer.
     * @return true si la suppression est réussie, sinon false.
     */
    public boolean removeCommande(int idCommande);

    /**
     * Enregistre une nouvelle commande dans le dépôt.
     * @param nouvelleCommande L'objet JsonObject représentant la nouvelle commande à enregistrer.
     * @return true si l'enregistrement est réussi, sinon false.
     */
    public boolean registerCommande(JsonObject nouvelleCommande);
}

