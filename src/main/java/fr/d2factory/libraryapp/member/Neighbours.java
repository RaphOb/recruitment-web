package fr.d2factory.libraryapp.member;

public class Neighbours extends Member {

    //constantes
    public static final int NEIGHBOURS_MAX_DAY = 60;
    public static final float REGULAR_PRICE = 0.10f;
    public static final float FINE = 0.20f;


    @Override
    public float payBook(int numberOfDays) {
        if (numberOfDays > NEIGHBOURS_MAX_DAY) {
            return ((numberOfDays * REGULAR_PRICE) + ((numberOfDays - NEIGHBOURS_MAX_DAY) * FINE));
        } else {
            return (numberOfDays * REGULAR_PRICE);
        }
    }


}
