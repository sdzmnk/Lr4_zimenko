import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SecondTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        // Генерація послідовності дійсних чисел
        CompletableFuture<List<Double>> sequenceFuture = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            List<Double> sequence = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 20; i++) {
                sequence.add(random.nextDouble() * 100); // Рандомні числа від 0 до 100
            }
            System.out.println("Початкова послідовність згенерована за " + (System.currentTimeMillis() - start) + " мс.");
            return sequence;
        });

        // Виведення початкової послідовності
        CompletableFuture<Void> printSequenceFuture = sequenceFuture.thenAcceptAsync(sequence -> {
            long start = System.currentTimeMillis();
            System.out.println("Початкова послідовність:" + sequence);
            System.out.println("\nВиведення послідовності завершено за " + (System.currentTimeMillis() - start) + " мс.");
        });

        // Обчислення виразу
        CompletableFuture<Double> calculationFuture = sequenceFuture.thenApplyAsync(sequence -> {
            long start = System.currentTimeMillis();
            double result = 0.0;
            for (int i = 0; i < sequence.size() - 1; i++) {
                result += sequence.get(i) * sequence.get(i + 1);
            }
            System.out.println("Обчислення результату завершено за " + (System.currentTimeMillis() - start) + " мс.");
            return result;
        });

        // Виведення результату
        CompletableFuture<Void> printResultFuture = calculationFuture.thenAcceptAsync(result -> {
            long start = System.currentTimeMillis();
            System.out.printf("Результат обчислень: %.2f\n", result);
            System.out.println("Виведення результату завершено за " + (System.currentTimeMillis() - start) + " мс.");
        });

        // Завершальний етап
        CompletableFuture<Void> finalStep = printResultFuture.thenRunAsync(() -> {
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Усі асинхронні операції завершено за " + duration + " мс.");
        });

        // Очікуємо завершення всіх задач
        finalStep.get();
    }
}
