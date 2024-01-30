package si.um.zimskasola.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.zimskasola.vao.Oseba;

import java.util.Optional;

@ApplicationScoped
public class OsebaRepository implements PanacheRepository<Oseba> {
    public Optional<Oseba> findByUsernameAndPassword(String username, String password) {
        return find("upr_ime = ?1 and geslo = ?2", username, password).firstResultOptional();
    }
}
