package com.github.payne.logic.files.modules.html;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.files.modules.GdxModule;
import com.github.payne.logic.files.modules.html.files.HtmlBuildGradleFile;
import com.github.payne.logic.files.modules.html.files.HtmlDefinitionGwtXmlFile;
import com.github.payne.logic.files.modules.html.files.HtmlSuperDevDefinitionGwtXmlFile;
import com.github.payne.logic.files.root.BuildGradleFile;
import com.github.payne.logic.files.root.DynamicFile;
import com.github.payne.utils.FileUtils;
import com.github.payne.utils.LibGdxVersion;
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
        inputPackage.addChild(defGwtXmlFile.createFile());

        DynamicFile superDevGwtXmlFile = new HtmlSuperDevDefinitionGwtXmlFile(input);
        inputPackage.addChild(superDevGwtXmlFile.createFile());

        // todo: Template stuff!

        webappFolder(input, vfs);
    }

    private void webappFolder(GeneratorConfigs input, AppendableTree vfs) {
        List<String> htmlModuleDestPath = Arrays.asList(folderName);
        List<String> webAppDestPath = FileUtils.appendFilePath(htmlModuleDestPath, "webapp");

        vfs.copyFolder("generator/static/html/webapp", htmlModuleDestPath, true);
        vfs.copyFile(getSoundManagerPath(input), webAppDestPath, "soundmanager2-jsmin.js");
    }

    private String getSoundManagerPath(GeneratorConfigs input) {
        try {
            LibGdxVersion gdxVersion = new LibGdxVersion(input.getLibGdxVersion());
            return gdxVersion.isOlderThan("1.9.6")
                    ? "generator/static/html/alternates/soundmanager2-jsmin_old.js"
                    : "generator/static/html/alternates/soundmanager2-jsmin.js";
        } catch (Exception e) {
            return "generator/static/html/alternates/soundmanager2-jsmin.js";
        }
    }
}
