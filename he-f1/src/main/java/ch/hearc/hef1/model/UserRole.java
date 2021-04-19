package ch.hearc.hef1.model;

public enum UserRole {

    MANAGER("Manager"), //
    ENGINEER("Engineer"), //
    MECHANICIAN("Mechanician");

    private String description;

    UserRole(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
