package DetailPesanan;

public class Jumlah {
    private int value;

    public Jumlah(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("Jumlah harus >= 1");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
