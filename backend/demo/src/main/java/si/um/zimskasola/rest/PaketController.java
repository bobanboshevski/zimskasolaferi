package si.um.zimskasola.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.zimskasola.dao.PaketRepository;
import si.um.zimskasola.dto.OsebaDto;
import si.um.zimskasola.dto.PaketDto;
import si.um.zimskasola.vao.Oseba;
import si.um.zimskasola.vao.Paket;

@Path("/pakete")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaketController {

    @Inject
    PaketRepository paketRepository;

    @POST
    @Transactional
    public Response postPaket(PaketDto dto) {
        Paket paket = new Paket(dto);
        paketRepository.persistAndFlush(paket);
        return Response.ok(paket.toDto()).status(Response.Status.CREATED).build();
    }

}
