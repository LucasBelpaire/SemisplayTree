package test;

import org.junit.Before;
import org.junit.Test;
import semisplay.SemiSplayTree;

import java.util.Iterator;

import static org.junit.Assert.*;

public class SemiSplayTreeTest {

    private int bigTestAmount, splaySize;
    private SemiSplayTree semiSplayTree;

    @Before
    public final void setUp() {
        bigTestAmount = 500;
        splaySize = 3;
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
        assertTrue(semiSplayTree.add(-1));
        assertTrue(semiSplayTree.contains(-2));
        assertTrue(semiSplayTree.contains(-3));
        assertTrue(semiSplayTree.contains(-1));
        assertTrue(semiSplayTree.remove(-2));
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
        assertEquals(semiSplayTree.depth(), 2);
        semiSplayTree.add(node3);
        assertEquals(semiSplayTree.depth(), 3);
        semiSplayTree.add(node4);
        assertEquals(semiSplayTree.depth(), 3);
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
        SemiSplayTree semi = new SemiSplayTree(3);
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
}
