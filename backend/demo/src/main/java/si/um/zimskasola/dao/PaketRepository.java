package si.um.zimskasola.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.zimskasola.vao.Paket;

import java.util.Optional;

@ApplicationScoped
public class PaketRepository implements PanacheRepository<Paket> {

    public Optional<Paket> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }
}
