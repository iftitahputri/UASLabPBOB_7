package models.pekerja;

// class kasir 
public class Kasir extends Pegawai {
    public Kasir(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.KASIR); // superclass constructor
    }
}
