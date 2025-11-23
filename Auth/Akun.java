package Auth;

public class Akun {
    protected String idPegawai;
    protected String username;
    protected String password;

    public Akun(String idPegawai, String username, String password) {
        this.idPegawai = idPegawai;
        this.username = username;
        this.password = password;
    }

    public String getIdPegawai() { return idPegawai; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
