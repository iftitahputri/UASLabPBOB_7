package User;

public class Kasir extends Pegawai {
    public Kasir(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.KASIR);
    }
}
