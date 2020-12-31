package com.simonjamesrowe.initializr.contributors;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class DockerfileManifestContributor extends BaseProjectContributor {

	@Override
	public void contribute(Path path) throws IOException {
		if (isLib()) {
			return;
		}
		Path file = Files.createFile(path.resolve("Dockerfile"));
		PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file));
		writer.println(mustacheTemplateRenderer.render("ms/Dockerfile", parameters()));
		writer.close();
	}

}
