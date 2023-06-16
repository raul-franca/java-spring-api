package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String senha;

    /**
     * Obtém a lista de autoridades (papéis) do usuário.
     *
     * @return A lista de autoridades do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return A senha do usuário.
     */
    @Override
    public String getPassword() {
        return senha;
    }

    /**
     * Obtém o nome de usuário do usuário.
     *
     * @return O nome de usuário do usuário.
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Verifica se a conta do usuário não está expirada.
     *
     * @return true se a conta não estiver expirada, false caso contrário.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Verifica se a conta do usuário não está bloqueada.
     *
     * @return true se a conta não estiver bloqueada, false caso contrário.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Verifica se as credenciais do usuário não estão expiradas.
     *
     * @return true se as credenciais não estiverem expiradas, false caso contrário.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Verifica se o usuário está habilitado.
     *
     * @return true se o usuário estiver habilitado, false caso contrário.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
