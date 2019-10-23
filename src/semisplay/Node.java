package semisplay;

public class Node {
    private Node parent, leftChild, rightChild;
    private int whichChild; // 0 = root, 1 = leftChild, 2 = rightChild
    private Comparable key;

    public Node(Comparable value){
        this.key = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) { this.leftChild = leftChild; }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) { this.rightChild = rightChild; }

    public Node getParent() { return parent; }

    public void setParent(Node parent) { this.parent = parent; }

    public Comparable getKey(){ return key; }

    /**
     * whichChild is an int value which indicates the state of the node in relation to its parent.
     * The value is either 0, 1, 2.
     * 0: the node is the root.
     * 1: the node is the leftChild of its parent.
     * 2: the node is the rightChild of its parent.
     * @return int value.
     */
    public int getWhichChild() { return whichChild; }

    /**
     * whichChild is an int value which indicates the state of the node in relation to its parent.
     * The value is either 0, 1, 2.
     * 0: the node is the root.
     * 1: the node is the leftChild of its parent.
     * 2: the node is the rightChild of its parent.
     * @param whichChild, sets the state of the node. Value should be 0,1,2.
     */
    public void setWhichChild(int whichChild) { this.whichChild = whichChild; }
}
