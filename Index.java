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
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class DynamicPathfinding {
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};
    private final int rows, cols;
    private final boolean[][] obstacles;
    
    public DynamicPathfinding(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.obstacles = new boolean[rows][cols];
    }
    
    public void addObstacle(int x, int y) {
        if (isValid(x, y)) {
            obstacles[x][y] = true;
        }
    }
    
    public void removeObstacle(int x, int y) {
        if (isValid(x, y)) {
            obstacles[x][y] = false;
        }
    }
    
    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        Node startNode = new Node(startX, startY);
        Node endNode = new Node(endX, endY);
        
        startNode.gCost = 0;
        startNode.hCost = heuristic(startX, startY, endX, endY);
        openSet.add(startNode);
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            if (current.equals(endNode)) {
                return reconstructPath(current);
            }
            
            closedSet.add(current);
            
            for (int i = 0; i < 4; i++) {
                int neighborX = current.x + dx[i];
                int neighborY = current.y + dy[i];
                
                if (!isValid(neighborX, neighborY) || obstacles[neighborX][neighborY]) {
                    continue;
                }
                
                Node neighbor = new Node(neighborX, neighborY);
                if (closedSet.contains(neighbor)) {
                    continue;
                }
                
                double tentativeGCost = current.gCost + 1;
                if (tentativeGCost < neighbor.gCost) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = heuristic(neighborX, neighborY, endX, endY);
                    neighbor.parent = current;
                    
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
         return Collections.emptyList(); 
    }

    private List<Node> reconstructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
      private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private double heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static void main(String[] args) {
        DynamicPathfinding pathfinding = new DynamicPathfinding(10, 10);

        pathfinding.addObstacle(3, 3);
        pathfinding.addObstacle(3, 4);
        pathfinding.addObstacle(3, 5);

        List<Node> path = pathfinding.findPath(0, 0, 7, 7);
        
                if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.printf("(%d, %d) -> ", node.x, node.y);
            }
            System.out.println("END");
        }
    }
}

