package med.voll.api.domain.medico.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamento motivo) {

}
