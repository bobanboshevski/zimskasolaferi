package si.um.zimskasola.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.zimskasola.dao.OsebaRepository;
import si.um.zimskasola.dao.PaketRepository;
import si.um.zimskasola.dto.OsebaDto;
import si.um.zimskasola.vao.Oseba;
import si.um.zimskasola.vao.Paket;

import java.time.LocalDate;
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

    @Inject
    PaketRepository paketRepository;

    @POST
    @Transactional
    public Response postOseba(OsebaDto dto) {
        Oseba oseba = new Oseba(dto);
        osebaRepository.persistAndFlush(oseba);
        return Response.ok(oseba.toDto()).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/addPaket")
    @Transactional
    public Response addPaketToOseba(@PathParam("id") Long osebaId, @QueryParam("paketId") Long paketId) {
        Optional<Oseba> osebaOptional = osebaRepository.findByIdOptional(osebaId);
        Optional<Paket> paketOptional = paketRepository.findByIdOptional(paketId);

        if (osebaOptional.isEmpty() || paketOptional.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Oseba or Paket ID").build();
        }

        Oseba oseba = osebaOptional.get();
        Paket paket = paketOptional.get();

        // Associate the Oseba with the specified Paket
        oseba.setPaket(paket);

        // Calculate expiration date (current date + one month)
        LocalDate expirationDate = LocalDate.now().plusMonths(1);

        // Set expiration date for the Oseba
        oseba.setExpirationDate(expirationDate);

        return Response.ok(oseba.toDto()).build();
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
            Long id = user.getId();
            String name = user.getIme();
            String surname = user.getPriimek();
            String paketIme = user.getPaket() != null ? user.getPaket().getImePaket() : null;
            LocalDate veljaDo = user.getPaket() != null ? user.getExpirationDate() : null;

            return Response.ok(Map.of("id", id, "name", name, "surname", surname,"paketIme", paketIme,"expirationDate", veljaDo)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "User not found")).build();
        }
    }
}
