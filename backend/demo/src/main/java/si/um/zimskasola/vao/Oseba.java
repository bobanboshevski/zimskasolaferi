package si.um.zimskasola.vao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.zimskasola.dto.OsebaDto;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Oseba {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ime, priimek, upr_ime, geslo;
    private int starost;

    @ManyToOne // Many Oseba entities can be associated with one Paket entity
    private Paket paket; // This represents the relationship with the Paket entity

    //@Column(name = "expiration_date")
    private LocalDate expirationDate;

    public OsebaDto toDto() {
        return new OsebaDto(id, ime, priimek, starost, upr_ime, geslo);
    }

    public Oseba(OsebaDto dto) {
        setIme(dto.ime());
        setPriimek(dto.priimek());
        setStarost(dto.starost());
        setUpr_ime(dto.upr_ime());
        setGeslo(dto.geslo());
    }
}
