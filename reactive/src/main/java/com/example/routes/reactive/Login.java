package com.example.routes.reactive;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * https://quarkus.io/guides/reactive-routes
 * Requisições reactives
 */

@ApplicationScoped
public class Login {

  /**
   * Mapeia a classe e implementa como resposta Json na request
   */
  private final Set<UsuarioVO> list = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

  @Route(path = "/login", methods = {HttpMethod.POST}, produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
  public Object login(RoutingContext router, @Body() UsuarioVO user, @Param("id") String id) {
    System.out.println("STRING Body: " + router.getBodyAsString());
    System.out.println("JSON Body: \n" + router.getBody());
    System.out.println("ID:" + id);
    System.out.println(user.toString());
    return Response.ok().entity(user).build().getEntity();
//    toJson(user);
//    return list;
  }

  @Route(path = "/uni")
  public Uni<UsuarioVO> uni(RoutingContext ctx) {
    return Uni.createFrom().item(UsuarioVO::new);
  }

  @Route(path = "/context", methods = {HttpMethod.POST}, produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
  public void context(RoutingContext ctx) {
    ctx.response().end("{ok: true}");
  }

  @Route(type = Route.HandlerType.FAILURE)
  public void unsupported(UnsupportedOperationException e, HttpServerResponse res) {
    res.setStatusCode(500).end(e.getMessage());
  }

  void toJson(UsuarioVO user) {
    list.add(user);
  }
}
