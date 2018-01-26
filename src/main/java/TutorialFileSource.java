import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import java.io.IOException;

public class TutorialFileSource {

    private static String styleSheet;

    public static void main(String ... args) {
        styleSheet = "node {" +
                "fill-color: black;}" +
                "node.marked {" +
                "	fill-color: red;" +
                "}";
        String filePath = "C:/Temp/dot_2.dot";
        Graph graph = new DefaultGraph("graph");
        FileSource fs = null;
        try {
            fs = FileSourceFactory.sourceFor(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fs.addSink(graph);


        try {
            fs.begin(filePath);

            while (fs.nextEvents()) {
                // Optionally some code here ...
            }
        } catch( IOException e) {
            e.printStackTrace();
        }

        try {
            fs.end();
        } catch( IOException e) {
            e.printStackTrace();
        } finally {
            fs.removeSink(graph);
        }

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
//        graph.setStrict(false);
        graph.display();


        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

    }
}