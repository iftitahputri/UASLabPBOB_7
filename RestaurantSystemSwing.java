import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import models.auth.Akun;
import services.PesananService;
import models.meja.KebersihanMeja;
import models.meja.KetersediaanMeja;
import models.meja.Meja;
import services.MejaService;
import services.MenuService;
import services.PesananService;
import models.pesanan.*;
import models.menu.MenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RestaurantSystemSwing extends JFrame {

    private RestaurantSystem system;
    private PesananService pesananService;

    private JPanel mainContentPanel; 
    private String namaPemesan = ""; 
    private int nomorMejaDipilih = -1;
    private JTextArea listPegawaiArea;
    private Akun akunLogin; // Untuk menyimpan info akun yang login
    private MenuService menuService;
    private MejaService mejaService;
    private java.util.List<TagihanDummy> riwayatTransaksi = new java.util.ArrayList<>();

    public RestaurantSystemSwing() {
        this.system = new RestaurantSystem(); 
        this.menuService = system.getMenuService();
        this.mejaService = system.getMejaService();
        this.pesananService = system.getPesananService();

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
        mainContentPanel.setLayout(new BorderLayout()); 
        mainContentPanel.add(panel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // --- LAYAR 1: Home Screen (Tampilan Awal) ---
    private void showHomeScreen() {
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

        JPanel mejaPanel = createMejaButtonPanel();
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel1.add(mejaPanel, gbc);
        
        // --- 3. Legend/Keterangan ---
        JPanel legendPanel = createLegendPanel();
        
        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1;
        panel1.add(legendPanel, gbc);
        
        // --- 4. Tombol Lanjut ---
        JButton lanjutButton = new JButton("Lanjut");
        lanjutButton.setBackground(Color.decode("#69F069"));
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
            
            // Set meja dipesan melalui MejaService
            // system.getMejaService().setMejaDipesan(nomorMejaDipilih);
            
            showCustomerPanel2_Menu();
        });
        
        showPanel(panel1);
    }
    
    // Helper untuk membuat Panel Tombol Meja
    private JPanel createMejaButtonPanel() {
        JPanel mejaPanel = new JPanel(new GridLayout(3, 5, 5, 5));
        
        // TODO: Integrasi dengan MejaService untuk mendapatkan status meja sebenarnya
        // List<Meja> semuaMeja = system.getMejaService().getAllMeja();
        
        for (int i = 1; i <= 15; i++) {
            // Status meja dari sistem (sementara hardcode)
            Color warna = Color.GRAY; 
            boolean tersedia = false;
            
            // Contoh: meja 2, 6 tersedia
            if (i == 2 || i == 6) {
                warna = Color.GREEN;
                tersedia = true;
            } else if (i == 4 || i == 8 || i == 12 || i == 13) {
                warna = Color.YELLOW; // Belum dibersihkan
            } else {
                warna = Color.LIGHT_GRAY; // Terisi
            }

            JToggleButton mejaButton = new JToggleButton(String.valueOf(i));
            mejaButton.setBackground(warna);
            mejaButton.setOpaque(true);
            mejaButton.setBorderPainted(false);
            
            if (tersedia) {
                mejaButton.addActionListener(e -> {
                    if (mejaButton.isSelected()) {
                        nomorMejaDipilih = Integer.parseInt(mejaButton.getText());
                    } else {
                        nomorMejaDipilih = -1;
                    }
                });
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
        
        legendPanel.add(createLegendItem(Color.YELLOW, "Belum Dibersihkan"));
        legendPanel.add(createLegendItem(Color.GREEN, "Kosong")); 
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
        // TODO: Integrasi dengan MenuService untuk mendapatkan data menu
        String[] columnNames = {"ID", "Nama", "Harga", "Tipe"};
        
        // Contoh data - ganti dengan data dari MenuService
        String[][] data = {
            {"F01", "Nasi Goreng", "25000", "Makanan"},
            {"D01", "Es Teh", "5000", "Minuman"},
            // Data lainnya dari MenuService
        };
        
        JTable menuTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        
        scrollPane.setPreferredSize(new Dimension(600, 300)); 
        JPanel tableContainer = new JPanel(new GridBagLayout());
        tableContainer.add(scrollPane);
        
        panel2.add(tableContainer, BorderLayout.CENTER);
        
        // --- Tombol Pesan Sekarang ---
        JButton pesanButton = new JButton("Pesan Sekarang");
        pesanButton.setBackground(Color.decode("#69F069")); 
        pesanButton.setOpaque(true);
        pesanButton.setBorderPainted(false);
        pesanButton.setPreferredSize(new Dimension(200, 50));
        
        JPanel southPanel = new JPanel();
        southPanel.add(pesanButton);
        panel2.add(southPanel, BorderLayout.SOUTH);

        pesanButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pesanan untuk " + namaPemesan + " di Meja " + nomorMejaDipilih + " akan dibuat.");
            // TODO: Integrasi dengan PesananService untuk membuat pesanan
            showHomeScreen();
        });

        showPanel(panel2);
    }
    
    private void showStaffLoginChoice() {
        JPanel staffPanel = new JPanel(new GridLayout(5, 1, 10, 10));
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
        loginPegawaiBtn.addActionListener(e -> showLoginDialog("PEGAWAI"));
        loginManagerBtn.addActionListener(e -> showLoginDialog("MANAGER"));
        daftarAkunBtn.addActionListener(e -> showDaftarAkunPegawaiDialog()); 

        showPanel(staffPanel);
    }
    
    // --- Metode Dialog (Pop-up) ---
    
    private void showLoginDialog(String role) {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("")); // placeholder
        loginPanel.add(new JLabel("")); // placeholder

        int result = JOptionPane.showConfirmDialog(this, loginPanel, 
                "Login " + role, JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            boolean success = false;

            if (role.equals("MANAGER")) {
                success = system.loginManagerGUI(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Login Manager berhasil!");
                    showManagerPanel_Home("Pak Bos " + username);
                }
            } else if (role.equals("PEGAWAI")) {
                akunLogin = system.loginPegawaiGUI(username, password);
                if (akunLogin != null) {
                    String rolePegawai = system.getRolePegawai(akunLogin.getIdPegawai());
                    JOptionPane.showMessageDialog(this, "Login " + rolePegawai + " berhasil!");
                    
                    // Navigasi berdasarkan role
                    switch (rolePegawai.toUpperCase()) {
                        case "PELAYAN":
                            showPelayanPanel_Home(akunLogin.getUsername());
                            break;
                        case "KOKI":
                            showKokiPanel_Home(akunLogin.getUsername());
                            break;
                        case "KASIR":
                            showKasirPanel_Home(akunLogin.getUsername());
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Role tidak dikenali: " + rolePegawai);
                    }
                    success = true; 
                }
            }

            if (!success) {
                JOptionPane.showMessageDialog(this, "Login gagal. Username atau Password salah.");
            }
        }
    }
    
