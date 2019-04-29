package ch.heigvd.res.AutomaticalMail.SMTP;

import configuration.Configuration;
import static configuration.Configuration.CHARSET;
import configuration.IConfiguration;
import model.MailAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import model.GeneratePrank;
import model.Person;
import model.Prank;

/**
 *
 * @author doriane kaffo
 */
public class FormelMail {

    private static Object address;
    private static final String CONNECT = "CONNECT";
    private static final String EHLO = "EHLO ";
    private static final String MAIL_FROM = "MAIL FROM: ";
    private static final String RCPT_TO = "RCPT TO: ";
    private static final String DATA = "DATA: ";
    private static final String QUIT = "QUIT: ";
    private final static int POSITIVE_RESPONSE = 2;
    private final static int DATA_RESPONSE = 3;

    public FormelMail() {
        super();
    }

    public void sendPrank(String serverName, int port, Prank myPrank)
            throws SmtpException, UnknownHostException, IOException {
        System.out.println(serverName + " " + port);
        serverAddress = InetAddress.getByName(serverName);
        System.out.println(serverAddress.getHostName() + " " + port);
        this.pran = myPrank;
        serverport = port;
        mail = this.pran.getMessage();
        message = mail.getBody();
        senderAddr = this.pran.getVictimSender();
        this.subject = mail.getSubject();
        send();
    }

