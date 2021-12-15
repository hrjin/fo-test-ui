/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com           2021. 8. 17.  First Draft.
 *
 ****************************************************/
package kyobobook.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : ThymeleafViewResolverConfig.java
 * @Date        : 2021. 8. 17.
 * @author      : smlee1@kyobobook.com
 * @description : Thymeleaf 사용을 위한 Configuration
 */
@Configuration
public class ThymeleafViewResolverConfig {
	
	private final ApplicationContext applicationContext;
	
	@Autowired
	public ThymeleafViewResolverConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver(); 
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("classpath:templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML"); 
		templateResolver.setCacheable(false);
		templateResolver.setCharacterEncoding("UTF-8");
		
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		// templateEngine.addDialect(new SpringSecurityDialect());
		 templateEngine.addDialect(new LayoutDialect());
		// templateEngine.setTemplateEngineMessageSource(messageSource());
		return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");		
		viewResolver.setOrder(0);
		return viewResolver;
	}
}