private void showDaftarAkunPegawaiDialog() {
    JPanel daftarPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Ubah jadi 5 baris untuk konfirmasi password
    JTextField idField = new JTextField();
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    
    daftarPanel.add(new JLabel("ID Pegawai:"));
    daftarPanel.add(idField);
    daftarPanel.add(new JLabel("Username:"));
    daftarPanel.add(usernameField);
    daftarPanel.add(new JLabel("Password:"));
    daftarPanel.add(passwordField);

    int result = JOptionPane.showConfirmDialog(this, daftarPanel, 
            "Daftar Akun Pegawai", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String id = idField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
 
        
        // Validasi input
        if (id.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        
        
        // Panggil AuthService untuk mendaftarkan akun
        try {
            boolean success = system.getAuthService().daftarAkunPegawai(id, username, password);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Pendaftaran akun untuk ID " + id + " berhasil!\nSilakan login dengan username: " + username,
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear form untuk input berikutnya
                idField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Pendaftaran gagal!\nMungkin ID tidak valid atau username sudah digunakan.\nMinta ID pegawai yang valid dari Manager.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

    // ========= Manager =======================================================
    private void showManagerPanel_Home(String namaManager) {
        JPanel homePanel = new JPanel(null);
        
        JLabel welcomeLabel = new JLabel("Selamat Siang " + namaManager);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(50, 50, 400, 30);
        homePanel.add(welcomeLabel);

        JButton aturKepegawaianBtn = new JButton("Atur Kepegawaian");
        aturKepegawaianBtn.setFont(new Font("Arial", Font.PLAIN, 24));
        aturKepegawaianBtn.setBounds(50, 100, 400, 150);
        aturKepegawaianBtn.setBackground(Color.LIGHT_GRAY);
        aturKepegawaianBtn.setOpaque(true);
        
        aturKepegawaianBtn.addActionListener(e -> showManagerPanel_AturKepegawaian());
        homePanel.add(aturKepegawaianBtn);

        JButton gantiManagerBtn = new JButton("Ganti Manager");
        gantiManagerBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        gantiManagerBtn.setForeground(Color.RED);
        gantiManagerBtn.setBorderPainted(false);
        gantiManagerBtn.setContentAreaFilled(false);
        gantiManagerBtn.setBounds(50, 270, 150, 20);
        
        gantiManagerBtn.addActionListener(e -> showGantiManagerDialog());
        homePanel.add(gantiManagerBtn);

        JButton backBtn = new JButton("< Kembali");
        backBtn.setBounds(650, 50, 100, 30);
        backBtn.addActionListener(e -> showHomeScreen());
        homePanel.add(backBtn);

        showPanel(homePanel);
    }

    private void showManagerPanel_AturKepegawaian() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // ✅ INISIALISASI listPegawaiArea di sini
        listPegawaiArea = new JTextArea();
        listPegawaiArea.setEditable(false);
        listPegawaiArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    
        // ✅ Load data pertama kali
        loadDataPegawai(listPegawaiArea);
    
        JLabel title = new JLabel("Pusat Kepegawaian", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JButton backBtn = new JButton("< Kembali");
        backBtn.addActionListener(e -> showManagerPanel_Home("Manager"));
        panel.add(backBtn, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);

        // Panel Kiri: Tambah Pegawai
        JPanel tambahPanel = new JPanel(new GridBagLayout());
        tambahPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel tambahTitle = new JLabel("Tambah Pegawai");
        tambahTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; 
        tambahPanel.add(tambahTitle, gbc);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup roleGroup = new ButtonGroup();
        JRadioButton kokiRadio = new JRadioButton("Koki");
        JRadioButton pelayanRadio = new JRadioButton("Pelayan");
        JRadioButton kasirRadio = new JRadioButton("Kasir");
        roleGroup.add(kokiRadio); roleGroup.add(pelayanRadio); roleGroup.add(kasirRadio);
        rolePanel.add(kokiRadio); rolePanel.add(pelayanRadio); rolePanel.add(kasirRadio);
        
        gbc.gridy = 1; 
        tambahPanel.add(rolePanel, gbc);

        JTextField namaField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField hpField = new JTextField(15);

        gbc.gridy = 2; tambahPanel.add(new JLabel("Nama"), gbc);
        gbc.gridy = 3; tambahPanel.add(namaField, gbc);
        gbc.gridy = 4; tambahPanel.add(new JLabel("Email"), gbc);
        gbc.gridy = 5; tambahPanel.add(emailField, gbc);
        gbc.gridy = 6; tambahPanel.add(new JLabel("No. HP"), gbc);
        gbc.gridy = 7; tambahPanel.add(hpField, gbc);

        JButton tambahBtn = new JButton("Tambah");
        tambahBtn.setBackground(Color.decode("#69F069")); 
        tambahBtn.setOpaque(true);
        tambahBtn.setBorderPainted(false);
        gbc.gridy = 8; gbc.anchor = GridBagConstraints.CENTER;
        tambahPanel.add(tambahBtn, gbc);
        
// Action Listener yang diperbaiki
tambahBtn.addActionListener(e -> {
    // Ambil role yang dipilih dari radio button
    String role = "";
    if (kokiRadio.isSelected()) {
        role = "KOKI";
    } else if (pelayanRadio.isSelected()) {
        role = "PELAYAN";
    } else if (kasirRadio.isSelected()) {
        role = "KASIR";
    }
    
    // Ambil data dari text field
    String nama = namaField.getText().trim();
    String email = emailField.getText().trim();
    String noHp = hpField.getText().trim();
    
    // Validasi input
    if (role.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih role pegawai terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (nama.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Email harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (noHp.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No. HP harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Panggil method untuk menambah pegawai
    handleTambahPegawai(role, nama, email, noHp, namaField, emailField, hpField, roleGroup);
});

        // Panel Kanan: List Pegawai
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel listTitle = new JLabel("List Pegawai", SwingConstants.CENTER);
        listTitle.setFont(new Font("Arial", Font.BOLD, 16));
        listPanel.add(listTitle, BorderLayout.NORTH);

        listPegawaiArea.setEditable(false);
        
        listPanel.add(new JScrollPane(listPegawaiArea), BorderLayout.CENTER);
        
        splitPane.setLeftComponent(tambahPanel);
        splitPane.setRightComponent(listPanel);

        panel.add(splitPane, BorderLayout.CENTER);
        showPanel(panel);
    }

public void handleTambahPegawai(String role, String nama, String email, String noHp, 
                               JTextField namaField, JTextField emailField, JTextField hpField,
                               ButtonGroup roleGroup) {
    try {
        // Panggil AuthService untuk menambah pegawai
        boolean success = system.tambahPegawaiGUI(role, nama, email, noHp);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Seorang (" + role + ") " + nama + " berhasil ditambahkan!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form setelah berhasil
            namaField.setText("");
            emailField.setText("");
            hpField.setText("");
            roleGroup.clearSelection(); // Reset radio buttons
            
            // Refresh data pegawai
            refreshDataPegawai();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Gagal menambah pegawai. Periksa data dan coba lagi.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

private void refreshDataPegawai() {
    //CEK NULL dulu sebelum menggunakan
    if (listPegawaiArea != null) {
        loadDataPegawai(listPegawaiArea);
        System.out.println("✅ Data pegawai berhasil di-refresh");
    } else {
        System.out.println("⚠️  listPegawaiArea masih null, tidak bisa refresh");
        // Optional: re-initialize atau show error message
        JOptionPane.showMessageDialog(this, 
            "Error: Komponen tampilan belum siap. Coba buka menu kepegawaian lagi.",
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void loadDataPegawai(JTextArea textArea) {
    try {
        textArea.setText(""); // Clear previous data
        
        List<String[]> data = Util.CSVUtils.readCSV("pegawai.csv");
        
        if (data.isEmpty()) {
            textArea.setText("Belum ada data pegawai.");
            return;
        }

        // Header
        textArea.append(String.format("%-10s %-12s %-20s %-20s %-15s\n", 
                "ID", "ROLE", "NAMA", "EMAIL", "NO HP"));
        textArea.append("--------------------------------------------------------------------------\n");
        
        // Data
        int counter = 1;
        for (String[] row : data) {
            if (row.length >= 5) {
                textArea.append(String.format("%-10s %-12s %-20s %-20s %-15s\n",
                        row[0], row[1], row[2], row[3], row[4]));
            }
            counter++;
        }
        
    } catch (Exception e) {
        textArea.setText("Error loading data: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void showGantiManagerDialog() {
        JPanel gantiPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        
        gantiPanel.add(new JLabel("Username Baru:"));
        gantiPanel.add(usernameField);
        gantiPanel.add(new JLabel("Password Baru:"));
        gantiPanel.add(passwordField);
        gantiPanel.add(new JLabel("Konfirmasi Password:"));
        gantiPanel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(this, gantiPanel, 
                "Ganti Kredensial Manager", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String newU = usernameField.getText();
            String newP = new String(passwordField.getPassword());
            String confirmP = new String(confirmPasswordField.getPassword());
            
            if (!newP.equals(confirmP)) {
                JOptionPane.showMessageDialog(this, "Password tidak cocok!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 
            // empty username ka bereh
            if (newU.isEmpty() || newP.isEmpty()){
                JOptionPane.showMessageDialog(this, 
                "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                system.updateAkunManagerGUI(newU, newP);
                JOptionPane.showMessageDialog(this, "Akun Manager berhasil diubah. Silakan login kembali.");
                showHomeScreen();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui akun: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ========= Pelayan =======================================================
    private void showPelayanPanel_Home(String namaPelayan) {
        JPanel homePanel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Selamat Siang " + namaPelayan + " (Pelayan)", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        homePanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton bersihkanMejaBtn = new JButton("Bersihkan Meja");
        JButton buatPesananBtn = new JButton("Buat Pesanan Baru");
        JButton lihatPesananBtn = new JButton("Lihat Daftar Pesanan");
        JButton kembaliBtn = new JButton("Kembali ke Menu Utama");

        buttonPanel.add(bersihkanMejaBtn);
        buttonPanel.add(buatPesananBtn);
        buttonPanel.add(lihatPesananBtn);
        buttonPanel.add(kembaliBtn);

        bersihkanMejaBtn.addActionListener(e -> {
            new BersihkanMejaFrame(system.getMejaService()).setVisible(true);});
        buatPesananBtn.addActionListener(e -> {
            new BuatPesananFrame(menuService, mejaService, pesananService).setVisible(true);
        });
        lihatPesananBtn.addActionListener(e -> {
            new DaftarPesananFrame(system.getPesananService()).setVisible(true);
        });
        kembaliBtn.addActionListener(e -> showHomeScreen());

        homePanel.add(buttonPanel, BorderLayout.CENTER);
        showPanel(homePanel);
    }

    class BersihkanMejaFrame extends JFrame {

        private MejaService mejaService;

        public BersihkanMejaFrame(MejaService mejaService) {
            this.mejaService = mejaService;

            setTitle("Pilih Meja untuk Dibersihkan");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 3, 10, 10));  // contoh 9 meja

            var list = mejaService.getDaftarMeja();

            for (var meja : list) {
                JButton btn = new JButton("Meja " + meja.getNomorMeja());

                // Tentukan warna berdasarkan 2 status
                Color warna = getWarna(meja);
                btn.setBackground(warna);

                btn.addActionListener(e -> {
                    // hanya update kebersihan
                    mejaService.bersihkanMejaGUI(meja.getNomorMeja());

                    JOptionPane.showMessageDialog(this,
                            "Meja " + meja.getNomorMeja() + " berhasil dibersihkan!");

                    // refresh tampilan
                    this.dispose();
                    new BersihkanMejaFrame(mejaService).setVisible(true);
                });

                panel.add(btn);
            }

            add(panel);
        }

        private Color getWarna(Meja m) {
            boolean tersedia = (m.getKetersediaan()==KetersediaanMeja.TERSEDIA);
            boolean bersih   = (m.getKebersihan()==KebersihanMeja.BERSIH);

            if (tersedia && bersih) return Color.GREEN;
            if (tersedia && !bersih) return Color.ORANGE;
            if (!tersedia && bersih) return Color.YELLOW;
            return Color.RED;  // DIPESAN + KOTOR
        }

        
    }

    public class BuatPesananFrame extends JFrame {
        private MenuService menuService;
        private MejaService mejaService;
        private PesananService pesananService;

        public BuatPesananFrame(MenuService menuService, MejaService mejaService, PesananService pesananService) {
            this.menuService = menuService;
            this.mejaService = mejaService;
            this.pesananService = pesananService;

            setTitle("Buat Pesanan");
            setSize(500, 400);
            setLayout(new BorderLayout());

            // Bagian pilih meja
            JPanel panelMeja = new JPanel(new GridLayout(0, 5, 5, 5));
            for (var meja : mejaService.getDaftarMeja()) {

                JButton btn = new JButton("Meja " + meja.getNomorMeja());

                if (meja.getKebersihan().toString().equals("KOTOR")) {
                    btn.setBackground(Color.RED);
                } else {
                    btn.setBackground(Color.GREEN);
                }

                btn.addActionListener(e -> pilihMenu(meja.getNomorMeja()));
                panelMeja.add(btn);
            }

            add(new JLabel("Pilih Meja:"), BorderLayout.NORTH);
            add(panelMeja, BorderLayout.CENTER);

            setVisible(true);
        }

        private void pilihMenu(int nomorMeja) {
            new PilihMenuFrame(menuService, mejaService, pesananService, nomorMeja);
        }
    }

    public class PilihMenuFrame extends JFrame {
        private MenuService menuService;
        private MejaService mejaService;
        private PesananService pesananService;
        private int nomorMeja;

        private Pesanan pesananTemp;

        public PilihMenuFrame(MenuService menuService, MejaService mejaService,
                              PesananService pesananService, int nomorMeja) {

            this.menuService = menuService;
            this.mejaService = mejaService;
            this.pesananService = pesananService;
            this.nomorMeja = nomorMeja;

            pesananTemp = new Pesanan(mejaService.getMejaByNomor(nomorMeja));

            setTitle("Pilih Menu");
            setSize(600, 500);
            setLayout(new BorderLayout());

            JPanel panelMenu = new JPanel(new GridLayout(0, 1));

            for (MenuItem item : menuService.getDaftarMenu()) {
                JButton btn = new JButton(item.getNama() + " - " + item.getHarga());

                btn.addActionListener(e -> tambahItem(item));

                panelMenu.add(btn);
            }

            JButton selesaiBtn = new JButton("Simpan Pesanan");
            selesaiBtn.addActionListener(e -> simpanPesanan());

            add(new JScrollPane(panelMenu), BorderLayout.CENTER);
            add(selesaiBtn, BorderLayout.SOUTH);

            setVisible(true);
        }

        private void tambahItem(MenuItem item) {
            String jumlahStr = JOptionPane.showInputDialog("Jumlah:");
            if (jumlahStr == null) return;

            int jumlah = Integer.parseInt(jumlahStr);

            DetailPesanan dp = new DetailPesanan(item, jumlah, "");
            pesananTemp.tambahDetailPesanan(dp);

            JOptionPane.showMessageDialog(this, "Ditambahkan: " + item.getNama());
        }

        private void simpanPesanan() {
            if (pesananTemp.getDetailPesanan().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pesanan kosong!");
                return;
            }

            pesananService.getDaftarPesanan().add(pesananTemp);
            mejaService.getMejaByNomor(nomorMeja).setKebersihan(models.meja.KebersihanMeja.KOTOR);

            JOptionPane.showMessageDialog(this, "Pesanan berhasil disimpan!");
            dispose();
        }
    }

    public class DaftarPesananFrame extends JFrame {

        private PesananService pesananService;

        public DaftarPesananFrame(PesananService pesananService) {
            this.pesananService = pesananService;

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

    private void showDaftarPesananDialog() {
        // TODO: Implementasi lihat daftar pesanan
        JOptionPane.showMessageDialog(this, "Fitur lihat daftar pesanan akan diimplementasi");
    }

    // ========= Koki ==========================================================
    private void showKokiPanel_Home(String namaKoki) {
        JPanel homePanel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Selamat Siang " + namaKoki + " (Koki)", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        homePanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton lihatPesananBtn = new JButton("Lihat Pesanan untuk Dimasak");
        JButton updateStatusBtn = new JButton("Update Status Pesanan");
        JButton kembaliBtn = new JButton("Kembali ke Menu Utama");

        buttonPanel.add(lihatPesananBtn);
        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(kembaliBtn);

        lihatPesananBtn.addActionListener(e -> showPesananUntukDimasakDialog());
        updateStatusBtn.addActionListener(e -> showUpdateStatusPesananDialog());
        kembaliBtn.addActionListener(e -> showHomeScreen());

        homePanel.add(buttonPanel, BorderLayout.CENTER);
        showPanel(homePanel);
    }

    private void showPesananUntukDimasakDialog() {
    // ambil pesanan dari daftar
    List<Pesanan> daftar = pesananService.getDaftarPesanan();

    if (daftar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada pesanan yang harus dimasak.");
        return;
    }

    String[] columnNames = {"ID", "Meja", "Menu", "Jumlah", "Status"};
    String[][] data = new String[daftar.size()][6];

    for (int i = 0; i < daftar.size(); i++) {
        Pesanan p = daftar.get(i);
        data[i][0] = String.valueOf(p.getIdPesanan());
        data[i][1] = String.valueOf(p.getMeja());
        data[i][2] = p.getDetailPesanan().get(0).getItem().getNama(); 
        data[i][3] = String.valueOf(p.getDetailPesanan().get(0).getJumlah());
        data[i][4] = p.getStatus().name();
    }

    JTable table = new JTable(data, columnNames);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setPreferredSize(new Dimension(650, 350));

    JOptionPane.showMessageDialog(this, scroll, "Pesanan untuk Dimasak", JOptionPane.PLAIN_MESSAGE);
    }

    private void showUpdateStatusPesananDialog() {
        List<Pesanan> daftar = pesananService.getDaftarPesanan();

    if (daftar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada pesanan.");
        return;
    }

    // Pilih pesanan
    String[] pilihanPesanan = new String[daftar.size()];
    for (int i = 0; i < daftar.size(); i++) {
        Pesanan p = daftar.get(i);
        pilihanPesanan[i] = "ID: " + p.getIdPesanan() + " | Meja " + p.getMeja() + " | Status: " + p.getStatus();
    }

    String selected = (String) JOptionPane.showInputDialog(
        this,
        "Pilih pesanan untuk update status:",
        "Update Status Pesanan",
        JOptionPane.QUESTION_MESSAGE,
        null,
        pilihanPesanan,
        pilihanPesanan[0]
    );

    if (selected == null) return; // user cancel

    // Ambil ID dari string pilihan
    int id = Integer.parseInt(selected.split(" ")[1]);

    // Update status (DIPESAN → DIMASAK → SELESAI)
    pesananService.updateStatusKoki(id);

    JOptionPane.showMessageDialog(this, "Status pesanan berhasil diperbarui!");
}

    // ========= Kasir =========================================================
    private void showKasirPanel_Home(String namaKasir) {
        JPanel homePanel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Selamat Siang " + namaKasir + " (Kasir)", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        homePanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton prosesPembayaranBtn = new JButton("Proses Pembayaran");
        JButton cetakStrukBtn = new JButton("Cetak Ulang Struk");
        JButton kembaliBtn = new JButton("Kembali ke Menu Utama");

        buttonPanel.add(prosesPembayaranBtn);
        buttonPanel.add(cetakStrukBtn);
        buttonPanel.add(kembaliBtn);

        prosesPembayaranBtn.addActionListener(e -> showKasirPanel_POS());
        cetakStrukBtn.addActionListener(e -> showCetakUlangDialog());
        kembaliBtn.addActionListener(e -> showHomeScreen());

        homePanel.add(buttonPanel, BorderLayout.CENTER);
        showPanel(homePanel);
    }
    // ================== LOGIKA KASIR POS (INTEGRASI) ==================
    
    // Variabel global sementara untuk logika kasir di panel ini
    private TagihanDummy tagihanTerpilih = null; 
    private JLabel lblTotalTagihanPOS;
    private JLabel lblKembalianPOS;
    private JTextField txtUangDiterimaPOS;
    private JButton btnProsesPOS;
    private DefaultTableModel tableModelPOS;
    
    private void showKasirPanel_POS() {
        JPanel mainPosPanel = new JPanel(new BorderLayout());
        
        // --- Header dengan Tombol Kembali ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel title = new JLabel("Proses Pembayaran (POS)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton btnBack = new JButton("< Kembali");
        btnBack.addActionListener(e -> showKasirPanel_Home("Kasir")); // Kembali ke menu kasir
        
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.CENTER);
        mainPosPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Split Pane (Kiri & Kanan) ---
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(250); 
        
        // BAGIAN KIRI: List Meja (Pending Bills)
        JPanel leftPanel = new JPanel(new BorderLayout());
        DefaultListModel<TagihanDummy> listModel = new DefaultListModel<>();
        JList<TagihanDummy> listMeja = new JList<>(listModel);
        listMeja.setFixedCellHeight(40);
        
        // Dummy Data (Nanti diganti dengan system.getPembayaranService().getPendingBills())
        loadDummyDataPOS(listModel); 
        
        listMeja.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tagihanTerpilih = listMeja.getSelectedValue();
                updateRincianTable();
            }
        });
        
        leftPanel.add(new JScrollPane(listMeja), BorderLayout.CENTER);
        
        // BAGIAN KANAN: Tabel & Kalkulator
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        // Tabel Rincian
        String[] cols = {"Menu", "Qty", "Harga", "Subtotal"};
        tableModelPOS = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModelPOS);
        rightPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Panel Bawah (Kalkulator)
        JPanel bottomPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        bottomPanel.add(new JLabel("Total Tagihan:"));
        lblTotalTagihanPOS = new JLabel("Rp 0");
        lblTotalTagihanPOS.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(lblTotalTagihanPOS);
        
        bottomPanel.add(new JLabel("Uang Diterima:"));
        txtUangDiterimaPOS = new JTextField();
        bottomPanel.add(txtUangDiterimaPOS);
        
        bottomPanel.add(new JLabel("Kembalian:"));
        lblKembalianPOS = new JLabel("Rp 0");
        lblKembalianPOS.setFont(new Font("Arial", Font.BOLD, 16));
        lblKembalianPOS.setForeground(new Color(0, 150, 0));
        bottomPanel.add(lblKembalianPOS);
        
        btnProsesPOS = new JButton("BAYAR & CETAK");
        btnProsesPOS.setEnabled(false);
        btnProsesPOS.setBackground(Color.decode("#69F069"));
        bottomPanel.add(new JLabel("")); // Spacer
        bottomPanel.add(btnProsesPOS);
        
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Logic Realtime Kembalian
        txtUangDiterimaPOS.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { hitungKembalian(); }
            public void removeUpdate(DocumentEvent e) { hitungKembalian(); }
            public void changedUpdate(DocumentEvent e) { hitungKembalian(); }
        });
        
       // Di dalam method showKasirPanel_POS...

        btnProsesPOS.addActionListener(e -> {
            String inputUang = txtUangDiterimaPOS.getText().replaceAll("[^0-9]", "");
            double uangDiterima = Double.parseDouble(inputUang);
            double totalTagihan = tagihanTerpilih.hitungTotal();
            double kembalian = uangDiterima - totalTagihan;

            // --- UPDATE LOGIKA BARU ---
            // 1. Simpan data pembayaran ke objek tagihan
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM HH:mm");
            
            tagihanTerpilih.uangDibayar = uangDiterima;
            tagihanTerpilih.kembalian = kembalian;
            tagihanTerpilih.waktuTransaksi = now.format(fmt);

            // 2. Masukkan ke List Riwayat
            riwayatTransaksi.add(tagihanTerpilih);

            // 3. Cetak Struk
            tampilkanStruk(tagihanTerpilih, uangDiterima, kembalian);
            
            // 4. Bersihkan UI
            JOptionPane.showMessageDialog(this, "Pembayaran Berhasil & Tersimpan di Riwayat.");
            listModel.removeElement(tagihanTerpilih); 
            tableModelPOS.setRowCount(0); 
            tagihanTerpilih = null;
            lblTotalTagihanPOS.setText("Rp 0");
            lblKembalianPOS.setText("Rp 0");
            txtUangDiterimaPOS.setText("");
            btnProsesPOS.setEnabled(false);
        });

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPosPanel.add(splitPane, BorderLayout.CENTER);

        showPanel(mainPosPanel); // Tampilkan Panel
    }
    private void showCetakUlangDialog() {
        if (riwayatTransaksi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada transaksi yang selesai hari ini.");
            return;
        }

        // Dialog Panel
        JDialog dialog = new JDialog(this, "Riwayat Transaksi (Cetak Ulang)", true);
        dialog.setSize(400, 500);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        // List Riwayat
        DefaultListModel<TagihanDummy> historyModel = new DefaultListModel<>();
        for (TagihanDummy t : riwayatTransaksi) {
            historyModel.addElement(t);
        }
        
        JList<TagihanDummy> listHistory = new JList<>(historyModel);
        listHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dialog.add(new JScrollPane(listHistory), BorderLayout.CENTER);

        // Tombol Cetak
        JButton btnPrint = new JButton("Cetak Ulang Struk");
        btnPrint.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnPrint.addActionListener(e -> {
            TagihanDummy selected = listHistory.getSelectedValue();
            if (selected != null) {
                dialog.dispose(); // Tutup dialog dulu
                // Panggil ulang fungsi cetak struk yang sudah ada
                tampilkanStruk(selected, selected.uangDibayar, selected.kembalian);
            } else {
                JOptionPane.showMessageDialog(dialog, "Pilih transaksi dulu!");
            }
        });

        dialog.add(btnPrint, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    // ================== LOGIKA CETAK STRUK ==================
    
    private void tampilkanStruk(TagihanDummy tagihan, double uangDiterima, double kembalian) {
        StringBuilder struk = new StringBuilder();
        
        // Format Tanggal Waktu Saat Ini
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String tanggal = now.format(formatter);
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // --- Header Struk ---
        struk.append("==========================================\n");
        struk.append("          RESTORAN IBU KANDUANG           \n");
        struk.append("       Jl. Raya Padang - Bukittinggi      \n");
        struk.append("==========================================\n");
        struk.append("Tanggal : " + tanggal + "\n");
        struk.append("Meja    : " + tagihan.meja + "\n");
        struk.append("Kasir   : Admin (Default)\n"); // Bisa diganti variable nama user login
        struk.append("Pelanggan: " + tagihan.namaPelanggan + "\n");
        struk.append("------------------------------------------\n");
        
        // --- Daftar Item ---
        // Format kolom: Nama (Kiri), Qty (Tengah), Total (Kanan)
        struk.append(String.format("%-18s %-5s %13s\n", "Menu", "Qty", "Subtotal"));
        struk.append("------------------------------------------\n");

        for (ItemMenuDummy item : tagihan.items) {
            double subtotal = item.harga * item.qty;
            String namaMenu = item.nama;
            
            // Jika nama menu kepanjangan, potong agar rapi
            if (namaMenu.length() > 18) namaMenu = namaMenu.substring(0, 15) + "...";
            
            struk.append(String.format("%-18s x%-4d %13s\n", 
                    namaMenu, 
                    item.qty, 
                    fmt.format(subtotal).replace("Rp", "")));
        }
        
        struk.append("------------------------------------------\n");
        
        // --- Total & Pembayaran ---
        double total = tagihan.hitungTotal();
        struk.append(String.format("Total Tagihan : %18s\n", fmt.format(total)));
        struk.append(String.format("Tunai         : %18s\n", fmt.format(uangDiterima)));
        struk.append(String.format("Kembalian     : %18s\n", fmt.format(kembalian)));
        struk.append("==========================================\n");
        struk.append("          TERIMA KASIH            \n");
        struk.append("    Silakan Datang Kembali        \n");
        struk.append("==========================================\n");

        // --- Tampilkan di Jendela Dialog ---
        JTextArea textArea = new JTextArea(struk.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Font Monospaced agar lurus
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 450));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Cetak Struk", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void updateRincianTable() {
        if (tagihanTerpilih == null) return;
        tableModelPOS.setRowCount(0);
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        for (ItemMenuDummy item : tagihanTerpilih.items) {
            tableModelPOS.addRow(new Object[]{
                item.nama, item.qty, fmt.format(item.harga), fmt.format(item.harga * item.qty)
            });
        }
        lblTotalTagihanPOS.setText(fmt.format(tagihanTerpilih.hitungTotal()));
        txtUangDiterimaPOS.setText("");
        lblKembalianPOS.setText("Rp 0");
    }
    
    private void hitungKembalian() {
        if (tagihanTerpilih == null) return;
        try {
            String input = txtUangDiterimaPOS.getText().replaceAll("[^0-9]", "");
            if (input.isEmpty()) return;
            double uang = Double.parseDouble(input);
            double total = tagihanTerpilih.hitungTotal();
            
            NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            double kembali = uang - total;
            lblKembalianPOS.setText(fmt.format(kembali));
            
            if (kembali >= 0) {
                btnProsesPOS.setEnabled(true);
                lblKembalianPOS.setForeground(Color.GREEN);
            } else {
                btnProsesPOS.setEnabled(false);
                lblKembalianPOS.setForeground(Color.RED);
            }
        } catch (Exception ex) {}
    }

    private void loadDummyDataPOS(DefaultListModel<TagihanDummy> model) {
        TagihanDummy t1 = new TagihanDummy("Meja 1", "Budi");
        t1.items.add(new ItemMenuDummy("Nasi Goreng", 2, 25000));
        t1.items.add(new ItemMenuDummy("Es Teh", 2, 5000));
        model.addElement(t1);
        
        TagihanDummy t2 = new TagihanDummy("Meja 5", "Siti");
        t2.items.add(new ItemMenuDummy("Ayam Bakar", 4, 30000));
        model.addElement(t2);
    }

    private void showProsesPembayaranDialog() {
        // TODO: Implementasi proses pembayaran
        JOptionPane.showMessageDialog(this, "Fitur proses pembayaran akan diimplementasi");
    }

    private void showCetakStrukDialog() {
        // TODO: Implementasi cetak struk
        JOptionPane.showMessageDialog(this, "Fitur cetak struk akan diimplementasi");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantSystemSwing());
    }
    // ================== CLASS PENDUKUNG KASIR (INTEGRASI) ==================
   class TagihanDummy {
        String meja, namaPelanggan;
        java.util.List<ItemMenuDummy> items = new java.util.ArrayList<>();
        
        // Data tambahan untuk riwayat
        double uangDibayar = 0;
        double kembalian = 0;
        String waktuTransaksi = "";

        public TagihanDummy(String m, String n) { meja = m; namaPelanggan = n; }
        
        public double hitungTotal() { 
            return items.stream().mapToDouble(i -> i.harga * i.qty).sum(); 
        }
        
        @Override public String toString() { 
            // Jika sudah ada waktu transaksi (sudah bayar), tampilkan waktunya
            if (!waktuTransaksi.isEmpty()) {
                return "[" + waktuTransaksi + "] " + meja + " - " + namaPelanggan;
            }
            return meja + " - " + namaPelanggan; 
        }
    }   
    class ItemMenuDummy {
        String nama; int qty; double harga;
        public ItemMenuDummy(String n, int q, double h) { nama = n; qty = q; harga = h; }
    }
    
}