package models.auth;

/**
 * Merepresentasikan data akun pengguna dalam sistem restoran.
 * Class ini menyimpan informasi login untuk authentication.
 * 
 * @author Kelompok_7
 * @version 1.0
 */
public class Akun {
    /**
     * ID unik pegawai yang terkait dengan akun ini
     */
    protected String idPegawai;
    
    /**
     * Username untuk login ke sistem
     */
    protected String username;
    
    /**
     * Password untuk authentication
     */
    protected String password;

    /**
     * Membuat instance akun baru dengan kredensial yang diberikan.
     * 
     * @param idPegawai ID pegawai yang terkait dengan akun
     * @param username username untuk login
     * @param password password untuk authentication
     */
    public Akun(String idPegawai, String username, String password) {
        this.idPegawai = idPegawai;
        this.username = username;
        this.password = password;
    }

    /**
     * Mengembalikan ID pegawai pemilik akun.
     * 
     * @return ID pegawai sebagai String
     */
    public String getIdPegawai() { return idPegawai; }
    
    /**
     * Mengembalikan username akun.
     * 
     * @return username sebagai String
     */
    public String getUsername() { return username; }
    
    /**
     * Mengembalikan password akun.
     * 
     * @return password sebagai String
     */
    public String getPassword() { return password; }
}