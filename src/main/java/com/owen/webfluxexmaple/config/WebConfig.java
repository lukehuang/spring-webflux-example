package com.owen.webfluxexmaple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

/**
 * Created by owen_q on 2018. 7. 6..
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    private Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public RouterFunction<ServerResponse> index(){
        return RouterFunctions.route(RequestPredicates.GET("/"), (serverRequest) ->
            ServerResponse.ok().render("index")
        );
    }

    @Bean
    public SpringWebFluxTemplateEngine templateEngine(){
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("classpath:/templates/");

        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafReactiveViewResolver thymeleafReactiveViewResolver = new ThymeleafReactiveViewResolver();
        thymeleafReactiveViewResolver.setTemplateEngine(templateEngine());
        thymeleafReactiveViewResolver.setApplicationContext(this.applicationContext);

        registry.viewResolver(thymeleafReactiveViewResolver);
    }
}
