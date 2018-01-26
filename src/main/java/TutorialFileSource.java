import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import java.io.IOException;

public class TutorialFileSource {

    private static String styleSheet;

    public static void main(String ... args) {
        styleSheet = "node {\n" +
                "\tfill-color: green;\n" +
                "\tsize: 10px;\n" +
                "\tstroke-mode: plain;\n" +
                "\tstroke-color: black;\n" +
                "\tstroke-width: 1px;\n" +
                "}\n" +
                "\n" +
                "node.important {\n" +
                "\tfill-color: red;\n" +
                "\tsize: 15px;\n" +
                "}";
        String filePath = "C:/Temp/dot_1.dot";
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
        graph.setStrict(false);
        graph.display();

        SpriteManager sman = new SpriteManager(graph);
        Sprite s = sman.addSprite("S11");

        //addind labels for each node and doing all work
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
//            System.out.println("'" + node.getId().toString() + "'");
            s.attachToNode(node.getId().toString());
            if (node.getId().toString().contains("B2MR_RISK_CONC"))
            {
                node.addAttribute("ui.class", "important"); // make the node appear as important.
            }
        }


    }
}