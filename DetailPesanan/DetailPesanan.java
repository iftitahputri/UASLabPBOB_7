package DetailPesanan;
import Menu.MenuItem;

public class DetailPesanan { 
    private MenuItem item;      
    private Jumlah jumlah;
    private Catatan catatan;
    private Subtotal subtotal;

    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = new Jumlah(jumlah);
        this.catatan = new Catatan(catatan);
        this.subtotal = new Subtotal(item, this.jumlah);
    }

    public MenuItem getItem() { 
        return item; 
    }
    public Jumlah getJumlah() { 
        return jumlah; 
    }
    public Catatan getCatatan() { 
        return catatan; 
    }
    public Subtotal getSubtotal() { 
        return subtotal; 
    }

    @Override
    public String toString() {
        if (catatan.isEmpty()) {
            return item.getNama() + " x " + jumlah.getValue();
        } else {
            return item.getNama() + " x " + jumlah.getValue() + " (" + catatan.getKeterangan() + ")";
        }
    }
}
