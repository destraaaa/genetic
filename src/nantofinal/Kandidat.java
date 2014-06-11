package nantofinal;

public class Kandidat {
    
    private char kode;
    private int enlightmentPerJam;
    private int energiPerJam;
    private int maksimalJamPerHari;
    private int udahBerapaJam;
    private String prerequisite;
    private int strength;
    private int charm;
    private int brain;
    private char[] jadwal;
    
    //Konstruktor
    public Kandidat(){
        
    }
    
    public Kandidat(char _kode,
            int _enlightmentPerJam,
            int _energiPerJam,
            int _maksimalJam,
            String _prerequisite,
            int _strength,
            int _charm,
            int _brain){
        
        this.kode = _kode;
        this.enlightmentPerJam = _enlightmentPerJam;
        this.energiPerJam = _energiPerJam;
        this.maksimalJamPerHari = _maksimalJam;
        this.prerequisite = _prerequisite;
        this.strength = _strength;
        this.charm = _charm;
        this.brain = _brain;
        jadwal = new char[84];
        
    }
    
    //Getter & Setter
    public int getMaksimalJamPerHari() {
        return maksimalJamPerHari;
    }
    public void setMaksimalJamPerHari(int maksimalJamPerHari) {
        this.maksimalJamPerHari = maksimalJamPerHari;
    }
    public int getUdahBerapaJam() {
        return udahBerapaJam;
    }
    public void setUdahBerapaJam(int udahBerapaJam) {
        this.udahBerapaJam = udahBerapaJam;
    }
    public String getPrerequisite() {
        return prerequisite;
    }
    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getCharm() {
        return charm;
    }
    public void setCharm(int charm) {
        this.charm = charm;
    }
    public int getBrain() {
        return brain;
    }
    public void setBrain(int brain) {
        this.brain = brain;
    }
    public char[] getJadwal() {
        return jadwal;
    }
    public void setJadwal(char[] jadwal) {
        this.jadwal = jadwal;
    }
    public char getKode() {
        return kode;
    }
    public void setKode(char kode) {
        this.kode = kode;
    }
    public int getEnlightmentPerJam() {
        return enlightmentPerJam;
    }
    public void setEnlightmentPerJam(int enlightmentPerJam) {
        this.enlightmentPerJam = enlightmentPerJam;
    }
    public int getEnergiPerJam() {
        return energiPerJam;
    }
    public void setEnergiPerJam(int energiPerJam) {
        this.energiPerJam = energiPerJam;
    }
    public void setJadwal(int idx, char value){
        jadwal[idx] = value;
    }
}
