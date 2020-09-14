package com.github.payne;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.generator.output.vfs.VirtualFileSystem;
import com.github.payne.utils.FileUtils;
import java.util.Arrays;
import org.junit.Test;

public class FileTests {

    @Test
    public void joinPath() {
        assertEquals("", FileUtils.joinPath(""));
        assertEquals("src", FileUtils.joinPath("src"));
        assertEquals("src/bob", FileUtils.joinPath("src", "bob"));
        assertEquals("src/bob/main", FileUtils.joinPath("src", "bob", "main"));
    }

    @Test
    public void isEmptyPath() {
        assertTrue(FileUtils.isEmptyPath(Arrays.asList()));
        assertTrue(FileUtils.isEmptyPath(Arrays.asList("")));
        assertTrue(FileUtils.isEmptyPath(Arrays.asList(" ")));
        assertTrue(FileUtils.isEmptyPath(null));

        assertFalse(FileUtils.isEmptyPath(Arrays.asList("src")));
        assertFalse(FileUtils.isEmptyPath(Arrays.asList("src", "main")));
        assertFalse(FileUtils.isEmptyPath(Arrays.asList("src", "main", "groovy")));
    }

    @Test
    public void fullPath() {
        FileNode root = new FileNode("root");

        FileNode folder1 = new FileNode("folder1");
        root.addChild(folder1);
        FileNode file1 = new FileNode("file1.txt", "text1".getBytes());
        root.addChild(file1);
        FileNode file2 = new FileNode("file2.txt", "text2".getBytes());
        folder1.addChild(file2);

        assertEquals("root", root.getFullPath());
        assertEquals("root/file1.txt", file1.getFullPath());
        assertEquals("root/folder1", folder1.getFullPath());
        assertEquals("root/folder1/file2.txt", file2.getFullPath());
    }

    @Test
    public void getParent() {
        FileNode root = new FileNode("root");

        FileNode folder1 = new FileNode("folder1");
        root.addChild(folder1);
        FileNode file1 = new FileNode("file1.txt", "text1".getBytes());
        root.addChild(file1);
        FileNode file2 = new FileNode("file2.txt", "text2".getBytes());
        folder1.addChild(file2);

        String msg = "Root shouldn't have a parent.";
        assertNull(msg, root.getParent());

        msg = "Node expected to have a parent.";
        assertEquals(msg, root, file1.getParent());
        assertEquals(msg, root, folder1.getParent());
        assertEquals(msg, folder1, file2.getParent());
    }

    @Test
    public void getChildren() {
        FileNode root = new FileNode("root");

        FileNode folder1 = new FileNode("folder1");
        root.addChild(folder1);
        FileNode folder2 = new FileNode("folder2");
        folder1.addChild(folder2);
        FileNode file1 = new FileNode("file1.txt", "text1".getBytes());
        root.addChild(file1);
        FileNode file2 = new FileNode("file2.txt", "text2".getBytes());
        folder1.addChild(file2);

        String msg = "Folder expected to not be empty.";
        assertFalse(msg, root.getChildren().isEmpty());
        assertFalse(msg, folder1.getChildren().isEmpty());
        assertFalse(msg, root.getChildren().contains(file2));

        msg = "Folder expected to have a child.";
        assertTrue(msg, root.getChildren().contains(file1));
        assertTrue(msg, root.getChildren().contains(folder1));
        assertTrue(msg, folder1.getChildren().contains(file2));
        assertTrue(msg, folder1.getChildren().contains(folder2));

        msg = "Leaf Node expected to be empty.";
        assertTrue(msg, file1.getChildren().isEmpty());
        assertTrue(msg, file2.getChildren().isEmpty());
        assertTrue(msg, folder2.getChildren().isEmpty());
    }

    @Test
    public void addRelativeToParent() {
        // todo: add test
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

//    @Test
//    public void copyFolder() {
//        FileNode root = new FileNode("root");
//        VirtualFileSystem vfs = new VirtualFileSystem(root);
//
//        final String CORE = "core";
//        final String COPIED = "test.txt";
//        final String RESOURCE_FOLDER = "copy-res-test";
//
//        FileNode folder = new FileNode("folder");
//        vfs.addFromRoot(Arrays.asList(CORE, "java"), folder);
//        FileNode file = new FileNode("file.txt", "Wow!".getBytes());
//        vfs.addFromRoot(Arrays.asList(CORE), file);
//
//        System.out.println("HERE");
//        vfs.copyFolder(Arrays.asList(RESOURCE_FOLDER), Arrays.asList(CORE));
//
//        /*
//            root
//            |__core
//               |__java
//                  |__folder
//               |__file.txt
//               |__copy-res-test
//                  |__sub-folder
//                     |__test3.txt
//                  |__test.txt
//                  |__test2.txt
//         */
//        String strContent = new String(vfs.getRoot().getChild(CORE).getChild(RESOURCE_FOLDER).getContent());
//        System.out.println("TEST");
//        System.out.println(strContent);
//        assertEquals("PieMenu", strContent);
//
//    }

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
    public void addChild_folder() {
        FileNode root = new FileNode("root");

        final String DUPLICATED_NAME = "ab";

        FileNode duplicated = new FileNode(DUPLICATED_NAME);
        root.addChild(duplicated);
        FileNode duplicate = new FileNode(DUPLICATED_NAME);
        root.addChild(duplicate);

        assertEquals("Root should only have 1 child", 1, root.getChildren().size());
        assertEquals("Should be same memory reference", duplicated, root.getChild(DUPLICATED_NAME));
    }

    @Test
    public void addChild_file() {
        FileNode root = new FileNode("root");

        final String FOLDER_NAME = "ab";
        final String DUPLICATED_NAME = FOLDER_NAME + ".txt";
        final byte[] OVERWRITTEN_TEXT = "PieMenu".getBytes();

        FileNode folder = new FileNode(FOLDER_NAME);
        root.addChild(folder);
        FileNode first = new FileNode(DUPLICATED_NAME, "I love Nate".getBytes());
        root.addChild(first);
        FileNode second = new FileNode(DUPLICATED_NAME, OVERWRITTEN_TEXT);
        root.addChild(second);

        assertEquals(2, root.getChildren().size());
        assertEquals("Folder should still be there", folder, root.getChild(FOLDER_NAME));
        assertEquals("Content was not overwritten", OVERWRITTEN_TEXT, first.getContent());
        assertEquals("Should be same memory reference", first, root.getChild(DUPLICATED_NAME));
        assertFalse("No reference should exist", root.getChildren().contains(second));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addChild_throwOnMix1() {
        FileNode root = new FileNode("root");

        final String NAME = "ab";

        FileNode folder = new FileNode(NAME);
        root.addChild(folder);
        FileNode file = new FileNode(NAME, "sexy mgsx".getBytes());
        root.addChild(file);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addChild_throwOnMix2() {
        FileNode root = new FileNode("root");

        final String NAME = "ab";

        FileNode file = new FileNode(NAME, "sexy mgsx".getBytes());
        root.addChild(file);
        FileNode folder = new FileNode(NAME);
        root.addChild(folder);
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
