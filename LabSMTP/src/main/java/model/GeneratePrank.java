package model;

import configuration.IConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author doriane kaffo
 */
public class GeneratePrank {

    private IConfiguration configuration;
    private List<Person> availableVictims = new LinkedList<>();
    private final static int MIN_NUMBER_VICTIMS = 5;

    public GeneratePrank(IConfiguration config) {
        this.configuration = config;

    }

    /**
     * This method is use to have the different group that we use to do a prank
     *
     * @param victims
     * @param numberGroup
     * @return list of group
     */
    public List<Group> generateGroup(List<Person> victims, int numberGroup) {

        Collections.shuffle(victims); //to swap the victim (to avoid the stansrt position)

        //initialise the list of map to indentifie the victim and group
        int j = 0;
        for (Person pers : victims) {
            //store to be the real victim (the victim in the list is'nt a victin absolutly)
            availableVictims.add(pers); 
        }

        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberGroup; i++) {
            //we simple create the groupe
            Group group = new Group();
            groups.add(group);
        }

        int index = 0;

        Group victimsGroup;

        for (; availableVictims.size() > 0;) {
            //for the first group
            victimsGroup = groups.get(index);
            // remove the first victim to avoid to use aigaint
            Person victim = availableVictims.remove(0);
            //we add the victim
            groups.get(index).addMember(victim);
            // % to avoid to go out of list (% ist cyclic)
            index = (index + 1) % groups.size();
        }
        return groups; // group with her victim
    }

    /**
     * This method is use to generate the prank and store it a list
     *
     * @return
     */
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<MailAddress> messages = configuration.getMessage();
        int indexMessage = 0; // to browse the message list
        int groupNumber = configuration.getNumberOfGroup();
        int victimsNumber = configuration.getVictim().size();

        // we check the number of victim because it is not necessary to create many group that we dont use
        //we dont want to have the group of one victim
        if (victimsNumber / groupNumber < MIN_NUMBER_VICTIMS) {
            groupNumber = victimsNumber / MIN_NUMBER_VICTIMS;
        }
        //it take the default group. Every group have her victim
        List<Group> groups = generateGroup(configuration.getVictim(), groupNumber);
        Person senderMail = null;
        int i = 0;

        // each group have a pran
        for (Group group : groups) {
            Prank myPrank = new Prank();
            //On the member of group, we take the victim
            List<Person> victim = group.getmenbers();
            Collections.shuffle(victim); // to avoid to have the fist victim have the first message

            if (!victim.isEmpty()) {//Verify that the list isn't empty message
                senderMail = victim.remove(0); // to avoid to have the sender mail in the victim receipient
            }

            myPrank.addVictimRecipients(victim);// we add the cc recpients

            MailAddress message = messages.get(indexMessage);// to take the message 
            indexMessage = (indexMessage + 1) % messages.size();// to take another message 
            myPrank.setVictimsender(senderMail);
            myPrank.setMessage(message);
            pranks.add(myPrank); // add the prank in the list of prank
        }
        return pranks;
    }

}
