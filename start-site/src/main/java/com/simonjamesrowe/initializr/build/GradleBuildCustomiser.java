package com.simonjamesrowe.initializr.build;

import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleTaskContainer;
import io.spring.initializr.generator.buildsystem.gradle.KotlinDslGradleBuildWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.beans.factory.annotation.Autowired;

public class GradleBuildCustomiser extends KotlinDslGradleBuildWriter implements BuildCustomizer<GradleBuild> {

	@Autowired
	private MutableProjectDescription mutableProjectDescription;

	@Override
	public void customize(GradleBuild build) {
		build.plugins().add("maven-publish");
		build.repositories().add(MavenRepository
				.withIdAndUrl("simonjamesrowe", "https://nexus-jx.simonjamesrowe.com/repository/maven-group/").build());
		build.dependencies().add("component-test");
		build.dependencies().add("logback-logstash-encoder");
		build.dependencies().add("logback-classic");
	}

	@Override
	protected void writeTasks(IndentingWriter writer, GradleTaskContainer tasks) {
		super.writeTasks(writer, tasks);
		if (mutableProjectDescription.getArtifactId().startsWith("lib")) {
			String extraTasks = "\ntasks.getByName<Jar>(\"jar\") {\n" + "\tenabled = true\n" + "}\n" + "\n"
					+ "tasks.getByName<Jar>(\"bootJar\") {\n" + "\tenabled = false\n" + "}";
			writer.println(extraTasks);
		}

		String publishing = "\npublishing {\n" + "\tpublications {\n" + "\t\tcreate<MavenPublication>(\"maven\") {\n"
				+ "\t\t\tfrom(components[\"java\"])\n" + "\t\t}\n" + "\t}\n" + "}";
		writer.println(publishing);
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
