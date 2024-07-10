import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        ArrayList<String> verses = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            verses.add(line);
        }
        reader.close();

        ArrayList<String> arrayList = new ArrayList<>(verses);
        HashSet<String> hashSet = new HashSet<>(verses);
        TreeSet<String> treeSet = new TreeSet<>(verses);
        HashMap<Integer, String> hashMap = new HashMap<>();

        for (String verse : verses) {
            hashMap.put(Integer.parseInt(verse.split("\t")[0]), verse.split("\t")[1]);
        }

        BufferedWriter writerArray = new BufferedWriter(new FileWriter("poemArrayList.txt"));
        for (String verse : arrayList) {
            writerArray.write(verse);
            writerArray.newLine();
        }
        writerArray.close();

        Collections.sort(arrayList, new idComparator());
        BufferedWriter writerArrayOrder = new BufferedWriter(new FileWriter("poemArrayListOrderByID.txt"));
        for (String verse : arrayList) {
            writerArrayOrder.write(verse);
            writerArrayOrder.newLine();
        }
        writerArrayOrder.close();

        BufferedWriter writerTreeSet = new BufferedWriter(new FileWriter("poemTreeSet.txt"));
        for (String verse : treeSet) {
            writerTreeSet.write(verse);
            writerTreeSet.newLine();
        }
        writerTreeSet.close();

        Set<String> treeSetById = new TreeSet<>(new idComparator());
        treeSetById.addAll(verses);
        BufferedWriter writerTreeSetOrder = new BufferedWriter(new FileWriter("poemTreeSetOrderById.txt"));
        for (String verse : treeSetById) {
            writerTreeSetOrder.write(verse);
            writerTreeSetOrder.newLine();
        }
        writerTreeSetOrder.close();

        BufferedWriter writerHashSet = new BufferedWriter(new FileWriter("poemHashSet.txt"));
        for (String verse : hashSet) {
            writerHashSet.write(verse);
            writerHashSet.newLine();
        }
        writerHashSet.close();

        BufferedWriter writerHashMap = new BufferedWriter(new FileWriter("poemHashMap.txt"));
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            writerHashMap.write(entry.getKey() + "\t" + entry.getValue());
            writerHashMap.newLine();
        }
        writerHashMap.close();

    }
}