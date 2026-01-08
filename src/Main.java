
import java.util.*;

public class Main {
    static String[] words = {"калейдоскоп", "прокрастинация", "перпендикуляр", "метеорит", "фуникулёр", "симметрия", "адъютант"};
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static int hangmanCounter = 0;
    static List<String> mistakeLetters = new ArrayList<>();

    public static void main(String[] args) {
        gameInit();
    }

    private static void gameInit() {
        while (true) {
            System.out.println("Начать новую игру? 1 - Да; 2 - Нет");
            String startGame = scanner.next();
            if (Objects.equals(startGame, "1")) {
                System.out.println("Игра началась");
                gameLoop();
            } else if (Objects.equals(startGame, "2")) {
                System.out.println("Вы вышли из игры");
                break;
            } else
                System.out.println("Некорректный ввод");
        }
    }

    private static void gameLoop() {
        String hiddenWord = letHiddenWord(words);
        String hiddenWordCells = printHiddenWordCells(hiddenWord);
        System.out.println(hiddenWordCells);
        while (!isGameOver(hiddenWord) && !isPlayerWin(hiddenWord, hiddenWordCells)) {
            String enteredLetter = inputLetter();
            if (isEnteredLetterValid(enteredLetter)) {
                boolean enteredLetterInHiddenWord = isEnteredLetterInHiddenWord(enteredLetter, hiddenWord);
                addMistakeLetterToListAndIncrementHangmanCounter(enteredLetterInHiddenWord, enteredLetter);
                String printedHiddenWordWithTrueLetter = printHiddenWordWithTrueLetter(hiddenWord, hiddenWordCells, enteredLetter);
                hiddenWordCells = printedHiddenWordWithTrueLetter;
                System.out.print(printHangmanStage());
                System.out.println(hiddenWordCells);
                printHangCounterWithMistakeLetters();
                if (isPlayerWin(hiddenWord, hiddenWordCells)) {
                    System.out.println("Вы отгадали слово");
                }
                if (isGameOver(hiddenWord)) {
                    System.out.println("Вы проиграли, загаданное слово: " + hiddenWord);
                }
            } else System.out.println("Некорректный ввод");
        }
        hangmanCounter = 0;
        mistakeLetters.clear();
    }

    private static boolean isPlayerWin(String hiddenWord, String hiddenWordCells) {
        boolean result = false;
        String[] wordArray = hiddenWord.split("");
        String[] wordCellsArray = hiddenWordCells.split("");
        if (Arrays.equals(wordArray, wordCellsArray)) {
            result = true;
        }
        return result;
    }

    private static boolean isGameOver(String hiddenWord) {
        boolean result = false;
        if (hangmanCounter == Assets.hang.length || hangmanCounter >= hiddenWord.length()) {
            result = true;
        }
        return result;
    }

    private static void printHangCounterWithMistakeLetters() {
        if (mistakeLetters.isEmpty()) {
            System.out.println("Количество ошибок [" + hangmanCounter + "].");
        } else {
            System.out.println("Количество ошибок [" + hangmanCounter + "]. " + "Неверные буквы " + mistakeLetters);
        }
    }

    private static void addMistakeLetterToListAndIncrementHangmanCounter(boolean enteredLetterInHiddenWord, String enteredLetter) {
        if (!enteredLetterInHiddenWord && !mistakeLetters.contains(enteredLetter)) {
            mistakeLetters.add(enteredLetter);
            hangmanCounter++;
        }
    }

    private static String printHiddenWordWithTrueLetter(String hiddenWord, String hiddenWordCells, String enteredLetter) {
        String[] wordArray = hiddenWord.split("");
        String[] wordArrayCells = hiddenWordCells.split("");
        for (int i = 0; i < wordArray.length; i++) {
            if (Objects.equals(enteredLetter, wordArray[i])) {
                wordArrayCells[i] = enteredLetter;
            }
        }
        return Arrays.toString(wordArrayCells).replace(", ", "").replace("]", "").replace("[", "");
    }

    private static boolean isEnteredLetterValid(String enteredLetter) {
        boolean result = false;
        char[] letterChar = enteredLetter.toCharArray();
        for (int i = 0; i < letterChar.length; i++) {
            if (!Character.isDigit(letterChar[i]) && enteredLetter.length() < 2 && enteredLetter.matches("^[а-яёА-ЯЁ]+$")) {
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

    private static boolean isEnteredLetterInHiddenWord(String enteredLetter, String hiddenWord) {
        boolean result = false;
        String[] wordArray = hiddenWord.split("");
        for (int i = 0; i < wordArray.length; i++) {
            if (Objects.equals(enteredLetter, wordArray[i])) {
                result = true;
            }
        }
        return result;
    }

    private static String inputLetter() {
        System.out.println("Введите букву");
        return scanner.next();
    }

    private static String printHiddenWordCells(String hiddenWord) {
        StringBuilder cells = new StringBuilder();
        for (int i = 0; i < hiddenWord.length(); i++) {
            cells.append('*');
        }
        return cells.toString();
    }

    private static String letHiddenWord(String[] words) {
        int i = random.nextInt(words.length);
        return words[i];
    }

}