    protected void send() throws SmtpException, IOException {

        Socket mySocket = null;

        try {
            trace("connection to the server.....");// trace: pour la visibilité de l'affichage sur netbeans

            mySocket = new Socket(serverAddress, serverport);

            BufferedReader reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream(), CHARSET));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream(), CHARSET));

            int replyCode = getResponse(reader);
            System.out.println(serverAddress.getHostName());

            if (replyCode / 100 != POSITIVE_RESPONSE) { // to check if the response server ok isn't 2
                throw new SmtpException(CONNECT, replyCode, lastcommandResonseOfServer);
            }

            sendcommand(writer, EHLO + InetAddress.getLocalHost().getHostName(), reader, POSITIVE_RESPONSE);
            sendcommand(writer, MAIL_FROM + senderAddr.getAddress(), reader, POSITIVE_RESPONSE);

            for (int i = 0; i < pran.getVictimRecipient().size(); ++i) {
                sendcommand(writer, RCPT_TO + pran.getVictimRecipient().get(i).getAddress(), reader, POSITIVE_RESPONSE);
            }
            sendcommand(writer, DATA, reader, DATA_RESPONSE);
            sendMail(writer);
            replyCode = getResponse(reader);
            if (replyCode / 100 != POSITIVE_RESPONSE) {  //to check if the code response is 3 to begin the sending data
                throw new SmtpException("You can send your data", replyCode, lastcommandResonseOfServer);
            }
            //to close conversation
            sendcommand(writer, QUIT, reader, POSITIVE_RESPONSE);
        } catch (IOException | SmtpException ex) {
            if (mySocket != null) {
                mySocket.close();
            }
            throw ex;
        }

    }

    /**
     *
     * When we want connect to the smtp server, It return a code that allow to
     * know if it accept or not tobegin the comminication
     *
     * @param reader Inputstream for smtp server
     * @return the code SMTP and the last command response of the server
     * @throws java.io.IOException
     */
    protected int getResponse(BufferedReader reader) throws IOException {
        int codeSMTP;
        boolean EndOfResponse;
        String line;
        StringBuilder text = new StringBuilder();


        do {
            line = reader.readLine();
            System.out.println("je suis dans get response");
            trace("response: " + line);


            EndOfResponse = (line.charAt(3) == '-');
            //to have the string part
            text.append((line.substring(4, line.length())));
        } while (EndOfResponse);
        // parse string an return an integer
        codeSMTP = Integer.parseInt(line.substring(0, 3));

        lastcommandResonseOfServer = text.toString();

        return codeSMTP;

    }

    /**
     * If the first carcter (code smtp) that return the server isn't valid we
     * retrun an exceptiom
     *
     * @param writer Outputstream for smtp server
     * @param sendCommad
     * @param reader Inputstream for smtp server
     * @param trueCode
     * @exception IOException error during communication
     * @throws ch.heigvd.res.AutomaticalMail.SMTP.SmtpException
     */
    protected void sendcommand(PrintWriter writer, String sendCommad, BufferedReader reader, int trueCode)
            throws SmtpException, IOException {

        trace("send: " + sendCommad);
        writer.write(sendCommad);
        writer.write("\r\n");
        writer.flush();

        int returnCode = getResponse(reader);

        if (returnCode / 100 != trueCode) {
            throw new SmtpException(sendCommad, trueCode, lastcommandResonseOfServer);
        }

    }

    /**
     * send mail with the formel mail SMTP (fom, to, cc, subjet)
     *
     * @param writer Outputstream
     */
    protected void sendMail(PrintWriter writer) {
        // Header of mail
        writer.write("From: " + senderAddr.getAddress() + "\r\n");
        writer.write("TO: " + pran.getVictimRecipient().get(0).getAddress());

        for (int i = 0; i < pran.getVictimRecipient().size(); ++i) {
            writer.write("," + pran.getVictimRecipient().get(i).getAddress());
        }

        writer.write("\r\n");
        writer.write("Cc: " + pran.getVictimRecipient().get(0).getAddress());

        for (int i = 0; i < pran.getVictimRecipient().size(); ++i) {
            writer.write("," + pran.getVictimRecipient().get(i).getAddress());
        }

        writer.write("\r\n");
        writer.write("Subject: " + mail.getSubject() + "\r\n");
        writer.write("\r\n"); // end of the email header

        //  String mailContent = formelMessageSmtp(message); //message to send
        // trace(mailContent);
        //  writer.write(mailContent);

        //the end of SMTP message is  <CR/LF>.<CR/LF>
        trace(".");
        writer.write("\r\n.\r\n");
        writer.flush();
    }

    /*   *//**
     *
     * verification of the true formel message SMTP
     *
     * @param message message to convert
     * @return converted message
     *//*
    protected String formelMessageSmtp(String message) {
        StringBuilder buffer = new StringBuilder();
        String line;
        int start = 0;
        int end = 0;
        if (message != null) {
            buffer.ensureCapacity(message.length() + 100);
            do {
                end = message.indexOf('\n', start);
                if (end == -1) {
                    line = message.substring(start);
                } else {
                    line = message.substring(start, end);
                    end++;
                }

                if (line.length() > 0 && line.charAt(0) == '.') {
                    buffer.append('.');
                }

                buffer.append(line);
                if (end != -1) {
                    buffer.append("\r\n");
                }

                start = end;
            } while (end != -1);
        }

        return buffer.toString();
    }*/





    public void debugMode(boolean debug) {
        this.debug = debug;
    }

    /**
     * Output: if deburg is actif, we gebug the string
     *
     * @param sTrace: the string that we want to trace
     */
    protected void trace(String sTrace) {
        if (debug) {
            System.err.println("D:" + sTrace);
        }
    }
    private boolean debug;
    private InetAddress serverAddress = null; //The server address smtp
    private int serverport = -1; //The client use the port
    private Person senderAddr;
    private MailAddress mail = new MailAddress();
    private Prank pran = null;
    private String message = null;
    String lastcommandResonseOfServer = null;// to store the response of the last command of server (help to debug)
    private String subject = null; // the subject of the mail to send 

    public static void main(String[] argv) throws IOException {

        // take the mail Content to generate the prank
        IConfiguration configurations = new Configuration();

        //list of prank
        List<Prank> pranks = new LinkedList<>();

        GeneratePrank generPranks = new GeneratePrank(configurations);

        //genrate the list of prank
        pranks = generPranks.generatePranks();

        //The name of server
        String serverName = configurations.getServerAddress();

        //The number of port
        int serverPort = configurations.getServerport();

        FormelMail smtp = new FormelMail();
        smtp.debugMode(true);
        try {
            for (Prank myPrank : pranks) {
                smtp.sendPrank(serverName, serverPort, myPrank);
            }
        } catch (SmtpException | IOException e) {
            System.err.println("The sending message have an error : " + e.toString());
            System.exit(1);
        }

        System.exit(0);
    }
}

class SmtpException extends Exception {

    /**
     * The new exception
     *
     * @param lastCommandBeforeError The last command that we send before the
     * error
     * @param errorCodeOfServer The response error code of server
     * @param MessageError The error message
     */
    public SmtpException(String lastCmd, int errorCode, String errorMsg) {
        this.lastCommandBeforeError = lastCommandBeforeError;
        this.errorCodeOfServer = errorCodeOfServer;
        this.MessageError = MessageError;
    }

    @Override
    public String toString() {
        //convertion de l'exception en string
        StringBuilder myException = new StringBuilder();

        StringBuilder append = myException.append("Error while executing cmd ").append(lastCommandBeforeError).append(":").append(errorCodeOfServer).append("-").append(MessageError);

        return myException.toString();// exception 
    }
    String lastCommandBeforeError = null;
    int errorCodeOfServer = -1;
    String MessageError = null;
}
