package nantofinal;

public class GARecord {
    //data member
    public final int populat = 4;
    public Individu curPopulation[] = new Individu[populat];
    public int mutatedIdx[] = new int[populat*populat];
    public int selectedIdx[] = new int[populat];
    public int crossedIdx[] = new int[populat*(populat-1)];
    public static int waktu;
}
