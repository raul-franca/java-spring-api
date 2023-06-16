package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.dto.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.dto.DadosCadastroPaciente;


@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter //anotação do projeto Lombok que gera automaticamente os métodos de acesso (getters) para os campos da classe.
@NoArgsConstructor //gera um construtor sem argumentos para a classe.
@AllArgsConstructor //gera um construtor que aceita todos os argumentos dos campos da classe.
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded //entidade embutida (embedded) na classe ou seja, as propriedades serão armazenadas diretamente na tabela
    private Endereco endereco;
    private boolean ativo;

    /**
     * Método para excluir o paciente.
     *
     * @param id O ID do paciente a ser excluído.
     */
    public Paciente(DadosCadastroPaciente dados){

        this.id = null;
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }


    public void excluir(Long id) {
        this.ativo =false;
    }

    /**
     * Método para atualizar as informações do paciente.
     *
     * @param dados Objeto contendo os novos dados do paciente.
     */
    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {

        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }
}
