package com.infoguia.gestaopessoa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;


@Configuration
@ConditionalOnClass(value=org.thymeleaf.templatemode.TemplateMode.class)
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/home").setViewName("home");
	}

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
			.addResourceLocations("/static");
			
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:validation");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
