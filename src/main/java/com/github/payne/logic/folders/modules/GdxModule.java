package com.github.payne.logic.folders.modules;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;
import com.github.payne.utils.EnumMapper;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

// todo: util method for DynamicFiles to have less code in implementations?
@Data
public abstract class GdxModule {

    protected final String folderName;

    /**
     * Points at the root of the module.
     */
    protected FileNode modulePackage;

    /**
     * Points at the "{@code main}" folder of the module (from "{@code module/src/main}").
     */
    protected FileNode mainPackage;

    /**
     * Points at the last folder designated by {@link GeneratorConfigs#getCorePackage()}. For
     * example, if {@code corePackage = "com.foo.bar"}, then this attribute points to the {@code
     * bar} folder.
     */
    protected FileNode corePackage;

    /**
     * Template Method Design Pattern: the invariable {@link #init(GeneratorConfigs,
     * AppendableTree)} is always called first, followed by the module-dependent implementation of
     * {@link #customize(GeneratorConfigs, AppendableTree)}.
     * <p>
     * The {@code init} phase creates the "{@code moduleName/src/main}" structure and then adds each
     * JVM language's folder individually. It also takes care of automatically adding the module's
     * {@code build.gradle} file.
     */
    public final void generate(GeneratorConfigs input, AppendableTree vfs) {
        init(input, vfs);
        customize(input, vfs);
        applyTemplate(input, vfs, EnumMapper.getTemplate(input.getTemplate()));
    }

    private void init(GeneratorConfigs input, AppendableTree vfs) {
        createModuleRoot(vfs);
        createJvmLanguageFolders(input);
        createBuildGradleFile(input, vfs);
        createCorePackages(input);
    }

    private void createModuleRoot(AppendableTree vfs) {
        modulePackage = vfs.addToRoot(new FileNode(folderName));
        mainPackage = modulePackage.getOrCreateChild("src").getOrCreateChild("main");
    }

    private void createJvmLanguageFolders(GeneratorConfigs input) {
        for (VersionedLanguage language : input.getLanguages()) {
            mainPackage.addChild(new FileNode(language.getLanguage()));
        }
    }

    private void createBuildGradleFile(GeneratorConfigs input, AppendableTree vfs) {
        vfs.addToParent(modulePackage, getBuildGradleFile(input).createFile());
    }

    /**
     * Currently enforcing the client's chosen custom package to be in the {@code java} folder.
     */
    private void createCorePackages(GeneratorConfigs input) {
        var packages = input.getCorePackage().split("\\.");
        corePackage = mainPackage.getOrCreateChild("java");
        for (String aPackage : packages) {
            corePackage = corePackage.getOrCreateChild(aPackage);
        }
    }

    protected final void copyIcons(AppendableTree vfs) {
        final List<String> DEST_PATH = Arrays.asList(folderName, "src", "main", "resources");
        vfs.copyFolder("generator/static/icons", DEST_PATH, false);
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

    /**
     * Use the provided {@link GdxTemplate} to call the appropriate method associated with the
     * module's implementation.
     *
     * @param template the template associated with the user's input.
     */
    protected abstract void applyTemplate(GeneratorConfigs input, AppendableTree vfs,
            GdxTemplate template);
}
