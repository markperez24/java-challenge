package jp.co.axa.apidemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

/**
 * Security config class using Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //Injecting values from the property file
    @Value("${security.admin.name}")
    private String adminUsername;

    @Value("${security.admin.password}")
    private String adminPassword;

    @Value("${security.admin.roles}")
    private String adminUserRoles;
    
    @Value("${security.user.name}")
    private String normalUsername;

    @Value("${security.user.password}")
    private String normalPassword;

    @Value("${security.user.roles}")
    private String normalUserRoles;

    /**
     * Configures authentication using in-memory user details.
     * 
     * @param auth The AuthenticationManagerBuilder.
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Using In-Memory user details
        auth.inMemoryAuthentication()
                .withUser(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .roles(adminUserRoles)
                .and()
                .withUser(normalUsername)
                .password(passwordEncoder().encode(normalPassword))
                .roles(normalUserRoles);    
    }

    /**
     * Configures HTTP security for the application.
     * 
     * @param http The HttpSecurity instance.
     * @throws If an error occurs during authentication.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/employees/retrieveAll")
                    .access("hasRole('"+normalUserRoles+"') or hasRole('"+adminUserRoles+"')")
                .antMatchers("/api/v1/employees/retrieve/**")
                    .access("hasRole('"+normalUserRoles+"') or hasRole('"+adminUserRoles+"')")
                .antMatchers("/api/v1/employees/save").hasRole(adminUserRoles) // Requires ADMIN Role
                .antMatchers("/api/v1/employees/delete/**").hasRole(adminUserRoles) // Requires ADMIN Role
                .antMatchers("/api/v1/employees/update/**").hasRole(adminUserRoles) // Requires ADMIN Role
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable(); 
    }

    /**
     * Provides an instance of the BCryptPasswordEncoder to encode the password.
     * 
     * @return The PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}