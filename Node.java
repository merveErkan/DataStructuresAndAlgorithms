import java.io.Serializable;
import java.util.HashMap;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L; // Serialize uyumluluğu için

    String name;
    int quantity;
    HashMap<String, Node> children;

    public Node(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.children = new HashMap<>();
    }
}
