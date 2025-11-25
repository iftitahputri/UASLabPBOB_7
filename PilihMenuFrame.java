import javax.swing.*;
import java.awt.*;

import models.menu.MenuItem;
import models.pesanan.Pesanan;
import models.pesanan.DetailPesanan;
import services.MenuService;
import services.MejaService;
import services.PesananService;

// frame untuk pilih menu
public class PilihMenuFrame extends JFrame {
    private MenuService menuService;
    private MejaService mejaService;
    private PesananService pesananService;
    private int nomorMeja;
    private Pesanan pesananTemp;

    // constructor
    public PilihMenuFrame(MenuService menuService, MejaService mejaService,
        PesananService pesananService, int nomorMeja) {
        this.menuService = menuService;
        this.mejaService = mejaService;
        this.pesananService = pesananService;
        this.nomorMeja = nomorMeja;
        this.pesananTemp = new Pesanan(mejaService.getMejaByNomor(nomorMeja));

        initializeUI();
    }

    // inisialisasi UI
    private void initializeUI() {
        setTitle("Pilih Menu");
        setSize(600, 500);
        setLayout(new BorderLayout());

        JPanel panelMenu = new JPanel(new GridLayout(0, 1));

        // buat tombol untuk setiap menu
        for (MenuItem item : menuService.getDaftarMenu()) {
            JButton btn = new JButton(item.getNama() + " - " + item.getHarga());
            btn.addActionListener(e -> tambahItem(item));
            panelMenu.add(btn);
        }

        // tombol selesai
        JButton selesaiBtn = new JButton("Simpan Pesanan");
        selesaiBtn.addActionListener(e -> simpanPesanan());

        add(new JScrollPane(panelMenu), BorderLayout.CENTER);
        add(selesaiBtn, BorderLayout.SOUTH);
        setVisible(true);
    }

    // method tambah item ke pesanan
    private void tambahItem(MenuItem item) {
        // input jumlah + catatan
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        JTextField jumlahField = new JTextField();
        JTextField catatanField = new JTextField();

        inputPanel.add(new JLabel("Jumlah:"));
        inputPanel.add(jumlahField);
        inputPanel.add(new JLabel("Catatan (opsional):"));
        inputPanel.add(catatanField);

        // tampilkan dialog confirm
        int result = JOptionPane.showConfirmDialog(
            this, 
            inputPanel, 
            "Tambah " + item.getNama(),
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return;

        try {
            // ambil input jumlah dan catatan
            int jumlah = Integer.parseInt(jumlahField.getText().trim());
            String catatan = catatanField.getText().trim();

            // buat detail pesanan dan tambahkan ke pesanan sementara
            DetailPesanan dp = new DetailPesanan(item, jumlah, catatan);
            pesananTemp.tambahDetailPesanan(dp);

            // tampilkan pesan sementara berhasil
            JOptionPane.showMessageDialog(this, 
                "Ditambahkan: " + item.getNama() + 
                (catatan.isEmpty() ? "" : "\nCatatan: " + catatan));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus angka!"); // validasi input jumlah
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); // tangani error lain
        }
    }

    // method simpan pesanan
    private void simpanPesanan() {
        if (pesananTemp.getDetailPesanan().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pesanan kosong!");
            return;
        }

        // simpan pesanan ke service
        pesananService.getDaftarPesanan().add(pesananTemp);
        mejaService.getMejaByNomor(nomorMeja).setKebersihan(models.meja.KebersihanMeja.KOTOR);

        // tampilkan pesan berhasil
        JOptionPane.showMessageDialog(this, "Pesanan berhasil disimpan!");
        dispose();
    }
}