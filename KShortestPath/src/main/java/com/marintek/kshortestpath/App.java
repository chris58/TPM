package com.marintek.kshortestpath;

import edu.asu.emit.qyan.alg.control.DijkstraShortestPathAlg;
import edu.asu.emit.qyan.alg.control.YenTopKShortestPathsAlg;
import edu.asu.emit.qyan.alg.model.Graph;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        // Import the graph from a file
        Graph graph = new Graph("/home/chris/tmp/data/chris");
//        Graph graph = new Graph("/home/chris/tmp/data/test_6_2");

        System.out.println("Testing Dijkstra Algorithm.");
        DijkstraShortestPathAlg alg = new DijkstraShortestPathAlg(graph);
        System.out.println(alg.get_shortest_path(graph.get_vertex(12), graph.get_vertex(7)));

        // The graph should be initiated only once to guarantee the correspondence 
        // between vertex id and node id in input text file. 

        graph = new Graph("/home/chris/tmp/data/chris");
        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(
                graph, graph.get_vertex(12), graph.get_vertex(7));
        int i = 0;

        while (yenAlg.has_next()) {
            System.out.println("Path " + i++ + " : " + yenAlg.next());
        }

        System.out.println(
                "Result # :" + i);
        System.out.println(
                "Candidate # :" + yenAlg.get_cadidate_size());
        System.out.println(
                "All generated : " + yenAlg.get_generated_path_size());

    }
}
