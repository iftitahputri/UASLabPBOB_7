public class DetailPesanan {
    private MenuItem item;
    private int jumlah;
    private String catatan;

    public DetailPesanan(MenuItem item, int jumlah, String catatan){
        this.item = item;
        this.jumlah = jumlah;
        this.catatan = catatan;
    }

    public MenuItem getItem(){
        return item;
    }

    public int getJumlah(){
        return jumlah;
    }

    public String getCatatan(){
        return catatan;
    }

    public double getSubtotal(){
        return item.getHarga() * jumlah;
    }

    @Override
    public String toString(){
        if (catatan == null || catatan.isEmpty()){
            return item.getNama() + "x" + jumlah;
        }else{
            return item.getNama() + "x" + jumlah + "(" + catatan + ")";
        }  
    }
}
