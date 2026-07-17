package robertovisconti.be_u5_w3_d5.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Ci permette di configurare le impostazioni di spring security
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    public SecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // disabilito per non far gestire l'autenticazione tramite i classici form generati dal server, come la pagina di login che mi crea di default spring
        httpSecurity.formLogin(formLogin -> formLogin.disable());


        // Dato che utilizzo JWT che è stateless devo disabilitare le sessioni
        httpSecurity.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Disabilito la protezone perchè non utilizzo i cookie
        httpSecurity.csrf(csrf -> csrf.disable());

        // Vado ad eliminare il controllo di autenticazione su tutti gli end-point che fà Spring Security di default (401)
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll().anyRequest().authenticated());

        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}
