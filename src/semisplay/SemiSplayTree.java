package semisplay;

import java.math.BigInteger;
import java.util.Iterator;

public class SemiSplayTree implements SearchTree {

    private Node root;
    private int size;
    private int splaySize;

    /**
     * Constructor
     * @param splaySize, the amount of nodes used in one splay step, must be larger or equal to 3.
     */
    public SemiSplayTree(int splaySize){
        this.root = null;
        this.size = 0;
        assert(splaySize >= 3);
        this.splaySize = splaySize;
    }

    /**
     * Constructor
     * @param rootKey, the key that will be become the root of the SemiSplayTree, must be an implementation of Java Comparable interface.
     * @param splaySize, the amount of nodes used in one splay step, must be larger or equal to 3.
     */
    public SemiSplayTree(Comparable rootKey, int splaySize) {
        this.root = new Node(rootKey);
        this.size = 1;
        assert(splaySize >= 3);
        this.splaySize = splaySize;
    }

    /**
     * Adds a key to the SemiSplayTree. If successful also adds 1 to the size of the SemiSplayTree.
     * @param key, must be an implementation of Java Comparable interface.
     * @return returns true if the key is added successfully, false otherwise
     */
    @Override
    public boolean add(Comparable key) {
        if (this.root == null) {
            this.root = new Node(key);
            incrementSize();
            return true;
        }
        return addRecursively(root, key);
    }

    /**
     * Adds a key to the SemiSplayTree recursively.
     * @param currentNode, the current node to which currently the newKey gets compared to.
     * @param newKey, the key that will be added, must be an implementation of Java Comparable interface.
     * @return returns true if the key is added successfully, false otherwise.
     */
    private boolean addRecursively(Node currentNode, Comparable newKey) {
        Comparable currentKeyValue = currentNode.getKey();

        // currentKeyValue and newKey are equal
        if (currentKeyValue == newKey) return false; // The newKey is already in the SemiSplayTree

        // currentKeyValue is less than newKey
        if (currentKeyValue.compareTo(newKey) < 0) {
            // If the currentKey has no rightChild, the new key becomes its rightChild
            if (currentNode.getRightChild() == null) {
                currentNode.setRightChild(new Node(newKey));
                incrementSize();
                return true;
            }
            // if the currentKey has a rightChild, we continue recursively
            return addRecursively(currentNode.getRightChild(), newKey);
        }

        // currentKeyValue is greater than newKey
        if (currentKeyValue.compareTo(newKey) > 0) {
            // If the currentKey has no leftChild, the new key becomes its leftChild
            if (currentNode.getLeftChild() == null) {
                currentNode.setLeftChild(new Node(newKey));
                incrementSize();
                return true;
            }
            // if the currentKey has a rightChild, we continue recursively
            return addRecursively(currentNode.getLeftChild(), newKey);
        }
        return false;
    }

    /**
     * Checks if the SemiSplayTree contains a specific key.
     * @param key, the key that gets checked, must be an implementation of Java Comparable interface.
     * @return returns true if the key is found, false otherwise
     */
    @Override
    public boolean contains(Comparable key) {
        if (root == null) return false;
        return containsRecursively(root, key);
    }

    /**
     * Checks if the SemiSplayTree contains a specific key recursively.
     * @param currentNode, the current node to which currently the key gets compared to.
     * @param key, the key that gets checked, must be an implementation of Java Comparable interface.
     * @return returns true if the key is found, false otherwise
     */
    private boolean containsRecursively(Node currentNode, Comparable key) {
        Comparable currentKeyValue = currentNode.getKey();
        // Key is found
        if (currentKeyValue == key) return true;
        // currentKeyValue is less than key
        if (currentKeyValue.compareTo(key) < 0) {
            // If the currentKey has no rightChild, no match was found
            if (currentNode.getRightChild() == null) {
                return false;
            }
            // if the currentKey has a rightChild, we continue recursively
            return containsRecursively(currentNode.getRightChild(), key);
        }
        // currentKeyValue is greater than key
        if (currentKeyValue.compareTo(key) > 0) {
            // If the currentKey has no leftChild, no match was found
            if (currentNode.getLeftChild() == null) {
                return false;
            }
            // if the currentKey has a rightChild, we continue recursively
            return containsRecursively(currentNode.getLeftChild(), key);
        }
        return false;
    }

    /**
     * Removes a node from the SemiSplayTree. If successful also decrements 1 to the size of the SemiSplayTree.
     * @param key, must be an implementation of Java Comparable interface.
     * @return returns true if the key is found and removed, false otherwise
     */
    @Override
    public boolean remove(Comparable key) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    private void incrementSize() {
        size++;
    }

    private void decrementSize() {
        size--;
    }
}
