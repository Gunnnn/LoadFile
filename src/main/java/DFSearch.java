import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.DepthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Simple Depth First search with highlighted edges.
 *
 * Created by pigne on 12/17/15.
 */
public class DFSearch {
    public static void main(String[] args) throws InterruptedException {

        Graph g = new SingleGraph("ok");

        // create a random graph
        BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator();
        gen.addSink(g);
        gen.begin();
        for(int i =0; i< 100; i++)
            gen.nextEvents();


        // display the graph before running the search
        g.display();
        g.addAttribute("ui.stylesheet","" +
                "edge.highlight {  " +
                "   fill-color: rgb(200,39,65);\n" +
                "   size: 3px;" +
                "}");

        DepthFirstIterator it = new DepthFirstIterator(g.getNode(0));
        while(it.hasNext()){
            Node n = it.next();
            for(Edge e: n.getEachEdge()){
                e.addAttribute("ui.class","highlight");
                Thread.sleep(100);
            }

        }

    }
}