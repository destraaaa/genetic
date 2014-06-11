package nantofinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import java.util.Random;
import java.util.Vector;

public class UI extends JFrame {
    
    public Vector<Barang> listB;
    public Vector<Kandidat> listK;
    public String kromosom; //string valid
    private ImageImplement kandidat[], barang, mall, gym, cafe, univ;
    private Stopwatch sw;
    private JPanel panel;
    private JTextField namaHari;
    private JTextField day;
    private JTextField hour;
    private JTextField minute;
    private JTextField second;
    private JButton Start;
    private JLabel lblWaktu;
    private JLabel lblEnlighment;
    private JTextField enlighment;
    private JLabel nanto;
    private JLabel lblNanto;
    private JTextArea aksi;
    private Random rand;
    public int amountEn, amountBr, amountCh, amountSt, amountMn, prevhour;
    public int amountEnAw, amountBrAw, amountChAw, amountStAw, amountMnAw, prevhourAw;
    private JTextField charm;
    private JLabel lblBrain;
    private JTextField brain;
    private JLabel lblMoney;
    private JTextField money;
    private JTextField strength;
    private JLabel lblStrength;
    
    public UI (Vector<Barang> b, Vector <Kandidat> k,String _kr, int _en, int _br, int _ch, int _st, int _mn) {
        
        listB = b; listK = k;
        inisiasi(_kr, _en, _br, _ch, _st, _mn);
        setBackground(new Color(0, 191, 255));
        sw = new Stopwatch(this);
        
        panel = new JPanel();
        panel.setBackground(new Color(0, 206, 209));
        panel.setLayout(null);
        
        namaHari = new JTextField();
        namaHari.setSize(139, 50);
        namaHari.setLocation(39, 75);
        namaHari.setFont(new Font("Miriam Fixed", Font.BOLD, 28));
        namaHari.setBackground(new Color(0, 206, 209));
        day = new JTextField();
        day.setFont(new Font("Miriam Fixed", Font.BOLD, 28));
        day.setBackground(new Color(0, 206, 209));
        hour = new JTextField();
        hour.setFont(new Font("Miriam Fixed", Font.BOLD, 28));
        hour.setBackground(new Color(0, 206, 209));
        minute = new JTextField();
        minute.setFont(new Font("Miriam Fixed", Font.BOLD, 28));
        minute.setBackground(new Color(0, 206, 209));
        second = new JTextField();
        day.setBounds(120, 30, 44, 40);
        day.setEditable(false);
        day.setText("01");
        hour.setBounds(285, 65, 40, 40);
        hour.setEditable(false);
        hour.setText("09");
        minute.setBounds(346, 65, 44, 40);
        minute.setEditable(false);
        minute.setText("59");
        
        Start = new JButton("Start");
        Start.setForeground(new Color(0, 0, 0));
        Start.setBackground(new Color(0, 206, 209));
        Start.setFont(new Font("Miriam Fixed", Font.BOLD, 12));
        Start.setBounds(587, 11, 86, 40);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (sw.getAlive()) {
                    Start.setText("Start");
                    sw.Stop();
                } else {
                    Start.setText("Stop");
                    sw.Start();
                }
            }
        });
        
        
        panel.add(namaHari);
        panel.add(day);
        panel.add(hour);
        panel.add(minute);
        
        panel.add(Start);
        
        
        //gambar
        kandidat = new ImageImplement[4];
        for (int i=0; i<4; i++){
            kandidat[i] = new ImageImplement(Toolkit.getDefaultToolkit().getImage("kandidat"+(i+1)+".png"));
            kandidat[i].setLocation(440, 238);
            panel.add(kandidat[i]);
            kandidat[i].setSize(217,191);
            kandidat[i].setVisible(false);
        }
        
        
        barang = new ImageImplement(Toolkit.getDefaultToolkit().getImage("barang.png"));
        barang.setLocation(440, 238);
        panel.add(barang);
        barang.setSize(217,191);
        barang.setLayout(null);
        barang.setVisible(false);
        
        cafe = new ImageImplement(Toolkit.getDefaultToolkit().getImage("cafe.jpg"));
        cafe.setLocation(440, 238);
        panel.add(cafe);
        cafe.setSize(217,191);
        cafe.setVisible(false);
        
        gym = new ImageImplement(Toolkit.getDefaultToolkit().getImage("Gym.jpg"));
        gym.setLocation(440, 238);
        panel.add(gym);
        gym.setSize(217,191);
        gym.setVisible(false);
        
        mall = new ImageImplement(Toolkit.getDefaultToolkit().getImage("mall.jpg"));
        mall.setLocation(440, 238);
        panel.add(mall);
        mall.setSize(217,191);
        mall.setVisible(false);
        
        univ = new ImageImplement(Toolkit.getDefaultToolkit().getImage("university.jpg"));
        univ.setLocation(440, 238);
        panel.add(univ);
        univ.setSize(217,191);
        univ.setVisible(false);
        
        getContentPane().add(panel);
        
        JLabel lblDay = new JLabel("Day");
        lblDay.setFont(new Font("Miriam Fixed", Font.BOLD, 28));
        lblDay.setBounds(50, 19, 92, 60);
        panel.add(lblDay);
        
        JLabel label = new JLabel(":");
        label.setFont(new Font("Miriam Fixed", Font.BOLD, 20));
        label.setBounds(329, 71, 21, 27);
        panel.add(label);
        
        lblWaktu = new JLabel("Waktu");
        lblWaktu.setFont(new Font("Miriam Fixed", Font.BOLD, 28));
        lblWaktu.setBounds(285, 11, 101, 51);
        panel.add(lblWaktu);
        
        lblEnlighment = new JLabel("Enlightenment :");
        lblEnlighment.setFont(new Font("Miriam Fixed", Font.BOLD, 18));
        lblEnlighment.setBounds(10, 474, 205, 52);
        panel.add(lblEnlighment);
        
        enlighment = new JTextField(""+amountEnAw);
        enlighment.setFont(new Font("Tahoma", Font.PLAIN, 11));
        enlighment.setForeground(new Color(0, 0, 0));
        enlighment.setBackground(new Color(0, 204, 204));
        enlighment.setBounds(199, 488, 131, 27);
        panel.add(enlighment);
        enlighment.setColumns(10);
        
        nanto = new JLabel("");
        nanto.setIcon(new ImageIcon("nanto.png"));
        nanto.setBounds(26, 228, 205, 201);
        panel.add(nanto);
        
        lblNanto = new JLabel("Nanto");
        lblNanto.setFont(new Font("Miriam Fixed", Font.BOLD | Font.ITALIC, 20));
        lblNanto.setBounds(77, 174, 75, 60);
        panel.add(lblNanto);
        
        aksi = new JTextArea();
        aksi.setLineWrap(true);
        aksi.setAlignmentX(CENTER_ALIGNMENT);
        aksi.setFont(new Font("Miriam Fixed", Font.BOLD | Font.ITALIC, 16));
        aksi.setBackground(new Color(0, 206, 209));
        aksi.setBounds(219, 301, 211, 64);
        panel.add(aksi);
        aksi.setColumns(10);
        
        JLabel lblCharm = new JLabel("Charm\t\t:");
        lblCharm.setFont(new Font("Miriam Fixed", Font.BOLD, 18));
        lblCharm.setBounds(10, 519, 173, 32);
        panel.add(lblCharm);
        
        charm = new JTextField(""+amountChAw);
        charm.setFont(new Font("Tahoma", Font.PLAIN, 11));
        charm.setBackground(new Color(0, 204, 204));
        charm.setBounds(199, 526, 131, 25);
        panel.add(charm);
        charm.setColumns(10);
        
        lblBrain = new JLabel("Brain:");
        lblBrain.setFont(new Font("Miriam Fixed", Font.BOLD, 18));
        lblBrain.setBounds(10, 551, 173, 32);
        panel.add(lblBrain);
        
        brain = new JTextField(""+amountBrAw);
        brain.setFont(new Font("Tahoma", Font.PLAIN, 11));
        brain.setColumns(10);
        brain.setBackground(new Color(0, 204, 204));
        brain.setBounds(199, 558, 131, 25);
        panel.add(brain);
        
        lblMoney = new JLabel("Money:");
        lblMoney.setFont(new Font("Miriam Fixed", Font.BOLD, 18));
        lblMoney.setBounds(407, 519, 173, 32);
        panel.add(lblMoney);
        
        money = new JTextField(""+amountMnAw);
        money.setFont(new Font("Tahoma", Font.PLAIN, 11));
        money.setColumns(10);
        money.setBackground(new Color(0, 204, 204));
        money.setBounds(526, 524, 131, 25);
        panel.add(money);
        
        strength = new JTextField(""+amountStAw);
        strength.setFont(new Font("Tahoma", Font.PLAIN, 11));
        strength.setColumns(10);
        strength.setBackground(new Color(0, 204, 204));
        strength.setBounds(526, 556, 131, 25);
        panel.add(strength);
        
        lblStrength = new JLabel(""+"Strength :");
        lblStrength.setFont(new Font("Miriam Fixed", Font.BOLD, 18));
        lblStrength.setBounds(407, 551, 173, 32);
        panel.add(lblStrength);
        
        rand = new Random();
        prevhour = -1;
        amountEn = 0;
        
        setTitle("TakeMeOutNanto!!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(689, 634);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
    }
    
    public void Update() {
        
        if (sw.getDay()%7==1) namaHari.setText("Senin");
        else if(sw.getDay()%7==2) namaHari.setText("Selasa");
        else if(sw.getDay()%7==3) namaHari.setText("Rabu");
        else if(sw.getDay()%7==4) namaHari.setText("Kamis");
        else if(sw.getDay()%7==5) namaHari.setText("Jum'at");
        else if(sw.getDay()%7==6) namaHari.setText("Sabtu");
        else if(sw.getDay()%7==0) namaHari.setText("Minggu");
        if (sw.getDay()<10) day.setText("0"+sw.getDay());
        else day.setText(""+sw.getDay());
        if (sw.getHour()<10) hour.setText("0"+sw.getHour());
        else hour.setText(""+sw.getHour());
        if (sw.getMinute()<10) minute.setText("0"+sw.getMinute());
        else minute.setText(""+sw.getMinute());
        
    }
    
    public void picUpdate() {
        for (int i=0; i<4; i++){
            kandidat[i].setVisible(false);
        }
        barang.setVisible(false);
        mall.setVisible(false);
        gym.setVisible(false);
        cafe.setVisible(false);
        univ.setVisible(false);
        if(sw.allHour < kromosom.length()){
            validate(kromosom.charAt(sw.allHour), sw.allHour);
        } else if (sw.allHour == kromosom.length()){
            sw.Pause();
            Start.setText("Reset");
            //backToStart();
        }
    }
    
    void validate(char X, int indeks){
        if (X>=49&&X<=57) {
            int j = rand.nextInt(4);
            kandidat[j].setVisible(true);
            aksi.setText("ngedate kandidat "+ X);
            
        } else if (X>=65&&X<=90) {
            barang.setVisible(true);
            aksi.setText("lagi beli barang");
            
        } else if (X=='m'){
            mall.setVisible(true);
            aksi.setText("lagi ke mall");
            
        }else if (X=='g'){
            gym.setVisible(true);
            aksi.setText("lagi ke gym");
        }else if (X=='c') {
            cafe.setVisible(true);
            aksi.setText("lagi ke cafe");
            
        }else if (X=='u'){
            univ.setVisible(true);
            aksi.setText("lagi ke kampus");
        }else if (X=='0') {
            aksi.setText("lagi gabut");
        }
        addAtribut(sw.allHour, X);
        
    }
    
    void addAtribut(int hournow, char X){
        if (hournow != prevhour){
            if (X>=49&&X<=57) {
                //jumlah enlightmentnya dibaca dr atribut kandidat, lewat char X
                for(int i = 0; i < listK.size(); i++){
                    if(listK.get(i).getKode() == X){
                        amountEn += listK.get(i).getEnlightmentPerJam();
                        break;
                    }
                }
            }else if (X>=65&&X<=90){
                //jumlah uangnya dibaca dr atribut barang, lewat char X
                for(int i = 0; i < listB.size(); i++){
                    if(listB.get(i).getKode() == X){
                        amountMn -= listB.get(i).getHarga();
                        break;
                    }
                }
            }else if (X=='m'){
                amountMn += 10000;
            }else if (X=='g'){
                amountSt += 2;
            }else if (X=='c') {
                amountCh += 2;
            }else if (X=='u'){
                amountBr += 3;
            }
            
        }
        enlighment.setText(Integer.toString(amountEn));
        money.setText(Integer.toString(amountMn));
        strength.setText(Integer.toString(amountSt));
        charm.setText(Integer.toString(amountCh));
        brain.setText(Integer.toString(amountBr));
        prevhour = hournow;
    }
    
    public void inisiasi(String _kr, int _en, int _br, int _ch, int _st, int _mn){
        kromosom = _kr;
        amountEn = _en; amountEnAw = _en;
        amountBr = _br; amountBrAw = _br;
        amountCh = _ch; amountChAw = _ch;
        amountSt = _st; amountStAw = _st;
        amountMn = _mn; amountMnAw = _mn;
    }
    
    public void backToStart(){
        amountEn = amountEnAw;
        amountBr = amountBrAw;
        amountCh = amountChAw;
        amountSt = amountStAw;
        amountMn = amountEnAw;
        aksi.setText("");
        for (int i=0; i<4; i++){
            kandidat[i].setVisible(false);
        }
        barang.setVisible(false);
        mall.setVisible(false);
        gym.setVisible(false);
        cafe.setVisible(false);
        univ.setVisible(false);
        hour.setText("09");
        minute.setText("59");
        enlighment.setText(Integer.toString(amountEnAw));
        money.setText(Integer.toString(amountMnAw));
        strength.setText(Integer.toString(amountStAw));
        charm.setText(Integer.toString(amountChAw));
        brain.setText(Integer.toString(amountBrAw));
    }
    
    public static void main(String[] args) {
        //new UI("3Mu0R11gm24mAc042m2", 0, 10, 10, 10, 10000);
    }
}