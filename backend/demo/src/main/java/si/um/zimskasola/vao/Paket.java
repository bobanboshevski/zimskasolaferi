package si.um.zimskasola.vao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.zimskasola.dto.PaketDto;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Paket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imePaketa;
    private int cenaPaketa;

    @OneToMany(mappedBy = "paket") // One Paket entity can be associated with many Oseba entities
    private List<Oseba> osebe; // This represents the relationship with the Oseba entities

    public PaketDto toDto() {
        return new PaketDto(id, imePaketa, cenaPaketa);
    }

    public Paket(PaketDto dto) {
        setImePaketa(dto.imePaket());
        setCenaPaketa(dto.cenaPaket());
    }


    public String getImePaket() {
        return imePaketa;
    }
}
