package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Cria um objeto UsernamePasswordAuthenticationToken com base nos dados de autenticação recebidos
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // Autentica o usuário com base no authenticationToken usando o AuthenticationManager
        var authentication = manager.authenticate(authenticationToken);

        // Gera um token JWT com base no usuário autenticado
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // Retorna uma resposta com o token JWT
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
