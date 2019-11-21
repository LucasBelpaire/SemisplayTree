package test;

import org.junit.Before;
import org.junit.Test;
import semisplay.Node;
import semisplay.SemiSplayTree;

import java.util.*;

import static org.junit.Assert.*;

public class SemiSplayTreeTest {

    private int bigTestAmount;
    private SemiSplayTree<Integer> semiSplayTree;

    @Before
    public final void setUp() {
        bigTestAmount = 100000;
        int splaySize = 3;
        int root = 0;
        semiSplayTree = new SemiSplayTree<>(root, splaySize);
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
            assertTrue((semiSplayTree.add(i)));
            assertTrue(semiSplayTree.contains(i));
        }
        for (int i = 1; i < bigTestAmount; i++){
            assertFalse(semiSplayTree.add(i));
            assertTrue(semiSplayTree.contains(i));
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
            assertFalse(semiSplayTree.contains(i));
            assertTrue(semiSplayTree.add(i));
            assertTrue(semiSplayTree.contains(i));
        }
    }

    @Test
    public void containsFalseTest(){
        assertTrue(semiSplayTree.add(3));
        assertTrue(semiSplayTree.contains(3));
        assertFalse(semiSplayTree.contains(4));
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
    public void testRemoveRoot() {
        assertTrue(semiSplayTree.contains(0));
        assertTrue(semiSplayTree.remove(0));
        assertFalse(semiSplayTree.contains(0));
    }

    @Test
    public void removeLeftChildLeaveTest() {
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.remove(-1));
        assertFalse(semiSplayTree.contains(-1));
    }

    @Test
    public void removeOneChildLeft() {
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.add(-2));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.contains(-2));
        assertTrue(semiSplayTree.remove(-1));
        assertFalse(semiSplayTree.contains(-1));
    }

    @Test
    public void removeOneChildLeftRoot() {
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.remove(0));
        assertFalse(semiSplayTree.contains(0));
    }

    @Test
    public void removeRootMultipleChildren(){
        assertTrue(semiSplayTree.add(1));
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.contains(1));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.remove(0));
        assertFalse(semiSplayTree.contains(0));
        assertTrue(semiSplayTree.contains(1));
        assertTrue(semiSplayTree.contains(-1));
    }

    @Test
    public void removeMultipleChildren(){
        assertTrue(semiSplayTree.add(3));
        assertTrue(semiSplayTree.add(1));
        assertTrue(semiSplayTree.add(4));
        assertTrue(semiSplayTree.contains(3));
        assertTrue(semiSplayTree.contains(1));
        assertTrue(semiSplayTree.contains(4));
        assertTrue(semiSplayTree.remove(3));
        assertFalse(semiSplayTree.contains(3));
        assertTrue(semiSplayTree.contains(1));
        assertTrue(semiSplayTree.contains(4));
        assertTrue(semiSplayTree.contains(0));
    }

    @Test
    public void removeMultipleChildren2() {
        assertTrue(semiSplayTree.add(-2));
        assertTrue(semiSplayTree.add(-3));
        assertEquals(-2, (long) semiSplayTree.getRoot().getKey());
        assertTrue(semiSplayTree.add(-1));
        assertEquals(-1, (long) semiSplayTree.getRoot().getKey());
        assertEquals(0, (long) semiSplayTree.getRoot().getRightChild().getKey());
        assertEquals(-2, (long) semiSplayTree.getRoot().getLeftChild().getKey());
        assertEquals(-3, (long) semiSplayTree.getRoot().getLeftChild().getLeftChild().getKey());
        assertTrue(semiSplayTree.contains(0));
        assertTrue(semiSplayTree.contains(-2));
        assertTrue(semiSplayTree.contains(-3));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.remove(-2));
        assertEquals(-1, (long) semiSplayTree.getRoot().getKey());
        assertFalse(semiSplayTree.contains(-2));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.contains(-3));
        assertTrue(semiSplayTree.contains(0));
    }

    @Test
    public void removeMultipleChildren3() {
        assertTrue(semiSplayTree.add(-4));
        assertTrue(semiSplayTree.add(-5));
        assertTrue(semiSplayTree.add(-2));
        assertTrue(semiSplayTree.add(-3));
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.contains(-2));
        assertTrue(semiSplayTree.contains(-3));
        assertTrue(semiSplayTree.contains(-4));
        assertTrue(semiSplayTree.contains(-5));
        assertTrue(semiSplayTree.remove(-4));
        assertFalse(semiSplayTree.contains(-4));
        assertTrue(semiSplayTree.contains(0));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.contains(-2));
        assertTrue(semiSplayTree.contains(-3));
        assertTrue(semiSplayTree.contains(-5));
    }


    @Test
    public void testRemoveBig() {
        for (int i = 1; i < bigTestAmount; i++) {
            semiSplayTree.add(i);
            assertTrue(semiSplayTree.contains(i));
            assertTrue(semiSplayTree.remove(i));
            assertFalse(semiSplayTree.contains(i));
            assertFalse(semiSplayTree.remove(i));
        }
        for (int i = 1; i < bigTestAmount; i++) {
            semiSplayTree.add(i);
        }
        for (int i = 1; i < bigTestAmount; i++) {
            assertTrue(semiSplayTree.remove(i));
            assertFalse(semiSplayTree.contains(i));
        }
    }

    @Test
    public void testRemoveBigReverse() {
        for (int i = bigTestAmount; i > 0; i--) {
            semiSplayTree.add(i);
            assertTrue(semiSplayTree.contains(i));
            assertTrue(semiSplayTree.remove(i));
            assertFalse(semiSplayTree.contains(i));
            assertFalse(semiSplayTree.remove(i));
        }
        for (int i = bigTestAmount; i > 0; i--) {
            semiSplayTree.add(i);
        }
        for (int i = bigTestAmount; i > 0; i--) {
            assertTrue(semiSplayTree.remove(i));
            assertFalse(semiSplayTree.contains(i));
        }
    }

    @Test
    public void sizeTest() {
        int node = 5;
        assertEquals(1, semiSplayTree.size());
        semiSplayTree.add(node);
        assertEquals(2, semiSplayTree.size());
        assertTrue(semiSplayTree.contains(0));
        semiSplayTree.remove(5);
        assertEquals(1, semiSplayTree.size());
        assertTrue(semiSplayTree.contains(0));
        semiSplayTree.remove(0);
        assertEquals(0, semiSplayTree.size());
    }

    @Test
    public void sizeTestBig(){
        assertEquals(1, semiSplayTree.size());
        for (int i = 1; i < bigTestAmount; i++) {
            semiSplayTree.add(i);
        }
        assertEquals(semiSplayTree.size(), bigTestAmount);
        for (int i = bigTestAmount; i > 0; i --) {
            semiSplayTree.remove(i);
        }
        assertEquals(1, semiSplayTree.size());
    }

    @Test
    public void depthTest(){
        assertEquals(semiSplayTree.depth(), 0);
        int node1 = 2, node2 = 3, node3 = 4, node4 = 1;
        semiSplayTree.add(node1);
        assertEquals(semiSplayTree.depth(), 1);
        semiSplayTree.add(node2);
        assertEquals(semiSplayTree.depth(), 1);
        semiSplayTree.add(node3);
        assertEquals(semiSplayTree.depth(), 2);
        semiSplayTree.add(node4);
        assertEquals(semiSplayTree.depth(), 2);
    }

    @Test
    public void depthTestBig(){
        SemiSplayTree<Integer> depthTree = new SemiSplayTree<>(0, bigTestAmount+1);
        assertEquals(depthTree.depth(), 0);
        for (int i  = 0; i < bigTestAmount; i++) {
            depthTree.add(i);
        }
        assertEquals(bigTestAmount-1, depthTree.depth());
    }

    @Test
    public void iteratorTest() {
        int node1 = 2, node2 = 3, node3 = 4, node4 = 1;
        semiSplayTree.add(node1);
        semiSplayTree.add(node2);
        semiSplayTree.add(node3);
        semiSplayTree.add(node4);
        Iterator it = semiSplayTree.iterator();
        assertEquals(0, it.next());
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        assertEquals(4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void constructorWithoutRootTest() {
        SemiSplayTree<Integer> semi = new SemiSplayTree<>(3);
        assertFalse(semi.contains(4));
        assertTrue(semi.add(4));
        assertTrue(semi.contains(4));
    }

    @Test
    public void addOnLeftSideWithMultipleChildren() {
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.add(-2));
        assertTrue(semiSplayTree.contains(-2));
    }

    @Test
    public void splay3aTest() {
        semiSplayTree.add(-3);
        semiSplayTree.add(-5);
        Node node = semiSplayTree.getRoot();
        assertEquals(-3, node.getKey());
        assertEquals(-5, node.getLeftChild().getKey());
        assertEquals(0, node.getRightChild().getKey());
    }

    @Test
    public void splay3bTest() {
        semiSplayTree.add(-6);
        semiSplayTree.add(-2);
        Node node = semiSplayTree.getRoot();
        assertEquals(-2, node.getKey());
        assertEquals(-6, node.getLeftChild().getKey());
        assertEquals(0, node.getRightChild().getKey());
    }

    @Test
    public void splay3cTest() {
        semiSplayTree.add(6);
        semiSplayTree.add(2);
        Node node = semiSplayTree.getRoot();
        assertEquals(2, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
        assertEquals(6, node.getRightChild().getKey());
    }

    @Test
    public void splay3dTest() {
        semiSplayTree.add(6);
        semiSplayTree.add(10);
        Node node = semiSplayTree.getRoot();
        assertEquals(6, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
        assertEquals(10, node.getRightChild().getKey());
    }

    @Test
    public void splay3BigTest() {
        semiSplayTree.add(6);
        semiSplayTree.add(10);
        Node node = semiSplayTree.getRoot();
        assertEquals(6, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
        assertEquals(10, node.getRightChild().getKey());
        semiSplayTree.add(8);
        node = semiSplayTree.getRoot();
        assertEquals(8, node.getKey());
        assertEquals(6, node.getLeftChild().getKey());
        assertEquals(0, node.getLeftChild().getLeftChild().getKey());
        assertEquals(10, node.getRightChild().getKey());
        semiSplayTree.add(-1);
        semiSplayTree.add(7);
        semiSplayTree.add(-5);
        node = semiSplayTree.getRoot();
        assertEquals(6, node.getKey());
        assertEquals(-1, node.getLeftChild().getKey());
        assertEquals(-5, node.getLeftChild().getLeftChild().getKey());
        assertEquals(0, node.getLeftChild().getRightChild().getKey());
        assertEquals(8, node.getRightChild().getKey());
        assertEquals(10, node.getRightChild().getRightChild().getKey());
        assertEquals(7, node.getRightChild().getLeftChild().getKey());

    }



    @Test
    public void splay4aTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(-1);
        splay4Tree.add(-2);
        splay4Tree.add(-3);
        Node node = splay4Tree.getRoot();
        assertEquals(-1, node.getKey());
        assertEquals(0, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(-2, node.getKey());
        assertEquals(-3, node.getLeftChild().getKey());
    }

    @Test
    public void splay4bTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(-4);
        splay4Tree.add(-2);
        splay4Tree.add(-3);
        Node node = splay4Tree.getRoot();
        assertEquals(-2, node.getKey());
        assertEquals(0, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(-3, node.getKey());
        assertEquals(-4, node.getLeftChild().getKey());
    }

    @Test
    public void splay4cTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(10);
        splay4Tree.add(8);
        splay4Tree.add(4);
        Node node = splay4Tree.getRoot();
        assertEquals(8, node.getKey());
        assertEquals(10, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(4, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
    }

    @Test
    public void splay4dTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(6);
        splay4Tree.add(10);
        splay4Tree.add(8);
        Node node = splay4Tree.getRoot();
        assertEquals(8, node.getKey());
        assertEquals(10, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(6, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
    }

    @Test
    public void splay4eTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(-4);
        splay4Tree.add(-8);
        splay4Tree.add(-6);
        Node node = splay4Tree.getRoot();
        assertEquals(-4, node.getKey());
        assertEquals(0, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(-6, node.getKey());
        assertEquals(-8, node.getLeftChild().getKey());
    }

    @Test
    public void splay4fTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(-10);
        splay4Tree.add(-8);
        splay4Tree.add(-6);
        Node node = splay4Tree.getRoot();
        assertEquals(-6, node.getKey());
        assertEquals(0, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(-8, node.getKey());
        assertEquals(-10, node.getLeftChild().getKey());
    }

    @Test
    public void splay4gTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(10);
        splay4Tree.add(6);
        splay4Tree.add(8);
        Node node = splay4Tree.getRoot();
        assertEquals(8, node.getKey());
        assertEquals(10, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(6, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
    }

    @Test
    public void splay4hTest() {
        SemiSplayTree<Integer> splay4Tree = new SemiSplayTree<>(0, 4);
        splay4Tree.add(10);
        splay4Tree.add(15);
        splay4Tree.add(20);
        Node node = splay4Tree.getRoot();
        assertEquals(15, node.getKey());
        assertEquals(20, node.getRightChild().getKey());
        node = node.getLeftChild();
        assertNotNull(node);
        assertEquals(10, node.getKey());
        assertEquals(0, node.getLeftChild().getKey());
    }

    @Test
    public void splay7Test() {
        SemiSplayTree<Integer> splay7Tree = new SemiSplayTree<>(0, 7);
        splay7Tree.add(1);
        splay7Tree.add(2);
        splay7Tree.add(3);
        splay7Tree.add(4);
        splay7Tree.add(5);
        splay7Tree.add(6);

        Node root = splay7Tree.getRoot();
        assertEquals(3, root.getKey());
        Node leftChild = root.getLeftChild();
        Node rightChild = root.getRightChild();
        assertEquals(1, leftChild.getKey());
        assertEquals(0, leftChild.getLeftChild().getKey());
        assertEquals(2, leftChild.getRightChild().getKey());
        assertEquals(5, rightChild.getKey());
        assertEquals(4, rightChild.getLeftChild().getKey());
        assertEquals(6, rightChild.getRightChild().getKey());
    }
}
