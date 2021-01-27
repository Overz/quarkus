package com.example.security;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.Principal;

@RequestScoped
@Path("/hello")
public class ExampleResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  Principal principal;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello RESTEasy";
  }

  @GET
  @Path("/all")
  @PermitAll
  @Produces(MediaType.TEXT_PLAIN)
  public String all() {
    return "principal: " + principal.getName() + " groups: " + jwt.getGroups() + " ID: " + jwt.getTokenID();
  }

  @GET
  @Path("/admin")
  @RolesAllowed("admin")
  @Produces(MediaType.TEXT_PLAIN)
  public String admin() {
    return "principal: " + principal.getName() + ", groups: " + jwt.getGroups() + ", ID: " + jwt.getTokenID();
  }
}