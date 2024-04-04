package fr.univamu.fr.commandes;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.persistence.Id;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Ressource REST pour les commandes.
 */
@Path("/commandes")
public class CommandeResource {

    /** Service pour gérer les commandes */
    private CommandeService service;

    /** Constructeur par défaut de CommandeResource */
    public CommandeResource(){
    }

    /**
     * Constructeur de CommandeResource avec injection de dépendance.
     *
     * @param commandeRepo Le repository des commandes
     */
    public @Inject CommandeResource(CommandeRepositoryInterface commandeRepo) {
        this.service = new CommandeService(commandeRepo);
    }

    /**
     * Constructeur de CommandeResource.
     *
     * @param service Le service de gestion des commandes
     */
    public CommandeResource(CommandeService service) {
        this.service = service;
    }

    /**
     * Récupère toutes les commandes au format JSON.
     *
     * @return Toutes les commandes au format JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCommandes() {
        return service.getAllCommandesJSON();
    }

    /**
     * Récupère une commande spécifique au format JSON.
     *
     * @param IdCommande L'identifiant de la commande à récupérer
     * @return La commande au format JSON
     */
    @GET
    @Path("{IdCommande}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCommande(@PathParam("IdCommande") int IdCommande) {
        String result = service.getCommandeJSON(IdCommande);
        if (result == null)
            throw new NotFoundException();
        return result;
    }

    /**
     * Met à jour une commande existante.
     *
     * @param IdCommande L'identifiant de la commande à mettre à jour
     * @param commande   La nouvelle commande
     * @return Réponse HTTP indiquant si la mise à jour a réussi
     */
    @PUT
    @Path("{IdCommande}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCommande(@PathParam("IdCommande") int IdCommande, Commande commande) {
        if (!service.updateCommande(IdCommande, commande))
            throw new NotFoundException();
        else
            return Response.ok("Commande mise à jour").build();
    }

    /**
     * Supprime une commande existante.
     *
     * @param IdCommande L'identifiant de la commande à supprimer
     * @return Réponse HTTP indiquant si la suppression a réussi
     */
    @GET
    @Path("/remove/{IdCommande}")
    public Response removeCommande(@PathParam("IdCommande") int IdCommande) {
        if (service.removeCommande(IdCommande))
            return Response.ok("Commande supprimée").build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Ajoute une nouvelle commande à partir d'un objet JSON.
     *
     * @param nouvelleCommande L'objet JSON représentant la nouvelle commande
     * @return Réponse HTTP indiquant si l'ajout a réussi
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMenu(JsonObject nouvelleCommande) {
        if (service.registerCommande(nouvelleCommande))
            return Response.ok("Commande ajoutée").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }
}
