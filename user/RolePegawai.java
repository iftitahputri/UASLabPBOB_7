package User;

public enum RolePegawai {
    PELAYAN("PEL"),
    KOKI("KOK"),
    KASIR("KAS"); 

    private String kode;

    RolePegawai(String kode) {
        this.kode = kode;
    }

    public String getKode() {
        return kode;
    }
}
