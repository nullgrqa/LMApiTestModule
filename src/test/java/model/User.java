package model;

public class User {
    String customerNumber;
    String email;
    String phone;
    String firstName;
    String lastName;


    public String getCustomerNumber() {
        return customerNumber;
    }

    public User withCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "customerNumber='" + customerNumber + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getCustomerNumber() != null ? !getCustomerNumber().equals(user.getCustomerNumber()) : user.getCustomerNumber() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        return getLastName() != null ? getLastName().equals(user.getLastName()) : user.getLastName() == null;
    }

    @Override
    public int hashCode() {
        int result = getCustomerNumber() != null ? getCustomerNumber().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }
}
