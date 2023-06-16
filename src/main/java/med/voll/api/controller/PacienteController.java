package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.dto.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.dto.DadosCadastroPaciente;
import med.voll.api.domain.paciente.dto.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.dto.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("paciente")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        // Cria um novo objeto Paciente com base nos dados recebidos
        var paciente = new Paciente(dados);

        // Salva o paciente no repositório
        repository.save(paciente);

        // Cria a URI para a resposta, com o ID do novo paciente criado
        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();

        // Retorna uma resposta com o status "created" e a URI do novo paciente
        // Além disso, retorna os dados do paciente para exibição
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        // Obtém a lista de pacientes ativos com paginação e mapeia para DadosListagemPaciente
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

        // Retorna uma resposta com a lista paginada de pacientes
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        // Obtém uma referência ao paciente pelo ID fornecido
        var paciente = repository.getReferenceById(id);

        // Retorna uma resposta com os detalhes do paciente
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        // Obtém uma referência ao paciente pelo ID fornecido
        var paciente = repository.getReferenceById(dados.id());

        // Atualiza as informações do paciente com base nos novos dados recebidos
        paciente.atualizarInformacoes(dados);

        // Retorna uma resposta com os detalhes do paciente atualizado
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable() Long id) {
        // Obtém uma referência ao paciente pelo ID fornecido
        var paciente = repository.getReferenceById(id);

        // Exclui o paciente
        paciente.excluir(id);

        // Retorna uma resposta com o status "no content" indicando que a exclusão foi bem-sucedida
        return ResponseEntity.noContent().build();
    }
}
