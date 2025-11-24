import javax.swing.*;
import java.awt.*;
import java.util.List;
import models.auth.Akun;
import services.PesananService;
import models.meja.KebersihanMeja;
import models.meja.KetersediaanMeja;
import models.meja.Meja;
import services.MejaService;
import services.MenuService;
import models.pesanan.*;
import models.transaksi.CardPayment;
import models.transaksi.QRISPayment;
import models.menu.MenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class RestaurantSystemSwing extends JFrame {

    private RestaurantSystem system;
    private PesananService pesananService;
    private JPanel mainContentPanel; 
    private JTextArea listPegawaiArea;
    private Akun akunLogin; // Untuk menyimpan info akun yang login
    private MenuService menuService;
    private MejaService mejaService;

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

    // --- LAYAR PELANGGAN: Lihat Meja + Menu + Panggil Pelayan ---
private void showCustomerPanel1_Meja() {
    JPanel panel1 = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;

    // Judul / Header
    JLabel header = new JLabel("Status Meja Saat Ini");
    header.setFont(new Font("Arial", Font.BOLD, 20));
    gbc.gridx = 0; gbc.gridy = 0;
    gbc.gridwidth = 3;
    panel1.add(header, gbc);

    // Panel meja (hanya tampilan, tidak bisa dipilih)
    JPanel mejaPanel = createMejaDisplayPanel();
    gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
    panel1.add(mejaPanel, gbc);

    // Legend
    JPanel legendPanel = createLegendPanel();
    gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 1;
    panel1.add(legendPanel, gbc);

    // Tombol lihat menu
    JButton lihatMenuBtn = new JButton("Lihat Menu");
    lihatMenuBtn.setPreferredSize(new Dimension(150, 40));
    lihatMenuBtn.setBackground(Color.decode("#69F069"));
    lihatMenuBtn.setOpaque(true);
    lihatMenuBtn.setBorderPainted(false);

    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
    panel1.add(lihatMenuBtn, gbc);

    // Tombol panggil pelayan
    JButton panggilPelayanBtn = new JButton("Panggil Pelayan");
    panggilPelayanBtn.setPreferredSize(new Dimension(150, 40));
    panggilPelayanBtn.setBackground(Color.decode("#FFD966"));
    panggilPelayanBtn.setOpaque(true);
    panggilPelayanBtn.setBorderPainted(false);

    gbc.gridx = 1; gbc.gridy = 2;
    panel1.add(panggilPelayanBtn, gbc);

    // Action
    lihatMenuBtn.addActionListener(e -> showCustomerPanel2_Menu());
    panggilPelayanBtn.addActionListener(e -> {
        JOptionPane.showMessageDialog(this,
                "Pelayan telah dipanggil. Harap tunggu sebentar.",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
    });

    showPanel(panel1);
}

private JPanel createMejaDisplayPanel() {
    JPanel mejaPanel = new JPanel(new GridLayout(3, 5, 5, 5));

    List<Meja> semuaMeja = system.getMejaService().getDaftarMeja();

    for (Meja meja : semuaMeja) {

        int nomor = meja.getNomorMeja();
        KebersihanMeja kebersihan = meja.getKebersihan();
        KetersediaanMeja ketersediaan = meja.getKetersediaan();

        Color warna;

        // === LOGIKA WARNA BERDASARKAN 2 ENUM ===
        if (kebersihan == KebersihanMeja.BERSIH && ketersediaan == KetersediaanMeja.TERSEDIA) {
            warna = Color.GREEN;        // bersih dan tersedia

        } else if (kebersihan == KebersihanMeja.KOTOR && ketersediaan == KetersediaanMeja.TERSEDIA) {
            warna = Color.YELLOW;       // kotor tapi tidak ada pelanggan

        } else { 
            warna = Color.LIGHT_GRAY;   // dipesan/occupied
        }

        // === Bikin Tombol ===
        JToggleButton mejaButton = new JToggleButton(String.valueOf(nomor));
        mejaButton.setOpaque(true);
        mejaButton.setBorderPainted(false);
        mejaButton.setBackground(warna);

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

    // --- LAYAR 3: Tampilkan Menu (Panel 2) ---
    private void showCustomerPanel2_Menu() {
    JPanel panel2 = new JPanel(new BorderLayout());

    JLabel titleLabel = new JLabel("Daftar Menu", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    panel2.add(titleLabel, BorderLayout.NORTH);

    // Ambil menu dari CSV via MenuService
    List<MenuItem> menuList = system.getMenuService().getDaftarMenu();

    String[] columnNames = {"ID", "Nama", "Harga", "Tipe"};
    String[][] data = new String[menuList.size()][4];

    for (int i = 0; i < menuList.size(); i++) {
        MenuItem item = menuList.get(i);
        data[i][0] = item.getId();
        data[i][1] = item.getNama();
        data[i][2] = String.valueOf(item.getHarga());
        data[i][3] = item.getTipe();
    }

    JTable menuTable = new JTable(data, columnNames);
    menuTable.setEnabled(false); // ← supaya benar-benar read-only

    JScrollPane scrollPane = new JScrollPane(menuTable);
    panel2.add(scrollPane, BorderLayout.CENTER);

    // Tombol kembali
    JButton backButton = new JButton("Kembali ke Home");
    backButton.setPreferredSize(new Dimension(200, 50));
    backButton.addActionListener(e -> showHomeScreen());

    JPanel southPanel = new JPanel();
    southPanel.add(backButton);
    panel2.add(southPanel, BorderLayout.SOUTH);

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
    List<Pesanan> daftar = pesananService.getDaftarPesanan().stream()
        .filter(p -> p.getStatus() == StatusPesanan.DIPESAN)
        .toList();

    if (daftar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada pesanan yang harus dimasak.");
        return;
    }

    String[] columnNames = {"ID", "Meja", "Menu", "Jumlah", "Status"};
    String[][] data = new String[daftar.size()][6];

    for (int i = 0; i < daftar.size(); i++) {
        Pesanan p = daftar.get(i);
        data[i][0] = String.valueOf(p.getIdPesanan());
        data[i][1] = String.valueOf(p.getMeja().getNomorMeja());
        data[i][2] = p.getDetailPesanan().get(0).getItem().getNama(); 
        data[i][3] = String.valueOf(p.getDetailPesanan().get(0).getJumlah().getValue());
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

    // List pesanan yg bisa diupdate (DIPESAN atau DIMASAK)
    List<Pesanan> bisaUpdate = daftar.stream()
        .filter(p -> p.getStatus() != StatusPesanan.SELESAI && p.getStatus() != StatusPesanan.LUNAS)
        .toList();

    if (bisaUpdate.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tidak ada pesanan yang dapat diupdate.");
        return;
    }

    String[] pilihanPesanan = new String[bisaUpdate.size()];
    for (int i = 0; i < bisaUpdate.size(); i++) {
        Pesanan p = bisaUpdate.get(i);
        pilihanPesanan[i] =
            "ID: " + p.getIdPesanan() +
            " | Menu: " + p.getDetailPesanan().get(0).getItem().getNama() +
            " | Status: " + p.getStatus();
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

    if (selected == null) return;

    // Ambil ID
    int id = Integer.parseInt(selected.split(" ")[1]);
    Pesanan pesanan = pesananService.cariPesananById(id);

    // Tentukan status berikutnya
    StatusPesanan statusBaru = switch (pesanan.getStatus()) {
        case DIPESAN -> StatusPesanan.DIMASAK;
        case DIMASAK -> StatusPesanan.SELESAI;
        default -> null; // selesai tidak bisa ubah
    };

    if (statusBaru == null) {
        JOptionPane.showMessageDialog(this, "Status tidak dapat diubah lagi.");
        return;
    }

    pesanan.setStatus(statusBaru);
    pesananService.updateStatus(id, statusBaru);

    JOptionPane.showMessageDialog(this, "Status berhasil diupdate menjadi: " + statusBaru);
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

// ================== LOGIKA KASIR POS (INTEGRASI REAL) ==================

// Variabel global
private Pesanan pesananTerpilih = null; 
private JLabel lblTotalTagihanPOS;
private JLabel lblKembalianPOS;
private JTextField txtUangDiterimaPOS;
private JButton btnProsesPOS;
private DefaultTableModel tableModelPOS;

// List untuk riwayat transaksi yang sudah LUNAS
private List<TransaksiSelesai> riwayatTransaksi = new ArrayList<>();

private void showKasirPanel_POS() {
    JPanel mainPosPanel = new JPanel(new BorderLayout());

    // ========== HEADER ==========
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(Color.WHITE);
    headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    JLabel title = new JLabel("Proses Pembayaran (POS)", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 18));

    JButton btnBack = new JButton("< Kembali");
    btnBack.addActionListener(e -> showKasirPanel_Home("Kasir"));

    headerPanel.add(btnBack, BorderLayout.WEST);
    headerPanel.add(title, BorderLayout.CENTER);
    mainPosPanel.add(headerPanel, BorderLayout.NORTH);

    // ========== SPLITPANE ==========
    JSplitPane splitPane = new JSplitPane();
    splitPane.setDividerLocation(250);

    // ========== LEFT: LIST PESANAN SELESAI ==========
    JPanel leftPanel = new JPanel(new BorderLayout());
    DefaultListModel<Pesanan> listModel = new DefaultListModel<>();
    JList<Pesanan> listPesanan = new JList<>(listModel);

    listPesanan.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            Pesanan p = (Pesanan) value;
            String display = "Meja " + p.getMeja().getNomorMeja() + 
                           " - Total: Rp " + String.format("%,.0f", p.hitungTotal());
            
            return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
        }
    });

    listPesanan.setFixedCellHeight(40);
    
    // LOAD PESANAN SELESAI (Status = SELESAI)
    loadPesananSelesai(listModel);

    listPesanan.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            pesananTerpilih = listPesanan.getSelectedValue();
            updateRincianTableReal();
        }
    });

    leftPanel.add(new JLabel("Pesanan Siap Bayar:", SwingConstants.CENTER), BorderLayout.NORTH);
    leftPanel.add(new JScrollPane(listPesanan), BorderLayout.CENTER);

    // ========== RIGHT PANEL ==========
    JPanel rightPanel = new JPanel(new BorderLayout());

    // TABLE
    String[] cols = {"Menu", "Qty", "Harga", "Subtotal"};
    tableModelPOS = new DefaultTableModel(cols, 0);
    JTable table = new JTable(tableModelPOS);
    rightPanel.add(new JScrollPane(table), BorderLayout.CENTER);

    // ========== BOTTOM PANEL (KALKULATOR + METODE PEMBAYARAN) ==========
    JPanel bottomPanel = new JPanel(new GridLayout(5, 2, 5, 5));
    bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // ========== 1. Total ==========
    bottomPanel.add(new JLabel("Total Tagihan:"));
    lblTotalTagihanPOS = new JLabel("Rp 0");
    lblTotalTagihanPOS.setFont(new Font("Arial", Font.BOLD, 16));
    bottomPanel.add(lblTotalTagihanPOS);

    // ========== 2. Metode Pembayaran ==========
    bottomPanel.add(new JLabel("Metode Pembayaran:"));
    String[] metode = {"Cash", "QRIS", "Card"};
    JComboBox<String> cmbMetode = new JComboBox<>(metode);
    bottomPanel.add(cmbMetode);

    // ========== 3. Uang Diterima ==========
    bottomPanel.add(new JLabel("Uang Diterima:"));
    txtUangDiterimaPOS = new JTextField();
    bottomPanel.add(txtUangDiterimaPOS);

    // ========== 4. Kembalian ==========
    bottomPanel.add(new JLabel("Kembalian:"));
    lblKembalianPOS = new JLabel("Rp 0");
    lblKembalianPOS.setFont(new Font("Arial", Font.BOLD, 16));
    lblKembalianPOS.setForeground(new Color(0, 150, 0));
    bottomPanel.add(lblKembalianPOS);

    // ========== 5. Tombol Bayar ==========
    btnProsesPOS = new JButton("BAYAR & CETAK");
    btnProsesPOS.setEnabled(false);
    btnProsesPOS.setBackground(Color.decode("#69F069"));
    bottomPanel.add(new JLabel(""));
    bottomPanel.add(btnProsesPOS);

    rightPanel.add(bottomPanel, BorderLayout.SOUTH);

    // ========== EVENT: ENABLE/DISABLE INPUT ==========
    cmbMetode.addActionListener(e -> {
        String selected = (String) cmbMetode.getSelectedItem();

        if (selected.equals("Cash")) {
            txtUangDiterimaPOS.setEnabled(true);
            txtUangDiterimaPOS.setText("");
            lblKembalianPOS.setText("Rp 0");
            btnProsesPOS.setEnabled(false);
        } else {
            txtUangDiterimaPOS.setEnabled(false);
            txtUangDiterimaPOS.setText("-");
            lblKembalianPOS.setText("Rp 0");
            btnProsesPOS.setEnabled(true);
        }
    });

    // ========== EVENT: HITUNG KEMBALIAN (CASH) ==========
    txtUangDiterimaPOS.getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { hitungKembalianReal(); }
        public void removeUpdate(DocumentEvent e) { hitungKembalianReal(); }
        public void changedUpdate(DocumentEvent e) { hitungKembalianReal(); }
    });

    // ========== EVENT: PROSES PEMBAYARAN ==========
    btnProsesPOS.addActionListener(e -> {
        if (pesananTerpilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan dulu!");
            return;
        }

        String metodeBayar = (String) cmbMetode.getSelectedItem();
        double total = pesananTerpilih.hitungTotal();
        double uangMasuk = 0;
        double kembalian = 0;

        boolean success = false;

        // ---- CASE 1: CASH ----
        if (metodeBayar.equals("Cash")) {
            String input = txtUangDiterimaPOS.getText().replaceAll("[^0-9]", "");
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan uang diterima!");
                return;
            }
            uangMasuk = Double.parseDouble(input);
            kembalian = uangMasuk - total;

            if (uangMasuk < total) {
                JOptionPane.showMessageDialog(this, "Uang kurang!");
                return;
            }
            success = true;
        }

        // ---- CASE 2: QRIS ----
        else if (metodeBayar.equals("QRIS")) {
            QRISPayment pay = new QRISPayment(total);
            success = pay.prosesPembayaran();
            uangMasuk = total;
        }

        // ---- CASE 3: CARD ----
        else if (metodeBayar.equals("Card")) {
            CardPayment pay = new CardPayment(total);
            success = pay.prosesPembayaran();
            uangMasuk = total;
        }

        if (!success) {
            JOptionPane.showMessageDialog(this, "Pembayaran gagal!");
            return;
        }

        // UPDATE STATUS PESANAN KE LUNAS
        pesananTerpilih.setStatus(StatusPesanan.LUNAS);
        pesananService.updateStatus(pesananTerpilih.getIdPesanan(), StatusPesanan.LUNAS);

        // UPDATE STATUS MEJA JADI TERSEDIA & KOTOR
        Meja meja = pesananTerpilih.getMeja();
        meja.setKetersediaan(KetersediaanMeja.TERSEDIA);
        // Kebersihan tetap KOTOR, nanti pelayan yang bersihkan

        // SIMPAN KE RIWAYAT
        TransaksiSelesai transaksi = new TransaksiSelesai(
            pesananTerpilih, 
            metodeBayar, 
            uangMasuk, 
            kembalian
        );
        riwayatTransaksi.add(transaksi);

        // CETAK STRUK
        tampilkanStrukReal(pesananTerpilih, metodeBayar, uangMasuk, kembalian);

        // RESET UI
        JOptionPane.showMessageDialog(this, 
            "Pembayaran Berhasil!\nMeja " + meja.getNomorMeja() + " sudah tersedia.");

        listModel.removeElement(pesananTerpilih);
        tableModelPOS.setRowCount(0);
        pesananTerpilih = null;

        lblTotalTagihanPOS.setText("Rp 0");
        txtUangDiterimaPOS.setText("");
        lblKembalianPOS.setText("Rp 0");
        btnProsesPOS.setEnabled(false);
    });

    splitPane.setLeftComponent(leftPanel);
    splitPane.setRightComponent(rightPanel);
    mainPosPanel.add(splitPane, BorderLayout.CENTER);

    showPanel(mainPosPanel);
}

