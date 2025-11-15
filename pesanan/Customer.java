package pesanan;

public class Customer extends Akun { // Inheritance

    public Customer(int id, String nama, String password) {
        super(id, nama, password);
    }

    
    public void buatPesanan() {
        // Dalam sistem ini, Customer tidak membuat pesanan langsung,
        // melainkan melalui Pelayan.
        System.out.println("Customer " + getNama() + " memanggil pelayan untuk membuat pesanan.");
    }
}
