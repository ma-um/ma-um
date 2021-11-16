package com.spuit.maum.diaryserver.web.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .securityContexts(newArrayList(securityContext()))
        .securitySchemes(newArrayList(apiKey()))
        ;
  }

  private ApiKey apiKey() {
    return new ApiKey(SECURITY_SCHEMA_NAME, "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build();
  }

  public static final String SECURITY_SCHEMA_NAME = "JWT";
  public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
  public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL,
        AUTHORIZATION_SCOPE_GLOBAL_DESC);
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return newArrayList(new SecurityReference(SECURITY_SCHEMA_NAME, authorizationScopes));
  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Maum diary-server API")
        .description("Maum diary-server API Reference for Developers")
        .termsOfServiceUrl("https://k5a207.p.ssafy.io").license("Maum")
        .licenseUrl("ssafy@ssafy.com")
        .version("0.0.1").build();
  }
}