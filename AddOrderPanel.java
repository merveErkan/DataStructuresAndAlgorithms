import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddOrderPanel extends JPanel {
    public AddOrderPanel(AddOrder addOrder, Runnable onBack) {
        setLayout(new BorderLayout());

        // Kullanıcıdan sipariş almak için bileşenler
        JLabel instructionLabel = new JLabel("Enter your order (comma-separated):", SwingConstants.CENTER);
        JTextField inputField = new JTextField();
        JButton addButton = new JButton("Add Order");
        JButton backButton = new JButton("Back");

        // Bileşenleri yerleştirme
        add(instructionLabel, BorderLayout.NORTH);
        add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // "Add Order" butonunun işlevselliği
        addButton.addActionListener(e -> {
            String input = inputField.getText().trim(); // Kullanıcıdan gelen girdi
            if (!input.isEmpty()) {
                List<String> newOrder = AddOrder.makeList(input); // Siparişi işleyip listeye dönüştür
                addOrder.addOrder(String.join(",", newOrder)); // Siparişi ağaca ekle
                FileUtils.saveOrderToFile(String.join(",", newOrder)); // Siparişi dosyaya kaydet
                JOptionPane.showMessageDialog(this, "Order added successfully!"); // Başarı mesajı
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid order."); // Hata mesajı
            }
        });

        // "Back" butonunun işlevselliği
        backButton.addActionListener(e -> onBack.run());
    }


    // Toplam düğüm sayısını hesaplar
    private int calculateTotalNodes(Node node) {
        if (node == null) return 0;

        int total = 1; // Mevcut düğümü say
        for (Node child : node.children.values()) { // Map yapısına uygun döngü
            total += calculateTotalNodes(child); // Çocuk düğümleri de hesapla
        }
        return total;
    }

}
