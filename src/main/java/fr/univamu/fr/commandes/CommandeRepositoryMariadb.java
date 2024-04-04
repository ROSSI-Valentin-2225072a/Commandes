package fr.univamu.fr.commandes;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class CommandeRepositoryMariadb implements CommandeRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public CommandeRepositoryMariadb (String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

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
                "VALUES ("+IdCommande+","+PrixCommande+","+AdresseLivraison+","+DateCommande+","+DateLivraison+","+IdUtilisateur+")";

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
