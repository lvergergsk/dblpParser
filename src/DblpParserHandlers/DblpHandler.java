package DblpParserHandlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DblpHandler extends DefaultHandler {
    int currentLevel;
    GroundTag currentElement;
    String currentTag;

    Query query;
    PrintWriter incollectionWriter;


    public DblpHandler() throws IOException {
        super();
        incollectionWriter = new PrintWriter(new FileWriter("./incollection.txt"));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentLevel++;
        if (currentLevel == 2) {
            switch (qName) {
                case "incollection":
                    this.currentElement = GroundTag.INCOLLLECTION;
                    query = new Inproceeding();
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
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length).trim();
        switch (currentElement) {
            case INCOLLLECTION:
                switch (currentTag) {
                    case "title":
                        ((Inproceeding) query).setTitle(str);
                        break;
                    case "year":
                        ((Inproceeding) query).setYear(str);
                        break;
                    case "booktitle":
                        ((Inproceeding) query).setBooktitle(str);
                        break;
                }
                break;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (currentElement.equals(GroundTag.INCOLLLECTION) && currentLevel == 2)
            incollectionWriter.println(this.query.getQueryStmt());
//            System.out.println(this.query.getQueryStmt());


        if (currentLevel == 2) currentElement = GroundTag.NONE;
        currentLevel--;
    }

    enum GroundTag {
        NONE(""), INCOLLLECTION("incollection");

        String tagString;

        GroundTag(String tagString) {
            this.tagString = tagString;
        }
    }

    class Incollection {
        String title, year, url, booktitle;

        public Incollection(String title, String year, String url, String booktitle) {
            this.title = title;
            this.year = year;
            this.url = url;
            this.booktitle = booktitle;
        }
    }
}
