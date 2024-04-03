package fr.univamu.fr.commandes;

public class DetailCommande {
    protected int idCommande;
    protected int idMenu;
    protected int quantiteMenu;

    public DetailCommande(int idCommande, int idMenu, int quantiteMenu) {
        this.idCommande = idCommande;
        this.idMenu = idMenu;
        this.quantiteMenu = quantiteMenu;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getQuantiteMenu() {
        return quantiteMenu;
    }

    public void setQuantiteMenu(int quantiteMenu) {
        this.quantiteMenu = quantiteMenu;
    }
}
