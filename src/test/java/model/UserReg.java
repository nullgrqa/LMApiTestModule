package model;

public class UserReg {

    private String lastName;
    private String firstName;
    private int refStoreId;
    private String email;
    private String phone;
    private String password;

    public String getLastName() {
        return lastName;
    }

    public UserReg withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserReg withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public int getRefStoreId() {
        return refStoreId;
    }

    public UserReg withRefStoreId(int refStoreId) {
        this.refStoreId = refStoreId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserReg withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserReg withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserReg withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "UserReg{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", refStoreId=" + refStoreId +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserReg)) return false;

        UserReg userReg = (UserReg) o;

        if (getRefStoreId() != userReg.getRefStoreId()) return false;
        if (getLastName() != null ? !getLastName().equals(userReg.getLastName()) : userReg.getLastName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(userReg.getFirstName()) : userReg.getFirstName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(userReg.getEmail()) : userReg.getEmail() != null) return false;
        if (getPhone() != null ? !getPhone().equals(userReg.getPhone()) : userReg.getPhone() != null) return false;
        return getPassword() != null ? getPassword().equals(userReg.getPassword()) : userReg.getPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = getLastName() != null ? getLastName().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + getRefStoreId();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }
}
