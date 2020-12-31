package com.simonjamesrowe.initializr.contributors;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class JenkinsPipelineContributor extends BaseProjectContributor {

	@Override
	public void contribute(Path path) throws IOException {

		Path file = Files.createFile(path.resolve("jenkins-x.yml"));
		PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file));
		if (isService()) {
			writer.println(mustacheTemplateRenderer.render("ms/jenkins-x.yml", parameters()));
		} else {
			writer.println(mustacheTemplateRenderer.render("lib/jenkins-x.yml", parameters()));
		}
		writer.close();
	}

}
