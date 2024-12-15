import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FirstTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Список шляхів до файлів
        List<Path> filePaths = List.of(
                Paths.get("C:\\Users\\sdzim\\IdeaProjects\\Lr4_zimenko\\src\\file1.txt"),
                Paths.get("C:\\Users\\sdzim\\IdeaProjects\\Lr4_zimenko\\src\\file2.txt"),
                Paths.get("C:\\Users\\sdzim\\IdeaProjects\\Lr4_zimenko\\src\\file3.txt")
        );

        // Масив для зберігання результатів обробки файлів
        List<String> results = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        List<CompletableFuture<Void>> processingFutures = new ArrayList<>();

        // Завантаження та обробка тексту для кожного файлу
        for (Path path : filePaths) {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // Читаємо текст з файлу
                    String sentence = Files.readString(path);
                    System.out.println("Текст файлу " +path.getFileName() +": " + sentence);
                    return sentence;
                } catch (IOException e) {
                    e.printStackTrace();
                    return ""; // Повертаємо порожній рядок у разі помилки
                }

            }).thenApplyAsync(content -> {
                // Обробка тексту: видалення усіх літер верхнього та нижнього регістрів
                long start = System.currentTimeMillis();
                String processed = content.replaceAll("[a-zA-ZА-Яа-я]", "");
                System.out.println("Обробка файлу " + path.getFileName() + " завершена за " +
                        (System.currentTimeMillis() - start) + " мс.\n");
                return processed;
            }).thenAcceptAsync(processed -> {
                synchronized (results) {
                    results.add(processed);
                }
                long start = System.currentTimeMillis();
                System.out.println("Результат видалення літер у файлі " + path.getFileName()+" : " + processed );
                System.out.println("Збереження результату завершено за " +
                        (System.currentTimeMillis() - start) + " мс.\n");
            });

            processingFutures.add(future);
        }


        CompletableFuture.allOf(processingFutures.toArray(new CompletableFuture[0])).thenRun(() -> {
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Усі задачі завершено за " + duration + " мс.");
            System.out.println("Результати обробки всіх файлів: " + results);
        }).get();
    }
}
