package com.github.payne.logic.modules;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.files.abstracts.BuildGradleFile;
import lombok.Data;

@Data
public abstract class GdxModule {

    protected final String folderName;
    protected FileNode moduleRoot;
    protected FileNode mainFolder;
    protected FileNode inputPackage;

    /**
     * Template Method Design Pattern: the invariable {@link #init(GeneratorConfigs,
     * AppendableTree)} is always called first, followed by the module-dependent implementation of
     * {@link #customize(GeneratorConfigs, AppendableTree)}.
     * <p>
     * The {@code init} phase creates the "{@code moduleName/src/main}" structure and then adds each
     * JVM language's folder individually. It also takes care of automatically adding the module's
     * {@code build.gradle} file.
     *
     * @param input
     * @param vfs
     */
    public void generate(GeneratorConfigs input, AppendableTree vfs) {
        init(input, vfs);
        customize(input, vfs);
    }

    private void init(GeneratorConfigs input, AppendableTree vfs) {
        createModuleRoot(vfs);
        createJvmLanguageFolders(input);
        createBuildGradleFile(input, vfs);
        createCorePackages(input);
    }

    private void createModuleRoot(AppendableTree vfs) {
        moduleRoot = vfs.addToRoot(new FileNode(folderName));
        mainFolder = moduleRoot.getOrCreateChild("src").getOrCreateChild("main");
    }

    private void createJvmLanguageFolders(GeneratorConfigs input) {
        for (VersionedLanguage language : input.getLanguages()) {
            mainFolder.addChild(new FileNode(language.getLanguage()));
        }
    }

    private void createBuildGradleFile(GeneratorConfigs input, AppendableTree vfs) {
        vfs.addToParent(moduleRoot, getBuildGradleFile(input).createFile());
    }

    /**
     * Currently enforcing the client's chosen custom package to be in the {@code java} folder.
     */
    private void createCorePackages(GeneratorConfigs input) {
        var packages = input.getCorePackage().split("\\.");
        inputPackage = mainFolder.getOrCreateChild("java");
        for (String aPackage : packages) {
            inputPackage = inputPackage.getOrCreateChild(aPackage);
        }
    }

    /**
     * @return the {@code build.gradle} file that will be created at the root.
     */
    protected abstract BuildGradleFile getBuildGradleFile(GeneratorConfigs input);

    /**
     * Everything specific to a concrete implementation of {@link GdxModule} should be done here,
     * things such as the creation of files specific to a particular module.
     */
    protected abstract void customize(GeneratorConfigs input, AppendableTree vfs);
}
