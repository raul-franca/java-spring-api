package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosListagemConsulta;
import med.voll.api.domain.medico.dto.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        // Agenda uma consulta com base nos dados de agendamento recebidos
        var dto = agenda.agendar(dados);

        // Retorna uma resposta com os detalhes da consulta agendada
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listarProximasConsultas(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var proximasConsultas = consultaRepository.findAllByDataGreaterThan(LocalDateTime.now(), paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(proximasConsultas);
    }




    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        // Cancela uma consulta com base nos dados de cancelamento recebidos
        agenda.cancelar(dados);

        // Retorna uma resposta com o status "no content" indicando que o cancelamento foi bem-sucedido
        return ResponseEntity.noContent().build();
    }

}
