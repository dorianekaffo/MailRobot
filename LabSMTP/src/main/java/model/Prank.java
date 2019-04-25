package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doriane kaffo
 *
 */
public class Prank {

    private Person victimSender; // to avoid to have the sender in the list of recipient or ccrecipients
    private final List<Person> victimRecipients = new ArrayList<>();
    private final List<Person> ccRecepients = new ArrayList<>();
    private MailAddress message;// each victim group have a message

    /**
     *
     * @return The victim sender
     */
    public Person getVictimSender() {
        return victimSender;
    }

    /**
     * add the list recipient
     *
     * @param victimRecipients
     */
    public void addVictimRecipients(List<Person> victimRecipients) {
        this.victimRecipients.addAll(victimRecipients);
    }

    public void addccVictim(List<Person> witnessrecipient) {
        this.ccRecepients.addAll(witnessrecipient);
    }

    /**
     *
     * @return the prank message that we choice
     */
    public MailAddress getMessage() {
        return message;
    }

    /**
     *
     * @return the list of victims recipient
     */
    public List<Person> getVictimRecipient() {
        return victimRecipients;
    }

    public List<Person> getccRecipient() {
        return ccRecepients;
    }

    public void setMessage(MailAddress msg) {
        this.message = msg;
    }

    public void setVictimsender(Person victimesender) {
        this.victimSender = victimesender;
    }

}
