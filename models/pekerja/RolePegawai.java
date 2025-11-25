package models.pekerja;

/**
 * Enum yang merepresentasikan peran (role) pegawai dalam sistem restoran.
 * Setiap role memiliki kode unik yang digunakan untuk identifikasi.
 * 
 * @author Kelompok_7
 * @version 1.0
 */
public enum RolePegawai {
    /**
     * Role pelayan - bertugas melayani pelanggan dan mengambil pesanan
     */
    PELAYAN("PEL"),
    
    /**
     * Role koki - bertugas mempersiapkan dan memasak pesanan
     */
    KOKI("KOK"),
    
    /**
     * Role kasir - bertugas menangani pembayaran dan transaksi
     */
    KASIR("KAS");

    private String kode;

    /**
     * Constructor untuk RolePegawai.
     * 
     * @param kode kode unik yang merepresentasikan role
     */
    RolePegawai(String kode) {
        this.kode = kode;
    }

    /**
     * Mendapatkan kode unik dari role pegawai.
     * 
     * @return kode role dalam format string
     */
    public String getKode() { 
        return kode; 
    }
    
    /**
     * Mendapatkan RolePegawai berdasarkan kode yang diberikan.
     * 
     * @param kode kode role yang dicari
     * @return RolePegawai yang sesuai dengan kode, atau null jika tidak ditemukan
     */
    public static RolePegawai getByKode(String kode) {
        for (RolePegawai role : values()) {
            if (role.getKode().equals(kode)) {
                return role;
            }
        }
        return null;
    }
    
    /**
     * Mendapatkan deskripsi lengkap dari role pegawai.
     * 
     * @return deskripsi role dalam bahasa Indonesia
     */
    public String getDeskripsi() {
        switch (this) {
            case PELAYAN:
                return "Pelayan - Melayani pelanggan dan mengambil pesanan";
            case KOKI:
                return "Koki - Memasak dan mempersiapkan pesanan";
            case KASIR:
                return "Kasir - Menangani pembayaran dan transaksi";
            default:
                return "Role tidak diketahui";
        }
    }
}