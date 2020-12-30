package com.simonjamesrowe.initializr;

import com.simonjamesrowe.initializr.build.GradleBuildCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {

    @Bean
    @Primary
    public GradleBuildCustomiser gradleBuildCustomiser() {
        return new GradleBuildCustomiser();
    }
}
