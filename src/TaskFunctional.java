/**
 * Текст можно набрать вручную или использовать закоментированный ниже текст
 * Не забудьте по окончании ввода текста нажать ctrl+d
 */
//       Returns a Collector implementing a cascaded "group by"
//       operation on input elements of type T, grouping elements
//       according to a classification function, and then performing
//       a reduction operation on the values associated with a given
//       key using the specified downstream Collector.
//        The classification function maps elements to some key type K.
//        The downstream collector operates on elements of type T and
//        produces a result of type D. The resulting collector
//        produces a Map<K, D>. There are no guarantees on the type,
//        mutability, serializability, or thread-safety of the Map returned.

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskFunctional {
    public static void main(String[] args) {
        words();
    }

    public static void words() {
        System.out.println("Введите текст в консоль и нажмите ctrl+d ");
        Map<String, Long> groupWords = new Scanner(System.in)
                .useDelimiter("\\W+")
                .tokens()
                .map(String::toLowerCase)
                //.filter(x -> !x.isBlank())
                .sorted()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        System.out.println("Общее количество слов - " + groupWords.values().stream().mapToLong(x -> x).sum());
        System.out.println("TOP 10 слов:");
        groupWords.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .forEach(v -> System.out.println(v.getValue() + " - " + v.getKey()));
    }
}
