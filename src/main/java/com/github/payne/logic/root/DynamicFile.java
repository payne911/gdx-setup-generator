package com.github.payne.logic.root;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.modules.html.HtmlModule;
import com.github.payne.utils.FileUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * A file taken from the {@code resources} folder. It contains at least one key (denoted by "{@code
 * ${}}") which will be replaced dynamically by some other content.
 */
@Data
@Getter(AccessLevel.NONE)
public abstract class DynamicFile {

    private final Map<String, String> replacements = new HashMap<>();


    /**
     * Will determine the name of the file created in the Virtual File System.
     */
    private final String fileName;

    /**
     * Thanks to your IDE, you should be able to "{@code Ctrl+Click}" this String to open the file.
     */
    private final String resourcePath;

    protected final GeneratorConfigs input;

    /**
     * Use this to define all the keys except the ones which can be extracted directly from {@link
     * GeneratorConfigs}.
     * <p>
     * Currently automatically extracted: {@code ${gwtVersion}}, {@code ${corePackage}}, {@code
     * ${assetsFolderName}}.
     */
    protected abstract void assignOtherKeys();

    private void assignKeys() {
        assignKey("gwtVersion", HtmlModule.deduceGwtVersion(input.getLibGdxVersion()));
        assignKey("corePackage", input.getCorePackage());
        assignKey("assetsFolderName", input.getAssetsFolderName());
        assignKey("targetAndroidApi", input.getTargetAndroidApi().toString());
        assignKey("serverJavaVersion", input.getServerJavaVersion());
        assignKey("androidSdkPath", input.getAndroidSdkPath());

        assignOtherKeys();
    }

    /**
     * All of the keys (denoted by "{@code ${}}") contained in the file linked via {@link
     * #resourcePath} will be replaced by the designated content.
     *
     * @param key         the key to be replaced
     * @param replacement the text to be injected where the key is
     */
    protected void assignKey(String key, String replacement) {
        replacements.put(key, replacement);
    }

    public String getContent() {
        assignKeys();
        return FileUtils.replaceFileContent(resourcePath, replacements);
    }

    public FileNode createFile() {
        return new FileNode(fileName, getContent().getBytes());
    }
}
