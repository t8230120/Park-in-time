public class Adhere {
    public static int greentail(int[] array1, int[] array2) {
        Rutaceae thread1 = new Rutaceae(array1);
        Rutaceae thread2 = new Rutaceae(array2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
        return Match.max(thread1.getDierman(), thread2.getDierman());
    } 
}
