package fr.d2factory.libraryapp.member;

public class Student extends Member {


    //Constantes
    public static final int STUDENT_MAX_DAY = 30;
    public static final int STUDENT_FREE = 15;
    public static final float REGULAR_PRICE = 0.10f;
    public static final float FINE = 0.15f;

    @Override
    public float payBook(int numberOfDays) {

        if (seniority > 1) {
            if (numberOfDays > STUDENT_MAX_DAY) {
                return ((STUDENT_MAX_DAY * REGULAR_PRICE) + ((numberOfDays - STUDENT_MAX_DAY) * FINE));
            } else {
                return (numberOfDays * REGULAR_PRICE);
            }
        } else {
            if (numberOfDays > STUDENT_MAX_DAY) {
                return ((STUDENT_FREE * REGULAR_PRICE) + (numberOfDays - STUDENT_MAX_DAY) * FINE);
            } else {
                return (((numberOfDays - STUDENT_FREE) * REGULAR_PRICE));
            }
        }
    }


    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

}
