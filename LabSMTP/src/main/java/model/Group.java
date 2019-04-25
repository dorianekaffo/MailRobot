package model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dorianekaffo
 *
 */
public class Group {

    private List<Person> members = new LinkedList<>();

    /**
     *
     * @param pers to add a person to a group
     */
    public void addMember(Person pers) {
        members.add(pers);
    }

    /**
     *
     * @return the members of group
     */
    public List<Person> getmenbers() {
        return members;
    }

}
