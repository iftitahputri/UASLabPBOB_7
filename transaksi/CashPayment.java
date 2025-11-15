package transaksi;
import java.time.LocalDateTime;
import java.util.UUID;
 
public class CashPayment implements Pembayaran {
    private String idPembayaran;  
    private double amount;
    private double cashTendered;
    private double cashReturn;
    private String status;
    private LocalDateTime paymentDate;
    
    public CashPayment(double amount, double cashTendered) {
        this.idPembayaran = "CASH-" + UUID.randomUUID().toString(); // Generate ID
        this.amount = amount;
        this.cashTendered = cashTendered;
        this.cashReturn = 0.0;
        this.status = "Pending";
    }
    
    @Override
    public boolean prosesPembayaran(double amount) {
    this.amount = amount; // gunakan parameter untuk konsistensi
    this.cashReturn = this.cashTendered - this.amount;
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
        return cashTendered >= amount && amount > 0;
    }
    
    @Override
    public String getDetailPembayaran() {
        return String.format("CASH - Jumlah: Rp%,.2f, Diberikan: Rp%,.2f, Kembalian: Rp%,.2f", amount, cashTendered, cashReturn);
    }
    
    @Override
    public String getStatusPembayaran() {
        return status;
    }
    
    @Override
    public String getMetodePembayaran() {
        return "CASH";
    }
    
    @Override
    public String getIdPembayaran() {
        return idPembayaran;
    }
    
    public double getKembalian() {
        return cashReturn;
    }
}