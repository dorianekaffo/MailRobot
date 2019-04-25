package model;

/**
 *
 * @author dorianekaffo
 */
public class Person {

    private String address;
    private String firstname;
    private String lastname;

    public Person(String address, String firstname, String lastname) {
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
