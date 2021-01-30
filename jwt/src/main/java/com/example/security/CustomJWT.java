package com.example.security;

import io.quarkus.vertx.web.Body;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.vertx.core.http.HttpServerRequest;
import org.jboss.resteasy.spi.HttpResponse;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.HashSet;

// Transformar uma classe para JSON para devolver o valor, a classe RESPONSE do java faz automatico
//https://stackoverflow.com/questions/4687271/jax-rs-how-to-return-json-and-http-status-code-together

/**
 * Classe de geração de um token personalizado
 * Tendo uma KEY, pode ser feito de maneira manual, como foi feito abaixo.
 * <p>
 * Ou caso a aplicação permita, como o caso do quarkus, de maneira automatica passando os valores
 * no arquivo application.properties
 */
@RequestScoped
@Path("/login")
public class CustomJWT {

  private static final String secret = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwPoJKLQvZP//Y76HlxGQVA0WtTjff6zF5o+IsIKBWP00CNTUxpQ9Md4go/rhQITD6eTodWaFuDFBUHVUSfkAknZKey2+V+tldwkmHmo/dgeebmFS2JdqgBassbHAUCEqP00I1+AcmIjVSlA92BickhGaVgRjvUf51VRcgFt8G02C6+G+emXg+Fqyd5tASLGv8lqZYL0fFg2O69kRsbrZgI9GVQ+xNOsK6dJFMxAFno2/rE6QKKAV5aVhcMXZqT5NZmIPAZvdxRPJn1Nh/xPf6QRpmygaLDA5ALej+7gJiGj2VxQ13Mm+NWSMdlxOnUa6SYye7b9650kVraq4TXPtNwIDAQAB";
  private static final String l = "admin";
  private static final String s = "admin";

  @POST
  @Path("/entrar")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(@Body LoginVO login, @Context HttpResponse res) {
    if (login.getLogin().equals(l) && login.getSenha().equals(s)) {
      String token = generateToken(login);
      res.getOutputHeaders().add(HttpHeaders.AUTHORIZATION, token);
      Cookie cookie = new Cookie("jwt", token, "/", "custom-jwt.local");
      return Response.ok().entity(login).cookie(new NewCookie(cookie)).build();
    }
    return Response.status(400).build();
  }


  @GET
  @Path("/cookie")
  @Produces(MediaType.APPLICATION_JSON)
  public String cookie(@Context HttpServerRequest req) {
    return req.getHeader("Cookie");
  }

  @GET
  @Path("/admin")
  @Produces(MediaType.APPLICATION_JSON)
  public Response admin(@Context HttpServerRequest req) {
    var map = req.headers();
    Object o = "{ ok: true, cookie: " + map.get("Cookie") + " }";
    return Response.ok().entity(o).build();
  }

  private String generateToken(LoginVO login) {
    JwtClaimsBuilder builder = Jwt.issuer("custom-jwt").groups(new HashSet<>(Collections.singletonList("admin"))).claim("email", login.getLogin());
    return builder.signWithSecret(secret);
  }

}
