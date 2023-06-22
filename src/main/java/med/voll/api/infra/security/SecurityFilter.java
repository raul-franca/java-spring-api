package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    /**
     * Filtra as requisições HTTP e verifica se há um token JWT válido no cabeçalho de autorização.
     * Caso haja um token válido, recupera as informações do usuário a partir do token e configura
     * a autenticação do usuário no contexto de segurança.
     *
     * @param request     O objeto HttpServletRequest contendo a requisição.
     * @param response    O objeto HttpServletResponse para a resposta.
     * @param filterChain O objeto FilterChain para continuar o processamento da requisição.
     * @throws ServletException Exceção lançada em caso de erro no filtro.
     * @throws IOException      Exceção lançada em caso de erro de E/S.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findBylogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho de autorização da requisição.
     *
     * @param request O objeto HttpServletRequest contendo a requisição.
     * @return O token JWT recuperado do cabeçalho de autorização.
     */
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}
