package com.simonjamesrowe.initializr.contributors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class HelmContributor extends BaseProjectContributor {

    @Value("${template.root.path:classpath}")
    private String templateRootPath;

    public void contribute(Path projectRoot) {
        if (isLib()) {
            return;
        }
        try {
            File preview = chartTemplateDirectory("templates/ms/charts/preview");
            createChartFiles("charts/preview", preview, projectRoot);
            File ms = chartTemplateDirectory("templates/ms/charts/ms-xxxx");
            createChartFiles("charts/ms-xxxx", ms, projectRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createChartFiles(String chartName, File file, Path projectRoot) throws IOException{
        if (file.isDirectory()) {
            for (File it: file.listFiles()){
                createChartFiles(chartName + "/" + it.getName(), new File(file, it.getName()), projectRoot);
            }
            return;
        }

        FileUtils.forceMkdir( projectRoot.resolve(chartName.replace(file.getName(), "").replace("ms-xxxx", mutableProjectDescription.getArtifactId())).toFile());
        Path createdFile = Files.createFile(
                projectRoot.resolve(chartName.replace(".mustache", "").replace("ms-xxxx", mutableProjectDescription.getArtifactId())));
        PrintWriter writer = new PrintWriter(Files.newBufferedWriter(createdFile));
        writer.println(mustacheTemplateRenderer.render(("ms/" + chartName).replace(".mustache", ""), parameters()));
        writer.close();

    }

    private File chartTemplateDirectory(String subPath) throws IOException {
        if (templateRootPath.equals("classpath")) {
            return new ClassPathResource(subPath).getFile();
        }
        return new File(templateRootPath + "/" + subPath);
    }

}