// ========== HELPER METHODS ==========

private void loadPesananSelesai(DefaultListModel<Pesanan> model) {
    List<Pesanan> pesananSelesai = pesananService.getDaftarPesanan().stream()
        .filter(p -> p.getStatus() == StatusPesanan.SELESAI)
        .collect(Collectors.toList());

    if (pesananSelesai.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada pesanan yang siap dibayar.");
    } else {
        for (Pesanan p : pesananSelesai) {
            model.addElement(p);
        }
    }
}

private void updateRincianTableReal() {
    if (pesananTerpilih == null) return;
    
    tableModelPOS.setRowCount(0);
    NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    for (DetailPesanan dp : pesananTerpilih.getDetailPesanan()) {
        MenuItem item = dp.getItem();
        int qty = dp.getJumlahValue();
        double harga = item.getHarga();
        double subtotal = harga * qty;
        
        tableModelPOS.addRow(new Object[]{
            item.getNama(), 
            qty, 
            fmt.format(harga), 
            fmt.format(subtotal)
        });
    }
    
    lblTotalTagihanPOS.setText(fmt.format(pesananTerpilih.hitungTotal()));
    txtUangDiterimaPOS.setText("");
    lblKembalianPOS.setText("Rp 0");
}

private void hitungKembalianReal() {
    if (pesananTerpilih == null) return;
    
    try {
        String input = txtUangDiterimaPOS.getText().replaceAll("[^0-9]", "");
        if (input.isEmpty()) return;
        
        double uang = Double.parseDouble(input);
        double total = pesananTerpilih.hitungTotal();
        
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
    } catch (Exception ex) {
        btnProsesPOS.setEnabled(false);
    }
}

