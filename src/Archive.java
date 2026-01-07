import java.util.*;

public class Archive {

    static String[] words = {"калейдоскоп", "прокрастинация", "перпендикуляр", "метеорит", "фуникулёр", "симметрия"};
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();


    public static void main(String[] args) {
        System.out.println("Игра Виселица");
        gameInitilization(words);

        // проблемы:
        // добавить проверку ввода символов отличных от кириллицы
        // добавить проверку ввода цифр вместо текста
        // не увеличивать счетчик при повторно введенных неправильных буквах
    }

    private static void checkWord(String hiddenWord, String cellsForHiddenWord) {
        String[] hiddenWordLetters = hiddenWord.split("");
        String[] cellsForHiddenWordArray = cellsForHiddenWord.split("");
        String cellsForHiddenWordArrayStars = Arrays.toString(cellsForHiddenWordArray).replace(", ", "").replace("]", "").replace("[", "");
        int hangCounter = 0;
        String inputLetterStatus = null;
        List<String> incorrectInputLetters = new ArrayList<>();
        boolean gameOver = false;
        System.out.println(cellsForHiddenWordArrayStars);
        while (!gameOver) {
            boolean pass = false;
            boolean repeat = false;
            String inputLetter = inputLetter();
            for (int i = 0; i < hiddenWordLetters.length; i++) {
                if (inputLetter.equals(hiddenWordLetters[i])) {
                    cellsForHiddenWordArray[i] = hiddenWordLetters[i];
                    pass = true;
                }
                if (inputLetter.equals(cellsForHiddenWordArray[i])) {
                    repeat = true;
                }
            }
            if (!pass) {
                hangCounter += 1;
                incorrectInputLetters.add(inputLetter);
            }

            // удаление повторяющихся неверно введённых букв в списке
            List<String> incorrectInputLettersWithoutDuplicates = incorrectInputLetters.stream()
                    .distinct()
                    .toList();

            System.out.println(printHangman(hangCounter));
            inputLetterStatus = checkInputLetterStatus(repeat, inputLetterStatus, inputLetter);
            System.out.println(inputLetterStatus + " Количество ошибок [" + hangCounter + "]. Неверно угаданные буквы " + incorrectInputLettersWithoutDuplicates + ".");
            System.out.println(cellsForHiddenWordArrayStars);

            if (Arrays.equals(hiddenWordLetters, cellsForHiddenWordArray)) {
                System.out.println("Вы отгадали слово");
                hangCounter = 0;
                gameInitilization(words);
                gameOver = true;
            } else if (hangCounter == Assets.hang.length) {
                System.out.println("Вы не отгадали слово, " + "загаданное слово было: " + hiddenWord);
                gameOver = true;
                gameInitilization(words);
            }

        }
    }

    private static String printHangman(int hangCounter) {
        String result = null;
        for (int i = 0; i < Assets.hang.length; i++) {
            if (hangCounter == i + 1) {
                result = Assets.hang[i];
            }
        }
        return result;
    }

    private static String checkInputLetterStatus(boolean repeat, String inputLetterStatus, String inputLetter) {
        if (repeat) {
            inputLetterStatus = "Буква [" + inputLetter + "] в слове есть.";
        } else {
            inputLetterStatus = "Буквы [" + inputLetter + "] в слове нет.";
        }
        return inputLetterStatus;
    }

    private static String inputLetter() {
        System.out.println("Введите букву");
        return scanner.next();
    }

    private static String printWordCells(String word) {
        String cells = "";
        for (int i = 0; i < word.length(); i++) {
            cells += "*";
        }
        return cells;
    }

    private static String letHiddenWord(String[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }

    private static void gameInitilization(String[] array) {
        System.out.println("Начать новую игру? 1 - Да; 2 - Нет");
        String startGame = scanner.next();
        while (!Objects.equals(startGame, "")) {
            if (Objects.equals(startGame, "1")) {
                String hiddenWord = letHiddenWord(array);
                String wordCells = printWordCells(hiddenWord);
                System.out.println("Игра началась");
                checkWord(hiddenWord, wordCells);
                startGame = ""; // костыль, чтобы игра не запускалась при завершении игры после однократного запуска
            } else if (Objects.equals(startGame, "2")) {
                System.out.println("Вы вышли из игры");
                break;
            } else {
                System.out.println("Некорректный ввод");
                System.out.println("Начать новую игру? 1 - Да; 2 - Нет");
                startGame = scanner.next();
            }
        }


    }

}