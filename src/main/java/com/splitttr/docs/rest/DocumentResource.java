package com.splitttr.docs.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.splitttr.docs.dto.*;
import com.splitttr.docs.service.DocumentService;

@Path("/api/documents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentResource {

    @Inject
    DocumentService service;

    @GET
    public Response list(@QueryParam("ownerId") String ownerId) {
        var docs = ownerId != null
            ? service.listByOwner(ownerId)
            : service.listAll();
        return Response.ok(docs.stream().map(DocumentResponse::from).toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        return service.getById(id)
            .map(doc -> Response.ok(DocumentResponse.from(doc)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(DocumentCreateRequest req) {
        var doc = service.create(req.title(), req.content(), req.ownerId());
        return Response.status(Response.Status.CREATED)
            .entity(DocumentResponse.from(doc))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, DocumentUpdateRequest req) {
        return service.update(id, req.title(), req.content())
            .map(doc -> Response.ok(DocumentResponse.from(doc)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        return service.delete(id)
            ? Response.noContent().build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }
}
