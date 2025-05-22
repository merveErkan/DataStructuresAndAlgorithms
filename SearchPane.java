import javax.swing.*;
import java.awt.*;
import java.util.List;

class SearchPanel extends JPanel {
    public SearchPanel(SearchProductQuantity searchProductQuantity, Runnable onBack) {
        setLayout(new BorderLayout());

        // Kullanıcıdan ürün yolunu almak için bileşenler
        JLabel instructionLabel = new JLabel("Enter the product path (comma-separated):", SwingConstants.CENTER);
        JTextField inputField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back");

        // Bileşenleri yerleştirme
        add(instructionLabel, BorderLayout.NORTH);
        add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // "Search" butonunun işlevselliği
        searchButton.addActionListener(e -> {
            String input = inputField.getText().trim(); // Kullanıcıdan gelen girdi
            if (!input.isEmpty()) {
                List<String> productPath = AddOrder.makeList(input); // Ürün yolunu işleyip listeye dönüştür
                int quantity = searchProductQuantity.searchProductQuantity(productPath); // Miktarı ara

                if (quantity > 0) {
                    JOptionPane.showMessageDialog(this, "Product quantity: " + quantity); // Başarı mesajı
                } else {
                    JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE); // Hata mesajı
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid product path."); // Hata mesajı
            }
        });

        // "Back" butonunun işlevselliği
        backButton.addActionListener(e -> onBack.run());
    }
}
