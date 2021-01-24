package com.example.minio;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class Hello {

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "{hello: world}";
	}
}
