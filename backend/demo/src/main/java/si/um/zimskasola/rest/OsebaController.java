package si.um.zimskasola.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.zimskasola.dao.OsebaRepository;
import si.um.zimskasola.dto.OsebaDto;
import si.um.zimskasola.vao.Oseba;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/osebe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OsebaController {

    @Inject
    OsebaRepository osebaRepository;
    @POST
    @Transactional
    public Response postOseba(OsebaDto dto) {
        Oseba oseba = new Oseba(dto);
        osebaRepository.persistAndFlush(oseba);
        return Response.ok(oseba.toDto()).status(Response.Status.CREATED).build();
    }
    @GET
    public List<OsebaDto> vseOsebe() {
        return osebaRepository.listAll()
                .stream()
                .map(Oseba::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        Optional<Oseba> userOptional = osebaRepository.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            Oseba user = userOptional.get();
            Long id= user.getId();
            String name = user.getIme();
            String surname = user.getPriimek();


            return Response.ok(Map.of("id", id,"name", name, "surname", surname)).build();
        } else {

            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "User not found")).build();
            //Response.Status.UNAUTHORIZED
        }
    }


}
