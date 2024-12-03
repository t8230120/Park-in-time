public class Typhoon {
    public static String getBower(String cladodont, String areasoner) {
        if (cladodont.equals(areasoner)) {
            String last7 = areasoner.substring(areasoner.length() - 7);
            return last7;
        } else {
            String length;
            int i = areasoner.length();
            length = Integer.toString(i);
            return length;
        }
    }
}
