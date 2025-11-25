package models.pekerja;

/**
 * Class yang merepresentasikan seorang Pelayan (Waiter) dalam sistem restoran.
 * Pelayan bertugas melayani pelanggan, mengambil pesanan, dan memastikan kepuasan pelanggan.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pegawai
 * @see RolePegawai
 */
public class Pelayan extends Pegawai {
    
    /**
     * Constructor untuk membuat objek Pelayan.
     * 
     * @param id ID unik pelayan
     * @param nama nama lengkap pelayan
     * @param email alamat email pelayan
     * @param noHp nomor handphone pelayan
     */
    public Pelayan(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.PELAYAN); // superclass constructor
    }
}