package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.dto.DadosCancelamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // indica que a classe AgendaDeConsultas é um serviço do Spring
public class AgendaDeConsultas {

    @Autowired //é usada para injetar automaticamente as dependências necessárias na classe.
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    /**
     * Serviço responsável pelo agendamento de consultas.
     * Recebe os dados de agendamento e realiza as validações necessárias antes de criar uma nova consulta.
     *
     * @param dados Dados de agendamento da consulta.
     * @return Dados detalhados da consulta agendada.
     */
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

        // Verifica se o ID do paciente existe
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        // Verifica se o ID do médico existe (opcional)
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        // Executa as validações personalizadas para o agendamento da consulta
        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        // Se não houver médico disponível, lança uma exceção
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nesta data!");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    /**
     * Escolhe um médico disponível para o agendamento da consulta.
     * Se o ID do médico estiver definido, retorna o médico correspondente.
     * Caso contrário, escolhe um médico aleatório, livre na data especificada e com a especialidade desejada.
     *
     * @param dados Dados de agendamento da consulta.
     * @return Médico disponível para o agendamento da consulta.
     */
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        // Se o ID do médico estiver definido, retorna o médico correspondente
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        // Se a especialidade não estiver definida, lança uma exceção
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

        // Escolhe um médico aleatório, livre na data especificada e com a especialidade desejada
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    /**
     * Cancela uma consulta existente com base nos dados de cancelamento fornecidos.
     * Realiza as validações necessárias antes de cancelar a consulta.
     *
     * @param dados Dados de cancelamento da consulta.
     */
    public void cancelar(DadosCancelamentoConsulta dados) {
        // Verifica se o ID da consulta existe
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        // Executa as validações personalizadas para o cancelamento da consulta
        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
