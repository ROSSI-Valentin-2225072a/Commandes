package fr.univamu.fr.commandes;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.persistence.Id;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/commandes")
public class CommandeResource {

    private CommandeService service;

    public CommandeResource(){
    }

    public @Inject CommandeResource(CommandeRepositoryInterface commandeRepo) {
        this.service = new CommandeService(commandeRepo);
    }

    public CommandeResource(CommandeService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllCommandes() {
        return service.getAllCommandesJSON();
    }

    @GET
    @Path("{IdCommande}")
    @Produces("application/json")
    public String getCommande(@PathParam("IdCommande") int IdCommande) {

        String result = service.getCommandeJSON(IdCommande);

        if (result == null)
            throw new NotFoundException();

        return result;
    }

    @PUT
    @Path("{IdCommande}")
    @Consumes("application/json")
    public Response updateCommande(@PathParam("IdCommande") int IdCommande, Commande commande) {

        if(!service.updateCommande(IdCommande, commande))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    @GET
    @Path("/remove/{IdCommande}")
    public Response removeCommande(@PathParam("IdCommande") int IdCommande) {

        if(service.removeCommande(IdCommande))
            return Response.ok("Commande supprimee").build();
        else
            return Response.status( Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addMenu(JsonObject nouvelleCommande) {
        if (service.registerCommande(nouvelleCommande))
            return Response.ok("Commande passee").build();
        else
            return Response.status( Response.Status.CONFLICT ).build();
    }
}