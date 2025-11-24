package models.pesanan;
 
// class untuk menyimpan jumlah pesanan
public class Jumlah {
    private int value;

    // constructor
    public Jumlah(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("Jumlah harus >= 1");
        }
        this.value = value;
    }

    // getter
    public int getValue() {return value;}
}