private void tampilkanStrukReal(Pesanan pesanan, String metode, double uangDiterima, double kembalian) {
    StringBuilder struk = new StringBuilder();
    
    // Format Tanggal Waktu
    java.time.LocalDateTime now = java.time.LocalDateTime.now();
    java.time.format.DateTimeFormatter formatter = 
        java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String tanggal = now.format(formatter);
    NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    // --- Header Struk ---
    struk.append("==========================================\n");
    struk.append("          RESTORAN IBU KANDUANG           \n");
    struk.append("       Jl. Raya Padang - Bukittinggi      \n");
    struk.append("==========================================\n");
    struk.append("ID Pesanan: " + pesanan.getIdPesanan() + "\n");
    struk.append("Tanggal   : " + tanggal + "\n");
    struk.append("Meja      : " + pesanan.getMeja().getNomorMeja() + "\n");
    struk.append("Kasir     : Admin\n");
    struk.append("------------------------------------------\n");
    
    // --- Daftar Item ---
    struk.append(String.format("%-18s %-5s %13s\n", "Menu", "Qty", "Subtotal"));
    struk.append("------------------------------------------\n");

    for (DetailPesanan dp : pesanan.getDetailPesanan()) {
        MenuItem item = dp.getItem();
        int qty = dp.getJumlahValue();
        double subtotal = item.getHarga() * qty;
        String namaMenu = item.getNama();
        
        if (namaMenu.length() > 18) namaMenu = namaMenu.substring(0, 15) + "...";
        
        struk.append(String.format("%-18s x%-4d %13s\n", 
                namaMenu, 
                qty, 
                fmt.format(subtotal).replace("Rp", "").trim()));
    }
    
    struk.append("------------------------------------------\n");
    
    // --- Total & Pembayaran ---
    double total = pesanan.hitungTotal();
    struk.append(String.format("Total Tagihan : %18s\n", fmt.format(total)));
    struk.append(String.format("Metode Bayar  : %18s\n", metode));
    
    if (metode.equalsIgnoreCase("Cash")) {
        struk.append(String.format("Tunai         : %18s\n", fmt.format(uangDiterima)));
        struk.append(String.format("Kembalian     : %18s\n", fmt.format(kembalian)));
    } else {
        struk.append("Status        : BERHASIL\n");
    }
    
    struk.append("==========================================\n");
    struk.append("          TERIMA KASIH            \n");
    struk.append("    Silakan Datang Kembali        \n");
    struk.append("==========================================\n");

    // --- Tampilkan di Dialog ---
    JTextArea textArea = new JTextArea(struk.toString());
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    textArea.setEditable(false);
    
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(350, 500));
    
    JOptionPane.showMessageDialog(this, scrollPane, "Struk Pembayaran", JOptionPane.PLAIN_MESSAGE);
}

