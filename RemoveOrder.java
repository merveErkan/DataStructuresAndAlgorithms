import java.util.List;

public class RemoveOrder {
    public Node root; // Kök düğüm

    public RemoveOrder(Node root) {
        this.root = root; // `root` düğümünü paylaş
    }

    public boolean remove(List<String> productsList) {
        if (root == null) {
            System.out.println("Root is not initialized. Please initialize first.");
            return false;
        }

        if (productsList.isEmpty()) {
            System.out.println("List is empty. If you want to remove something, please enter something...");
            return false;
        }

        boolean result = removeRecursive(root, productsList, 0);

        if (result) {
            root.quantity--;
            FileUtils.removeOrderFromFile(productsList); // Dosyadan siparişi sil
        }
        return result;
    }

    public boolean removeRecursive(Node currentNode, List<String> productsList, int index) {
        if (isExist(productsList)) {
            if (index < productsList.size()) {
                String product = productsList.get(index);
                Node child = currentNode.children.get(product);

                if (child.quantity == 1) {
                    currentNode.children.remove(product);
                } else {
                    child.quantity--;
                }

                removeRecursive(child, productsList, index + 1);
            }
            return true;
        }
        return false;
    }

    public boolean isExist(List<String> productList) {
        return isExistRecursive(root, productList, 0);
    }

    public boolean isExistRecursive(Node currentNode, List<String> productList, int index) {
        if (currentNode == null) {
            return false;
        }
        if (index == productList.size()) {
            return currentNode.quantity > 0;
        }

        String product = productList.get(index);
        Node child = currentNode.children.get(product);

        if (child == null) {
            return false;
        }

        return isExistRecursive(child, productList, index + 1);
    }

    public int sumOfChildrenQuantities(Node currentNode) {
        int totalQuantity = 0;

        if (currentNode.children != null) {
            for (Node child : currentNode.children.values()) {
                totalQuantity += child.quantity;
            }
        }
        return totalQuantity;
    }
}
