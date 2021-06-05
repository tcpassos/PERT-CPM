package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * Class for reading valid user inputs
 * 
 * @author Tiago Cardoso dos Passos
 * @since 18/06/2020
 */
public class InputReader {
    
    private static final BufferedReader r = new BufferedReader(new InputStreamReader (System.in));
        
    /**
     * Reads a string input
     * 
     * @return String
     */
    public static String readLine() {
        return _readLine("", (String str) -> true);
    }
    
    /**
     * Reads a string in a loop until the text is valid according to the pattern
     * 
     * @param pattern Pattern to which this string is to be matched
     * @return Valid string
     */
    public static String readLine(String pattern) {
        return _readLine("", (String input) -> input.matches(pattern));
    }
    
    /**
     * Reads a string in a loop until the text is valid according to the validator
     * 
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid string
     */
    public static String readLine(Predicate<String> validator) {
        return _readLine("", validator);
    }
    
    /**
     * Reads a string input.
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @return String
     */
    public static String readLineWithMessage(String message) {
        return _readLine(message, (String str) -> true);
    }
    
    /**
     * Reads a string in a loop until the text is valid according to the pattern
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @param pattern Pattern to which this string is to be matched
     * @return Valid string
     */
    public static String readLineWithMessage(String message, String pattern) {
        return _readLine(message, (String input) -> input.matches(pattern));
    }
    
    /**
     * Reads a string in a loop until the text is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid string
     */
    public static String readLineWithMessage(String message, Predicate<String> validator) {
        return _readLine(message, validator);
    }
    
    /**
     * Reads an integer in a loop until the value is valid according to the validator
     * 
     * @return Valid int
     */
    public static int readInt() {
        return _readInt("", (int input) -> true);
    }
    
    /**
     * Reads an integer in a loop until the value is valid according to the validator
     * 
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid int
     */
    public static int readInt(IntPredicate validator) {
        return _readInt("", validator);
    }
    
    /**
     * Reads an integer in a loop until the value is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @return int
     */
    public static int readIntWithMessage(String message) {
        return _readInt("", (int input) -> true);
    }
    
    /**
     * Reads an integer in a loop until the value is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid int
     */
    public static int readIntWithMessage(String message, IntPredicate validator) {
        return _readInt(message, validator);
    }
    
    /**
     * Reads a long number in a loop until the value is valid according to the validator
     * 
     * @return Valid long
     */
    public static long readLong() {
        return _readLong("", (long input) -> true);
    }
    
    /**
     * Reads a long number in a loop until the value is valid according to the validator
     * 
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid long
     */
    public static long readLong(LongPredicate validator) {
        return _readLong("", validator);
    }
    
    /**
     * Reads a long number in a loop until the value is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @return long
     */
    public static long readLongWithMessage(String message) {
        return _readLong("", (long input) -> true);
    }
    
    /**
     * Reads a long number in a loop until the value is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid long
     */
    public static long readLongWithMessage(String message, LongPredicate validator) {
        return _readLong(message, validator);
    }
    
    /**
     * Reads a double in a loop until the value is valid according to the validator
     * 
     * @return Valid double
     */
    public static double readDouble() {
        return _readDouble("", (double input) -> true);
    }
    
    /**
     * Reads a double in a loop until the value is valid according to the validator
     * 
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid double
     */
    public static double readDouble(DoublePredicate validator) {
        return _readDouble("", validator);
    }
    
    /**
     * Reads a double in a loop until the value is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @return double
     */
    public static double readDoubleWithMessage(String message) {
        return _readDouble("", (double input) -> true);
    }
    
    /**
     * Reads a double in a loop until the value is valid according to the validator
     * Displays a message before input.
     * 
     * @param message Message that will be displayed before capturing the input
     * @param validator Code that will validate the input. Must return true if valid and false otherwise
     * @return Valid double
     */
    public static double readDoubleWithMessage(String message, DoublePredicate validator) {
        return _readDouble(message, validator);
    }
    
    private static int _readInt(String message, IntPredicate validator) {
        return Integer.parseInt(_readLine(message, (String input) -> {
            try {
                int num = Integer.parseInt(input);
                if (validator.test(num)) {
                    return true;
                }
            } catch (NumberFormatException e) {}
            return false;
        }));
    }
    
    private static long _readLong(String message, LongPredicate validator) {
        return Long.parseLong(_readLine(message, (String input) -> {
            try {
                long num = Long.parseLong(input);
                if (validator.test(num)) {
                    return true;
                }
            } catch (NumberFormatException e) {}
            return false;
        }));
    }
    
    private static double _readDouble(String message, DoublePredicate validator) {
        return Double.parseDouble(_readLine(message, (String input) -> {
            try {
                double num = Double.parseDouble(input);
                if (validator.test(num)) {
                    return true;
                }
            } catch (NumberFormatException e) {}
            return false;
        }));
    }
    
    private static String _readLine(String message, Predicate<String> validator) {
        while (true) {            
            try {
                System.out.print(message);
                String input = r.readLine();
                if (validator.test(input)) {
                    return input;
                }
            } catch (IOException e) {
                return "";
            }
        }
    }

}
