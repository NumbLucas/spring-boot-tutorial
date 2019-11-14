package io.github.dunwu.springboot.security.config;

import io.github.dunwu.springboot.security.handler.CustomAuthenticationFailureHandler;
import io.github.dunwu.springboot.security.handler.CustomAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSucessHandler authenticationSucessHandler;

	@Autowired
	private CustomAuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user1").password(passwordEncoder().encode("123456")).roles("USER")
			.and()
			.withUser("user2").password(passwordEncoder().encode("qweasd")).roles("USER")
			.and()
			.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/unauthorized", "/login", "/*.html", "/css/*.css").permitAll()
			.anyRequest().authenticated(); // 所有请求都需要认证

		http.formLogin() // 表单登录
			// http.httpBasic() // HTTP Basic
			.loginPage("/unauthorized") // 登录页面
			.loginProcessingUrl("/login") // 处理登录请求表单的 URL
			.successHandler(authenticationSucessHandler) // 处理登录成功事件
			.failureHandler(authenticationFailureHandler); // 处理登录失败事件

		http.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
