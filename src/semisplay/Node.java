package semisplay;

public class Node {
    private Node leftChild, rightChild;
    private Comparable key;

    public Node(Comparable value){
        this.key = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Comparable getKey(){
        return key;
    }
}
