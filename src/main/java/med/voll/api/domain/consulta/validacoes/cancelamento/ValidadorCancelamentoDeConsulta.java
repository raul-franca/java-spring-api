package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.medico.dto.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta dados);

}
