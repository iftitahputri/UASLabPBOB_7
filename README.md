# UASLabPBOB_7
**🍽️ Sistem Restoran Ibu Kanduang**

Sistem Restoran Ibu Kanduang adalah sebuah aplikasi manajemen restoran yang dikembangkan menggunakan Java, dirancang untuk mendukung operasional harian di sebuah restoran.

Sistem ini dapat dijalankan dalam dua mode:

CLI (Command Line Interface): Berjalan melalui RestaurantSystem.java.

GUI (Graphical User Interface): Berbasis Java Swing, berjalan melalui RestaurantSystemSwing.java.

**Fungsi Utama**

Sistem ini mendukung peran multi-user, termasuk Pelanggan, Pelayan, Koki, Kasir, dan Manager, masing-masing dengan akses dan tugas yang spesifik, seperti:

Pemesanan: Pelayan dapat membuat pesanan baru dan Koki dapat memperbarui status masakan (Dipesan -> Dimasak -> Selesai).

Meja: Pelayan dapat membersihkan meja, dan Pelanggan dapat melihat status ketersediaan meja.

Pembayaran: Kasir dapat memproses pembayaran (Cash, Card, QRIS) dan mencetak struk.

Administrasi: Manager dapat menambah pegawai baru.

**How To Run?**
- mulai dengan mengcompile semua file
```
javac RestaurantSystemSwing.java 
```
- jalankan seperti biasa
```
java RestaurantSystemSwing 
```
atau
```
java RestaurantSystem
```

**Default User**
Gunakan Username: admin dengan Password: admin123








