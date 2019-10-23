package test;

import org.junit.Before;
import org.junit.Test;
import semisplay.Node;
import semisplay.SemiSplayTree;

import static org.junit.Assert.*;

public class SemiSplayTreeTest {

    private int bigTestAmount, splaySize;
    private SemiSplayTree semiSplayTree;

    @Before
    public final void setUp() {
        bigTestAmount = 100;
        splaySize = 0;
        int root = 0;
        semiSplayTree = new SemiSplayTree(root, splaySize);
    }

    @Test
    public void addTest() {
        int node = 5;
        assertTrue(semiSplayTree.add(node));
        assertTrue(semiSplayTree.contains(node));
        assertFalse(semiSplayTree.add(node));
    }

    @Test
    public void addTestBig() {
        for (int i = 1; i < bigTestAmount; i++){
            int currNode = i;
            assertTrue((semiSplayTree.add(currNode)));
            assertTrue(semiSplayTree.contains(currNode));
        }
        for (int i = 1; i < bigTestAmount; i++){
            int currNode = i;
            assertFalse(semiSplayTree.add(currNode));
            assertTrue(semiSplayTree.contains(currNode));
        }
    }

    @Test
    public void containsTest() {
        int node = 5;
        assertFalse(semiSplayTree.contains(node));
        semiSplayTree.add(node);
        assertTrue(semiSplayTree.contains(node));
    }

    @Test
    public void containsTestBig() {
        for (int i = 1; i < bigTestAmount; i++){
            int currNode = i;
            assertFalse(semiSplayTree.contains(currNode));
            semiSplayTree.add(currNode);
            assertTrue(semiSplayTree.contains(currNode));
        }
    }

    @Test
    public void testRemove() {
        int node = 5;
        semiSplayTree.add(node);
        assertTrue(semiSplayTree.contains(node));
        assertTrue(semiSplayTree.remove(node));
        assertFalse(semiSplayTree.contains(node));
        assertFalse(semiSplayTree.remove(node));
    }

    @Test
    public void testRemoveBig() {
        for (int i = 1; i < bigTestAmount; i++) {
            int currNode = i;
            semiSplayTree.add(currNode);
            assertTrue(semiSplayTree.contains(currNode));
            assertTrue(semiSplayTree.remove(currNode));
            assertFalse(semiSplayTree.contains(currNode));
            assertFalse(semiSplayTree.remove(currNode));
        }
        for (int i = 1; i < bigTestAmount; i++) {
            int currNode = i;
            semiSplayTree.add(currNode);
        }
        for (int i = 1; i < bigTestAmount; i++) {
            int currNode = i;
            assertTrue(semiSplayTree.remove(currNode));
            assertFalse(semiSplayTree.contains(currNode));
        }
    }

    @Test
    public void sizeTest() {
        int node = 5;
        assertEquals(semiSplayTree.size(), 0);
        semiSplayTree.add(node);
        assertEquals(semiSplayTree.size(), 1);
        semiSplayTree.remove(5);
        assertEquals(semiSplayTree.size(), 0);
    }

    @Test
    public void sizeTestBig(){
        assertEquals(semiSplayTree.size(), 0);
        for (int i = 0; i < bigTestAmount; i++) {
            int currNode = i;
            semiSplayTree.add(currNode);
        }
        assertEquals(semiSplayTree.size(), bigTestAmount);
        for (int i = 0; i < bigTestAmount; i ++) {
            int currNode = i;
            semiSplayTree.remove(currNode);
        }
        assertEquals(semiSplayTree.size(), 0);
    }

    @Test
    public void depthTest(){
        assertEquals(semiSplayTree.depth(), 0);
        int node1 = 2, node2 = 3, node3 = 4, node4 = 1;
        semiSplayTree.add(node1);
        assertEquals(semiSplayTree.depth(), 1);
        semiSplayTree.add(node2);
        assertEquals(semiSplayTree.depth(), 2);
        semiSplayTree.add(node3);
        assertEquals(semiSplayTree.depth(), 3);
        semiSplayTree.add(node4);
        assertEquals(semiSplayTree.depth(), 3);
    }
}
