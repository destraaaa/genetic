package nantofinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Scheduler {
    
    //private class
    private class Fixer {
        public int idxInvalid;
        public String possible;
    }
    
    //Container
    public Vector<Barang> listBarang;
    public Vector<Kandidat> listKandidat;
    public Vector<Tempat> listTempat;
    public int idxCross[][] = new int[4][3];
    public int idxMutated[] = new int[16];
    public int idxSelected[] = new int[4];
    
    
    //Informasi umum soal
    public int modalUang;
    public int weeks;
    public int energiPerDay;
    public int strengthAwal;
    public int charmAwal;
    public int brainAwal;
    
    //Genetic Algorithm
    public Vector<Individu> populasi = new Vector<Individu>();
    
    /* INISIASI */
    
    //baca informasi umum soal
    public void bacaInformasiUmum(String filename) throws FileNotFoundException {
        File file = new File(filename);
        try (Scanner sc = new Scanner(file)) {
            int modalUang = sc.nextInt();
            int waktu = sc.nextInt();
            int energi = sc.nextInt();
            int strengthAwal = sc.nextInt();
            int charmAwal = sc.nextInt();
            int brainAwal = sc.nextInt();
            
            int jumlahKandidat = sc.nextInt();
            listKandidat = new Vector<Kandidat>();
            for (int i=0; i<jumlahKandidat; i++) {
                char id = (char) (i+1+48);
                int enlightmentBuatNanto = sc.nextInt();
                int energy = sc.nextInt();
                int max = sc.nextInt();
                String kode = sc.next();
                if(kode.equals("-")) {
                    kode = "";
                }
                int strength = sc.nextInt();
                int charm = sc.nextInt();
                int brain = sc.nextInt();
                listKandidat.add(new Kandidat(id,enlightmentBuatNanto,energy,max,kode,strength,charm,brain));
            }
            
            int jumlahBarang = sc.nextInt();
            listBarang = new Vector<Barang>();
            for (int i=0; i<jumlahBarang; i++) {
                String kode = sc.next();
                int harga = sc.nextInt();
                int restock = sc.nextInt();
                listBarang.add(new Barang(kode.charAt(0), harga, restock));
            }
            
            this.modalUang = modalUang;
            this.weeks = waktu;
            this.energiPerDay = energi;
            this.strengthAwal = strengthAwal;
            this.charmAwal = charmAwal;
            this.brainAwal = brainAwal;
        }
    }
    
    //baca jadwal kandidat
    public void bacaJadwalKandidat(String filename) throws FileNotFoundException {
        File file = new File(filename);
        try (Scanner sc = new Scanner(file)) {
            int jumlahKandidat = listKandidat.size();
            for (int i=0; i<jumlahKandidat; i++) {
                String jadwal = sc.next();
                int hari = 0;
                int jam = 0;
                for (int j=0; j<jadwal.length(); j++) {
                    if (jadwal.charAt(j)=='1'){
                        listKandidat.elementAt(i).setJadwal((hari*12)+jam, '1');
                    } else {
                        listKandidat.elementAt(i).setJadwal((hari*12)+jam, '0');
                    }
                    if ((j+1)%12 == 0) {
                        hari++;
                        jam = 0;
                    } else {
                        jam++;
                    }
                }
            }
        }
    }
    
    //baca jadwal tempat
    public void bacaJadwalTempat(String filename) throws FileNotFoundException {
        //instansiasi list tempat dulu
        listTempat = new Vector<Tempat>();
        Tempat m = new Tempat("Mall", 8); listTempat.add(m);
        Tempat g = new Tempat("Gymnasium", 12); listTempat.add(g);
        Tempat c = new Tempat("Cafe", 6); listTempat.add(c);
        Tempat u = new Tempat("University", 15); listTempat.add(u);
        
        File file = new File(filename);
        try (Scanner sc = new Scanner(file)) {
            int jumlahTempat = 4;
            for (int i=0; i<jumlahTempat; i++) {
                String jadwal = sc.nextLine();
                int hari = 0;
                int jam = 0;
                for (int j=0; j<jadwal.length(); j++) {
                    if (jadwal.charAt(j)=='1'){
                        listTempat.elementAt(i).setJadwal((hari*12)+jam, '1');
                    } else {
                        listTempat.elementAt(i).setJadwal((hari*12)+jam, '0');
                    }
                    if ((j+1)%12 == 0) {
                        hari++;
                        jam = 0;
                    } else {
                        jam++;
                    }
                }
            }
        }
    }
    
    /* UTILITY METHOD */
    //randomizer
    public String randomizer(Individu individu){
        String result = "";
        
        for(int i = 0; i < individu.getKromosom().length(); i++){
            if(i != validator(individu).idxInvalid){
                result += individu.getKromosom().charAt(i);
            } else {
                //System.out.println(validator(individu).possible);
                result += validator(individu).possible.charAt((int)(Math.random() * validator(individu).possible.length()));
            }
        }
        
        return result;
    }
    
    //validator
    public Fixer validator(Individu individu){
        Fixer result = new Fixer();
        String possible = "";
        
        result.idxInvalid = -1;
        
        boolean found = false;
        
        for(int i = 0; i < individu.getKromosom().length() && !found; i++){
            //inisiasi awal string
            if(i == 0){
                individu.bag = new String();
                
                individu.meeting = new int[listKandidat.size()];
                for(int j = 0; j < individu.meeting.length; j++){
                    individu.meeting[j] = 0;
                }
                
                individu.money = modalUang;
                individu.str = strengthAwal;
                individu.brn = brainAwal;
                individu.crm = charmAwal;
                individu.enlight = 0;
            }
            
            //restoration per day
            if(i % 12 == 0){
                individu.energy = energiPerDay;
                
                for(int j = 0; j < individu.meeting.length; j++){
                    individu.meeting[j] = 0;
                }
                
                for(int j = 0; j < listBarang.size(); j++){
                    listBarang.get(j).setStockHariIni(listBarang.get(j).getRestockPerHari());
                }
            }
            
            if(individu.getKromosom().charAt(i) >= 49 && individu.getKromosom().charAt(i) <= 57){
                for(int j = 0; j < listKandidat.size(); j++){
                    if(listKandidat.get(j).getKode() == individu.getKromosom().charAt(i) &&
                            (listKandidat.get(j).getJadwal()[i%84] == '0'
                            || listKandidat.get(j).getEnergiPerJam() > individu.energy
                            || listKandidat.get(j).getMaksimalJamPerHari() < individu.meeting[j]
                            || listKandidat.get(j).getStrength() > individu.str
                            || listKandidat.get(j).getBrain() > individu.brn
                            || listKandidat.get(j).getCharm() > individu.crm
                            || !cekBarang(individu.bag, listKandidat.get(j).getPrerequisite()))){
                        found = true;
                        result.idxInvalid = i;
                    } else if (listKandidat.get(j).getKode() == individu.getKromosom().charAt(i)){
                        individu.enlight += listKandidat.get(j).getEnlightmentPerJam();
                        
                        individu.energy -= listKandidat.get(j).getEnergiPerJam();
                        individu.meeting[j] += 1;
                        
                        individu.bag = removeItems(individu.bag, listKandidat.get(j).getPrerequisite());
                    }
                }
            } else if(individu.getKromosom().charAt(i) >= 65 && individu.getKromosom().charAt(i) <= 90){
                for(int j = 0; j < listBarang.size() && !found; j++){
                    if(individu.getKromosom().charAt(i) == listBarang.get(j).getKode()){
                        if(listBarang.get(j).getHarga() > individu.money || listBarang.get(j).getStockHariIni() < 1){
                            found = true;
                            result.idxInvalid = i;
                        } else {
                            individu.money -= listBarang.get(j).getHarga();
                            individu.bag += listBarang.get(j).getKode();
                            listBarang.get(j).setStockHariIni(listBarang.get(j).getStockHariIni()-1);
                        }
                    }
                }
            } else if(individu.getKromosom().charAt(i) == 'g') {
                if(individu.energy < 12 || listTempat.get(1).getJadwal()[i%84] == '0'){
                    found = true;
                    result.idxInvalid = i;
                } else {
                    individu.str += 2;
                    individu.energy -= 12;
                }
            } else if(individu.getKromosom().charAt(i) == 'c') {
                if(individu.energy < 6 || listTempat.get(2).getJadwal()[i%84] == '0'){
                    found = true;
                    result.idxInvalid = i;
                } else {
                    individu.crm += 2;
                    individu.energy -= 6;
                }
            } else if(individu.getKromosom().charAt(i) == 'm') {
                if(individu.energy < 8 || listTempat.get(0).getJadwal()[i%84] == '0'){
                    found = true;
                    result.idxInvalid = i;
                } else {
                    individu.money += 10000;
                    individu.energy -= 8;
                }
            } else if(individu.getKromosom().charAt(i) == 'u') {
                if(individu.energy < 15 || listTempat.get(3).getJadwal()[i%84] == '0'){
                    found = true;
                    result.idxInvalid = i;
                } else {
                    individu.brn += 3;
                    individu.energy -= 15;
                }
            }
        }
        
        
        //possible option
        if(result.idxInvalid != -1){
            for(int i = 0; i < listKandidat.size(); i++){
                if(listKandidat.get(i).getJadwal()[result.idxInvalid%84] == '1'
                        && listKandidat.get(i).getEnergiPerJam() <= individu.energy
                        && listKandidat.get(i).getMaksimalJamPerHari() >= individu.meeting[i]
                        && listKandidat.get(i).getStrength() <= individu.str
                        && listKandidat.get(i).getBrain() <= individu.brn
                        && listKandidat.get(i).getCharm() <= individu.crm
                        && cekBarang(individu.bag, listKandidat.get(i).getPrerequisite())){
                    possible += listKandidat.get(i).getKode();
                }
            }
            
            for(int i = 0; i < listKandidat.size(); i++){
                if(!cekBarang(individu.bag, listKandidat.get(i).getPrerequisite())){
                    for(int j = 0; j < listKandidat.get(i).getPrerequisite().length(); j++){
                        if(!cekBarang(individu.bag, listKandidat.get(i).getPrerequisite().substring(j, j+1))){
                            for(int k = 0; k < listBarang.size(); k++){
                                if(listBarang.get(k).getKode() == listKandidat.get(i).getPrerequisite().charAt(j) && listBarang.get(k).getHarga() <= individu.money){
                                    possible += listBarang.get(k).getKode();
                                }
                            }
                        }
                    }
                }
            }
            
            int maxSTR = 0;
            int maxBRN = 0;
            int maxCRM = 0;
            
            for(int i = 0; i < listKandidat.size(); i++){
                if(i == 0){
                    maxSTR = listKandidat.get(i).getStrength();
                    maxBRN = listKandidat.get(i).getBrain();
                    maxCRM = listKandidat.get(i).getCharm();
                } else {
                    if(maxSTR < listKandidat.get(i).getStrength())
                        maxSTR = listKandidat.get(i).getStrength();
                    if(maxBRN < listKandidat.get(i).getBrain())
                        maxBRN = listKandidat.get(i).getBrain();
                    if(maxCRM < listKandidat.get(i).getCharm())
                        maxCRM = listKandidat.get(i).getCharm();
                    
                }
            }
            
            //System.out.println(maxSTR);
            //System.out.println(maxBRN);
            //System.out.println(maxCRM);
            
            if(individu.str < maxSTR && individu.energy >= 12 && listTempat.get(1).getJadwal()[result.idxInvalid%84] == '1'){
                possible += 'g';
            }
            if(individu.crm < maxCRM && individu.energy >= 6 && listTempat.get(2).getJadwal()[result.idxInvalid%84] == '1'){
                possible += 'c';
            }
            if(individu.energy >= 8 && listTempat.get(0).getJadwal()[result.idxInvalid%84] == '1'){
                possible += 'm';
            }
            if(individu.brn < maxBRN && individu.energy >= 15 && listTempat.get(3).getJadwal()[result.idxInvalid%84] == '1'){
                possible += 'u';
            }
            
            if(possible == "")
                possible += '0';
            
            result.possible = possible;
        }
        
        return result;
    }
    
    //ngebuang barang dari tas
    public String removeItems(String bag, String prerequisite){
        String result = new String();
        String removed = "";
        
        for(int j = 0; j < prerequisite.length(); j++){
            for(int k = 0; k < bag.length(); k++){
                if(bag.charAt(k) == prerequisite.charAt(j) && !removed.contains(bag.subSequence(k, k+1))){
                    removed += bag.charAt(k);
                    StringBuilder sb = new StringBuilder(bag);
                    sb.deleteCharAt(k);
                    bag = sb.toString();
                }
            }
        }
        
        result = new String(bag);
        
        return result;
    }
    
    //cek barang di tas dengan prerequisite
    public boolean cekBarang(String bag, String prerequisite){
        boolean fulfilled = true;
        
        for(int i = 0; i < prerequisite.length(); i++){
            if(bag.contains(prerequisite.subSequence(i, i+1))){
                fulfilled = fulfilled && true;
            } else {
                fulfilled = fulfilled && false;
            }
        }
        
        return fulfilled;
    }
    
    //"serang" kromosom
    /**
     * Terinspirasi dari simulated annealing. Kita geser/serang state sekarang secara random
     * dengan tujuan dapet lagi yang lebih baik.
     * Fungsi ini juga ngebantu ngurangin frekuensi aksi '0'
     */
    private String attackKromosom(String kromosom){
        String result = "";
        
        for(int i = 0; i < kromosom.length(); i++){
            if(Math.random() < 0.5 || kromosom.charAt(i) == '0'){
                result += listKandidat.get((int)(Math.random()*listKandidat.size())).getKode();
            } else {
                result += kromosom.charAt(i);
            }
        }
        
        return result;
    }
    
    /* GENETIC ALGORITHM */
    //generate
    public void generate(){
        for(int i = 0; i < 4; i++){
            Individu individu = new Individu();
            
            char[] krom = new char[84*weeks];
            for(int j = 0; j < krom.length; j++){
                krom[j] = listKandidat.get((int)(Math.random() * listKandidat.size())).getKode();
            }
            
            individu.setKromosom(new String(krom));
            
            populasi.add(individu);
        }
        
        mutation();
    }
    
    //selection
    /**
     * Memilih 4 terbaik. Dengan state akhir individu sudah terurut berdasarkan  nilai fitness
     * fitness paling tinggi adalah individu 1
     */
    public void selection(){
        Individu individu1 = new Individu();
        Individu individu2 = new Individu();
        Individu individu3 = new Individu();
        Individu individu4 = new Individu();
        
        for(int i = 0; i < 4; i++){
            int max = 0;
            int idx = 0;
            for(int j = 0; j < populasi.size(); j++){
                if(j == 0){
                    max = populasi.get(j).getFitness();
                    idx = j;
                } else if(max < populasi.get(j).getFitness()){
                    max = populasi.get(j).getFitness();
                    idx = j;
                }
                
            }
            idxSelected[i] = idx;
            if(i == 0){
                individu1 = populasi.get(idx);
                Vector<Individu> newPopulation = new Vector<Individu>();
                for(int j = 0; j < populasi.size(); j++){
                    if(j != idx)
                        newPopulation.add(populasi.get(j));
                }
                populasi = new Vector<Individu>();
                for(int j = 0; j < newPopulation.size(); j++){
                    populasi.add(newPopulation.get(j));
                }
            } else if(i == 1){
                individu2 = populasi.get(idx);
                Vector<Individu> newPopulation = new Vector<Individu>();
                for(int j = 0; j < populasi.size(); j++){
                    if(j != idx)
                        newPopulation.add(populasi.get(j));
                }
                populasi = new Vector<Individu>();
                for(int j = 0; j < newPopulation.size(); j++){
                    populasi.add(newPopulation.get(j));
                }
            } else if(i == 2){
                individu3 = populasi.get(idx);
                Vector<Individu> newPopulation = new Vector<Individu>();
                for(int j = 0; j < populasi.size(); j++){
                    if(j != idx)
                        newPopulation.add(populasi.get(j));
                }
                populasi = new Vector<Individu>();
                for(int j = 0; j < newPopulation.size(); j++){
                    populasi.add(newPopulation.get(j));
                }
            } else if(i == 3){
                individu4 = populasi.get(idx);
                Vector<Individu> newPopulation = new Vector<Individu>();
                for(int j = 0; j < populasi.size(); j++){
                    if(j != idx)
                        newPopulation.add(populasi.get(j));
                }
                populasi = new Vector<Individu>();
                for(int j = 0; j < newPopulation.size(); j++){
                    populasi.add(newPopulation.get(j));
                }
            }
        }
        
        populasi = new Vector<Individu>();
        
        populasi.add(individu1);
        populasi.add(individu2);
        populasi.add(individu3);
        populasi.add(individu4);
    }
    
    //crossover
    private void crossover(){
        Individu individu1 = populasi.get(0);
        Individu individu2 = populasi.get(1);
        Individu individu3 = populasi.get(2);
        Individu individu4 = populasi.get(3);
        Vector<Individu> newPopulation = new Vector<Individu>();
        for(int i = 0; i < 4; i++){
            int l=0;
            for(int j = 0; j < 4; j++){
                if(i==j){
                    l++;
                }
                if(i != j){
                    Individu newIndividu = new Individu();
                    String kromosom = populasi.get(i).getKromosom();
                    int uniformRate = (int)(Math.random() * kromosom.length());
                    idxCross[i][j-l] = uniformRate;
                    for(int k = 0; k < kromosom.length(); k++){
                        if(k > uniformRate){
                            StringBuilder newKromosom = new StringBuilder(kromosom);
                            newKromosom.setCharAt(k, populasi.get(j).getKromosom().charAt(k));
                            kromosom = new String(newKromosom);
                        }
                    }
                    newIndividu.setKromosom(kromosom);
                    newPopulation.add(newIndividu);
                }
            }
        }
        
        populasi = new Vector<Individu>();
        populasi.add(individu1);
        populasi.add(individu2);
        populasi.add(individu3);
        populasi.add(individu4);
        for(int i = 0; i < newPopulation.size(); i++){
            validator(newPopulation.get(i));
            populasi.add(newPopulation.get(i));
        }
    }
    
    //mutation
    private void mutation(){
        for(int i = 0; i < populasi.size(); i++){
            while(validator(populasi.get(i)).idxInvalid != -1){
                populasi.get(i).setKromosom(randomizer(populasi.get(i)));
            }
            populasi.get(i).setFitness(populasi.get(i).enlight);
        }
        if(populasi.get(0).getFitness() == populasi.get(1).getFitness() && populasi.get(0).getFitness() == populasi.get(2).getFitness()
                && populasi.get(0).getFitness() == populasi.get(3).getFitness()){
            for(int i = 0; i < populasi.size()-1; i++){
                if(populasi.get(i).getFitness() == populasi.get(i+1).getFitness()){
                    populasi.get(i+1).setKromosom(attackKromosom(populasi.get(i+1).getKromosom()));
                    while(validator(populasi.get(i+1)).idxInvalid != -1){
                        populasi.get(i+1).setKromosom(randomizer(populasi.get(i+1)));
                    }
                    populasi.get(i+1).setFitness(populasi.get(i+1).enlight);
                }
            }
        }
    }
    
    /* ITERASI */
    /**
     * Ini bisa dipake buat di GUI...
     */
    
    public void iterate(){
        crossover();
        mutation();
        /** Bisa ambil populasi disini */
        selection();
        /** Sama disini (hasil seleksi) */
    }
    
    public void iterate1(){
        crossover();
        mutation();
    }
    
    /* DEBUG ONLY */
    public void printPopulation(){
        System.out.println("====== DEBUG ======");
        for(int i = 0; i < populasi.size(); i++){
            System.out.println("Populasi-"+(i+1));
            System.out.println(populasi.get(i).getKromosom());
            System.out.println("Fitness : " + populasi.get(i).getFitness());
            System.out.println("STR : " + populasi.get(i).str);
            System.out.println("CRM : " + populasi.get(i).crm);
            System.out.println("BRN : " + populasi.get(i).brn);
        }
    }
}
