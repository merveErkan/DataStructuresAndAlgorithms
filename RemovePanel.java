import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RemovePanel extends JPanel {
    public RemovePanel(RemoveOrder removeOrder, Runnable onBack) {
        setLayout(new BorderLayout());

        // Kullanıcıdan ürün listesini almak için bileşenler
        JLabel instructionLabel = new JLabel("Enter the order to remove (comma-separated):", SwingConstants.CENTER);
        JTextField inputField = new JTextField();
        JButton removeButton = new JButton("Remove Order");
        JButton backButton = new JButton("Back");

        // Bileşenleri yerleştirme
        add(instructionLabel, BorderLayout.NORTH);
        add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // "Remove Order" butonunun işlevselliği
        removeButton.addActionListener(e -> {
            String input = inputField.getText().trim(); // Kullanıcıdan gelen girdi
            if (!input.isEmpty()) {
                List<String> orderToRemove = AddOrder.makeList(input); // Girilen listeyi işle
                boolean result = removeOrder.remove(orderToRemove); // Listeyi silmeye çalış

                if (result) {
                    JOptionPane.showMessageDialog(this, "Order removed successfully!"); // Başarı mesajı
                } else {
                    JOptionPane.showMessageDialog(this, "Order not found or cannot be removed.", "Error", JOptionPane.ERROR_MESSAGE); // Hata mesajı
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid order to remove."); // Hata mesajı
            }
        });

        // "Back" butonunun işlevselliği
        backButton.addActionListener(e -> onBack.run());
    }

    public String getInput() {
        return null;
    }

}
