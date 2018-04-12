package fun.gad.time;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@EnableResourceServer
@EnableOAuth2Sso
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {


    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers()
                    .antMatchers("/ping/**")

                    .and()
                .requestMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/ping/**")))
//                .requestMatchers()
//                .antMatchers("/time/**")
                .authorizeRequests()
                .antMatchers("/time/**").authenticated();
    }

}