// ========== CETAK ULANG ==========
private void showCetakUlangDialog() {
    if (riwayatTransaksi.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada transaksi yang selesai hari ini.");
        return;
    }

    JDialog dialog = new JDialog(this, "Riwayat Transaksi (Cetak Ulang)", true);
    dialog.setSize(400, 500);
    dialog.setLayout(new BorderLayout());
    dialog.setLocationRelativeTo(this);

    DefaultListModel<TransaksiSelesai> historyModel = new DefaultListModel<>();
    for (TransaksiSelesai t : riwayatTransaksi) {
        historyModel.addElement(t);
    }
    
    JList<TransaksiSelesai> listHistory = new JList<>(historyModel);
    listHistory.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            TransaksiSelesai t = (TransaksiSelesai) value;
            String display = "[" + t.waktu + "] Meja " + 
                           t.pesanan.getMeja().getNomorMeja() + 
                           " - " + t.metode;
            
            return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
        }
    });
    
    listHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    dialog.add(new JScrollPane(listHistory), BorderLayout.CENTER);

    JButton btnPrint = new JButton("Cetak Ulang Struk");
    btnPrint.setFont(new Font("Arial", Font.BOLD, 14));
    
    btnPrint.addActionListener(e -> {
        TransaksiSelesai selected = listHistory.getSelectedValue();
        if (selected != null) {
            dialog.dispose();
            tampilkanStrukReal(selected.pesanan, selected.metode, 
                             selected.uangDibayar, selected.kembalian);
        } else {
            JOptionPane.showMessageDialog(dialog, "Pilih transaksi dulu!");
        }
    });

    dialog.add(btnPrint, BorderLayout.SOUTH);
    dialog.setVisible(true);
}

// ========== CLASS PENDUKUNG ==========
class TransaksiSelesai {
    Pesanan pesanan;
    String metode;
    double uangDibayar;
    double kembalian;
    String waktu;
    
    public TransaksiSelesai(Pesanan p, String m, double u, double k) {
        this.pesanan = p;
        this.metode = m;
        this.uangDibayar = u;
        this.kembalian = k;
        
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter fmt = 
            java.time.format.DateTimeFormatter.ofPattern("dd-MM HH:mm");
        this.waktu = now.format(fmt);
    }
}

public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RestaurantSystemSwing();
        });
    }
}