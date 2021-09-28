package io.github.jeyhung.shared;

public class UserContextHolder {
    private static final ThreadLocal<Long> USER_CONTEXT = new ThreadLocal<>();

    public static void setUserId(long userId) {
        USER_CONTEXT.set(userId);
    }

    public static long getUserId() {
        return USER_CONTEXT.get();
    }

    public static void clear() {
        USER_CONTEXT.remove();
    }
}
