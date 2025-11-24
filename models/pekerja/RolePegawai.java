package models.pekerja;

// enum role pegawai
public enum RolePegawai {
    PELAYAN("PEL"),
    KOKI("KOK"),
    KASIR("KAS"); 

    private String kode;

    // constructor
    RolePegawai(String kode) {
        this.kode = kode;
    }

    // getter
    public String getKode() { return kode; }
}
