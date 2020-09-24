package com.github.payne.logic.modules.ios;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.modules.GdxModule;
import com.github.payne.logic.modules.ios.files.IosBuildGradleFile;
import com.github.payne.logic.modules.ios.files.IosRobovmPropertiesFile;
import com.github.payne.logic.modules.ios.files.IosRobovmXmlFile;
import com.github.payne.logic.root.BuildGradleFile;
import com.github.payne.logic.root.DynamicFile;
import java.util.Arrays;

public class IosModule extends GdxModule {

    public IosModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new IosBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        // todo: TEMPLATE

        vfs.copyFolder("generator/static/ios", Arrays.asList(folderName), false);

        DynamicFile roboProperties = new IosRobovmPropertiesFile(input);
        moduleRoot.addChild(roboProperties.createFile());

        DynamicFile roboXml = new IosRobovmXmlFile(input);
        moduleRoot.addChild(roboXml.createFile());

        // todo: Reflected classes (ios.ktx L94)
    }
}
