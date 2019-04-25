package configuration;

import java.io.IOException;
import java.util.List;
import model.MailAddress;
import model.Person;

/**
 *
 * @author doriane kaffo
 */
public interface IConfiguration {

    public List<MailAddress> loadDataFromFile(String fileName) throws IOException;

    public List<Person> loadAddressFromFile(String fileName) throws IOException;

    public void loadPropertie(String fileName) throws IOException;

    public String getServerAddress();

    public int getServerport();

    public List<Person> getVictim();

    public List<MailAddress> getMessage();

    public int getNumberOfGroup();

}
