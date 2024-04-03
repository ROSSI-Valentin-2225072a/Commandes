package fr.univamu.fr.commandes;

import java.util.ArrayList;

public class Commande {
    protected int IdCommande;
    protected int PrixCommande;
    protected String AdresseLivraison;
    protected String DateCommande;
    protected String DateLivraison;
    protected int IdUtilisateur;
    protected ArrayList<DetailCommande> DetailCommande;

    public Commande(int idCommande, int prixCommande, String adresseLivraison,
                    String dateCommande,String dateLivraison, int idUtilisateur) {
        IdCommande = idCommande;
        PrixCommande = prixCommande;
        AdresseLivraison = adresseLivraison;
        DateCommande = dateCommande;
        DateLivraison = dateLivraison;
        IdUtilisateur = idUtilisateur;
    }

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

    public int getIdCommande() {
        return IdCommande;
    }

    public void setIdCommande(int idCommande) {
        IdCommande = idCommande;
    }

    public int getPrixCommande() {
        return PrixCommande;
    }

    public void setPrixCommande(int prixCommande) {
        PrixCommande = prixCommande;
    }

    public String getAdresseLivraison() {
        return AdresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        AdresseLivraison = adresseLivraison;
    }

    public String getDateCommande() {
        return DateCommande;
    }

    public void setDateCommande(String dateCommande) {
        DateCommande = dateCommande;
    }

    public String getDateLivraison() {
        return DateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        DateLivraison = dateLivraison;
    }

    public int getIdUtilisateur() {
        return IdUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        IdUtilisateur = idUtilisateur;
    }

    public ArrayList<fr.univamu.fr.commandes.DetailCommande> getDetailCommande() {
        return DetailCommande;
    }

    public void setDetailCommande(ArrayList<fr.univamu.fr.commandes.DetailCommande> detailCommande) {
        DetailCommande = detailCommande;
    }
}
