package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doriane kaffo
 */
public class MailAddress {

    private String from;
    private String address;
    private final List<String> to = new ArrayList<>();
    private final List<String> cc = new ArrayList<>();
    String Subject;
    String body;
    String trueName; 

    /**
     *
     * @return the sender address
     */
    public String getFrom() {
        return this.from;
    }

    /**
     *
     * @return the body message
     */
    public String getBody() {
        return this.body;
    }

    /**
     *
     * @param return the value of the address of To
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * @param retourn the subjet
     */
    public String getSubject() {
        return Subject;
    }

    /**
     *
     * @param the body of To
     */
    public void setTo(List<String> s) {
        this.to.addAll(s);
    }

    /**
     *
     * @param from to modifie the sender
     */
    public void SetFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @param s the list of receiver
     */
    public void setCc(List<String> s) {
        this.cc.addAll(s);
    }

    /**
     * modifie le coprs du message
     *
     * @param body
     */
    public void setbody(String body) {
        this.body = body;
    }

    public void setSubject(String subject) {
        this.Subject = subject;
    }

    /**
     *
     * @return the address email of user
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address email (change the address field)
     */
    public void setAdress(int index) {
        this.address = to.get(index);
    }

    /**
     *
     * @param realName the name that exist
     */
    public void setTrueName(String realName) {
        this.trueName = trueName;
    }

    /**
     *
     * @return the name that exist
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * This method ist to have the address on the form name <address>
     * @return buffer sting
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        if (trueName != null) {
            buffer.append("\"").append(trueName).append("\"");
        }
        if (address != null) {
            buffer.append("<").append(address).append(">");
        } else {
            return null;
        }
        return buffer.toString();
    }
}
