package nantofinal;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.Timer;
public class BouncingBall extends Applet implements ActionListener, KeyListener {
    private Button go = new Button("Populate");
    private Button canc = new Button("Break");
    public static MainFrame mf;
    private TransformGroup objTrans;
    public BranchGroup objRoot;
    public SimpleUniverse u;
    public boolean isCreating = true;
    public boolean isMoving = false;
    //public Vector<GARecord> g = new Vector<>();
    public int it = 0;
    public int jt = 0;
    public int kt = 4;
    public int m[] = new int[16];
    public int selected[] = new int[4];
    public boolean started = false;
    public int iterasi1 = 0;
    public int iterasi2 = 4;
    public float yloc1,yloc2, endposY;
    public String state = "populating";
    private Transform3D trans = new Transform3D();
    private float height=0.0f;
    public Vector<Barang> barang = new Vector<>();
    public Vector<Kandidat> kandidat = new Vector<>();
    public String krom;
    public int br, ch, st, md;
    public final float width = 0.01f;
    private float sign = 1.0f; // going up or down
    private Timer timer;
    public final float initialPosX = -0.9f;
    public final int populat = 16;
    public final float initialPosY = -0.4f;
    public float startPosX[][] = new float[populat][84];
    public float startPosY[][] = new float[populat][84];
    public Transform3D t[][] = new Transform3D[populat][84];
    public Vector3f v[][] = new Vector3f[populat][84];
    public Box b[][] = new Box[populat][84];
    public TransformGroup tg[][] = new TransformGroup[populat][84];
    public Individu in[] = new Individu[populat];
    public float stopPos = 0.10f;
    private float xloc=0.0f;
    public Color getColorOfKrom(int k){
        if (k==0) return Color.BLUE;
        if (k==1) return Color.RED;
        if (k==2) return Color.YELLOW;
        else return Color.GREEN;
    }
    public void initPos(){
        startPosX[0][0] = initialPosX;
        startPosY[0][0] = initialPosY;
        for (int i=0; i<populat; i++){
            if (i!=0){
                startPosX[i][0] = initialPosX;
                startPosY[i][0] = startPosY[i-1][0] + 0.05f;
            }
            for (int j=1; j<84; j++){
                startPosX[i][j] = startPosX[i][j-1] + width;
                startPosY[i][j] = startPosY[i][j-1];
                //System.out.println(startPosX[i][j]+" "+startPosY[i][j]);
            }
            //System.out.println();
        }
    }
    public static Appearance getAppearance(Color color) {
        return getAppearance(new Color3f(color));
    }
    public static Appearance getAppearance(Color3f color) {
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Appearance ap = new Appearance();
        Texture texture = new Texture2D();
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
        Material mat = new Material(color, black, color, white, 70f);
        ap.setTextureAttributes(texAttr);
        ap.setMaterial(mat);
        ap.setTexture(texture);
        ColoringAttributes ca = new ColoringAttributes(color,
                ColoringAttributes.NICEST);
        ap.setColoringAttributes(ca);
        return ap;
    }
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        objRoot = new BranchGroup();
        objRoot.setCapability(BranchGroup.ALLOW_DETACH);
        objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f light1Color = new Color3f(1.0f, 0.0f, 0.2f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1
                = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);
        // Set up the ambient light
        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }
    public BouncingBall() {
        in[0] = new Individu();
        in[0].setKromosom("QWERTYUIOPASDFGHJKLZXCVBNM1234567890mgucQWERTYUIOPASDFGHJKLZXCVBNM1234567890mgcuQWER");
        in[1] = new Individu();
        in[1].setKromosom("MNBVCXZLKJHGFDSAPOIUYTREWQ1234567890mgucQWERTYUIOPASDFGHJKLZXCVBNM1234567890mgcuQWER");
        in[2] = new Individu();
        in[2].setKromosom("QWERTYUIOPASDFGHJKLZXCVBNM0987654321mgucQWERTYUIOPASDFGHJKLZXCVBNM1234567890mgcuQWER");
        in[3] = new Individu();
        in[3].setKromosom("MNBVCXZLKJHGFDSAPOIUYTREWQ1234567890mgucMNBVCXZLKJHGFDSAPOIUYTREWQ1234567890mgcuQWER");
        this.initPos();
        for (int n = 0; n<4; n++){
            Random rand = new Random();
            selected[n] = rand.nextInt(16);
        }
        //g.get(0).selectedIdx[0] = 3;
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);
        add("Center", c);
        c.addKeyListener(this);
        timer = new Timer(100,this);
        //timer.start();
        Panel p =new Panel();
        go.setSize(200, 20);
        canc.setSize(200, 20);
        p.add(go);
        p.add(canc);
        add("North",p);
        go.addActionListener(this);
        go.addKeyListener(this);
        canc.addActionListener(this);
        canc.addKeyListener(this);
        // Create a simple scene and attach it to the virtual universe
        BranchGroup scene = createSceneGraph();
        u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
    }
    public void keyPressed(KeyEvent e) {
        //Invoked when a key has been pressed.
        //if (e.getKeyChar()=='s') {xloc = xloc + .1f;}
        //if (e.getKeyChar()=='a') {xloc = xloc - .1f;}
    }
    public void keyReleased(KeyEvent e){
        // Invoked when a key has been released.
    }
    public void keyTyped(KeyEvent e){
        //Invoked when a key has been typed.
    }
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
        if (e.getSource()==go){
            if (!timer.isRunning()) {
                timer.start();
            }
        }
        else if (e.getSource() == canc){
            mf.setVisible(false);
            UI ui = new UI(barang, kandidat, krom, 0, br, ch, st, md); //individunya masih dummy data
        }
        else {
                if(state.equals("populating")){
                    objRoot.detach();
                    objRoot.removeAllChildren();
                    objTrans = new TransformGroup();
                    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                    // Create a simple shape leaf node, add it to the scene graph.
                    for (int i=0; i<4; i++){
                        for (int j=0; j<84; j++){
                            b[i][j] = new Box(width, width, width, Box.ENABLE_APPEARANCE_MODIFY,
                                    getAppearance(new Color3f(getColorOfKrom(i))));
                            b[i][j].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
                            t[i][j] = new Transform3D();
                            v[i][j] = new Vector3f(startPosX[i][j], startPosY[i][j], 0.0f);
                            t[i][j].setTranslation(v[i][j]);
                            tg[i][j] = new TransformGroup();
                            tg[i][j].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                            tg[i][j].setTransform(t[i][j]);
                            objRoot.detach();
                            tg[i][j].addChild(b[i][j]);
                            objRoot.addChild(tg[i][j]);
                            //objTrans.addChild(tg[i][j]);
                        }
                    }
                    u.addBranchGraph(objRoot);
                    if (timer.isRunning()) {
                        timer.stop();
                        go.setLabel("crossover");
                    }
                    state = "crossing";
                }
                else if(state.equals("crossing")){
                    if (iterasi2<16){
                        Random rand = new Random();
                        int n = rand.nextInt(84);
                        if (isCreating){
                            if (it==jt){
                                kt--;
                                jt++;
                            }
                            if(jt>=4){
                                jt = 0;
                                it++;
                            }
                            for (int j=0; j<n; j++){
                                b[iterasi2][j] = new Box(width, width, width, Box.ENABLE_APPEARANCE_MODIFY,
                                        getAppearance(new Color3f(getColorOfKrom(it))));
                                b[iterasi2][j].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
                                t[iterasi2][j] = new Transform3D();
                                v[iterasi2][j] = new Vector3f(startPosX[it][j], startPosY[it][j], 0.0f);
                                yloc1 = startPosY[it][j];
                                endposY = startPosY[iterasi2][j];
                                t[iterasi2][j].setTranslation(v[iterasi2][j]);
                                tg[iterasi2][j] = new TransformGroup();
                                tg[iterasi2][j].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                                tg[iterasi2][j].setTransform(t[iterasi2][j]);
                                objRoot.detach();
                                tg[iterasi2][j].addChild(b[iterasi2][j]);
                                objRoot.addChild(tg[iterasi2][j]);
                            }
                            for (int j=n; j<84; j++){
                                b[iterasi2][j] = new Box(width, width, width, Box.ENABLE_APPEARANCE_MODIFY,
                                        getAppearance(new Color3f(getColorOfKrom(jt))));
                                b[iterasi2][j].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
                                t[iterasi2][j] = new Transform3D();
                                v[iterasi2][j] = new Vector3f(startPosX[jt][j], startPosY[jt][j], 0.0f);
                                yloc2 = startPosY[jt][j];
                                t[iterasi2][j].setTranslation(v[iterasi2][j]);
                                tg[iterasi2][j] = new TransformGroup();
                                tg[iterasi2][j].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                                tg[iterasi2][j].setTransform(t[iterasi2][j]);
                                tg[iterasi2][j].addChild(b[iterasi2][j]);
                                objRoot.detach();
                                //tg[iterasi2][j].addChild(b[iterasi2][j]);
                                objRoot.addChild(tg[iterasi2][j]);
                                //temp.addChild(objTrans);
                                //objRoot.addChild(temp);
                            }
                            isCreating = false;
                            isMoving = true;
                            u.addBranchGraph(objRoot);
                            jt++;
                        }
                        if (isMoving){
                            if(yloc1<=endposY){
                                for (int j=0; j<n; j++){
                                    v[iterasi2][j] = new Vector3f(startPosX[iterasi2][j], yloc1, 0.0f);
                                    t[iterasi2][j] = new Transform3D();
                                    t[iterasi2][j].setTranslation(v[iterasi2][j]);
                                    //tg[iterasi2][j] = new TransformGroup();
                                    //tg[iterasi2][j].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                                    tg[iterasi2][j].setTransform(t[iterasi2][j]);
                                    //objRoot.detach();
                                    //objRoot.addChild(tg[iterasi2][j]);
                                }
                                yloc1+=0.1f;
                            }
                            if(yloc2<=endposY){
                                for (int j=n; j<84; j++){
                                    v[iterasi2][j] = new Vector3f(startPosX[iterasi2][j], yloc2, 0.0f);
                                    t[iterasi2][j] = new Transform3D();
                                    t[iterasi2][j].setTranslation(v[iterasi2][j]);
                                    //tg[iterasi2][j] = new TransformGroup();
                                    //tg[iterasi2][j].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                                    tg[iterasi2][j].setTransform(t[iterasi2][j]);
                                    //objRoot.detach();
                                    //objRoot.addChild(tg[iterasi2][j]);
                                }
                                yloc2+=0.1f;
                            }
                            //System.out.println(iterasi2+" "+yloc1+" "+yloc2+" "+endposY);
                            if((yloc1>endposY)&&(yloc2>endposY)){
                                for (int j = 0; j<84; j++){
                                    v[iterasi2][j] = new Vector3f(startPosX[iterasi2][j], endposY, 0.0f);
                                    t[iterasi2][j] = new Transform3D();
                                    t[iterasi2][j].setTranslation(v[iterasi2][j]);
                                    tg[iterasi2][j].setTransform(t[iterasi2][j]);
                                }
                                isCreating = true;
                                isMoving = false;
                                //u.addBranchGraph(objRoot);
                            }
                        }
                        if((yloc1>endposY)&&(yloc2>endposY)){
                            iterasi2++;
                        }
                    }
                    if (iterasi2>=16){
                        iterasi2 = 4;
                        state = "mutating";
                        if(timer.isRunning()){
                            timer.stop();
                            go.setLabel("mutate");
                        }
                    }
                }
                else if(state.equals("mutating")){
                    go.setLabel("fitness");
                    if (isCreating){
                        if(xloc < 0.1f){
                            for (int s = 0; s<16; s++){
                                Random rand = new Random();
                                m[s] = rand.nextInt(84);
                                for (int j=0; j<84; j++){
                                    if (j<m[s]) v[s][j] = new Vector3f(startPosX[s][j]-xloc, startPosY[s][j], 0.0f);
                                    else if(j>m[s]) v[s][j] = new Vector3f(startPosX[s][j]+xloc, startPosY[s][j], 0.0f);
                                    else if (j == m[s]) v[s][j] = new Vector3f(startPosX[s][j], startPosY[s][j], 0.0f);
                                    t[s][j] = new Transform3D();
                                    t[s][j].setTranslation(v[s][j]);
                                    //tg[iterasi2][j] = new TransformGroup();
                                    //tg[iterasi2][j].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                                    tg[s][j].setTransform(t[s][j]);
                                    //objRoot.detach();
                                    //objRoot.addChild(tg[iterasi2][j]);
                                }
                            }
                            xloc += 0.01f;
                        }
                        if (xloc >= 0.1f){
                            xloc = 0;
                            isCreating = false;
                            isMoving = true;
                        }
                    }
                    else if (isMoving){
                        for (int s = 0; s<16; s++){
                            b[s][m[s]].setAppearance(getAppearance(Color.WHITE));
                        }
                        isMoving = false;
                        isCreating = true;
                        started = true;
                    }
                    if (started){
                        state = "fitness";
                        if(timer.isRunning()){
                            timer.stop();
                        }
                    }
                }
                else if(state.equals("fitness")){
                    go.setLabel("populate");
                    for (int s = 0; s<16; s++){
                        if ((s!=selected[0])&&(s!=selected[1])&&(s!=selected[2])&&(s!=selected[3])){
                            for (int f = 0; f<84; f++){
                                objRoot.detach();
                                objRoot.removeChild(tg[s][f]);
                            }
                        }
                    }
                    u.addBranchGraph(objRoot);
                    iterasi1++;
                    state = "populating";
                    if(timer.isRunning()){
                        timer.stop();
                    }
                }
            /*if(xloc<=stopPos) {
             * xloc+= .01;
             * }
             * height += .01 * sign;
             * if (Math.abs(height *2) >= 1 ) sign = -1.0f * sign;
             * if (height<-0.4f) {
             * trans.setScale(new Vector3d(1.0, .8, 1.0));
             * }
             * else {
             * trans.setScale(new Vector3d(1.0, 1.0, 1.0));
             * }
             * trans.setTranslation(new Vector3f(xloc ,height,0.0f));
             * objTrans.setTransform(trans);*/
        }
    }
    public void pass(Vector<Barang> b, Vector<Kandidat> k, String s, int en, int brain, int charm, int strength, int modal){
        barang = b;
        kandidat = k;
        krom =s;
        br = brain;
        ch= charm;
        st =strength;
        md = modal;
    }
    /*public static void main(String[] args) {
        System.out.println("Program Started");
        BouncingBall bb = new BouncingBall();
        bb.addKeyListener(bb);
        mf = new MainFrame(bb, 1000, 500);
    }*/
}