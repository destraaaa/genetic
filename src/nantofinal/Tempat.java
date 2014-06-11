package nantofinal;

public class Tempat {
    
    private String nama;
    private int[][] atributPlus; //money[0][0], strange[1][0], charm[2][0], brain[3][0]
    private int energiMin;
    private char[] jadwal;
    
    //konstruktor
    public Tempat(){
        
    }
    public Tempat(String _nama, int _energiMin){
        nama = _nama;
        atributPlus = new int[4][1];
        for(int i = 0; i<4; i++){
            atributPlus[i][0]=0;
        }
        switch (_nama) {
            case "Mall":
                atributPlus[0][0]=10000;
                break;
            case "Gymnasium":
                atributPlus[1][0]=2;
                break;
            case "Cafe":
                atributPlus[2][0]=2;
                break;
            case "University":
                atributPlus[3][0]=3;
                break;
        }
        energiMin = _energiMin;
        jadwal = new char[84];
    }
    
    //Getter & Setter
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public int getEnergiMin() {
        return energiMin;
    }
    public void setEnergiMin(int energiMin) {
        this.energiMin = energiMin;
    }
    public char[] getJadwal() {
        return jadwal;
    }
    public void setJadwal(char[] jadwal) {
        this.jadwal = jadwal;
    }
    public void setJadwal(int idx, char value){
        jadwal[idx] = value;
    }
}
