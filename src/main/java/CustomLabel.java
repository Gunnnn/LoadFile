import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.thread.ThreadProxyPipe;
//import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.Viewer;

public class CustomLabel extends ThreadProxyPipe {

    Graph graph;
    String labelKey;

    public CustomLabel(Graph graph, String labelKey) {
        super(graph, true);

        this.graph = graph;
        this.labelKey = labelKey;
    }

    public void nodeAttributeAdded(String graphId, long timeId, String nodeId,
                                   String attribute, Object value) {
        if (attribute.equals(labelKey))
            attribute = "ui.label";

        super.nodeAttributeAdded(graphId, timeId, nodeId, attribute, value);
    }

    public void nodeAttributeChanged(String graphId, long timeId,
                                     String nodeId, String attribute, Object oldValue, Object newValue) {
        if (attribute.equals(labelKey))
            attribute = "ui.label";

        super.nodeAttributeChanged(graphId, timeId, nodeId, attribute, oldValue, newValue);
    }

    public void nodeAttributeRemoved(String graphId, long timeId,
                                     String nodeId, String attribute) {
        super.nodeAttributeRemoved(graphId, timeId, nodeId, attribute);

        if (attribute.equals(labelKey)) {
            Node n = graph.getNode(nodeId);

            if (n.hasLabel("ui.label"))
                super.sendNodeAttributeChanged(sourceId, nodeId, "ui.label", null, n.getAttribute("ui.label"));
            else if (n.hasLabel("label"))
                super.sendNodeAttributeChanged(sourceId, nodeId, "ui.label", null, n.getAttribute("label"));
        }
    }

    public static void main(String[] args) throws Exception {
        Graph g = new DefaultGraph("g");
        Node a = g.addNode("A");

        Viewer v = new Viewer(new CustomLabel(g, "myLabel"));
        v.enableAutoLayout();
        v.addDefaultView(true);

        a.setAttribute("ui.label", "A");
        Thread.sleep(1000);
        a.setAttribute("myLabel", "I'm A");
        Thread.sleep(1000);
        a.removeAttribute("myLabel");
    }
}