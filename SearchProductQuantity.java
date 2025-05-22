import java.util.List;

public class SearchProductQuantity {
    private final Node root;

    public SearchProductQuantity(Node root) {
        this.root = root;
    }

    public int searchProductQuantity(List<String> productPath) {
        return searchProductQuantityRecursive(root, productPath, 0);
    }

    private int searchProductQuantityRecursive(Node currentNode, List<String> productPath, int index) {
        if (currentNode == null || index >= productPath.size()) {
            return 0;
        }
        String currentProduct = productPath.get(index);

        int sum = 0;

        if (currentNode.name.equals(currentProduct)) {
            // Eğer son ürünse, miktarı topla
            if (index == productPath.size() - 1) {
                sum += currentNode.quantity;
            } else {
                // Alt düğümlerde devam et
                for (Node child : currentNode.children.values()) {
                    sum += searchProductQuantityRecursive(child, productPath, index + 1);
                }
            }
        }

        for (Node child : currentNode.children.values()) {
            sum += searchProductQuantityRecursive(child, productPath, index);
        }

        return sum;
    }
}
