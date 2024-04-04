package fr.univamu.fr.commandes;

import java.util.ArrayList;

public interface CommandeRepositoryInterface {

    public void close();

    public Commande getCommande ( int IdCommande );

    public ArrayList<Commande> getAllCommandes ();

    public boolean updateCommande ( int idCommande, int prixCommande, String adresseLivraison,
                                String dateCommande,String dateLivraison, int idUtilisateur );

    public boolean removeCommande( int idCommande );

}
