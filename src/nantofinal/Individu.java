package nantofinal;

public class Individu {
    
    private String kromosom;
    private int fitness;
    
    //Atribut nanto
    public int money;
    public int energy;
    public int str;
    public int crm;
    public int brn;
    public String bag;
    public int[] meeting;
    public int enlight;
    
    //Konstruktor
    public Individu(){
        kromosom = new String();
        fitness = 0;
    }
    
    //Getter & Setter
    public String getKromosom() {
        return kromosom;
    }
    
    public void setKromosom(String kromosom) {
        this.kromosom = kromosom;
    }
    
    public int getFitness() {
        return fitness;
    }
    
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    
    
}
