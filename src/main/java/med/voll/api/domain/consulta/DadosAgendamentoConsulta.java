package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.util.Date;

public record DadosAgendamentoConsulta(Long id, Medico medico, Paciente paciente, Date data) {

}
