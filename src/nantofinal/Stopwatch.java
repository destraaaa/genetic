package nantofinal;

public class Stopwatch {
    
    private UI UserInterface;   //class UI
    private int day;     //menyimpan nilai day
    private int hour;           //menyimpan nilai jam
    private int minute;         //menyimpan nilai menit
    private boolean active;     //true apabila tombol start dan resume ditekan, false apabila tombol stop dan pause ditekan.
    private boolean alive;      //true apabila tombol start ditekan, false apabila tombol stop ditekan.
    private Thread t;           //Thread untuk innerclass Timer
    public int allHour;
    
    public Stopwatch(UI ui) {
        day = 1;
        hour = 10;
        minute = 0;
        UserInterface = ui;
        allHour = 0;
    }
    
    public int getDay() {
        return day;
    }
    
    public int getHour() {
        return hour;
    }
    
    public int getMinute() {
        return minute;
    }
    
    public boolean getActive() {
        return active;
    }
    
    public boolean getAlive() {
        return alive;
    }
    
    private void nextMiliSecond() {
        /* fungsi ini akan menambahkan 1 milisecond tiap kali dipanggil serta
         * memanggil fungsi update pada user interface untuk menampilkan waktu stopwatch.
         * fungsi ini pun akan menambahkan nilai second, minute, hour apabila diperlukan.
         * contoh : pada saat minute bernilai 60, maka hour akan ditambahkan 1 */
        //Lengkapi
        if (minute==0)
            UserInterface.picUpdate();
        if (minute==59)
        {
            if (hour==21)
            {
                day++;
                hour=9;
                UserInterface.picUpdate();
            }
            else {hour++; allHour++; minute = 0; UserInterface.picUpdate();}
        }
        else {minute++;}
        
        UserInterface.Update();
    }
    
    public String Print() {
        /* fungsi ini akan mengembalikan string dengan format hour:minute:second:milisecond
         * fungsi ini dipanggil ketika tombol Print ditekan */
        //Lengkapi
        String st;
        st = getDay()+":"+getHour()+":"+getMinute();
        return st;
        
    }
    
    
    public void Start() {
        /* fungsi ini akan memulai perhitungan waktu pada stopwatch dengan thread
         * serta mengubah nilai active dan alive pada stopwatch
         * fungsi ini dipanggil ketika tombol Start ditekan */
        //Lengkapi
        alive = true;
        active = true;
        t = new Thread(new Timer());
        t.start();
        
    }
    
    public void Stop() {
        /* fungsi ini akan menghentikkan proses pada thread, mengubah nilai active dan alive,
         * serta mereset kembali nilai hour, minute, second, dan milisecond
         * fungsi ini dipanggil ketika tombol Stop ditekan */
        //Lengkapi
        UserInterface.backToStart();
        UserInterface.prevhour = -1;
        alive = false;
        active = false;
        day = 1;
        hour = 10;
        minute = 0;
        allHour = 0;
        /*UserInterface.amountEn = 0; UserInterface.amountCh = 0; UserInterface.amountBr = 0;
         * UserInterface.amountSt = 0; UserInterface.amountMn = 0;*/
        //UserInterface.Update();
    }
    
    public void Pause() {
        /* fungsi ini akan mengubah nilai active
         * fungsi ini dipanggil ketika tombol Pause ditekan */
        //Lengkapi
        active = false;
    }
    
    public void Resume() {
        /* fungsi ini akan mengubah nilai active
         * fungsi ini dipanggil ketika tombol Resume ditekan */
        //Lengkapi
        active = true;
    }
    
    private class Timer implements Runnable//Lengkapi
    {
        @Override
        public void run()
        {
            while(alive==true)
            {
                while(active==true)
                {
                    nextMiliSecond();
                    try { Thread.sleep(20); } catch (Exception e) {}
                }
            }
        }
    }
}