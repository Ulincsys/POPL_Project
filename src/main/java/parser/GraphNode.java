package parser;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

public class GraphNode {
    private String label;
    private List<GraphNode> children;

    GraphNode(String label, List<GraphNode> children) {
        this.children = children;
        this.label = label;
    }

    GraphNode(String label, GraphNode child) {
        this.label = label;
        this.children = Arrays.asList(child);
    }

    GraphNode(String label) {
        this.label = label;
        this.children = Arrays.asList();
    }

    private class QueueElement {
        public GraphNode node;
        public long id;
        public long parentId;
        public QueueElement(GraphNode node, long id, long parentId) {
            this.node = node;
            this.id = id;
            this.parentId = parentId;
        }
    }
    
    private String escapeString(String string) {
        StringBuilder builder = new StringBuilder(string.length() * 2);
        for (char c : string.toCharArray()) {
            if (c >= 0x20 && c <= 0x7f && c != '"' && c != '\\') {
                builder.append(c);
            } else if (c == '"' || c == '\\') {
                builder.append('\\');
                builder.append(c);
            } else {
                builder.append(String.format("\\\\u%04X", (int)c));
            }
        }
        return builder.toString();
    }

    public String toGraphvizDot() {
        long nextId = 0;
        StringBuilder dotSource = new StringBuilder(4096);
        Queue<QueueElement> nodeQueue = new ArrayDeque<>();
        nodeQueue.add(new QueueElement(this, nextId++, 0));
        // dot file header
        dotSource.append("digraph parse_tree {\n");
        QueueElement node;
        while ((node = nodeQueue.poll()) != null) {
            for (GraphNode childNode : node.node.children) {
                nodeQueue.add(new QueueElement(childNode, nextId++, node.id));
            }
            dotSource.append(" " + node.id + " [");
            // TODO: attributes
            // TODO: escape
            dotSource.append("label=\"" + escapeString(node.node.label) + "\"]");

            dotSource.append(";\n");
            
            if (node.id != node.parentId) {
                // Don't put an edge from root to itself
                dotSource.append(" " + node.parentId + " -> " + node.id + ";\n");
            }
        }
        dotSource.append("}");
        return dotSource.toString();
    }
}
