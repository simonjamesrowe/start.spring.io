package com.simonjamesrowe.initializr;

import com.simonjamesrowe.initializr.build.GradleBuildCustomiser;
import com.simonjamesrowe.initializr.contributors.DockerfileManifestContributor;
import com.simonjamesrowe.initializr.contributors.HelmContributor;
import com.simonjamesrowe.initializr.contributors.JenkinsPipelineContributor;
import com.simonjamesrowe.initializr.contributors.SkaffoldContributor;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
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

	@Bean
	public DockerfileManifestContributor dockerfileManifestContributor() {
		return new DockerfileManifestContributor();
	}

	@Bean
	public HelmContributor helmContributor() {
		return new HelmContributor();
	}

	@Bean
	public JenkinsPipelineContributor jenkinsPipelineContributor() {
		return new JenkinsPipelineContributor();
	}

	@Bean
	public SkaffoldContributor skaffoldContributor() {
		return new SkaffoldContributor();
	}

	@Bean
	public Cache templatesCache() {
		return new ConcurrentMapCache("templatesCache");
	}

}
