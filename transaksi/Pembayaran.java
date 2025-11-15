package transaksi;


public interface Pembayaran {
    boolean prosesPembayaran(double amount);
    boolean validasiPembayaran();
    String getDetailPembayaran();
    String getStatusPembayaran();
    String getMetodePembayaran(); 
    String getIdPembayaran();
} 