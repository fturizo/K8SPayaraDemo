package fish.payara.support.demo.endpoints;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/log")
@RequestScoped
public class LoggingEndpoint {

    private static final Logger LOG = Logger.getLogger(LoggingEndpoint.class.getName());
    
    @GET
    @Path("/ping")
    public void ping(){
        LogManager.getLoggingMXBean().getLoggerLevel(LOG.getName());
        LOG.info("This is an information message");
        LOG.fine("This is a more detailed message");
    }
    
    @GET
    @Path("/path")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLogFilePath(){
        return System.getProperty("java.util.logging.config.file");
    }
    
    @PUT
    @Path("/{level}")
    public void switchToLevel(@PathParam("level") String level){
        LogManager.getLoggingMXBean().setLoggerLevel(LOG.getName(), level);
    }
}
