import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanel extends JPanel {
    private Node root;

    public TreePanel(Node root) {

        this.root = root;
        setLayout(new BorderLayout()); // BorderLayout kullanarak yazıyı alt kısma ekleyebiliriz.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (root != null) {
            // Başlangıç X koordinatını belirle
            int startX = getWidth() / 2;
            // Dinamik ağacı çizmeye başla
            Map<Node, Integer> subtreeSizes = calculateSubtreeSizes(root);
            drawTree(g, root, startX, 50, subtreeSizes, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, Map<Node, Integer> subtreeSizes, int xOffset) {
        if (node == null) return;

        // Çocuk düğümler bir alt seviyede yer alır
        int childY = y + 80; // Dikey mesafe sabit bir değerle artırıldı

        // Her bir çocuk düğümün X koordinatını hesapla
        int totalSubtreeWidth = subtreeSizes.get(node);
        int startX = x - (totalSubtreeWidth / 2); // Çocuk düğümleri ortalamak için

        for (Map.Entry<String, Node> entry : node.children.entrySet()) {
            Node child = entry.getValue();
            int childWidth = subtreeSizes.get(child);
            int childX = startX + (childWidth / 2); // Çocuk düğümün merkezine göre yerleştir
            startX += childWidth; // Bir sonraki çocuk için X başlangıç noktasını güncelle

            // Çocuk düğüme bağlantı çizgisi çiz
            g.setColor(Color.BLACK);
            g.drawLine(x, y, childX, childY);

            // Çocuğu çiz
            drawTree(g, child, childX, childY, subtreeSizes, xOffset / 2);
        }

        // Yazı tipi ve boyutunu ayarla
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Ovalin boyutlarını tanımlayın
        int ovalWidth = 80;
        int ovalHeight = 40;

        // Ovalin merkezini hesaplayarak çizin
        g.setColor(Color.BLACK);
        g.fillOval(x - (ovalWidth / 2), y - (ovalHeight / 2), ovalWidth, ovalHeight);

        // Yazıyı ortalamak için FontMetrics kullanın
        FontMetrics metrics = g.getFontMetrics();
        String text = node.name + " (" + node.quantity + ")";
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        // Yazının koordinatlarını ovalin ortasına göre ayarla
        int textX = x - (textWidth / 2);
        int textY = y + (textHeight / 4);

        // Yazıyı çizin
        g.setColor(Color.WHITE);
        g.drawString(text, textX, textY);
    }

    // Alt düğüm sayısını hesaplayan yardımcı metot
    private Map<Node, Integer> calculateSubtreeSizes(Node node) {
        Map<Node, Integer> subtreeSizes = new HashMap<>();
        calculateSubtreeSizesRecursively(node, subtreeSizes);
        return subtreeSizes;
    }

    private int calculateSubtreeSizesRecursively(Node node, Map<Node, Integer> subtreeSizes) {
        if (node.children.isEmpty()) {
            subtreeSizes.put(node, 100); // Yaprak düğümler için sabit genişlik
            return 100;
        }

        int totalSize = 0;
        for (Node child : node.children.values()) {
            totalSize += calculateSubtreeSizesRecursively(child, subtreeSizes);
        }

        subtreeSizes.put(node, Math.max(totalSize, 100)); // Minimum genişlik 100 piksel
        return totalSize;
    }
    // Ağaç panelini gösteren metot
    public void showTreePanel(AddOrder addOrder, Runnable onBack) {
        JFrame treeFrame = new JFrame("Tree Visualization");
        treeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Toplam sipariş ve ürün miktarını hesapla
        int totalOrders = sumOfChildrenQuantities(addOrder.root);
        int totalProducts = calculateTotalProductsExcludingRoot(addOrder.root);

        // Panel genişliğini düğüm sayısına göre ayarla
        int width = Math.max(1000, totalOrders * 60);
        int height = Math.max(600, totalProducts * 83); // Yeni satırın yüksekliğini artır

        // Ağaç panelini oluştur ve kaydırma özelliği ekle
        TreePanel treePanel = new TreePanel(addOrder.root);
        treePanel.setPreferredSize(new Dimension(width, height));
        JScrollPane scrollPane = new JScrollPane(treePanel);

        // Geri butonu oluştur
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            treeFrame.dispose(); // Tree panelini kapat
            onBack.run(); // Ana menüye dön
        });

        // Üst panel oluştur
        JPanel topPanel = new JPanel(new BorderLayout());
        String totalInfoText = "Total Orders: " + totalOrders + " | Total Products: " + totalProducts;
        JLabel totalInfoLabel = new JLabel(totalInfoText, SwingConstants.CENTER);
        totalInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Üst panel bileşenlerini yerleştir
        topPanel.add(backButton, BorderLayout.WEST); // Geri butonunu sola yerleştir
        topPanel.add(totalInfoLabel, BorderLayout.CENTER); // Toplam sipariş ve ürün miktarını merkeze yerleştir

        // Ana pencereye üst paneli ve kaydırma panelini ekle
        treeFrame.add(topPanel, BorderLayout.NORTH);
        treeFrame.add(scrollPane, BorderLayout.CENTER);

        treeFrame.setSize(1900, 1000);
        treeFrame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
        treeFrame.setVisible(true);
    }


    // Bir düğümün sadece çocuklarının toplam miktarını hesaplar
    public int sumOfChildrenQuantities(Node currentNode) {
        int totalQuantity = 0;

        if (currentNode.children != null) {
            for (Node child : currentNode.children.values()) {
                totalQuantity += child.quantity; // Çocukların miktarlarını topla
            }
        }
        return totalQuantity;
    }

    // Kök düğümün altındaki tüm düğümlerin toplamını hesaplar (kök miktarı hariç)
    public int calculateTotalProductsExcludingRoot(Node currentNode) {
        if (currentNode == null) {
            return 0;
        }

        int totalProducts = calculateTotalProducts(currentNode); // Tüm düğümlerin toplamını hesapla
        int rootQuantity = currentNode.quantity; // Kök düğümün kendi miktarını al

        return totalProducts - rootQuantity; // Kök düğüm miktarını toplamdan çıkar
    }

    // Ağacın tamamındaki tüm düğümlerin toplam miktarını hesaplar
    public int calculateTotalProducts(Node currentNode) {
        if (currentNode == null) {
            return 0;
        }

        int total = currentNode.quantity; // Mevcut düğümün miktarını ekle

        for (Node child : currentNode.children.values()) {
            total += calculateTotalProducts(child); // Alt düğümleri özyinelemeli topla
        }

        return total;
    }







}
