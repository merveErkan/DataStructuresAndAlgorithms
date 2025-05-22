import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AddOrder addOrder = new AddOrder();

        RemoveOrder removeOrder = new RemoveOrder(addOrder.root); // Aynı root düğümünü paylaş
        SearchProductQuantity searchProductQuantity = new SearchProductQuantity(addOrder.root);

        // Önceki siparişleri yükle ve ağaca ekle
        List<List<String>> orders = FileUtils.loadOrdersFromFile();
        if (orders != null && !orders.isEmpty()) {
            for (List<String> order : orders) {
                // Her siparişi virgülle ayırarak birleştir
                String orderString = String.join(",", order);
                addOrder.addOrder(orderString);
            }
        }

        // Giriş panelini oluştur
        JFrame inputFrame = new JFrame("Product Management");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(500, 500);
        inputFrame.setLayout(new GridLayout(6, 1)); // 6 buton için düzen
        inputFrame.setLocationRelativeTo(null); // Ortala

        // Bilgilendirme etiketi
        JLabel infoLabel = new JLabel("Select an action:", SwingConstants.CENTER);
        inputFrame.add(infoLabel);

        // "Ekle" butonu
        JButton addProductButton = new JButton("Add Order");
        addProductButton.addActionListener(e -> {
            JFrame addOrderFrame = new JFrame("Add Order");
            addOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addOrderFrame.setSize(400, 200);
            addOrderFrame.setLayout(new BorderLayout());

            addOrderFrame.add(new AddOrderPanel(addOrder, () -> {
                addOrderFrame.dispose();
                inputFrame.setVisible(true);
            }));

            addOrderFrame.setLocationRelativeTo(null);
            addOrderFrame.setVisible(true);
        });
        inputFrame.add(addProductButton);

        // "Remove Order" butonu
        JButton removeProductButton = new JButton("Remove Order");
        removeProductButton.addActionListener(e -> {
            JFrame removeFrame = new JFrame("Remove Order");
            removeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            removeFrame.setSize(400, 200);
            removeFrame.setLayout(new BorderLayout());

            removeFrame.add(new RemovePanel(removeOrder, () -> {
                removeFrame.dispose();
                inputFrame.setVisible(true);
            }));

            removeFrame.setLocationRelativeTo(null);
            removeFrame.setVisible(true);
        });
        inputFrame.add(removeProductButton);

        // "Search Quantity" butonu
        JButton searchQuantityButton = new JButton("Search Product Quantity");
        searchQuantityButton.addActionListener(e -> {
            JFrame searchFrame = new JFrame("Search Product Quantity");
            searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            searchFrame.setSize(400, 200);
            searchFrame.setLocationRelativeTo(null);

            SearchPanel searchPanel = new SearchPanel(searchProductQuantity, searchFrame::dispose);
            searchFrame.add(searchPanel);

            searchFrame.setVisible(true);
        });
        inputFrame.add(searchQuantityButton);

        // "Ağacı Görüntüle" butonu
        JButton viewTreeButton = new JButton("View The Tree");
        viewTreeButton.addActionListener(e -> {
            TreePanel treePanel = new TreePanel(addOrder.root);
            treePanel.showTreePanel(addOrder, () -> inputFrame.setVisible(true));
        });
        inputFrame.add(viewTreeButton);

        // "Siparişleri Temizle" butonu
        JButton clearOrdersButton = new JButton("Clear Orders");
        clearOrdersButton.addActionListener(e -> {
            // Ağaç boş mu kontrol et
            if (addOrder.root.children.isEmpty()) {
                JOptionPane.showMessageDialog(inputFrame,
                        "Order list is empty. Nothing to clear.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);
                return; // İşlemi sonlandır
            }

            // Onay mesajı
            int result = JOptionPane.showConfirmDialog(inputFrame,
                    "Are you sure you want to delete all orders?",
                    "Approval Required",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                FileUtils.clearOrdersFromFile("orders.txt"); // Dosyayı temizle
                clearTree(addOrder); // Tree yapısını sıfırla
                JOptionPane.showMessageDialog(inputFrame,
                        "Orders cleared successfully!",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        inputFrame.add(clearOrdersButton);

        // Çerçeveyi görünür yap
        inputFrame.setVisible(true);
    }

    // Tree yapısını temizleyen metot
    public static void clearTree(AddOrder addOrder) {
        addOrder.root = new Node("root", 0); // Kök düğümü sıfırla
    }
}
