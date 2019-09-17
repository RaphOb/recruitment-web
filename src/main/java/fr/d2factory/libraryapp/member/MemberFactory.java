package fr.d2factory.libraryapp.member;

public class MemberFactory {

    public static Member getMember(String memb) {
        if (memb.equalsIgnoreCase("NEIGHBOURS")) {
            return new Neighbours();
        } else if (memb.equalsIgnoreCase("STUDENT")) {
            return new Student();
        }
        return null;
    }
}
