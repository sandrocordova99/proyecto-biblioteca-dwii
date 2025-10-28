/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Home
 */
public class hola {

    @GET
    @Path("/hola")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hola() {
        System.out.println("🎯 ENDPOINT /hola LLAMADO");

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Hola mundo desde REST!");
        response.put("funciona", "si");

        return Response.ok(response).build();
    }

}
