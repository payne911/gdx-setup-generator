package com.github.payne.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.github.payne.generator.output.vfs.FileNode;
import org.junit.Test;

public class FileNodeTests {

    @Test
    public void isFolder() {
        FileNode root = new FileNode("root");

        FileNode folder1 = new FileNode("folder1", null);
        root.addChild(folder1);
        FileNode folder2 = new FileNode("folder2");
        folder1.addChild(folder2);
        FileNode folder3 = new FileNode("folder3");
        root.addChild(folder3);
        FileNode file1 = new FileNode("file1.txt", "text1".getBytes());
        root.addChild(file1);
        FileNode file2 = new FileNode("file2", "".getBytes());
        folder3.addChild(file2);

        assertTrue(root.isFolder());
        assertTrue(folder1.isFolder());
        assertTrue(folder2.isFolder());
        assertTrue(folder3.isFolder());

        assertFalse(file1.isFolder());
        assertFalse(file2.isFolder());
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
}
