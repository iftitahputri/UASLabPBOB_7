package models.pekerja;

/**
 * Class yang merepresentasikan seorang Kasir (Cashier) dalam sistem restoran.
 * Kasir bertugas menangani transaksi pembayaran, menerima pembayaran dari pelanggan,
 * dan mengelola kas restoran.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pegawai
 * @see RolePegawai
 */
public class Kasir extends Pegawai {
    
    /**
     * Constructor untuk membuat objek Kasir.
     * 
     * @param id ID unik kasir
     * @param nama nama lengkap kasir
     * @param email alamat email kasir
     * @param noHp nomor handphone kasir
     */
    public Kasir(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.KASIR); // superclass constructor
    }
}