package fr.univamu.fr.commandes;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Cette classe implémente l'interface CommandeRepositoryInterface et fournit des méthodes pour interagir avec une base de données MariaDB pour les commandes.
 */
public class CommandeRepositoryMariadb implements CommandeRepositoryInterface, Closeable {

    protected Connection dbConnection;

    /**
     * Constructeur de la classe CommandeRepositoryMariadb.
     * @param infoConnection Informations de connexion à la base de données.
     * @param user Nom d'utilisateur pour se connecter à la base de données.
     * @param pwd Mot de passe pour se connecter à la base de données.
     * @throws SQLException Si une erreur survient lors de l'accès à la base de données.
     * @throws ClassNotFoundException Si la classe du pilote JDBC n'est pas trouvée.
     */
    public CommandeRepositoryMariadb (String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Ferme la connexion à la base de données.
     */
    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Récupère une commande à partir de son identifiant.
     * @param IdCommande L'identifiant de la commande à récupérer.
     * @return La commande correspondant à l'identifiant donné, ou null si aucune commande n'est trouvée.
     */
    @Override
    public Commande getCommande(int IdCommande) {
        Commande selectedCommande = null;
        ArrayList<DetailCommande> allDetail;

        String query = "SELECT * FROM Commande WHERE IdCommande=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query)){
            ps.setInt(1, IdCommande);
            ResultSet result = ps.executeQuery();

            String queryDetail = "SELECT * FROM DetailCommande WHERE IdCommande=" + IdCommande;

            if( result.next() )
            {
                int PrixCommande = result.getInt("PrixCommande");
                String AdresseLivraison = result.getString("AdresseLivraison");
                String DateCommande = result.getString("DateCommande");
                String DateLivraison = result.getString("DateLivraison");
                int IdUtilisateur = result.getInt("IdUtilisateur");

                try ( PreparedStatement psDetail = dbConnection.prepareStatement(queryDetail)) {
                    ResultSet resultDetail = psDetail.executeQuery();

                    allDetail = new ArrayList<>();

                    while (resultDetail.next()) {
                        int IdMenu = resultDetail.getInt("IdMenu");
                        int QuantiteMenu = resultDetail.getInt("QuantiteMenu");
                        DetailCommande currentDetail = new DetailCommande(IdCommande, IdMenu, QuantiteMenu);
                        allDetail.add(currentDetail);
                    }
                }

                // création et initialisation de l'objet Book
                selectedCommande = new Commande(IdCommande, PrixCommande,
                        AdresseLivraison,
                        DateCommande,
                        DateLivraison,
                        IdUtilisateur,
                        allDetail);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return selectedCommande;
    }

    /**
     * Récupère toutes les commandes depuis la base de données.
     * @return Une liste d'objets Commande contenant toutes les commandes.
     * @throws RuntimeException Si une erreur survient lors de l'exécution de la requête SQL.
     */
    @Override
    public ArrayList<Commande> getAllCommandes() {
        ArrayList<Commande> listCommandes;
        ArrayList<DetailCommande> allDetail;

        String query = "SELECT * FROM Commande";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)){
            ResultSet result = ps.executeQuery();

            listCommandes = new ArrayList<>();


            while( result.next())
            {
                int IdCommande = result.getInt("IdCommande");
                int PrixCommande = result.getInt("PrixCommande");
                String AdresseLivraison = result.getString("AdresseLivraison");
                String DateCommande = result.getString("DateCommande");
                String DateLivraison = result.getString("DateLivraison");
                int IdUtilisateur = result.getInt("IdUtilisateur");

                String queryDetail = "SELECT * FROM DetailCommande WHERE IdCommande="+IdCommande;

                try ( PreparedStatement psDetail = dbConnection.prepareStatement(queryDetail)) {
                    ResultSet resultDetail = psDetail.executeQuery();

                    allDetail = new ArrayList<>();

                    while (resultDetail.next()) {
                        //int IdCommandeDumper = resultDetail.getInt("IdCommande");
                        int IdMenu = resultDetail.getInt("IdMenu");
                        int QuantiteMenu = resultDetail.getInt("QuantiteMenu");
                        DetailCommande currentDetail = new DetailCommande(IdCommande, IdMenu, QuantiteMenu);
                        allDetail.add(currentDetail);
                    }
                }

                Commande currentCommande = new Commande(IdCommande, PrixCommande,
                        AdresseLivraison,
                        DateCommande,
                        DateLivraison,
                        IdUtilisateur,
                        allDetail);

                listCommandes.add(currentCommande);
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listCommandes;
    }

    /**
     * Met à jour une commande existante dans la base de données.
     * @param idCommande L'identifiant de la commande à mettre à jour.
     * @param prixCommande Le nouveau prix de la commande.
     * @param adresseLivraison La nouvelle adresse de livraison de la commande.
     * @param dateCommande La nouvelle date de commande de la commande.
     * @param dateLivraison La nouvelle date de livraison de la commande.
     * @param idUtilisateur Le nouvel identifiant de l'utilisateur associé à la commande.
     * @return true si la mise à jour est réussie, sinon false.
     * @throws RuntimeException Si une erreur survient lors de l'exécution de la requête SQL.
     */
    @Override
    public boolean updateCommande(int idCommande, int prixCommande, String adresseLivraison, String dateCommande, String dateLivraison, int idUtilisateur) {
        String query = "UPDATE Commande SET IdCommande=?, PrixCommande=?, AdresseLivraison=?, " +
                       "DateLivraison=?, DateCommande=?, IdUtilisateur=?";

        int nbRowModified;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, idCommande);
            ps.setInt(2, prixCommande);
            ps.setString(3, adresseLivraison );
            ps.setString(4, dateCommande);
            ps.setString(5, dateLivraison);
            ps.setInt(6, idUtilisateur);

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    /**
     * Supprime une commande de la base de données.
     * @param IdCommande L'identifiant de la commande à supprimer.
     * @return true si la suppression est réussie, sinon false.
     * @throws RuntimeException Si une erreur survient lors de l'exécution de la requête SQL.
     */
    @Override
    public boolean removeCommande (int IdCommande) {
        String query = "DELETE FROM DetailCommande WHERE IdCommande = " + IdCommande;

        int nbRowModified;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "DELETE FROM Commande WHERE IdCommande = " + IdCommande;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            nbRowModified += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return ( nbRowModified != 0);
    }
    /**
     * Enregistre une nouvelle commande dans la base de données.
     * @param nouvelleCommande Les détails de la nouvelle commande à enregistrer.
     * @return true si l'enregistrement est réussi, sinon false.
     */
    @Override
    public boolean registerCommande (JsonObject nouvelleCommande) {

        int IdCommande = generateIdCommande();
        int PrixCommande = nouvelleCommande.getInt("prixCommande");
        String AdresseLivraison = nouvelleCommande.getString("adresseLivraison");
        String DateCommande = nouvelleCommande.getString("dateCommande");
        String DateLivraison = nouvelleCommande.getString("dateLivraison");
        int IdUtilisateur = nouvelleCommande.getInt("idUtilisateur");

        JsonArray DetailerCommande = nouvelleCommande.getJsonArray("contentDetails");

        ArrayList<DetailCommande> detailCommande = new ArrayList<>();

        for (JsonValue obj : DetailerCommande) {
            int IdMenu = ((JsonObject) obj).getInt("idMenu");
            int QuantiteMenu = ((JsonObject) obj).getInt("quantite");

            detailCommande.add(new DetailCommande(IdCommande, IdMenu, QuantiteMenu));
        }

        String query = "INSERT INTO Commande (`IdCommande`, `PrixCommande`, `AdresseLivraison`, `DateCommande`, `DateLivraison`, `IdUtilisateur`)" +
                "VALUES (\""+IdCommande+"\",\""+PrixCommande+"\",\""+AdresseLivraison+"\",\""+DateCommande+"\",\""+DateLivraison+"\",\""+IdUtilisateur+"\")";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.executeUpdate();

            for (DetailCommande dc : detailCommande) {

                query = "INSERT INTO DetailCommande (`IdCommande`, `IdMenu`, `QuantiteMenu`)" +
                        "VALUES ("+dc.idCommande+","+dc.idMenu+","+dc.quantiteMenu+")";

                try ( PreparedStatement psdc = dbConnection.prepareStatement(query) ){
                    psdc.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private int generateIdCommande() {
        String query = "SELECT MAX(IdCommande) FROM Commande;";

        int maxId = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();
            if (result.next()) {

                maxId = result.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return maxId;
    }
}
