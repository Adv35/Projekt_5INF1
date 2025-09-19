package com.adv;

public class User {
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String passwordHash;
    private final String email;
    private final String role;

    public User(String userId,String firstName, String lastName, String username, String passwordHash, String email, String role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;

    }

    public User(String firstName, String lastName, String username, String passwordHash, String email, String role) {
        this(null, firstName, lastName, username, passwordHash, email, role);
    }

    public String getId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", username='" + username + '\'' +
                ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               '}';
    }

}
