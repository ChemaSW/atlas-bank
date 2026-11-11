package com.atlas.bank.atlas_bank.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity //Habilitamos la seguridad web, pero ya viene por defecto, no es necesario hacerlo
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests( auth -> auth
                // Cuidar el orden de las rutas, ya que un .anyRequest().permitAll(), puede romper toodo lo que tenemos debajo
                        //Accounts
                            //Que rutas estan habilitadas para que rol
                        .requestMatchers( HttpMethod.POST, "/api/v1/accounts" ).hasRole("ADMIN") //todo buscamos por medio de keycloack el ROL_ADMIN
                        .requestMatchers( HttpMethod.GET, "/api/v1/accounts").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.GET, "/api/v1/accounts/{id}").hasAnyRole("USER","ADMIN") //indica que puede se cualquier role de estos

                        //Transactions
                        .requestMatchers( HttpMethod.POST, "/api/v1/transactions/transfer")
                            .hasAnyRole("USER", "ADMIN")
                        .requestMatchers( HttpMethod.GET, "/api/v1/transactions/{id}/transactions")
                            .hasAnyRole("USER", "ADMIN")

                        //H2
                        .requestMatchers("/h2-console/**").permitAll() //permite toodo lo que tenga que ver con la consola
                        .anyRequest().authenticated() //necesita autenticacion
                )
                .oauth2ResourceServer(oauth2 -> oauth2 //valida un JWT y lo convertimos por medio de un metodo
                        .jwt( jwt -> jwt //recibe un token de jwt, trae roles en formato que no conoce(Keycloack)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                    //Asociada a H2, pq H2 console se renderiza dentro de un frame y Spring Security por defecto bloquea eso, entonces con esta configuracion
                        // es permitir que esta app se muestre dentro de ese frame
                .headers( headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                    //cross site request forgery->Proteccion contra ataques, donde alguien ejecuta acciones en nombre del usuario, ahorita esta para el teme de la config de h2
                .csrf(AbstractHttpConfigurer::disable);//crsf -> crsf.disable()

        return http.build();

    }

        //EXTRAER ROLES DEL TOKEN
    //Metodo de conversion de token de Keycloack para que lo entienda JWT
    private JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        //Cuando llega un JWT extrae los roles de este lugar especifico para poder convertirlos
        converter.setJwtGrantedAuthoritiesConverter( jwt -> {
            var realmsAccess = jwt.getClaimAsMap("realm_access");
            if(realmsAccess == null || realmsAccess.get("roles") == null){
                return List.of(); //Si no hay roles, no se asigna ninguno
            }
            var roles = (List<String>) realmsAccess.get("roles");

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role)) //Lo convertimos en GrantedAuthority para que el oauth2Resources -> jtw lo pueda leer
                    .collect(Collectors.toList());
        });
        /***
         *  {
         *     "realm_access:{
         *          "roles": ["USER", "ADMIN"]
         *      }"
         *  }
         */

        return converter;
    }

}


