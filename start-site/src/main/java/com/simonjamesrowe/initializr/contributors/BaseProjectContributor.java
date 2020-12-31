package com.simonjamesrowe.initializr.contributors;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import javax.annotation.PostConstruct;
import java.util.Map;

public abstract class BaseProjectContributor implements ProjectContributor {

	@Autowired
	protected MutableProjectDescription mutableProjectDescription;

	@Autowired(required = false)
	protected GradleBuild gradleBuild;

	@Autowired
	protected Cache cache;

	protected MustacheTemplateRenderer mustacheTemplateRenderer;

	@PostConstruct
	public void postInit() {
		mustacheTemplateRenderer = new MustacheTemplateRenderer("classpath:/templates/", cache);
	}

	protected Map<String, ?> parameters() {
		return Map.of("artifactId", mutableProjectDescription.getArtifactId());
	}

	protected boolean isLib() {
		return mutableProjectDescription.getArtifactId().startsWith("lib");
	}

	protected boolean isService() {
		return !isLib();
	}

	protected boolean isGradleBuild() {
		return gradleBuild != null;
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
