import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    private static final String FILE_NAME = "orders.txt";

    // Siparişi dosyaya kaydet
    public static void saveOrderToFile(String order) {
        // Gelen siparişi alfabetik sıraya göre düzenle
        List<String> sortedOrder = AddOrder.makeList(order);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) { // Dosyaya ekle (append mode)
            writer.write(String.join(",", sortedOrder)); // Alfabetik sıralanmış listeyi yaz
            writer.newLine(); // Yeni satır
        } catch (IOException e) {
            System.err.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }


    // Dosyadan siparişleri yükle
    public static List<List<String>> loadOrdersFromFile() {
        List<List<String>> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(Arrays.asList(line.split("\\s*,\\s*")));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating a new one.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return orders;
    }

    // Siparişi dosyadan sil
    public static void removeOrderFromFile(List<String> orderToRemove) {
        List<List<String>> allOrders = loadOrdersFromFile(); // Tüm siparişleri oku
        if (allOrders != null) {
            allOrders.removeIf(order -> order.equals(orderToRemove)); // Eşleşen siparişi kaldır

            // Güncellenmiş listeyi dosyaya yaz
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
                for (List<String> order : allOrders) {
                    writer.write(String.join(",", order));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error updating the file: " + e.getMessage());
            }
        }
    }
    public static void clearOrdersFromFile(String file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(""); // Dosyayı sıfırla
            writer.flush(); // Belleği temizle
        } catch (IOException e) {
            System.err.println("Dosya temizleme hatası: " + e.getMessage());
        }
    }

    public static boolean isFileEmpty(String file) {
        File targetFile = new File(file);
        return targetFile.length() == 0;
    }


}
