package User;

public class Koki extends Pegawai {
    public Koki(String id, String nama, String email, String noHp) {
        super(id, nama, email, noHp, RolePegawai.KOKI);
    }
}
