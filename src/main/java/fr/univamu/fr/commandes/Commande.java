package fr.univamu.fr.commandes;

import java.util.ArrayList;

/**
 * Représente une commande.
 */
public class Commande {

    /** Identifiant de la commande */
    protected int IdCommande;

    /** Prix de la commande */
    protected int PrixCommande;

    /** Adresse de livraison de la commande */
    protected String AdresseLivraison;

    /** Date de la commande */
    protected String DateCommande;

    /** Date de livraison de la commande */
    protected String DateLivraison;

    /** Identifiant de l'utilisateur associé à la commande */
    protected int IdUtilisateur;

    /** Liste des détails de la commande */
    protected ArrayList<DetailCommande> DetailCommande;

    /**
     * Constructeur de la classe Commande.
     *
     * @param idCommande       L'identifiant de la commande
     * @param prixCommande     Le prix de la commande
     * @param adresseLivraison L'adresse de livraison de la commande
     * @param dateCommande     La date de la commande
     * @param dateLivraison    La date de livraison de la commande
     * @param idUtilisateur    L'identifiant de l'utilisateur associé à la commande
     */
    public Commande(int idCommande, int prixCommande, String adresseLivraison,
                    String dateCommande, String dateLivraison, int idUtilisateur) {
        IdCommande = idCommande;
        PrixCommande = prixCommande;
        AdresseLivraison = adresseLivraison;
        DateCommande = dateCommande;
        DateLivraison = dateLivraison;
        IdUtilisateur = idUtilisateur;
    }

    /**
     * Constructeur de la classe Commande.
     *
     * @param idCommande       L'identifiant de la commande
     * @param prixCommande     Le prix de la commande
     * @param adresseLivraison L'adresse de livraison de la commande
     * @param dateCommande     La date de la commande
     * @param dateLivraison    La date de livraison de la commande
     * @param idUtilisateur    L'identifiant de l'utilisateur associé à la commande
     * @param detailCommande   La liste des détails de la commande
     */
    public Commande(int idCommande, int prixCommande, String adresseLivraison,
                    String dateCommande, String dateLivraison, int idUtilisateur,
                    ArrayList<DetailCommande> detailCommande) {
        IdCommande = idCommande;
        PrixCommande = prixCommande;
        AdresseLivraison = adresseLivraison;
        DateCommande = dateCommande;
        DateLivraison = dateLivraison;
        IdUtilisateur = idUtilisateur;
        DetailCommande = detailCommande;
    }

    /**
     * Récupère l'identifiant de la commande.
     *
     * @return L'identifiant de la commande
     */
    public int getIdCommande() {
        return IdCommande;
    }

    /**
     * Modifie l'identifiant de la commande.
     *
     * @param idCommande Le nouvel identifiant de la commande
     */
    public void setIdCommande(int idCommande) {
        IdCommande = idCommande;
    }

    /**
     * Récupère le prix de la commande.
     *
     * @return Le prix de la commande
     */
    public int getPrixCommande() {
        return PrixCommande;
    }

    /**
     * Modifie le prix de la commande.
     *
     * @param prixCommande Le nouveau prix de la commande
     */
    public void setPrixCommande(int prixCommande) {
        PrixCommande = prixCommande;
    }

    /**
     * Récupère l'adresse de livraison de la commande.
     *
     * @return L'adresse de livraison de la commande
     */
    public String getAdresseLivraison() {
        return AdresseLivraison;
    }

    /**
     * Modifie l'adresse de livraison de la commande.
     *
     * @param adresseLivraison La nouvelle adresse de livraison de la commande
     */
    public void setAdresseLivraison(String adresseLivraison) {
        AdresseLivraison = adresseLivraison;
    }

    /**
     * Récupère la date de la commande.
     *
     * @return La date de la commande
     */
    public String getDateCommande() {
        return DateCommande;
    }

    /**
     * Modifie la date de la commande.
     *
     * @param dateCommande La nouvelle date de la commande
     */
    public void setDateCommande(String dateCommande) {
        DateCommande = dateCommande;
    }

    /**
     * Récupère la date de livraison de la commande.
     *
     * @return La date de livraison de la commande
     */
    public String getDateLivraison() {
        return DateLivraison;
    }

    /**
     * Modifie la date de livraison de la commande.
     *
     * @param dateLivraison La nouvelle date de livraison de la commande
     */
    public void setDateLivraison(String dateLivraison) {
        DateLivraison = dateLivraison;
    }

    /**
     * Récupère l'identifiant de l'utilisateur associé à la commande.
     *
     * @return L'identifiant de l'utilisateur associé à la commande
     */
    public int getIdUtilisateur() {
        return IdUtilisateur;
    }

    /**
     * Modifie l'identifiant de l'utilisateur associé à la commande.
     *
     * @param idUtilisateur Le nouvel identifiant de l'utilisateur associé à la commande
     */
    public void setIdUtilisateur(int idUtilisateur) {
        IdUtilisateur = idUtilisateur;
    }

    /**
     * Récupère la liste des détails de la commande.
     *
     * @return La liste des détails de la commande
     */
    public ArrayList<fr.univamu.fr.commandes.DetailCommande> getDetailCommande() {
        return DetailCommande;
    }

    /**
     * Modifie la liste des détails de la commande.
     *
     * @param detailCommande La nouvelle liste des détails de la commande
     */
    public void setDetailCommande(ArrayList<fr.univamu.fr.commandes.DetailCommande> detailCommande) {
        DetailCommande = detailCommande;
    }
}
