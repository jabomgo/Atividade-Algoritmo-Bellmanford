import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.StringTokenizer;

public class BellmanFord {
    private  static final double EPSILON = 1E-14;
    private double[] distTo;
    private int[] edgeTo;
    private DirectedEdge[] aresta;
    private int V, E, s;
    private double cost;

    public BellmanFord(String filepath, int source){
        this.s = source;
        readInput(filepath);
        Inicializa();

        int j,i;
        for (i =1; i<V;++i){
            for (j = 0; j<E; ++j){
                relax(aresta[j]);
            }
        }
        for (i=0; i<V; ++i){
            if(hasPathTo(i)){
                System.out.printf("Rota de %d a %d (%5.2f)", s, i, distTo[i]);
                printPathTo(i);
                System.out.println();
            }
        }
        if(!negative()){
            System.out.print("\nNão há ciclos negativos");
        }
    }
    private boolean negative(){
        int i,v,w;
        double peso;
        boolean negative = false;
        for (i = 0; i < E; ++i){
            v = this.aresta[i].from();
            w = this.aresta[i].to();
            peso = this.aresta[i].weight();
            if(this.distTo[w] > this.distTo[v] + peso + EPSILON){
                negative = true;
                ;
                System.out.printf("\nCiclo negativo entre %d e %d", v,w);
            }
        }
        return negative;
    }
    public void printPathTo(int v){
        if (v == this.s){
            System.out.print("\t");
            System.out.print(this.s);
            return;
        }
        printPathTo(this.edgeTo[v]);
        System.out.print(" - > ");
        System.out.print(v);
    }
    public void Inicializa(){
        int i;
        for (i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
            this.edgeTo[i] = -1;
        }
        this.distTo[s] = 0.0;
    }
    public void relax(DirectedEdge aresta){
        int v,w;
        double peso;
        v = aresta.from();
        w = aresta.to();
        peso = aresta.weight();
        if(this.distTo[w] > this.distTo[v] + peso + EPSILON){
            this.distTo[w] = this.distTo[v] + peso;
            this.edgeTo[w] = v;
        }
    }
    public boolean hasPathTo(int v){
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }
    public void readInput(String filepath){
        Scanner sc;
        File file = null;
        String line;
        int a,b,i;
        StringTokenizer tk;
        try{
            file = new File(filepath);
            sc = new Scanner(file);
            if(sc.hasNext()){
                this.V = sc.nextInt();
            } else {
                System.out.println("\nArquivo Inválido");
                System.exit(0);
            }
            if (sc.hasNext()){
                E = sc.nextInt();
            } else {
                System.out.println("\nArquivo Inválido");
                System.exit(0);
            }
            this.distTo = new double[this.V];
            this.edgeTo = new int[this.V];
            this.aresta = new DirectedEdge[this.E];
            for(i = 0; i<this.E; ++i){
               a = sc.nextInt();
               b = sc.nextInt();
               this.cost = Double.parseDouble(sc.next());
               this.aresta[i] = new DirectedEdge(a,b,this.cost);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("\n\nUso java BellmanFor Grafo no-inicial\n");
            return;
        }
        BellmanFord bf = new BellmanFord(args[0], Integer.parseInt(args[1]));
    }
}
