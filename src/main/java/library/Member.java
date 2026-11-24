package library;

public class Member {
    private final String number;
    private final String name;

    public Member(String number, String name) {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Member number cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Member name cannot be null or blank");
        }
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
