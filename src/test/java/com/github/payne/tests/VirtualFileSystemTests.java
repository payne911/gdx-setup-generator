package com.github.payne.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.generator.output.vfs.VirtualFileSystem;
import java.util.Arrays;
import org.junit.Test;

public class VirtualFileSystemTests {

    @Test
    public void addRelativeToParent() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");
        FileNode root = vfs.getRoot();

        final String FIRST_FOLDER = "firstFolder";
        final String THIRD_FOLDER = "thirdFolder";
        final String FOURTH_FOLDER = "fourthFolder";
        final String FIFTH_FOLDER = "fifthFolder";

        FileNode folder1 = new FileNode(FIRST_FOLDER);
        vfs.addRelativeToParent(Arrays.asList(""), root, folder1);
        FileNode folder2 = new FileNode("secondFolder");
        vfs.addRelativeToParent(Arrays.asList(""), root, folder2);
        FileNode folder3 = new FileNode(THIRD_FOLDER);
        vfs.addRelativeToParent(Arrays.asList(FIRST_FOLDER), root, folder3);
        FileNode folder4 = new FileNode(FOURTH_FOLDER);
        vfs.addRelativeToParent(Arrays.asList(FIRST_FOLDER, THIRD_FOLDER), root, folder4);
        FileNode folder5 = new FileNode(FIFTH_FOLDER);
        vfs.addRelativeToParent(Arrays.asList(THIRD_FOLDER, FOURTH_FOLDER), folder1, folder5);

        FileNode file1 = new FileNode("firstFile", "whatever".getBytes());
        vfs.addRelativeToParent(Arrays.asList(""), root, file1);
        FileNode file2 = new FileNode("secondFile", "whatever".getBytes());
        vfs.addRelativeToParent(Arrays.asList(FOURTH_FOLDER, FIFTH_FOLDER), folder3, file2);

        /*
            root
            |__folder1
               |__folder3
                  |__folder4
                     |__folder5
                        |__file2
            |__folder2
            |__file1
         */

        assertEquals(3, root.getChildren().size());
        assertEquals(1, folder1.getChildren().size());
        assertEquals(1, folder3.getChildren().size());
        assertEquals(1, folder4.getChildren().size());
        assertEquals(1, folder5.getChildren().size());
        assertEquals(0, file1.getChildren().size());
        assertEquals(0, file2.getChildren().size());

        assertTrue(root.getChildren().contains(file1));
        assertTrue(root.getChildren().contains(folder1));
        assertTrue(root.getChildren().contains(folder2));
        assertTrue(folder1.getChildren().contains(folder3));
        assertTrue(folder3.getChildren().contains(folder4));
        assertTrue(folder4.getChildren().contains(folder5));
        assertTrue(folder5.getChildren().contains(file2));

