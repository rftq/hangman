import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main2 {
    static String[] words = {"калейдоскоп", "прокрастинация", "перпендикуляр", "метеорит", "фуникулёр", "симметрия"};
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static int hangmanCounter = 0;


    public static void main(String[] args) {

        String hiddenWord = letHiddenWord(words);
        String hiddenWordCells = printHiddenWordCells(hiddenWord);
        String enteredLetter = inputLetter();
        boolean isValid = isEnteredLetterValid(enteredLetter);
        boolean EnteredLetterInHiddenWord = isEnteredLetterInHiddenWord(enteredLetter, hiddenWord);
        String hangmanStage = printHangmanStage();


        // проверки:
        System.out.println(hiddenWordCells);
        System.out.println(enteredLetter);
        System.out.println(hangmanStage);
        System.out.println(EnteredLetterInHiddenWord);
        System.out.println(isValid);
        System.out.println(printHiddenWordWithTrueLetter(hiddenWord, hiddenWordCells, enteredLetter));
        System.out.println(hangmanCounter);
        System.out.println(hangmanStage);

    }


    private static String printHiddenWordWithTrueLetter(String word, String wordCells, String letter) {
        String[] wordArray = word.split("");
        String[] wordArrayCells = wordCells.split("");
        for (int i = 0; i < wordArray.length; i++) {
            if (Objects.equals(letter, wordArray[i])) {
                wordArrayCells[i] = letter;
            }
        }
        return Arrays.toString(wordArrayCells);
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

    private static void gameInit() {
        System.out.println("Начать новую игру? 1 - Да; 2 - Нет");
        String startGame = scanner.next();
        while (true) {
        }
    }


}
