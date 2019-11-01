package semisplay;

import java.util.*;

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
                    splay(currentNode.getRightChild());
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
                    splay(currentNode.getLeftChild());
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
                splay(currentNode);
                return true;
            }
            // currentKeyValue is less than key
            if (currentKeyValue.compareTo(key) < 0) currentNode = currentNode.getRightChild();
            // currentKeyValue is greater than key
            if(currentKeyValue.compareTo(key) > 0) currentNode = currentNode.getLeftChild();
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
            if (currentKeyValue.compareTo(key) < 0) currentNode = currentNode.getRightChild();
            // currentKeyValue is greater than key
            if(currentKeyValue.compareTo(key) > 0)  currentNode = currentNode.getLeftChild();
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
                    splay(currentNode.getParent());
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
                    splay(child);
                    return true;
                }
                // Case 3: multiple children
                if (currentNode.getLeftChild() != null && currentNode.getRightChild() != null) {
                    // find the smallest node in its right subtree
                    Node<E> smallestNode = currentNode.getRightChild();
                    while(smallestNode.getLeftChild() != null) {
                        smallestNode = smallestNode.getLeftChild();
                    }
                    Node<E> startOfSplay;
                    if (smallestNode.getParent() != currentNode) startOfSplay = smallestNode.getParent();
                    else startOfSplay = smallestNode;
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
                    splay(startOfSplay);
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
     * Based on: https://www.geeksforgeeks.org/iterative-method-to-find-height-of-binary-tree/
     * @return the depth of the SemiSplay, int. Will return -1 if the tree is empty, and 0 if there is only root.
     */
    @Override
    public int depth() {
        // return depthRecursively(this.root, -1);

        if (this.root == null) return -1;

        Queue<Node<E>> nodes = new LinkedList<>();
        nodes.add(this.root);
        int depth = -1;
        int nodeCount = nodes.size();
        while (nodeCount != 0) {
            depth++;

            while(nodeCount > 0) {
                Node<E> newNode = nodes.peek();
                nodes.remove();
                if (newNode.getLeftChild() != null) {
                    nodes.add(newNode.getLeftChild());
                }
                if (newNode.getRightChild() != null) {
                    nodes.add(newNode.getRightChild());
                }
                nodeCount--;
            }
            nodeCount = nodes.size();
        }
        return depth;
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

    /**
     * Helper method used for testing
     * @return returns the root of the SemiSplaytree
     */
    public Node<E> getRoot() {
        return this.root;
    }

    /**
     * This functions implements the splay operations.
     * First it calculates the splay path, after which it will perform the splay operation on every n consecutive nodes.
     * Where n equals the "splaygrootte"
     * @param node, the node from where the splay path begins
     */
    private void splay(Node<E> node) {
        // Load the splaypath into a queue, so we can easily go over the nodes in the right order later
        Queue<Node<E>> path = new LinkedList<>();
        Node<E> currNode = node;
        path.add(currNode);
        while(currNode.getParent() != null) {
            path.add(currNode.getParent());
            currNode = currNode.getParent();
        }

        // Now we go over the path and splay every n consecutive nodes
        // The function will keep looping until there are less than n nodes left in the path
        List<Node<E>> listOfNodesOnPath = new ArrayList<>();
        List<Node<E>> listOfSubTreesOfPath = new ArrayList<>(); // will be double as long as listOfNodesOnPath
        while (path.size() + listOfNodesOnPath.size() >= this.splaySize ) {

            // Get n nodes, for the current splay operation
            int listOfNodesOnPathSize = listOfNodesOnPath.size();
            for (int i = 0; i < this.splaySize - listOfNodesOnPathSize; i++) {
                listOfNodesOnPath.add(path.peek());
                path.remove();
            }
            // Get the last node of the path, its parent will become the parent of the new root of the subtree
            // if its parent is null, the new root of the subtree will become the root of the SemiSplaytree
            Node<E> parent = listOfNodesOnPath.get(this.splaySize-1).getParent();
            // sort the nodes in the right order, from smallest to largest
            Collections.sort(listOfNodesOnPath);
            // Get the subtrees in the right order
            for (Node nodeOnPath : listOfNodesOnPath) {
                if (!listOfNodesOnPath.contains(nodeOnPath.getLeftChild())) listOfSubTreesOfPath.add(nodeOnPath.getLeftChild());
                if (!listOfNodesOnPath.contains(nodeOnPath.getRightChild())) listOfSubTreesOfPath.add(nodeOnPath.getRightChild());
            }
            // This node will be the root of our subtree
            int middleNodeIndex = listOfNodesOnPath.size() / 2;
            Node<E> rootOfSubTree = listOfNodesOnPath.get(middleNodeIndex);
            // change its parent to the parent of the old root of the path
            // but if the parent is null, this means that rootOfSubtree will become the new root of the SemiSplay
            if (parent == null) {
                rootOfSubTree.setParent(null);
                rootOfSubTree.setWhichChild(0);
                this.root = rootOfSubTree;
            }
            if (parent != null) {
                rootOfSubTree.setParent(parent);
                if (rootOfSubTree.compareTo(parent) < 0) {
                    parent.setLeftChild(rootOfSubTree);
                    rootOfSubTree.setWhichChild(1);
                }
                if (rootOfSubTree.compareTo(parent) > 0) {
                    parent.setRightChild(rootOfSubTree);
                    rootOfSubTree.setWhichChild(2);
                }
            }
            // now recursively fill the subtree, with rootOfSubTree as its root
            List<Node<E>> leftPath = listOfNodesOnPath.subList(0, middleNodeIndex);
            List<Node<E>> rightPath = listOfNodesOnPath.subList(middleNodeIndex+1, listOfNodesOnPath.size());
            rootOfSubTree.setLeftChild(buildSubTreeRecursively(rootOfSubTree, leftPath));
            rootOfSubTree.setRightChild(buildSubTreeRecursively(rootOfSubTree, rightPath));


            // Now that the tree is build correctly we need to add the outer subtrees
            // We loop over each node in the pad in ascending order
            // if a node doesn't have a child that is in listOfNodesOnPath it will get an outer subtree as a child
            int subTreeIndex = 0;
            for (Node<E> pathNode : listOfNodesOnPath) {
                if (!listOfNodesOnPath.contains(pathNode.getLeftChild())) {
                    pathNode.setLeftChild(listOfSubTreesOfPath.get(subTreeIndex));
                    if (listOfSubTreesOfPath.get(subTreeIndex) != null) {
                        listOfSubTreesOfPath.get(subTreeIndex).setParent(pathNode);
                        listOfSubTreesOfPath.get(subTreeIndex).setWhichChild(1);
                    }
                    subTreeIndex++;
                }
                if (!listOfNodesOnPath.contains(pathNode.getRightChild())) {
                    pathNode.setRightChild(listOfSubTreesOfPath.get(subTreeIndex));
                    if (listOfSubTreesOfPath.get(subTreeIndex) != null) {
                        listOfSubTreesOfPath.get(subTreeIndex).setParent(pathNode);
                        listOfSubTreesOfPath.get(subTreeIndex).setWhichChild(2);
                    }
                    subTreeIndex++;
                }
            }
            // Now that the subtrees got added to our splay we are done with the splay operation.
            listOfNodesOnPath.clear();
            listOfSubTreesOfPath.clear();
            listOfNodesOnPath.add(rootOfSubTree);
        }
    }

    private Node<E> buildSubTreeRecursively(Node<E> subRoot, List<Node<E>> path) {
        if (path.size() == 0) return null;
        int middleNodeIndex = path.size() / 2;
        Node<E> middleNode = path.get(middleNodeIndex);
        middleNode.setParent(subRoot);
        // Depending on if the middleNode is the right or left child of its parent, set the right parameter
        if (middleNode.compareTo(subRoot) < 0) middleNode.setWhichChild(1);
        if (middleNode.compareTo(subRoot) > 0) middleNode.setWhichChild(2);

        // Now we recursively add the correct right and left child to the middleNode
        List<Node<E>> leftPath = path.subList(0, middleNodeIndex);
        List<Node<E>> rightPath = path.subList(middleNodeIndex+1, path.size());
        middleNode.setLeftChild(buildSubTreeRecursively(middleNode, leftPath));
        middleNode.setRightChild(buildSubTreeRecursively(middleNode, rightPath));
        return middleNode;
    }
}
