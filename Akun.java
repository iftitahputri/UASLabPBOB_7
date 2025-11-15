package PROJECT UAS;

public abstract class Akun {
    // Encapsulation: fields bersifat private
    private int id;
    private String nama;
    private String password;

    public Akun(int id, String nama, String password) {
        this.id = id;
        this.nama = nama;
        this.password = password;
    }

    // Getter (Encapsulation)
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }
}
