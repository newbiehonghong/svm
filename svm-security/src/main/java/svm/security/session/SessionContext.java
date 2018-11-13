package svm.security.session;

public class SessionContext {
    public static final ThreadLocal<UserSession> context = new ThreadLocal<UserSession>();

    public static UserSession get() {
        return context.get();
    }

    public static void set(UserSession userSession) {
        context.set(userSession);
    }

    public static void clear() {
        context.remove();
    }
}
