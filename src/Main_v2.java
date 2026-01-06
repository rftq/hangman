
import java.util.*;

public class Main_v2 {
    static String[] words = {"калейдоскоп", "прокрастинация", "перпендикуляр", "метеорит", "фуникулёр", "симметрия"};
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static int hangmanCounter = 0;


    public static void main(String[] args) {

        gameLoop();

        // проверки:
//        System.out.println(gameInitilization);
//        System.out.println(hiddenWordCells);
//        System.out.println(enteredLetter);
//        System.out.println(hangmanStage);
//        System.out.println("Введённая буква есть в слове? " + enteredLetterInHiddenWord);
//        System.out.println("Введённые данные корректны?" + letterValid);
//        System.out.println(printedHiddenWordWithTrueLetter);
//        System.out.println(hangmanCounter);
//        System.out.print(hangmanStage);
//        System.out.println(mistakesLetters);
//        System.out.println("Игра окончена?" + gameOver);
//        System.out.println("Игрок отгадал слово?" + playerWin);
    }

    public static void gameLoop() {
        gameInit();
        String hiddenWord = letHiddenWord(words);
        String hiddenWordCells = printHiddenWordCells(hiddenWord);
        while (!isGameOver() && !isPlayerWin(hiddenWord, hiddenWordCells)) {
            String enteredLetter = inputLetter();
            isEnteredLetterValid(enteredLetter);
            isEnteredLetterInHiddenWord(enteredLetter, hiddenWord);
            String printedHiddenWordWithTrueLetter = printHiddenWordWithTrueLetter(hiddenWord, hiddenWordCells, enteredLetter);
            hiddenWordCells = printedHiddenWordWithTrueLetter;
            System.out.print(printHangmanStage());
            System.out.println(hiddenWordCells);
            System.out.println("Количество ошибок [" + hangmanCounter + "].");
            System.out.println(isPlayerWin(hiddenWord, hiddenWordCells));
        }
    }

    private static boolean isPlayerWin(String word, String wordCells) {
        boolean result = false;
        String[] wordArray = word.split("");
        String[] wordCellsArray = wordCells.split("");
        if (Arrays.equals(wordArray, wordCellsArray)) {
            result = true;
        }
        return result;
    }

    private static boolean isGameOver() {
        boolean result = false;
        if (hangmanCounter == Assets.hang.length) {
            result = true;
        }
        return result;
    }

    private static List<String> addMistakeLetterToList(boolean isLetterinWord, String letter) {
        List<String> mistakesLetters = new ArrayList<>();
        if (!isLetterinWord) {
            mistakesLetters.add(letter);
        }
        return mistakesLetters.stream()
                .distinct()
                .toList();
    }

    private static String printHiddenWordWithTrueLetter(String word, String wordCells, String letter) {
        String[] wordArray = word.split("");
        String[] wordArrayCells = wordCells.split("");
        for (int i = 0; i < wordArray.length; i++) {
            if (Objects.equals(letter, wordArray[i])) {
                wordArrayCells[i] = letter;
            }
        }
        return Arrays.toString(wordArrayCells).replace(", ", "").replace("]", "").replace("[", "");
    }

    private static boolean isEnteredLetterValid(String letter) {
        boolean result = false;
        char[] letterChar = letter.toCharArray();
        for (int i = 0; i < letterChar.length; i++) {
            if (!Character.isDigit(letterChar[i]) && letter.length() < 2) {
                result = true;
            }
        }
        return result;
    }

    private static String printHangmanStage() {
        String result = "";
        for (int i = 0; i < Assets.hang.length; i++) {
            if (hangmanCounter == i + 1) {
                result = Assets.hang[i];
            }
        }
        return result;
    }

    private static boolean isEnteredLetterInHiddenWord(String letter, String word) {
        boolean result = false;
        String[] wordArray = word.split("");
        for (int i = 0; i < wordArray.length; i++) {
            if (Objects.equals(letter, wordArray[i])) {
                result = true;
            }
        }
        if (!result) {
            hangmanCounter++;
        }
        return result;
    }

    private static String inputLetter() {
        System.out.println("Введите букву");
        return scanner.next();
    }

    private static String printHiddenWordCells(String word) {
        StringBuilder cells = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            cells.append('*');
        }
        return cells.toString();
    }

    private static String letHiddenWord(String[] wordsArray) {
        int i = random.nextInt(wordsArray.length);
        return wordsArray[i];
    }

    private static String gameInit() {
        String result = "";
        System.out.println("Начать новую игру? 1 - Да; 2 - Нет");
        String startGame = scanner.next();
        if (Objects.equals(startGame, "1")) {
            result = "Игра началась";
        } else if (Objects.equals(startGame, "2")) {
            result = "Вы вышли из игры";
        } else result = "Некорректный ввод";
        return result;
    }


}
