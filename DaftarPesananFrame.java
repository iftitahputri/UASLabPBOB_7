// File: DaftarPesananFrame.java
import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
import models.pesanan.Pesanan;
import models.pesanan.DetailPesanan;
import services.PesananService;

public class DaftarPesananFrame extends JFrame {
    private PesananService pesananService;

    public DaftarPesananFrame(PesananService pesananService) {
        this.pesananService = pesananService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Daftar Pesanan");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Data untuk tabel
        String[] columnNames = {"Meja", "Jumlah Item", "Total Harga", "Detail", "Status"};
        Object[][] data = convertPesananToTableData();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private Object[][] convertPesananToTableData() {
        var list = pesananService.getDaftarPesanan();
        Object[][] data = new Object[list.size()][5];

        for (int i = 0; i < list.size(); i++) {
            Pesanan p = list.get(i);

            int totalItem = p.getDetailPesanan().stream()
                    .mapToInt(DetailPesanan::getJumlahValue)
                    .sum();

            double totalHarga = p.getDetailPesanan().stream()
                    .mapToDouble(dp -> dp.getJumlahValue() * dp.getItem().getHarga())
                    .sum();

            // Buat string detail item
            StringBuilder sb = new StringBuilder();
            for (DetailPesanan dp : p.getDetailPesanan()) {
                sb.append(dp.getItem().getNama())
                  .append(" x ")
                  .append(dp.getJumlah())
                  .append("\n");
            }

            data[i][0] = p.getMeja().getNomorMeja();
            data[i][1] = totalItem;
            data[i][2] = totalHarga;
            data[i][3] = sb.toString();
            data[i][4] = p.getStatus().toString();
        }

        return data;
    }
}