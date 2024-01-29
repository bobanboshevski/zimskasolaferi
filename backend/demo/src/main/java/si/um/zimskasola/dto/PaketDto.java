package si.um.zimskasola.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record PaketDto(Long id, String imePaket, int cenaPaket) {
}
