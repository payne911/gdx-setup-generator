package com.github.payne.logic.folders;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.utils.FileUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * A file taken from the {@code resources} folder. It contains at least one key (denoted by "{@code
 * ${}}") which will be replaced dynamically by some other content.
 * <p>
 * Bear in mind some keys are automatically detected and replaced. See {@link
 * FileUtils#injectConfigs(String, GeneratorConfigs)}.
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
     * Use this to define all the keys except the ones listed below (since they can be extracted
     * directly from {@link GeneratorConfigs}).
     * <p>
     * See {@link FileUtils#injectConfigs(String, GeneratorConfigs)} for the list of keys which are
     * automatically replaced.
     * <p>
     * This current function gets called <i>before</i> the automatic injector is called.
     */
    protected abstract void assignKeys();

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

    /**
     * @return the content of the specified resource file, with all the keys replaced by the
     * specified content.
     * @see FileUtils#injectConfigs(String, GeneratorConfigs)
     */
    public String getContent() {
        assignKeys();
        String tmpResult = FileUtils.replaceFileContent(resourcePath, replacements);
        return FileUtils.injectConfigs(tmpResult, input);
    }

    public FileNode createFile() {
        return new FileNode(fileName, getContent().getBytes());
    }
}
