import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import utils.*;
import java.util.stream.*;

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

        @Override
        public String toString() {
            return this.name + " = " + (left == null ? "null" : left.name) + ", "
                    + (right == null ? "null" : right.name);
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

        String instructions = lines.get(0);

        ArrayList<Node> nodes = new ArrayList<>();

        int i =0;
        for (String line : lines) {
            if (i++ < 2){
                continue;
            }

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

    private HashMap<Node, Integer> findNodes(ArrayList<Node> nodes, char end) {
        HashMap<Node, Integer> found = new HashMap<>();

        for (Node node : nodes) {
            if (node.name.charAt(2) == end) {
                found.put(node, -1);
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

        HashMap<Node, Integer> startNodeLengths = findNodes(nodes, 'A');
        ArrayList<Node> currentNodes = new ArrayList<>(startNodeLengths.keySet());

        for (Node start : currentNodes) {
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

                if (current.name.charAt(2) == 'Z') {
                    done = true;
                }
            }

            startNodeLengths.put(start, steps);
        }

        int[] lengths = startNodeLengths.values().stream().mapToInt(Integer::intValue).toArray();
        long lcm = MathHelper.lcm(lengths);

        print("Steps to done", lcm);
    }
}