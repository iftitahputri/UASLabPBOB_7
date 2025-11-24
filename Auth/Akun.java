package Auth;

// class menyimpan data akun
public class Akun {
    // data dasar login
    protected String idPegawai;
    protected String username;
    protected String password;

    // constructor
    public Akun(String idPegawai, String username, String password) {
        this.idPegawai = idPegawai;
        this.username = username;
        this.password = password;
    }

    // getter
    public String getIdPegawai() { return idPegawai; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}