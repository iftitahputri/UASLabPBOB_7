package transaksi;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardPayment implements Pembayaran {
    private String idPembayaran;  
    private double amount;
    private String bank;          
    private String status;
    private LocalDateTime paymentDate;
    
    public CardPayment(double amount, String bank) {
        this.idPembayaran = "CARD-" + UUID.randomUUID().toString();
        this.amount = amount;
        this.bank = bank;
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
        return amount > 0 && bank != null && !bank.isEmpty();
    }
    
    @Override
    public String getDetailPembayaran() {
        return String.format("KARTU - Jumlah: Rp%,.2f, Bank: %s", amount, bank);
    }
    
    @Override
    public String getStatusPembayaran() {
        return status;
    }
    
    @Override
    public String getMetodePembayaran() {
        return "KARTU";
    }
    
    @Override
    public String getIdPembayaran() {
        return idPembayaran;
    }
    
    public String getBank() { 
        return bank; 
    }
}