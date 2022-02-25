
//package cs124progassign1;

import java.util.Random;
import java.io.IOException;
import java.util.PriorityQueue;

public class CS124ProgAssign1 {
    
    public static int N;
    public static boolean[] V;
    public static coord[] loc;
    public static double[] total = new double[5];
    
    public static void init_loc(int dim) {
        Random gen = new Random(System.currentTimeMillis());
        
        loc = new coord[N];
        for (int i = 0; i < N; i++) {
            switch (dim) {
                case 2:
                    loc[i] = new coord(gen.nextDouble(), gen.nextDouble(), 0, 0);
                    break;
                case 3:
                    loc[i] = new coord(gen.nextDouble(), gen.nextDouble(), gen.nextDouble(), 0);
                    break;
                case 4:
                    loc[i] = new coord(gen.nextDouble(), gen.nextDouble(), gen.nextDouble(), gen.nextDouble());
                    break;
            }
        }
    }
    
    public static double dist(coord c1, coord c2) {
        return Math.sqrt((c1.w - c2.w) * (c1.w - c2.w) + (c1.x - c2.x) * (c1.x - c2.x) + (c1.y - c2.y) * (c1.y - c2.y) + (c1.z - c2.z) * (c1.z - c2.z));
    }
    
    public static double MST_0() {
        Random gen = new Random(System.currentTimeMillis());
        
        point[] graph = new point[N];
        double[] key = new double[N];
        
        for (int i = 0; i < N; i++) {
            if (i != 0) {
                key[i] = Double.MAX_VALUE;

                point node = new point(i, Double.MAX_VALUE);
                graph[i] = node;
            }
            else {
                key[i] = 0;
                
                point node = new point(i, 0);
                graph[i] = node;
            }
        }
        
        PriorityQueue<point> PQ = new PriorityQueue<>();
        
        PQ.add(graph[0]);
                
        while (!PQ.isEmpty()) {
            point node1 = PQ.poll();
            int i = node1.ID;
            V[i] = true;
            
            for (int j = 0; j < N; j++) {
                if (!V[j] && j != i) {
                    double rand = gen.nextDouble();
                    if (rand < key[j]) {
                        key[j] = rand;
                        PQ.remove(graph[j]);
                        graph[j] = new point(j, key[j]);
                        PQ.add(graph[j]);
                    }
                }
            }
        }
        
        double sum = 0D;
        for (double v : key)
            sum += v;
        return sum;
    }
    
    public static double MST_dim(int dim) {
        if (dim == 0)
            return MST_0();
        
        init_loc(dim);
        
        point[] graph = new point[N];
        double[] key = new double[N];
        
        for (int i = 0; i < N; i++) {
            if (i != 0) {
                key[i] = Double.MAX_VALUE;

                point node = new point(i, Double.MAX_VALUE);
                graph[i] = node;
            }
            else {
                key[i] = 0;
                
                point node = new point(i, 0);
                graph[i] = node;
            }
        }
        
        PriorityQueue<point> PQ = new PriorityQueue<>();
        
        PQ.add(graph[0]);
                
        while (!PQ.isEmpty()) {
            point node1 = PQ.poll();
            int i = node1.ID;
            V[i] = true; 
           
            for (int j = 0; j < N; j++) {
                if (!V[j] && j != i) {
                    double dist = dist(loc[i], loc[j]);
                    if (dist < key[j]) {
                        key[j] = dist;
                        PQ.remove(graph[j]);
                        graph[j] = new point(j, key[j]);
                        PQ.add(graph[j]);
                    }
                }
            }
        }
        
        double sum = 0D;
        for (double v : key)
            sum += v;
        return sum;
    }
    
    public static void main(String[] args) throws IOException {
        
        N = Integer.parseInt(args[0]);
        int runs = Integer.parseInt(args[1]);
        int dim = Integer.parseInt(args[2]);

        double avg_weight = 0;

        for(int x = 0; x < runs; x++) {
            V = new boolean[N];
            avg_weight += MST_dim(dim); // get total weights from all the runs
        }
        avg_weight /= runs; // get the average weight from all the runs

        System.out.println(avg_weight + " " + N + " " + runs + " " + dim);

    }
}

class point implements Comparable<point> {

    public int ID;
    public double key;

    public point(int ID, double key) {
        this.ID = ID;
        this.key = key;
    }

    @Override
    public int compareTo(point gph) {
        if (this.key < gph.key)
            return -1;
        if (this.key > gph.key)
            return 1;
        return 0;
    }
}

class coord {
    
    public double w, x, y, z;
    
    public coord(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}