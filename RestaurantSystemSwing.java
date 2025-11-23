import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSystemSwing extends JFrame {

    private RestaurantSystem system;
    private JPanel mainContentPanel; 
    private String namaPemesan = ""; 
    private int nomorMejaDipilih = -1;

    public RestaurantSystemSwing() {
        this.system = new RestaurantSystem(); 

        setTitle("Restoran Ibu Kanduang");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());

        mainContentPanel = new JPanel();
        add(mainContentPanel, BorderLayout.CENTER);

        showHomeScreen();
        
        setVisible(true);
    }

    // --- UTILITY: Mengganti Panel Utama ---
    private void showPanel(JPanel panel) {
        mainContentPanel.removeAll();
        // Atur agar panel baru mengambil seluruh ruang
        mainContentPanel.setLayout(new BorderLayout()); 
        mainContentPanel.add(panel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // --- LAYAR 1: Home Screen (Tampilan Awal) ---
    private void showHomeScreen() {
        // ... (Kode untuk titleLabel, PesanSekarangButton, StaffOnlyButton tetap sama) ...
        
        JPanel homePanel = new JPanel(null); 
        
        // Label Judul
        JLabel titleLabel = new JLabel("Restoran Ibu Kanduang", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(0, 50, 800, 30); 
        homePanel.add(titleLabel);

        // Tombol Besar "Pesan Sekarang"
        JButton pesanSekarangButton = new JButton("Pesan Sekarang");
        pesanSekarangButton.setFont(new Font("Arial", Font.PLAIN, 36));
        
        int btnWidth = 350;
        int btnHeight = 250;
        int x = (this.getWidth() - btnWidth) / 4; 
        int y = (this.getHeight() - btnHeight) / 3; 
        
        pesanSekarangButton.setBounds(x, y, btnWidth, btnHeight);
        
        pesanSekarangButton.addActionListener(e -> {
            // Aksi: Menuju ke Panel Input Nama dan Pilih Meja
            showCustomerPanel1_Meja();
        });
        homePanel.add(pesanSekarangButton);

        // Tombol Kecil "Staff Only"
        JButton staffOnlyButton = new JButton("Staff Only");
        staffOnlyButton.setBounds(650, 480, 100, 30); 
        staffOnlyButton.addActionListener(e -> showStaffLoginChoice());
        homePanel.add(staffOnlyButton);

        showPanel(homePanel);
    }

    // --- LAYAR 2: Input Nama & Pilih Meja (Panel 1) ---
    private void showCustomerPanel1_Meja() {
        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // --- 1. Nama Pemesan ---
        JLabel namaLabel = new JLabel("Nama Pemesan");
        JTextField namaField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panel1.add(namaLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(namaField, gbc);

        // --- 2. Pilih Meja ---
        JLabel mejaLabel = new JLabel("Pilih Meja");
        mejaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.NONE;
        panel1.add(mejaLabel, gbc);

        JPanel mejaPanel = createMejaButtonPanel(); // Panel khusus tombol meja
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel1.add(mejaPanel, gbc);
        
        // --- 3. Legend/Keterangan ---
        JPanel legendPanel = createLegendPanel();
        
        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1;
        panel1.add(legendPanel, gbc);
        
        // --- 4. Tombol Lanjut ---
        JButton lanjutButton = new JButton("Lanjut");
        lanjutButton.setBackground(Color.decode("#69F069")); // Warna hijau
        lanjutButton.setOpaque(true);
        lanjutButton.setBorderPainted(false);
        lanjutButton.setPreferredSize(new Dimension(150, 40));
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(lanjutButton, gbc);

        // Action Listener untuk Lanjut
        lanjutButton.addActionListener(e -> {
            namaPemesan = namaField.getText().trim();
            if (namaPemesan.isEmpty() || nomorMejaDipilih == -1) {
                JOptionPane.showMessageDialog(this, "Nama dan Meja harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Logic: Ubah status meja yang dipilih menjadi DIPESAN
            // system.getMejaByNomor(nomorMejaDipilih).setKetersediaan(KetersediaanMeja.DIPESAN);
            
            showCustomerPanel2_Menu();
        });
        
        showPanel(panel1);
    }
    
    // Helper untuk membuat Panel Tombol Meja
    private JPanel createMejaButtonPanel() {
        JPanel mejaPanel = new JPanel(new GridLayout(3, 5, 5, 5)); // Contoh 3 baris x 5 kolom = 15 meja
        ButtonGroup group = new ButtonGroup();
        
        // Loop untuk 15 meja (asumsi dari RestaurantSystem.java)
        for (int i = 1; i <= 15; i++) {
            // Logika status meja (perlu diakses dari RestaurantSystem)
            // Warna akan didapatkan dari status meja yang sebenarnya
            Color warna = Color.GRAY; 
            
            // Contoh Hardcode berdasarkan desain user (Hanya untuk ilustrasi)
            if (i == 2 || i == 6) warna = Color.GREEN; // Kosong
            else if (i == 4 || i == 8 || i == 12 || i == 13) warna = Color.YELLOW; // Belum Dibersihkan
            else if (i == 1 || i == 5) warna = Color.LIGHT_GRAY; // Terisi
            else warna = Color.LIGHT_GRAY; // Sisa dianggap terisi

            JToggleButton mejaButton = new JToggleButton(String.valueOf(i));
            mejaButton.setBackground(warna);
            mejaButton.setOpaque(true);
            mejaButton.setBorderPainted(false);
            
            if (warna.equals(Color.GREEN)) { // Hanya meja yang KOSONG yang bisa dipilih
                 mejaButton.addActionListener(e -> {
                    if (mejaButton.isSelected()) {
                        nomorMejaDipilih = Integer.parseInt(mejaButton.getText());
                    } else {
                        nomorMejaDipilih = -1;
                    }
                });
                group.add(mejaButton); // Hanya satu meja bisa dipilih (Toggle Group)
            } else {
                 mejaButton.setEnabled(false);
            }
            mejaPanel.add(mejaButton);
        }
        return mejaPanel;
    }
    
    // Helper untuk membuat Panel Legend/Keterangan Meja
    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        // Belum Dibersihkan (KOTOR)
        legendPanel.add(createLegendItem(Color.YELLOW, "Belum Dibersihkan"));
        // Kosong (BERSIH & TERSEDIA)
        legendPanel.add(createLegendItem(Color.GREEN, "Kosong")); 
        // Terisi (DIPESAN/DITEMPATI)
        legendPanel.add(createLegendItem(Color.LIGHT_GRAY, "Terisi")); 
        
        return legendPanel;
    }
    
    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel colorBox = new JLabel("  ");
        colorBox.setOpaque(true);
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        item.add(colorBox);
        item.add(new JLabel(text));
        return item;
    }

    // --- LAYAR 3: Daftar Menu (Panel 2) ---
    private void showCustomerPanel2_Menu() {
        JPanel panel2 = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Daftar Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel2.add(titleLabel, BorderLayout.NORTH);

        // --- Tampilan Daftar Menu (JTable) ---
        // Anda harus memodifikasi RestaurantSystem untuk mendapatkan data menu dalam format Array/List
        String[] columnNames = {"ID", "Nama", "Harga", "Tipe"};
        // String[][] data = system.getMenuDataForTable(); // Contoh: Memanggil data dari sistem
        
        // Contoh Data Hardcode
        String[][] data = {
            {"F01", "Nasi Goreng", "25000", "Makanan"},
            {"D01", "Es Teh", "5000", "Minuman"},
            // ... menu lainnya
        };
        
        JTable menuTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        
        // Atur ukuran tabel agar mirip desain
        scrollPane.setPreferredSize(new Dimension(600, 300)); 
        JPanel tableContainer = new JPanel(new GridBagLayout()); // Gunakan GridBagLayout untuk center
        tableContainer.add(scrollPane);
        
        panel2.add(tableContainer, BorderLayout.CENTER);
        
        // --- Tombol Pesan Sekarang ---
        JButton pesanButton = new JButton("Pesan Sekarang");
        pesanButton.setBackground(Color.decode("#69F069")); 
        pesanButton.setOpaque(true);
        pesanButton.setBorderPainted(false);
        pesanButton.setPreferredSize(new Dimension(200, 50));
        
        JPanel southPanel = new JPanel(); // Untuk menampung tombol
        southPanel.add(pesanButton);
        panel2.add(southPanel, BorderLayout.SOUTH);

        pesanButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pesanan untuk " + namaPemesan + " di Meja " + nomorMejaDipilih + " akan dibuat.");
            // TODO: Arahkan ke form detail pemesanan atau kembali ke menu utama
        });

        showPanel(panel2);
    }
    
    private void showStaffLoginChoice() {
        JPanel staffPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 baris, 1 kolom, spasi 10

        staffPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

        JLabel title = new JLabel("Area Staf", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        
        JButton daftarAkunBtn = new JButton("Daftar Akun Pegawai Baru");
        JButton loginPegawaiBtn = new JButton("Login Pegawai (Koki, Kasir, Pelayan)");
        JButton loginManagerBtn = new JButton("Login Manager");
        JButton kembaliBtn = new JButton("Kembali ke Menu Utama");

        staffPanel.add(title);
        staffPanel.add(daftarAkunBtn);
        staffPanel.add(loginPegawaiBtn);
        staffPanel.add(loginManagerBtn);
        staffPanel.add(kembaliBtn);

        kembaliBtn.addActionListener(e -> showHomeScreen());
        
        // Aksi untuk login akan memanggil metode showLoginDialog
        loginPegawaiBtn.addActionListener(e -> showLoginDialog("PEGAWAI"));
        loginManagerBtn.addActionListener(e -> showLoginDialog("MANAGER"));
        
        // Aksi untuk mendaftar akun pegawai
        daftarAkunBtn.addActionListener(e -> showDaftarAkunPegawaiDialog()); 

        showPanel(staffPanel);
    }
    
    // --- Metode Dialog (Pop-up) ---
    
    private void showLoginDialog(String role) {
        // Implementasi dialog login (seperti pada contoh sebelumnya)
        String username = JOptionPane.showInputDialog(this, "Masukkan Username " + role + ":");
        if (username == null) return; 

        String password = JOptionPane.showInputDialog(this, "Masukkan Password " + role + ":");
        if (password == null) return;
        
        // Lakukan Otentikasi
        boolean success = false;
        
        if (role.equals("MANAGER")) {
             // Anda perlu membuat metode loginManager di RestaurantSystem mengembalikan boolean
             // success = system.loginManager(username, password); 
             // Untuk saat ini, kita anggap sukses
             success = true; 
        } else if (role.equals("PEGAWAI")) {
             // success = system.akunPegawai.login(username, password) != null;
             success = true;
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Login " + role + " berhasil!");
            // TODO: Arahkan ke menu sesuai role (menuManager/menuPelayan/menuKoki/menuKasir)
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal. Username atau Password salah.");
        }
    }
    
    private void showDaftarAkunPegawaiDialog() {
         // Logika untuk menampilkan dialog pendaftaran akun baru
         JOptionPane.showMessageDialog(this, "Anda akan diarahkan ke form pendaftaran akun pegawai.");
         // TODO: Anda perlu membuat form input untuk ID, Username, Password, lalu memanggil system.daftarAkunPegawai(id, u, p)
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantSystemSwing());
    }
}