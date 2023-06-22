package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário fornecido.
     *
     * @param usuario O objeto do usuário para o qual o token será gerado.
     * @return O token JWT gerado.
     * @throws RuntimeException Exceção lançada em caso de erro na geração do token JWT.
     */
    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    /**
     * Obtém o subject (login do usuário) do token JWT fornecido.
     *
     * @param tokenJWT O token JWT a ser verificado.
     * @return O subject (login do usuário) extraído do token JWT.
     * @throws RuntimeException Exceção lançada em caso de erro na verificação do token JWT.
     */
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    /**
     * Obtém a data de expiração para o token JWT.
     * Neste exemplo, a data de expiração é definida como 2 horas após o momento atual.
     *
     * @return A data de expiração em formato Instant.
     */
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
