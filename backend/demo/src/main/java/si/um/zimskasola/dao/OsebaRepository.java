package si.um.zimskasola.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.zimskasola.vao.Oseba;

@ApplicationScoped
public class OsebaRepository implements PanacheRepository<Oseba> {

}
