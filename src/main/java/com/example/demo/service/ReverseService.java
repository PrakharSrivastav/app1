package com.example.demo.service;

import com.example.demo.integration.ReverseIntegration;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Service
@Path("/reverse")
public class ReverseService {

    @Autowired
    ReverseIntegration reverseIntegration;

    @GET
    @Produces("text/plain")
    public String reverse(@QueryParam("data") @NotNull String data) {
        return reverseIntegration.reverse(data);
    }
}