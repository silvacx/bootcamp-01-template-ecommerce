package br.com.zup.mercadolivre.config;

import br.com.zup.mercadolivre.model.Usuario;
import br.com.zup.mercadolivre.model.UsuarioLogado;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsMapper implements UserDetailsMapper {

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario) shouldBeASystemUser);
    }

}