package transaksi;
import java.time.LocalDateTime;
import java.util.UUID;

public class QRISPayment implements Pembayaran {
    private String idPembayaran; 
    private double amount;
    private String merchantName;
    private String status;
    private LocalDateTime paymentDate;
    
    public QRISPayment(double amount, String merchantName) {
        this.idPembayaran = "QRIS-" + UUID.randomUUID().toString();
        this.amount = amount;
        this.merchantName = merchantName;
        this.status = "Pending";
    }
    
    @Override
    public boolean prosesPembayaran(double amount) {
        this.amount = amount;
        if (validasiPembayaran()) {
            this.status = "Success";
            this.paymentDate = LocalDateTime.now();
            return true;
        }
        this.status = "Failed";
        return false;
    }
    
    @Override
    public boolean validasiPembayaran() {
        return amount > 0 && merchantName != null && !merchantName.isEmpty();
    }
    
    @Override
    public String getDetailPembayaran() {
        return String.format("QRIS - Jumlah: Rp%,.2f, Merchant: %s", amount, merchantName);
    }
    
    @Override
    public String getStatusPembayaran() {
        return status;
    }
    
    @Override
    public String getMetodePembayaran() {
        return "QRIS";
    }
    
    @Override
    public String getIdPembayaran() {
        return idPembayaran;
    }
    
    public String getMerchantName() { 
        return merchantName;
    }
}