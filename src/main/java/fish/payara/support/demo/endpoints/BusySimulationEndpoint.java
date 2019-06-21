package fish.payara.support.demo.endpoints;

import fish.payara.support.demo.service.LoadGeneratorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Sample endpoint used to start/stop a simulation load on the CPU of the current host
 * @author Fabio Turizo
 */
@Path("/simulate/busy")
@RequestScoped
public class BusySimulationEndpoint {
    
    @Inject
    LoadGeneratorService loadGeneratorService;
    
    @POST
    @Path("/start")
    public Response startSimulation(){
        if(loadGeneratorService.generateCPULoad()){
            return Response.noContent().build();
        }else{
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/stop")
    public Response stopSimulation(){
        if(loadGeneratorService.stopCurrentCPULoad()){
            return Response.noContent().build();
        }else{
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
