package User;

public abstract class Pegawai {
    private String id;       
    private String nama;
    private String email;
    private String noHp;
    private RolePegawai role;

    public Pegawai(String id, String nama, String email, String noHp, RolePegawai role) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public RolePegawai getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getNoHp() {
        return noHp;
    }
}
