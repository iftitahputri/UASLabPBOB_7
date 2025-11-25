package models.pekerja;

/**
 * Abstract class yang merepresentasikan data dasar seorang pegawai.
 * Class ini menjadi parent class untuk semua jenis pegawai dalam sistem restoran.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see RolePegawai
 */
public abstract class Pegawai {
    private String id;       
    private String nama;
    private String email;
    private String noHp;
    private RolePegawai role;

    /**
     * Constructor untuk membuat objek Pegawai.
     * 
     * @param id ID unik pegawai
     * @param nama nama lengkap pegawai
     * @param email alamat email pegawai
     * @param noHp nomor handphone pegawai
     * @param role role/jabatan pegawai dalam sistem
     */
    public Pegawai(String id, String nama, String email, String noHp, RolePegawai role) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
        this.role = role;
    }

    /**
     * Mendapatkan ID unik pegawai.
     * 
     * @return ID pegawai
     */
    public String getId() { return id; }

    /**
     * Mendapatkan nama lengkap pegawai.
     * 
     * @return nama pegawai
     */
    public String getNama() { return nama; }

    /**
     * Mendapatkan role/jabatan pegawai.
     * 
     * @return role pegawai
     */
    public RolePegawai getRole() { return role; }

    /**
     * Mendapatkan alamat email pegawai.
     * 
     * @return email pegawai
     */
    public String getEmail() { return email; }

    /**
     * Mendapatkan nomor handphone pegawai.
     * 
     * @return nomor handphone pegawai
     */
    public String getNoHp() { return noHp; }
}