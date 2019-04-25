/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import model.MailAddress;
import model.Person;

/**
 *
 * @author dorianekaffo
 */
public class Configuration implements IConfiguration {

    private String smtpServerAddress;
    private int serverport;
    private final List<Person> victims;
    private final List<MailAddress> messages;
    private int numberOfGroup;
    private String subject = null;
    public final static String CHARSET = "UTF-8";
    public final static String SERVER_ADR = "smtpserverAddress";
    public final static String SERVER_PORT = "smtpServerport";
    public final static String GROUP_NUMBER = "numberOfGroup";

    public Configuration() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        victims = loadAddressFromFile("victim.utf8");
        messages = loadDataFromFile("message.utf8");
        loadPropertie("configuration.properties");

    }

    //read the properties 
    public void loadPropertie(String fileName) throws IOException {
        FileInputStream f = new FileInputStream(fileName);
        Properties properpties = new Properties();
        properpties.load(f);
        //read propertie since the file configuration
        this.smtpServerAddress = properpties.getProperty(SERVER_ADR);
        this.serverport = Integer.parseInt(properpties.getProperty(SERVER_PORT));
        this.numberOfGroup = Integer.parseInt(properpties.getProperty(GROUP_NUMBER));
    }

    /**
     * The buffuring to have best perfomance in the modification of string this
     * methode Read the file and return the list of adress of different victim
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public List<MailAddress> loadDataFromFile(String fileName) throws IOException {

        List<MailAddress> result = new LinkedList<>();

        try (FileInputStream filname = new FileInputStream(fileName)) {

            InputStreamReader fls = new InputStreamReader(filname, CHARSET);
            try (BufferedReader reader = new BufferedReader(fls)) {
                String line = reader.readLine();
                while (line != null) {
                    MailAddress m = new MailAddress();
                    StringBuilder body = new StringBuilder();
                    //to separate the different message
                    while ((line != null) && (!line.equals("**"))) {

                        if (line.indexOf("subject") != -1) {
                            String str[] = line.split(":");
                            m.setSubject(str[1]);
                        } else {
                            body.append(line);
                            body.append("\r\n");
                        }
                        m.setbody(body.toString());
                        line = reader.readLine();
                    }

                    result.add(m);

                    line = reader.readLine();
                }
                return result;
            }
        }
    }

    /**
     * read the adress mail To extract first first name and last name
     *
     * @param fileName le nom du fichier contenant les addresses des victims
     * @return the list of peronne store in the configuration file
     * @throws IOException
     */
    public List<Person> loadAddressFromFile(String fileName) throws IOException {
        List<Person> result;
        String tmp = null;

        try (FileInputStream f = new FileInputStream(fileName)) {
            InputStreamReader fls = new InputStreamReader(f, CHARSET);

            try (BufferedReader reader = new BufferedReader(fls)) {
                result = new LinkedList<>();
                String address = reader.readLine();
                while (address != null) {
                    String[] extract = address.split("@");// to extract the name and first name
                    //split to table the first element of table is the name and the second is the firstname
                    extract = extract[0].split("\\.");// extract .

                    if (extract.length == 2) { //to respect the exact form adress mail
                        tmp = extract[1];// to avoid planting the programm
                    }
                    result.add(new Person(address, extract[0], tmp));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    /**
     *
     * @return the server address
     */
    public String getServerAddress() {
        return smtpServerAddress;
    }

    /**
     *
     * @return the nomber of port of the server
     */
    public int getServerport() {
        return serverport;
    }

    /**
     *
     * @return the list of victim
     */
    public List<Person> getVictim() {
        return victims;
    }

    /**
     * @return the nomber of group
     */
    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    /**
     * @return list of message
     */
    public List<MailAddress> getMessage() {
        return messages;
    }

}
