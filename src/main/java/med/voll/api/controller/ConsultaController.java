package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        // Agenda uma consulta com base nos dados de agendamento recebidos
        var dto = agenda.agendar(dados);

        // Retorna uma resposta com os detalhes da consulta agendada
        return ResponseEntity.ok(dto);
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
