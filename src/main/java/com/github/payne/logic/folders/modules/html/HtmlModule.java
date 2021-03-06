package com.github.payne.logic.folders.modules.html;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.LibGdxVersion;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.folders.DynamicFile;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.html.files.HtmlBuildGradleFile;
import com.github.payne.logic.folders.modules.html.files.HtmlDefinitionGwtXmlFile;
import com.github.payne.logic.folders.modules.html.files.HtmlSuperDevDefinitionGwtXmlFile;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;
import java.util.Arrays;
import java.util.List;

public class HtmlModule extends GdxModule {

    public HtmlModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new HtmlBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        DynamicFile defGwtXmlFile = new HtmlDefinitionGwtXmlFile(input);
        corePackage.addChild(defGwtXmlFile.createFile());

        DynamicFile superDevGwtXmlFile = new HtmlSuperDevDefinitionGwtXmlFile(input);
        corePackage.addChild(superDevGwtXmlFile.createFile());

        webappFolder(input, vfs);
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addGwtLauncher(input, vfs, corePackage, folderName);
    }

    private void webappFolder(GeneratorConfigs input, AppendableTree vfs) {
        List<String> htmlModuleDestPath = Arrays.asList(folderName);
        List<String> webAppDestPath = FileUtils.appendFilePath(htmlModuleDestPath, "webapp");

        vfs.copyFolder("generator/static/html/webapp", htmlModuleDestPath, true);

        LibGdxVersion gdxVersion = input.getLibGdxVersionObject();
        if (gdxVersion.isOlderThan("1.9.12")) {
            vfs.copyFile(getSoundManagerPath(gdxVersion), webAppDestPath, "soundmanager2-jsmin.js");
            vfs.copyFile("generator/static/html/alternates/soundmanager2-setup.js", webAppDestPath);
            vfs.copyFile("generator/static/html/alternates/index_old.html", webAppDestPath,
                    "index.html");
        } else {
            vfs.copyFile("generator/static/html/alternates/index.html", webAppDestPath);
        }
    }

    private String getSoundManagerPath(LibGdxVersion gdxVersion) {
        return gdxVersion.isOlderThan("1.9.6")
                ? "generator/static/html/alternates/soundmanager2-jsmin_old.js"
                : "generator/static/html/alternates/soundmanager2-jsmin.js";
    }
}
