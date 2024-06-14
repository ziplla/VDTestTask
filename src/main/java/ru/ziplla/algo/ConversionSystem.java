package ru.ziplla.algo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

class ConversionSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> inputLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }
            inputLines.add(line);
        }

        ConversionSystem conversionSystem = new ConversionSystem();
        List<String> queries = new ArrayList<>();

        for (String line : inputLines) {
            if (line.contains("?")) {
                queries.add(line);
            } else {
                conversionSystem.addConversion(line);
            }
        }

        for (String query : queries) {
            String result = conversionSystem.solveQuery(query);
            System.out.println(result);
        }

        scanner.close();
    }

    private final Map<String, Map<String, Double>> conversionMap = new HashMap<>();

    public void addConversion(String conversion) {
        String[] parts = conversion.split(" ");
        double a = Double.parseDouble(parts[0]);
        String fromUnit = parts[1];
        double b = Double.parseDouble(parts[3]);
        String toUnit = parts[4];

        conversionMap.putIfAbsent(fromUnit, new HashMap<>());
        conversionMap.putIfAbsent(toUnit, new HashMap<>());

        conversionMap.get(fromUnit).put(toUnit, b / a);
        conversionMap.get(toUnit).put(fromUnit, a / b);
    }

    public String solveQuery(String query) {
        String[] parts = query.split(" ");
        double a = Double.parseDouble(parts[0]);
        String fromUnit = parts[1];
        String toUnit = parts[4];

        if (!conversionMap.containsKey(fromUnit) || !conversionMap.containsKey(toUnit)) {
            return "Conversion not possible.";
        }

        Double result = bfs(fromUnit, toUnit);
        if (result == null) {
            return "Conversion not possible.";
        } else {
            DecimalFormat df = new DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.US));
            String formattedResult = df.format(a * result);
            return String.format(Locale.US, "%.1f %s = %s %s", a, fromUnit, formattedResult, toUnit);
        }
    }


    private Double bfs(String start, String end) {
        Queue<Pair<String, Double>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Pair<>(start, 1.0));

        while (!queue.isEmpty()) {
            Pair<String, Double> current = queue.poll();
            String unit = current.getKey();
            double value = current.getValue();

            if (unit.equals(end)) {
                return value;
            }

            visited.add(unit);

            Map<String, Double> neighbors = conversionMap.get(unit);
            for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
                if (!visited.contains(entry.getKey())) {
                    queue.add(new Pair<>(entry.getKey(), value * entry.getValue()));
                }
            }
        }
        return null;
    }

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

