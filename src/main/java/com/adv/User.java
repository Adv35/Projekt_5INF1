package com.adv;

/**
 * Die User-Klasse repraesentiert einen Benutzer der App.
 * Ein Objekt dieser Klasse entspricht einer Zeile in der Tabelle users in der Datenbank.
 * Benutzer koennen verschiedene Rollen haben (student, teacher, admin).
 *
 * @author Advik Vattamwar
 * @version 10.01.2026
 */

public class User {
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String passwordHash;
    private final String role;

    /**
     * Konstruktor mit userID (fuer wenn aus der Datenbank geladen wird)
     *
     **/
    public User(String userId, String firstName, String lastName, String username, String passwordHash, String role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;

    }

    /**
     * Konstruktor ohne userID (wenn in die Datenbank geladen werden muss)
     *
     **/
    public User(String firstName, String lastName, String username, String passwordHash, String role) {
        this(null, firstName, lastName, username, passwordHash, role);
    }

    /**
     * Getter-Methode.
     *
     * @return Gibt die userID zurueck.
     *
     **/
    public String getId() {
        return userId;
    }

    /**
     * Getter-Methode.
     *
     * @return Gibt den Vornamen zurueck.
     *
     **/
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter-Methode.
     *
     * @return Gibt den Nachnamen zurueck.
     *
     **/
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter-Methode.
     *
     * @return Gibt den Nutzernamen zurueck.
     *
     **/
    public String getUsername() {
        return username;
    }

    /**
     * Getter-Methode.
     *
     * @return Gibt das gehashte Passwort zurueck.
     *
     **/
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter-Methode.
     *
     * @return Gibt die Rolle des Nutzers zurueck.
     *
     **/
    public String getRole() {
        return role;
    }

    /**
     * Abbildung einer Note in einem String mit All seinen Werten ausser dem PasswortHash.
     *
     **/
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
