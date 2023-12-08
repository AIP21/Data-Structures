import java.util.ArrayList;

import utils.*;

public class Day8 extends AbstractDay {
    public static void main(String[] args) {
        new Day8();
    }

    public Day8() {
        super(false, 2023);
    }

    private class Node {
        String name;
        Node left;
        Node right;

        public Node(String name, Node left, Node right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }

    private Node findNode(ArrayList<Node> nodes, String name) {
        for (Node node : nodes) {
            if (node.name.equals(name)) {
                return node;
            }
        }

        return null;
    }

    @Override
    public void part1() {
        // testInput("""
        // RL

        // AAA = (BBB, CCC)
        // BBB = (DDD, EEE)
        // CCC = (ZZZ, GGG)
        // DDD = (DDD, DDD)
        // EEE = (EEE, EEE)
        // GGG = (GGG, GGG)
        // ZZZ = (ZZZ, ZZZ)
        // """);

        // testInput("""
        // LLR

        // AAA = (BBB, BBB)
        // BBB = (AAA, ZZZ)
        // ZZZ = (ZZZ, ZZZ)
        // """);

        String instructions = lines.remove(0);

        lines.remove(0);

        ArrayList<Node> nodes = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" = ");
            String[] connected = split[1].replace("(", "").replace(")", "").split(", ");

            nodes.add(new Node(split[0], new Node(connected[0], null, null), new Node(connected[1], null, null)));
        }

        for (Node node : nodes) {
            node.left = findNode(nodes, node.left.name);
            node.right = findNode(nodes, node.right.name);
        }

        Node start = findNode(nodes, "AAA");

        int steps = 0;
        Node current = start;
        boolean done = false;

        while (!done) {
            char instruction = instructions.charAt(steps % instructions.length());

            if (instruction == 'L') {
                current = current.left;
            } else if (instruction == 'R') {
                current = current.right;
            }

            steps++;

            if (current.name.equals("ZZZ")) {
                done = true;
            }
        }

        print("Steps to done", steps);
    }

    private ArrayList<Node> findNodes(ArrayList<Node> nodes, char end) {
        ArrayList<Node> found = new ArrayList<>();

        for (Node node : nodes) {
            if (node.name.charAt(node.name.length() - 1) == end) {
                found.add(node);
            }
        }

        return found;
    }

    @Override
    public void part2() {
        // testInput("""
        // LR

        // 11A = (11B, XXX)
        // 11B = (XXX, 11Z)
        // 11Z = (11B, XXX)
        // 22A = (22B, XXX)
        // 22B = (22C, 22C)
        // 22C = (22Z, 22Z)
        // 22Z = (22B, 22B)
        // XXX = (XXX, XXX)
        // """);

        String instructions = lines.remove(0);

        lines.remove(0);

        ArrayList<Node> nodes = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" = ");
            String[] connected = split[1].replace("(", "").replace(")", "").split(", ");

            nodes.add(new Node(split[0], new Node(connected[0], null, null), new Node(connected[1], null, null)));
        }

        for (Node node : nodes) {
            node.left = findNode(nodes, node.left.name);
            node.right = findNode(nodes, node.right.name);
        }

        ArrayList<Node> currentNodes = findNodes(nodes, 'A');

        int steps = 0;

        boolean done = false;

        while (!done) {
            int atEnd = 0;

            for (int i = 0; i < currentNodes.size(); i++) {
                char instruction = instructions.charAt(steps % instructions.length());

                if (instruction == 'L') {
                    currentNodes.set(i, currentNodes.get(i).left);
                } else if (instruction == 'R') {
                    currentNodes.set(i, currentNodes.get(i).right);
                }

                Node afterMove = currentNodes.get(i);

                if (afterMove.name.charAt(afterMove.name.length() - 1) == 'Z') {
                    atEnd++;
                }
            }

            if (atEnd == currentNodes.size()) {
                done = true;
            }

            steps++;
        }

        print("Steps to done", steps);
    }
}