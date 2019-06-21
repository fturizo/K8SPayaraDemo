package fish.payara.support.demo.endpoints;

import fish.payara.support.demo.entities.UserData;
import fish.payara.support.demo.service.UserDataService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * REST endpoint used to manage user's data
 * 
 * @author Fabio Turizo
 */
@Path("/data")
@RequestScoped
public class UserDataEndpoint {

    @Inject
    UserDataService userDataService;

    @POST
    public Response createUser(@Valid UserData data, @Context UriInfo uriInfo) {
        UserData newUser = userDataService.store(data);
        return Response.created(UriBuilder.fromPath(uriInfo.getPath()).path("{id}").build(newUser.getId())).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") @NotNull Integer id) {
        return userDataService.retrieve(id).map(Response::ok).map(Response.ResponseBuilder::build)
                .orElseThrow(() -> new NotFoundException());
    }
    
    @GET
    @Path("/all")
    public List<UserData> getAllUsers(){
        return userDataService.listAll();
    }
}
