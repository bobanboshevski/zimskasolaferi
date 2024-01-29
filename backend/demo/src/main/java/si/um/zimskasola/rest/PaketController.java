package si.um.zimskasola.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.zimskasola.dao.PaketRepository;
import si.um.zimskasola.dto.OsebaDto;
import si.um.zimskasola.dto.PaketDto;
import si.um.zimskasola.vao.Oseba;
import si.um.zimskasola.vao.Paket;

import java.util.List;
import java.util.stream.Collectors;

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
    @GET
    public List<PaketDto> vsePakete() {
        return paketRepository.listAll()
                .stream()
                .map(Paket::toDto)
                .collect(Collectors.toList());
    }

}
