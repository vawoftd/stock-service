package com.vawo.foundation.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zz
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Value("${api.doc.path:com.vawo.foundation.demo}")
    private String path;

    @Value("${api.doc.title:stock api doc}")
    private String title;

    @Value("${api.doc.version:0.1}")
    private String version;

    @Value("${api.doc.description:stock rest api doc}")
    private String description;

    @Bean
    public Docket createApi() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name(SenseFaceConstant.ACCESSTOKEN)
//                .description("用户令牌")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(true)
//                .build();
//        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(path))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title).description(description).version(version).build();
    }
}
