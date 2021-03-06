package org.genia.trainchecker.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
        /// Select name, password and 'enabled' flag
        .usersByUsernameQuery(
            "select name, password, 1 from user where name=?")
        .authoritiesByUsernameQuery(
            "select name, role from user where name=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.httpBasic()
        .and()
            .formLogin().usernameParameter("name").passwordParameter("password").loginPage("/login")
        .and()
            .logout().logoutSuccessUrl("/logout")
        .and()
            .authorizeRequests()
                .antMatchers("/views/stations.html", "/views/resetpass.html", "/views/newPass.html", "/views/registration.html", "/views/index.html", "/views/logout.html", "/login",
                        "/logout", "/register", "/views/home.html", "/", "/resources/**", "/stations/**", "/components/**",
                        "/views/about.html", "/views/wip.html", "/requestReset", "/checkLink", "/setNewPass")
                .permitAll().anyRequest().authenticated()
        .and()
            .csrf().csrfTokenRepository(csrfTokenRepository()).and()
            .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    private CsrfTokenRepository csrfTokenRepository() {
          HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
          repository.setHeaderName("X-XSRF-TOKEN");
          return repository;
    }
}
