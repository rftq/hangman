
import java.util.*;

public class Main_v2 {
    static String[] words = {"калейдоскоп", "прокрастинация", "перпендикуляр", "метеорит", "фуникулёр", "симметрия", "адъютант"};
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static int hangmanCounter = 0;
    static List<String> mistakesLetters = new ArrayList<>();

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

    public static void gameLoop() {
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
                System.out.println("Количество ошибок [" + hangmanCounter + "]. " + "Неверные буквы " + mistakesLetters);
                if (isPlayerWin(hiddenWord, hiddenWordCells)) {
                    System.out.println("Вы отгадали слово");
                }
                if (isGameOver(hiddenWord)) {
                    System.out.println("Вы проиграли, загаданное слово было: " + hiddenWord);
                }
            } else System.out.println("Некорректный ввод");
        }
        hangmanCounter = 0;
        mistakesLetters.clear();
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

    private static boolean isGameOver(String word) {
        boolean result = false;
        if (hangmanCounter == Assets.hang.length || hangmanCounter >= word.length()) {
            result = true;
        }
        return result;
    }

    private static void addMistakeLetterToListAndIncrementHangmanCounter(boolean isLetterInWord, String letter) {
        if (!isLetterInWord && !mistakesLetters.contains(letter)) {
            mistakesLetters.add(letter);
            hangmanCounter++;
        }
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
            if (!Character.isDigit(letterChar[i]) && letter.length() < 2 && letter.matches("^[а-яёА-ЯЁ]+$")) {
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

}
