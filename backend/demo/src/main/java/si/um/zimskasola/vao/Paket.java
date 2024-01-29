package si.um.zimskasola.vao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.zimskasola.dto.OsebaDto;
import si.um.zimskasola.dto.PaketDto;

@Entity
@Data
@NoArgsConstructor
public class Paket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imePaketa;
    private int cenaPaketa;

    public PaketDto toDto() {
        return new PaketDto(id, imePaketa, cenaPaketa);
    }
    public Paket(PaketDto dto) {

        setImePaketa(dto.imePaket());
        setCenaPaketa(dto.cenaPaket());
    }

}
