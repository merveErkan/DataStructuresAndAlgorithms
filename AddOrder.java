import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddOrder {
    public Node root; // Kök düğüm

    public AddOrder() {
        root = new Node("root", 0);
    }

    public void addOrder(String order) {
        // Gelen siparişi düzenle
        List<String> productList = makeList(order);

        if (productList.isEmpty()) {
            System.out.println("The list is empty. Please enter something to add to the list.");
            return;
        }

        addOrderRecursive(root, productList, 0);
        root.quantity++;
    }

    private void addOrderRecursive(Node currentNode, List<String> productsList, int index) {
        if (index < productsList.size()) {
            String product = productsList.get(index);
            Node child = currentNode.children.get(product);

            if (child == null) {
                child = new Node(product, 1);
                currentNode.children.put(product, child);
            } else {
                child.quantity++;
            }

            addOrderRecursive(child, productsList, index + 1);
        }
    }

    // Sipariş listesini düzenlemek için kullanılan yardımcı metod
    public static List<String> makeList(String order) {
        List<String> productList = new ArrayList<>(Arrays.asList(order.split("\\s*,\\s*")));
        productList.replaceAll(String::toLowerCase); // Tüm girdileri küçük harfe çevir
        productList.replaceAll(String::trim); // Boşlukları kaldır
        Collections.sort(productList); // Alfabetik olarak sırala
        return productList;
    }


}