        assertFalse(root.getChildren().contains(folder3));
        assertFalse(folder3.getChildren().contains(folder5));
    }

    @Test
    public void addFromRoot() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");

        final String CORE = "core";
        final String SRC = "src";
        final String MAIN = "main";
        final String JAVA = "java";
        final String TEST = "test";
        final String GROOVY = "groovy";
        final String SANITY_FOLDER = "lastEmptyFolder";

        FileNode javaFile1 = new FileNode("file1.java", "psvm{sout(HelloWorld)}".getBytes());
        vfs.addFromRoot(Arrays.asList(CORE, SRC, MAIN, JAVA), javaFile1);
        FileNode javaFile2 = new FileNode("file2.java", "bob;".getBytes());
        vfs.addFromRoot(Arrays.asList(CORE, SRC, MAIN, JAVA), javaFile2);
        FileNode groovyFile = new FileNode("file3.groovy", "dayum-boi!".getBytes());
        vfs.addFromRoot(Arrays.asList(CORE, SRC, MAIN, GROOVY), groovyFile);
        FileNode testFile = new FileNode("test1.txt", "whatev...".getBytes());
        vfs.addFromRoot(Arrays.asList(CORE, SRC, TEST, JAVA), testFile);
        FileNode readme = new FileNode("README.md", "long live to PieMenu !!".getBytes());
        vfs.addFromRoot(Arrays.asList(""), readme);
        FileNode emptyFolder = new FileNode("randomEmptyFolder");
        vfs.addFromRoot(null, emptyFolder);
        FileNode anotherFolder = new FileNode("anotherFolder");
        vfs.addFromRoot(Arrays.asList(""), anotherFolder);
        FileNode lastEmptyFolder = new FileNode(SANITY_FOLDER);
        vfs.addFromRoot(Arrays.asList("anotherFolder"), lastEmptyFolder);

        /*
            root
            |__README.md
            |__randomEmptyFolder
            |__anotherFolder
               |__lastEmptyFolder
            |__core
               |__src
                  |__main
                     |__java
                        |__file1.java
                        |__file2.java
                     |__groovy
                        |__file3.groovy
                  |__test
                     |__java
                        |__test1.txt
         */

        // Extracting generated intermediary folders
        FileNode root = vfs.getRoot();
        FileNode coreFolder = root.getChild(CORE);
        FileNode srcFolder = coreFolder.getChild(SRC);
        FileNode mainFolder = srcFolder.getChild(MAIN);
        FileNode srcTestFolder = srcFolder.getChild(TEST);
        FileNode javaTestFolder = srcTestFolder.getChild(JAVA);
        FileNode javaMainFolder = mainFolder.getChild(JAVA);
        FileNode groovyFolder = mainFolder.getChild(GROOVY);

        // Sanity check on "getChild"
        FileNode duplicateLastEmptyFolder = anotherFolder.getChild(SANITY_FOLDER);
        assertEquals(duplicateLastEmptyFolder, lastEmptyFolder);

        assertEquals(4, root.getChildren().size());
        assertEquals(0, readme.getChildren().size());
        assertEquals(0, emptyFolder.getChildren().size());
        assertEquals(1, anotherFolder.getChildren().size());
        assertEquals(0, lastEmptyFolder.getChildren().size());
        assertEquals(1, coreFolder.getChildren().size());
        assertEquals(2, srcFolder.getChildren().size());
        assertEquals(2, mainFolder.getChildren().size());
        assertEquals(2, javaMainFolder.getChildren().size());
        assertEquals(0, javaFile1.getChildren().size());
        assertEquals(0, javaFile2.getChildren().size());
        assertEquals(1, groovyFolder.getChildren().size());
        assertEquals(0, groovyFile.getChildren().size());
        assertEquals(1, srcTestFolder.getChildren().size());
        assertEquals(1, javaTestFolder.getChildren().size());
        assertEquals(0, testFile.getChildren().size());

        // only test all positives for files because we already tested getChildren.size() and getChild()
        assertTrue(root.getChildren().contains(readme));
        assertTrue(root.getChildren().contains(emptyFolder));
        assertTrue(root.getChildren().contains(anotherFolder));
        assertTrue(javaMainFolder.getChildren().contains(javaFile1));
        assertTrue(javaMainFolder.getChildren().contains(javaFile2));
        assertTrue(groovyFolder.getChildren().contains(groovyFile));
        assertTrue(javaTestFolder.getChildren().contains(testFile));

        assertTrue(root.isFolder());
        assertTrue(coreFolder.isFolder());
        assertTrue(srcFolder.isFolder());
        assertTrue(mainFolder.isFolder());
        assertTrue(srcTestFolder.isFolder());
        assertTrue(javaTestFolder.isFolder());
        assertTrue(javaMainFolder.isFolder());
        assertTrue(groovyFolder.isFolder());
        assertTrue(emptyFolder.isFolder());
        assertTrue(anotherFolder.isFolder());
        assertTrue(lastEmptyFolder.isFolder());

        assertFalse(readme.isFolder());
        assertFalse(javaFile1.isFolder());
        assertFalse(javaFile2.isFolder());
        assertFalse(testFile.isFolder());
        assertFalse(groovyFile.isFolder());
    }

    @Test
    public void copyFile() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");

        final String CORE = "core";
        final String COPIED = "test.txt";
        final String RESOURCE_FOLDER = "copy-res-test";

        FileNode folder = new FileNode("folder");
        vfs.addFromRoot(Arrays.asList(CORE, "java"), folder);
        FileNode file = new FileNode("file.txt", "Wow!".getBytes());
        vfs.addFromRoot(Arrays.asList(CORE), file);

        vfs.copyFile(Arrays.asList(RESOURCE_FOLDER, COPIED), Arrays.asList(CORE));

        /*
            root
            |__core
               |__java
                  |__folder
               |__file.txt
               |__test.txt
         */

        String strContent = new String(vfs.getRoot().getChild(CORE).getChild(COPIED).getContent());
        assertEquals("PieMenu", strContent);

        assertEquals(1, vfs.getRoot().getChildren().size());
        assertEquals(3, vfs.getRoot().getChild(CORE).getChildren().size());
        assertTrue(vfs.getRoot().getChild(CORE).getChild("java").isFolder());
    }

    @Test
    public void addToParent() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");
        FileNode root = vfs.getRoot();

        FileNode folder = new FileNode("folder");
        FileNode rootFile = new FileNode("rootFile.txt", "text".getBytes());
        FileNode folderFile1 = new FileNode("folderFile1.txt", "wow".getBytes());
        FileNode folderFile2 = new FileNode("folderFile2.txt", "BUY IOTA".getBytes());

        vfs.addToParent(root, rootFile);
        vfs.addToParent(root, folder);
        vfs.addToParent(folder, folderFile1);
        vfs.addToParent(folder, folderFile2);

        /*
            root
            |__folder
               |__folderFile1.txt
               |__folderFile2.txt
            |__rootFile.txt
         */

        assertEquals(2, root.getChildren().size());
        assertTrue(root.getChildren().contains(rootFile));
        assertTrue(root.getChildren().contains(folder));

        assertEquals(2, folder.getChildren().size());
        assertTrue(folder.getChildren().contains(folderFile1));
        assertTrue(folder.getChildren().contains(folderFile2));
    }

    @Test
    public void sortByName_foldersOrdering() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");
        FileNode root = vfs.getRoot();

        FileNode fourth = new FileNode("bc");
        vfs.addToParent(root, fourth);
        FileNode fifth = new FileNode("BC");
        vfs.addToParent(root, fifth);
        FileNode first = new FileNode("a");
        vfs.addToParent(root, first);
        FileNode second = new FileNode("AB");
        vfs.addToParent(root, second);
        FileNode third = new FileNode("ab");
        vfs.addToParent(root, third);

        vfs.sortByNames();

        assertEquals(5, root.getChildren().size());
        assertEquals(first, root.getChildren().get(0));
        assertEquals(second, root.getChildren().get(1));
        assertEquals(third, root.getChildren().get(2));
        assertEquals(fourth, root.getChildren().get(3));
        assertEquals(fifth, root.getChildren().get(4));
    }

    @Test
    public void sortByName_filesOrdering() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");
        FileNode root = vfs.getRoot();

        FileNode fourth = new FileNode("bc", "a".getBytes());
        vfs.addToParent(root, fourth);
        FileNode fifth = new FileNode("BC", "b".getBytes());
        vfs.addToParent(root, fifth);
        FileNode first = new FileNode("a", "c".getBytes());
        vfs.addToParent(root, first);
        FileNode second = new FileNode("AB", "d".getBytes());
        vfs.addToParent(root, second);
        FileNode third = new FileNode("ab", "e".getBytes());
        vfs.addToParent(root, third);

        vfs.sortByNames();

        assertEquals(5, root.getChildren().size());
        assertEquals(first, root.getChildren().get(0));
        assertEquals(second, root.getChildren().get(1));
        assertEquals(third, root.getChildren().get(2));
        assertEquals(fourth, root.getChildren().get(3));
        assertEquals(fifth, root.getChildren().get(4));
    }

    @Test
    public void sortByName_mixedOrdering() {
        VirtualFileSystem vfs = new VirtualFileSystem("root");
        FileNode root = vfs.getRoot();

        FileNode fourth = new FileNode("b", "I am a file too!".getBytes());
        vfs.addToParent(root, fourth);
        FileNode third = new FileNode("a", "I am a file".getBytes());
        vfs.addToParent(root, third);
        FileNode second = new FileNode("z");
        vfs.addToParent(root, second);
        FileNode first = new FileNode("F");
        vfs.addToParent(root, first);

        vfs.sortByNames();

        assertEquals(4, root.getChildren().size());
        assertEquals(first, root.getChildren().get(0));
        assertEquals(second, root.getChildren().get(1));
        assertEquals(third, root.getChildren().get(2));
        assertEquals(fourth, root.getChildren().get(3));
    }
}
