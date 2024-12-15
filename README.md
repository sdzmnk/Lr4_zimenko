**Завдання 1:**
Ця програма демонструє асинхронну обробку текстових файлів за допомогою CompletableFuture. Вона зчитує текст із трьох файлів, обробляє його (видаляє всі літери), зберігає результати й виводить їх у консоль. Завдяки асинхронності програма працює швидше, виконуючи всі задачі паралельно. Також підраховується загальний час виконання всіх операцій.CompletableFuture в цьому коді використовується для асинхронного виконання задач, таких як читання файлів, обробка тексту та збереження результатів. Кожен етап обробки (читання, обробка, збереження) виконується у окремих асинхронних потоках, що дозволяє виконувати ці операції паралельно, не блокуючи основний потік.

**Завдання 2:**
Цей програма генерує послідовність випадкових чисел і виконує кілька асинхронних операцій. Спочатку за допомогою CompletableFuture.supplyAsync() асинхронно генерується список з 20 випадкових чисел. Потім за допомогою thenAcceptAsync() виводиться згенерована послідовність. Після цього, за допомогою thenApplyAsync(), обчислюється сума добутків кожної пари сусідніх чисел. Далі, результат обчислень виводиться за допомогою thenAcceptAsync(). В кінці, за допомогою thenRunAsync(), виводиться загальний час виконання всіх асинхронних операцій. Всі ці операції виконуються асинхронно, що дозволяє скоротити загальний час виконання програми.