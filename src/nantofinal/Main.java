package nantofinal;

import com.sun.j3d.utils.applet.MainFrame;
import java.io.FileNotFoundException;
import java.util.Vector;
import static nantofinal.BouncingBall.mf;

public class Main {
    public static Vector<GARecord> g = new Vector<>();
    public static void main(String[] args) throws FileNotFoundException {
        
        Scheduler test = new Scheduler();
        GARecord.waktu = test.weeks;
        test.bacaInformasiUmum("InformasiUmum.txt");
        test.bacaJadwalKandidat("JadwalKandidat.txt");
        test.bacaJadwalTempat("JadwalTempat.txt");
        
        test.generate();
        
        int treshold = test.populasi.get(0).getFitness();
        for (int i = 0; i<4; i++){
            g.add(new GARecord());
            g.get(i).curPopulation[i] = test.populasi.get(i);
        }
        int counter = 0;
        
        //treshold jika dalam 20 generasi tidak ada perubahan nilai fitness tertinggi
        for(int i = 0; counter < 20; i++){
            test.iterate();
            if(treshold < test.populasi.get(0).getFitness()){
                treshold = test.populasi.get(0).getFitness();
                counter = 0;
                System.out.println(test.populasi.get(0).getFitness()+ " " + test.populasi.get(0).getKromosom());
            } else {
                counter++;
            }
        }
        
        test.printPopulation();
        System.out.println("Program Started");
        BouncingBall bb = new BouncingBall();
        bb.pass(test.listBarang, test.listKandidat, test.populasi.get(0).getKromosom(), 0, test.brainAwal, test.charmAwal, test.strengthAwal, test.modalUang);
        bb.addKeyListener(bb);
        mf = new MainFrame(bb, 1000, 500);
    }
}
