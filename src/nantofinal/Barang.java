package nantofinal;

public class Barang {
    
    private char kode;
    private String namaBarang;
    private int harga;
    private int restockPerHari;
    private int stockHariIni;
    
    //Konstruktor
    public Barang(){
        
    }
    public Barang(char _kode, int _harga, int _restockPerHari){
        kode = _kode;
        harga = _harga;
        restockPerHari = _restockPerHari;
    }
    
    //Getter & Setter
    public char getKode() {
        return kode;
    }
    public void setKode(char kode) {
        this.kode = kode;
    }
    public String getNamaBarang() {
        return namaBarang;
    }
    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
    public int getHarga() {
        return harga;
    }
    public void setHarga(int harga) {
        this.harga = harga;
    }
    public int getRestockPerHari() {
        return restockPerHari;
    }
    public void setRestockPerHari(int restockPerHari) {
        this.restockPerHari = restockPerHari;
    }
    public int getStockHariIni() {
        return stockHariIni;
    }
    public void setStockHariIni(int stockHariIni) {
        this.stockHariIni = stockHariIni;
    }
}
