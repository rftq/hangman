import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static String[] words = {"калейдоскоп", "прокрастинация", "перпендикуляр", "метеорит", "фуникулёр", "симметрия"};
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();


    public static void main(String[] args) {
        System.out.println("Игра Виселица");
        init(words);
    }


    public static void checkWord(String hiddenWord, String wordCells) {
        String[] wordLetters = hiddenWord.split("");
        String[] wordCellsArray = wordCells.split("");
        int hangCounter = 0;
        String wordLetterStatus;
        boolean gameOver = false;
        while (!gameOver) {
            boolean pass = false;
            boolean repeat = false;
            String inputLetter = inputLetter();
            for (int i = 0; i < wordLetters.length; i++) {
                if (inputLetter.equals(wordLetters[i])) {
                    wordCellsArray[i] = wordLetters[i];
                    pass = true;
                }
                if (inputLetter.equals(wordCellsArray[i])) {
                    repeat = true;
                }
            }
            if (!pass) {
                hangCounter += 1;
            }

            for (int i = 0; i < Assets.hang.length; i++) {
                if (hangCounter == i + 1) {
                    System.out.print(Assets.hang[i]);
                }
            }
            if (repeat) {
                wordLetterStatus = "Буква [" + inputLetter + "] в слове есть.";
            } else {
                wordLetterStatus = "Буквы [" + inputLetter + "] в слове нет.";
            }
            System.out.println(wordLetterStatus + " Количество ошибок: " + hangCounter);
            System.out.println(Arrays.toString(wordCellsArray).replace(", ", "").replace("]", "").replace("[", ""));
            if (Arrays.equals(wordLetters, wordCellsArray)) {
                System.out.println("Вы отгадали слово");
                hangCounter = 0;
                init(words);
                gameOver = true;
            } else if (hangCounter == Assets.hang.length) {
                System.out.println("Вы проиграли, " + "загаданное слово было: " + hiddenWord);
                gameOver = true;
                init(words);
            }
        }
    }


    public static String inputLetter() {
        System.out.println("Введите букву");
        return scanner.next();
    }

    public static String printWordCells(String word) {
        String cells = "";
        for (int i = 0; i < word.length(); i++) {
            cells += "*";
        }
        return cells;
    }

    public static String letHiddenWord(String[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }

    public static void init(String[] array) {
        System.out.println("Начать новую игру? 1 - Да; 2 - Нет");
        int runoff = scanner.nextInt();
        if (runoff == 1) {
            String hiddenWord = letHiddenWord(array);
            String wordCells = printWordCells(hiddenWord);
            System.out.println("Игра началась");
            checkWord(hiddenWord, wordCells);
        } else {
            System.out.println("Вы вышли из игры");
        }
    }

}