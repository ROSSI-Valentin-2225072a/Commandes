package fr.univamu.fr.commandes;

/**
 * Représente un détail de commande.
 */
public class DetailCommande {

    /** Identifiant de la commande */
    protected int idCommande;

    /** Identifiant du menu */
    protected int idMenu;

    /** Quantité du menu */
    protected int quantiteMenu;

    /**
     * Constructeur de la classe DetailCommande.
     *
     * @param idCommande   L'identifiant de la commande
     * @param idMenu       L'identifiant du menu
     * @param quantiteMenu La quantité du menu
     */
    public DetailCommande(int idCommande, int idMenu, int quantiteMenu) {
        this.idCommande = idCommande;
        this.idMenu = idMenu;
        this.quantiteMenu = quantiteMenu;
    }

    /**
     * Récupère l'identifiant de la commande.
     *
     * @return L'identifiant de la commande
     */
    public int getIdCommande() {
        return idCommande;
    }

    /**
     * Modifie l'identifiant de la commande.
     *
     * @param idCommande Le nouvel identifiant de la commande
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    /**
     * Récupère l'identifiant du menu.
     *
     * @return L'identifiant du menu
     */
    public int getIdMenu() {
        return idMenu;
    }

    /**
     * Modifie l'identifiant du menu.
     *
     * @param idMenu Le nouvel identifiant du menu
     */
    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * Récupère la quantité du menu.
     *
     * @return La quantité du menu
     */
    public int getQuantiteMenu() {
        return quantiteMenu;
    }

    /**
     * Modifie la quantité du menu.
     *
     * @param quantiteMenu La nouvelle quantité du menu
     */
    public void setQuantiteMenu(int quantiteMenu) {
        this.quantiteMenu = quantiteMenu;
    }
}
