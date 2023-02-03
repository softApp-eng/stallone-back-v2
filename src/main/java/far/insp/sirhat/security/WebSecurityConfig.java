package far.insp.sirhat.security;

import far.insp.sirhat.security.jwt.AuthEntryPointJwt;
import far.insp.sirhat.security.jwt.AuthTokenFilter;
import far.insp.sirhat.security.services.UserDetailsServiceImpl;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	UserDetailsServiceImpl userDetailsService;

	private AuthEntryPointJwt entryPointJwt;

	private CorsConfiguration corsConfiguration;

	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt entryPointJwt, CorsConfiguration corsConfiguration) {
		this.userDetailsService = userDetailsService;
		this.entryPointJwt = entryPointJwt;
		this.corsConfiguration = corsConfiguration;
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	
	@Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

	
	@Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests( auth -> auth
						.requestMatchers("/auth/**","/public/**").permitAll()
						.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/create/**").hasAnyAuthority("ADMIN","CREATE")
						.requestMatchers("/edit/**","/save/**","/delete/**").hasAnyAuthority("ADMIN","EDIT","ADMINPAS")
						.requestMatchers("/spa/**").hasAnyAuthority("SPA")
						.anyRequest().authenticated()
				)
				.exceptionHandling(e->e.authenticationEntryPoint(entryPointJwt))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.cors().configurationSource(request -> corsConfiguration).and()
				.build();
  }
	@Bean
  public WebSecurityCustomizer webSecurityCustomizer(){
		return web ->web.ignoring()
				.dispatcherTypeMatchers(DispatcherType.ERROR);
  }

}
