package utility;

/**
 * Contains logic for checking recursion when execute_script is running
 *
 * @author ilestegor
 */
public class RecursionLimiter {
    public static int maxLevel = 10;

    /**
     * Checks depth of recursion, if it > 10 than command stops running and exception is thrown
     *
     * @throws RecursionException
     */
    public static void emerge() throws RecursionException {
        if (maxLevel == 0)
            return;
        try {
            throw new RecursionException("Выход из скрипта! Большая рекурсия");
        } catch (IllegalStateException | RecursionException e) {
            if (e.getStackTrace().length > maxLevel)
                throw e;
        }
    }
}
