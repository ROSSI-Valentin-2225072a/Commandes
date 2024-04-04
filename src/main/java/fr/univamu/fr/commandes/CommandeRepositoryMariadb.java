package fr.univamu.fr.commandes;

import jakarta.persistence.Id;

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
                        int IdCommandeDumper = resultDetail.getInt("IdCommande");
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

        int nbRowModified = 0;

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
        String query = "DELETE FROM Commande, DetailCommande WHERE IdCommande=" + IdCommande;

        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ( nbRowModified != 0);
    }
}
