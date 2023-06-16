package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.dto.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.dto.DadosCadastroMedico;
import med.voll.api.domain.medico.dto.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.dto.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicosController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        // Cria um novo objeto Medico com base nos dados recebidos
        var medico = new Medico(dados);

        // Salva o médico no repositório
        repository.save(medico);

        // Cria a URI para a resposta, com o ID do novo médico criado
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        // Retorna uma resposta com o status "created" e a URI do novo médico
        // Além disso, retorna os dados do médico para exibição
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        // Obtém a lista de médicos ativos com paginação e mapeia para DadosListagemMedico
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        // Retorna uma resposta com a lista paginada de médicos
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        // Obtém uma referência ao médico pelo ID fornecido
        var medico = repository.getReferenceById(id);

        // Retorna uma resposta com os detalhes do médico
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        // Obtém uma referência ao médico pelo ID fornecido
        var medico = repository.getReferenceById(dados.id());

        // Atualiza as informações do médico com base nos novos dados recebidos
        medico.atualizarInformacoes(dados);

        // Retorna uma resposta com os detalhes do médico atualizado
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        // Obtém uma referência ao médico pelo ID fornecido
        var medico = repository.getReferenceById(id);

        // Exclui o médico
        medico.excluir(id);

        // Retorna uma resposta com o status "no content" indicando que a exclusão foi bem-sucedida
        return ResponseEntity.noContent().build();
    }
}