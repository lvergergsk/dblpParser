package DblpParserHandlers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DblpHandler extends DefaultHandler {
    private int currentLevel;
    private GroundTag currentElement;
    private String currentTag;

    private Query query;
    private PrintWriter incollectionWriter;
    private PrintWriter bookWriter;
    private PrintWriter proceedingWriter;


    public DblpHandler() throws IOException {
        super();
        incollectionWriter = new PrintWriter(new FileWriter("./incollection.sql"));
        bookWriter = new PrintWriter(new FileWriter("./book.sql"));
        proceedingWriter = new PrintWriter(new FileWriter("./proceedings.sql"));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentLevel++;
        if (currentLevel == 2) {
            switch (qName) {
                case "incollection":
                    this.currentElement = GroundTag.INCOLLLECTION;
                    query = new Incollection();
                    break;
                case "book":
                    this.currentElement = GroundTag.BOOK;
                    query = new Book();
                    break;
                case "proceedings":
                    this.currentElement = GroundTag.PROCEEDING;
                    query = new Proceeding();
                    break;
                default:
                    this.currentElement = GroundTag.NONE;
                    break;
            }
        } else if (currentLevel == 3) {
            currentTag = qName;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length).trim();
        switch (currentElement) {
            case INCOLLLECTION:
                switch (currentTag) {
                    case "title":
                        ((Incollection) query).setTitle(str);
                        break;
                    case "year":
                        ((Incollection) query).setYear(str);
                        break;
                    case "booktitle":
                        ((Incollection) query).setBooktitle(str);
                        break;
                }
                break;
            case BOOK:
                switch (currentTag) {
                    case "title":
                        ((Book) query).setTitle(str);
                        break;
                    case "year":
                        ((Book) query).setYear(str);
                        break;
                }
                break;
            case PROCEEDING:
                switch (currentTag) {
                    case "title":
                        ((Proceeding) query).setTitle(str);
                        break;
                    case "year":
                        ((Proceeding) query).setYear(str);
                        break;
                }
                break;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (currentLevel == 2) {
            switch (currentElement) {
                case INCOLLLECTION:
                    incollectionWriter.println(this.query.getQueryStmt());
                    break;
                case BOOK:
                    bookWriter.println(this.query.getQueryStmt());
                    break;
                case PROCEEDING:
                    proceedingWriter.println(this.query.getQueryStmt());
                    break;
            }
        }

        if (currentLevel == 2) currentElement = GroundTag.NONE;
        currentLevel--;
    }

    enum GroundTag {
        NONE, INCOLLLECTION, PROCEEDING, BOOK
    }
}
