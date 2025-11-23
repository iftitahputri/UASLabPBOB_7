package User;

public class Pelayan extends Pegawai {
    public Pelayan(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.PELAYAN);
    }
}