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
}
