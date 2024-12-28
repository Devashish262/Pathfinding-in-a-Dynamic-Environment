import java.util.*;

class Node implements Comparable<Node> {
    int x, y;
    double gCost, hCost;
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = Double.MAX_VALUE;
        this.hCost = 0;
        this.parent = null;
    }

    public double fCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.fCost(), other.fCost());
    }

     @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }
