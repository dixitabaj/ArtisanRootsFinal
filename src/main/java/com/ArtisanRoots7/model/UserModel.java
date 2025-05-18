package com.ArtisanRoots7.model;

import java.time.LocalDate;

/**
 * Model class representing a User with personal and account details.
 */
public class UserModel {
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private LocalDate dob;
    private String email;
    private String role;
    private String password;
    private String gender;
    private String userImage;
    private LocalDate joinedDate;
    private String status;

    /**
     * Default constructor.
     */
    public UserModel() {
        // No-argument constructor
    }

    /**
     * Constructs a new UserModel with all required attributes.
     * 
     * @param userId     unique identifier for the user
     * @param firstName  user's first name
     * @param lastName   user's last name
     * @param username   username chosen by the user
     * @param phone      user's phone number
     * @param dob        user's date of birth
     * @param email      user's email address
     * @param role       role of the user (e.g., admin, customer)
     * @param password   user's password
     * @param gender     user's gender
     * @param joinedDate date the user joined
     * @param status     current status of the user (e.g., active, inactive)
     * @param userImage  filename or path of the user's profile image
     */
    
    public UserModel(int userId, String firstName, String lastName, String username, String phone,
                     LocalDate dob, String email, String role, String password, String gender,
                     LocalDate joinedDate, String status, String userImage) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.role = role;
        this.password = password;
        this.gender = gender;
        this.joinedDate = joinedDate;
        this.status = status;
        this.userImage = userImage;
    }

    /**
     * Returns the user's ID.
     * 
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's ID.
     * 
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the user's first name.
     * 
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     * 
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user's last name.
     * 
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     * 
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's phone number.
     * 
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the user's phone number.
     * 
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the user's date of birth.
     * 
     * @return the dob
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Sets the user's date of birth.
     * 
     * @param dob the dob to set
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Returns the user's email.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's role.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * 
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the user's password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's gender.
     * 
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender.
     * 
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the user's profile image path or name.
     * 
     * @return the userImage
     */
    public String getUserImage() {
        return userImage;
    }

    /**
     * Sets the user's profile image path or name.
     * 
     * @param userImage the userImage to set
     */
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    /**
     * Returns the date when the user joined.
     * 
     * @return the joinedDate
     */
    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    /**
     * Sets the date when the user joined.
     * 
     * @param joinedDate the joinedDate to set
     */
    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    /**
     * Returns the user's current status.
     * 
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the user's current status.
     * 
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
