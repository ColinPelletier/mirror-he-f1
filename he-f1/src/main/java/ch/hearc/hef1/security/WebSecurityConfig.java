package ch.hearc.hef1.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private final String USERS_QUERY = "select username, password, 'true' as enabled from user where username=?";
	// private final String ROLES_QUERY = "select u.email, r.role from user u inner
	// join user_role ur on (u.id = ur.user_id) inner join role r on
	// (ur.role_id=r.id) where u.email=?";
	// private final String ROLES_QUERY = "select u.username, ur.role from user u
	// join role ur on u.role = ur.id where u.username=?";
	private final String ROLES_QUERY = "select username, role from user where username=?";

	/**
	 * Configuration de l'authentification par JDBC
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// JDBC Authentication
		auth.jdbcAuthentication().usersByUsernameQuery(USERS_QUERY).authoritiesByUsernameQuery(ROLES_QUERY)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/home").permitAll().antMatchers("/form").authenticated()
				// .antMatchers("/todoByDate").permitAll()
				.and().formLogin().loginPage("/login").permitAll().and().formLogin().loginPage("/login")
				.failureUrl("/login-error").defaultSuccessUrl("/").usernameParameter("username")
				.passwordParameter("password").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().rememberMe();
	}
	// protected void configure(HttpSecurity http) throws Exception {
	// http.authorizeRequests()
	// .antMatchers("/insert", "/form").hasRole("ADMIN")
	// .anyRequest().authenticated()
	// .and()
	// .formLogin().permitAll()
	// .and()
	// .logout().permitAll()
	// .and()
	// .exceptionHandling().accessDeniedPage("/403")
	// ;
	// }

}
