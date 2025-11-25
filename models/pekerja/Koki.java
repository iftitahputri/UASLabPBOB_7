package models.pekerja;

/**
 * Class yang merepresentasikan seorang Koki (Chef) dalam sistem restoran.
 * Koki bertugas mempersiapkan, memasak, dan menyajikan makanan sesuai pesanan pelanggan.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pegawai
 * @see RolePegawai
 */
public class Koki extends Pegawai {
    
    /**
     * Constructor untuk membuat objek Koki.
     * 
     * @param id ID unik koki
     * @param nama nama lengkap koki
     * @param email alamat email koki
     * @param noHp nomor handphone koki
     */
    public Koki(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.KOKI); // superclass constructor
    }
}