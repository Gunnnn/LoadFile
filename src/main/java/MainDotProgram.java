import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class MainDotProgram {

    private static String styleSheet;

    public static void main(String ... args) {
        //System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String DotFilePath = new String("");
        String TabName = new String("");
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            DotFilePath = file.toString();
            TabName = file.getName();
            int pos = TabName.lastIndexOf(".");
            if (pos > 0) {
                TabName = TabName.substring(0, pos);
            }
        }
        else
        {
            System.exit(0);
        }
        File StyleSheetFile = new File(MainDotProgram.class.getClassLoader().getResource("StyleSheet.css").getPath());
        System.out.println(StyleSheetFile.toString());
        Graph graph = new DefaultGraph("graph");
        FileSource fs = null;
        try {
            fs = FileSourceFactory.sourceFor(DotFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fs.addSink(graph);
        try {
            fs.begin(DotFilePath);
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
        graph.addAttribute("ui.stylesheet", "url('file:"+StyleSheetFile.toString()+"')");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        //graph.addAttribute("layout.gravity", 0.03);
        graph.display();

        //addind labels for each node and doing all work
        for (Node node : graph) {
            //node.addAttribute("ui.style", "shape:circle;fill-color: yellow;size: 90px; text-alignment: center;");
            //add label to node
            node.addAttribute("ui.label", node.getId());
            if (node.getId().toString().contains(TabName))
            {
                node.addAttribute("ui.class", "important"); // make the node appear as important.
            }
        //for (Node edge : graph) {
        //    edge.addAttribute("layout.weight", "3");
        ///    }
        }






    }
}