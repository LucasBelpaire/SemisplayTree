package semisplay;

import java.util.Iterator;
import java.util.NoSuchElementException;

// public class SemiSplayTree<E extends Comparable<? super E>> implements SearchTree<E>
public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    private Node<E> root;
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
    public SemiSplayTree(E rootKey, int splaySize) {
        this.root = new Node<>(rootKey);
        this.root.setWhichChild(0);
        this.size = 1;
        assert(splaySize >= 3);
        this.splaySize = splaySize;
    }

    /**
     * Adds a key to the SemiSplayTree. If successful also adds 1 to the size of the SemiSplayTree.
     * @param key, must be an implementation of Java Comparable interface.
     * @return returns true if the key is added successfully, false otherwise.
     */
    @Override
    public boolean add(E key) {
        if (this.root == null) {
            this.root = new Node<>(key);
            this.root.setWhichChild(0);
            incrementSize();
            return true;
        }
        Node<E> currentNode = this.root;
        while (currentNode != null) {
            E currentKeyValue = currentNode.getKey();
            // The key is already in the SemiSplayTree
            if (currentKeyValue.equals(key)) return false;
            // currentKeyValue is less than key
            if (currentKeyValue.compareTo(key) < 0) {
                // If the currentKey has no rightChild, the new key becomes its leftChild
                if (currentNode.getRightChild() == null) {
                    currentNode.setRightChild(new Node<>(key));
                    currentNode.getRightChild().setParent(currentNode);
                    currentNode.getRightChild().setWhichChild(2);
                    incrementSize();
                    return true;
                }
                currentNode = currentNode.getRightChild();
            }
            // currentKeyValue is greater than newKey
            if (currentKeyValue.compareTo(key) > 0) {
                // If the currentKey has no leftChild, the new key becomes its leftChild
                if (currentNode.getLeftChild() == null) {
                    currentNode.setLeftChild(new Node<>(key));
                    currentNode.getLeftChild().setParent(currentNode);
                    currentNode.getLeftChild().setWhichChild(1);
                    incrementSize();
                    return true;
                }
                currentNode = currentNode.getLeftChild();
            }
        }
        return false;
    }

    /**
     * Checks if the SemiSplayTree contains a specific key.
     * @param key, the key that gets checked, must be an implementation of Java Comparable interface.
     * @return returns true if the key is found, false otherwise.
     */
    @Override
    public boolean contains(E key) {
        Node<E> currentNode = this.root;
        while (currentNode != null) {
            E currentKeyValue = currentNode.getKey();
            // Key is found
            if (currentKeyValue.equals(key)) {
                return true;
            }
            // currentKeyValue is less than key
            if (currentKeyValue.compareTo(key) < 0) {
                currentNode = currentNode.getRightChild();
            }
            // currentKeyValue is greater than key
            if(currentKeyValue.compareTo(key) > 0){
                currentNode = currentNode.getLeftChild();
            }
        }
        return false;
    }

    /**
     * Removes a node from the SemiSplayTree. If successful also decrements 1 to the size of the SemiSplayTree.
     * @param key, must be an implementation of Java Comparable interface.
     * @return returns true if the key is found and removed, false otherwise.
     */
    @Override
    public boolean remove(E key) {
        if (this.root == null) return false;
        Node<E> currentNode = this.root;
        while (currentNode != null) {
            E currentKeyValue = currentNode.getKey();
            // currentKeyValue is less than key
            if (currentKeyValue.compareTo(key) < 0) {
                currentNode = currentNode.getRightChild();
            }
            // currentKeyValue is greater than key
            if(currentKeyValue.compareTo(key) > 0){
                currentNode = currentNode.getLeftChild();
            }
            // currentKey is equal to key
            if (currentKeyValue.equals(key)) {
                // Case 1: no children, just remove the key
                if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
                    switch (currentNode.getWhichChild()) {
                        case 0: this.root = null; // currentNode is the root
                            break;
                        case 1: currentNode.getParent().setLeftChild(null); // currentNode is the leftChild of its parent
                            break;
                        case 2: currentNode.getParent().setRightChild(null); // currentNode is the rightChild of its parent
                            break;
                        default: return false;
                    }
                    decrementSize();
                    return true;
                }

                // Case 2: one child
                // leftChild not null or rightChild is not null
                if ((currentNode.getLeftChild() != null && currentNode.getRightChild() == null) || (currentNode.getRightChild() != null && currentNode.getLeftChild() == null)) {
                    Node<E> child;
                    if (currentNode.getLeftChild() != null){
                        child = currentNode.getLeftChild();
                    } else {
                        child = currentNode.getRightChild();
                    }
                    switch (currentNode.getWhichChild()) {
                        case 0: this.root = child; // currentNode is the root
                            child.setParent(null);
                            this.root.setWhichChild(0);
                            break;
                        case 1: currentNode.getParent().setLeftChild(child); // currentNode is the leftChild of its parent
                            child.setParent(currentNode.getParent());
                            child.setWhichChild(1);
                            break;
                        case 2: currentNode.getParent().setRightChild(child); // currentNode is the rightChild of its parent
                            child.setParent(currentNode.getParent());
                            child.setWhichChild(2);
                            break;
                        default: return false;
                    }
                    decrementSize();
                    return true;
                }
                // Case 3: multiple children
                if (currentNode.getLeftChild() != null && currentNode.getRightChild() != null) {
                    // find the smallest node in its right subtree
                    Node<E> smallestNode = currentNode.getRightChild();
                    while(smallestNode.getLeftChild() != null) {
                        smallestNode = smallestNode.getLeftChild();
                    }
                    // smallestNode is a leaf, so we can just remove its link with its parent without a problem
                    switch (smallestNode.getWhichChild()) {
                        case 1: smallestNode.getParent().setLeftChild(null);
                            break;
                        case 2: smallestNode.getParent().setRightChild(null);
                            break;
                        default: return false;
                    }
                    // our currentNode can be the root, which is a special case
                    if (this.root == currentNode) {
                        smallestNode.setParent(null); // our root doesn't have a parent.
                        smallestNode.setWhichChild(0);
                        smallestNode.setLeftChild(this.root.getLeftChild());
                        this.root = smallestNode;

                        // it is possible that our new root was the only rightChild of the original root
                        // in this case: set rightChild equal to null
                        if (currentNode.getRightChild() == smallestNode) {
                            this.root.setRightChild(null);
                            return true;
                        }
                        // else the rightChild of the original root will become the rightChild of the new root.
                        this.root.setRightChild(currentNode.getRightChild());
                        return true;
                    }

                    smallestNode.setParent(currentNode.getParent());
                    // now link the smallestNode with its new parent
                    switch (currentNode.getWhichChild()) {
                        case 1: currentNode.getParent().setLeftChild(smallestNode);
                            smallestNode.setLeftChild(currentNode.getLeftChild());
                            smallestNode.setRightChild(currentNode.getRightChild());
                            break;
                        case 2: currentNode.getParent().setRightChild(smallestNode);
                            smallestNode.setLeftChild(currentNode.getLeftChild());
                            smallestNode.setRightChild(currentNode.getRightChild());
                            break;
                        default: return false;
                    }
                    decrementSize();
                    return true;
                }
            }


        }
        return false;
    }

    /**
     * Returns the amount of nodes in the SemiSplayTree
     * @return amount of nodes, int
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the depth of the SemiSplay, meaning the longest path down.
     * @return the depth of the SemiSplay, int. Will return -1 if the tree is empty, and 0 if there is only root.
     */
    @Override
    public int depth() {
        return depthRecursively(this.root, -1);
    }

    private int depthRecursively(Node currentNode, int depth) {
        // arrived at a nullPointer, meaning the max depth
        if (currentNode == null) return depth;
        // check the leftPath and rightPath
        int leftDepth = depthRecursively(currentNode.getLeftChild(), depth+1);
        int rightDepth = depthRecursively(currentNode.getRightChild(), depth+1);
        // return the biggest path
        if (leftDepth > rightDepth) return leftDepth;
        return rightDepth;
    }

    @Override
    public Iterator<E> iterator() {
        return new SemiSplayTreeIterator(this.root);
    }

    private class SemiSplayTreeIterator implements Iterator<E> {
        private Node<E> nextNode;

        private SemiSplayTreeIterator(Node<E> root) {
            nextNode = root;
            if (nextNode == null) return;

            while (nextNode.getLeftChild() != null) {
                nextNode = nextNode.getLeftChild();
            }
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node<E> currentNode = nextNode;

            if (nextNode.getRightChild() != null) {
                nextNode = nextNode.getRightChild();
                while (nextNode.getLeftChild() != null) {
                    nextNode = nextNode.getLeftChild();
                }
                return currentNode.getKey();
            }
            while (true) {
                if (nextNode.getParent() == null) {
                    nextNode = null;
                    return currentNode.getKey();
                }
                if (nextNode.getParent().getLeftChild() == nextNode) {
                    nextNode = nextNode.getParent();
                    return currentNode.getKey();
                }
                nextNode = nextNode.getParent();
            }
        }
    }

    private void incrementSize() {
        size++;
    }

    private void decrementSize() {
        size--;
    }
}
