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
    private PrintWriter inproceedingWriter;
    private PrintWriter articleWriter;


    public DblpHandler() throws IOException {
        super();
        incollectionWriter = new PrintWriter(new FileWriter("./incollection.sql"));
        bookWriter = new PrintWriter(new FileWriter("./book.sql"));
        proceedingWriter = new PrintWriter(new FileWriter("./proceedings.sql"));
        inproceedingWriter = new PrintWriter(new FileWriter("./inproceedings.sql"));
        articleWriter = new PrintWriter(new FileWriter("./article.sql"));
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
                    this.currentElement = GroundTag.PROCEEDINGS;
                    query = new Proceedings();
                    break;
                case "inproceedings":
                    this.currentElement = GroundTag.INPROCEEDINGS;
                    query = new Inproceedings();
                    break;
                case "article":
                    this.currentElement = GroundTag.ARTICLE;
                    query = new Article();
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
            case PROCEEDINGS:
                switch (currentTag) {
                    case "title":
                        ((Proceedings) query).setTitle(str);
                        break;
                    case "year":
                        ((Proceedings) query).setYear(str);
                        break;
                }
                break;
            case INPROCEEDINGS:
                switch (currentTag) {
                    case "title":
                        ((Inproceedings) query).setTitle(str);
                        break;
                    case "year":
                        ((Inproceedings) query).setYear(str);
                        break;
                }
                break;
            case ARTICLE:
                switch (currentTag) {
                    case "title":
                        ((Article) query).setTitle(str);
                        break;
                    case "year":
                        ((Article) query).setYear(str);
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
                case PROCEEDINGS:
                    proceedingWriter.println(this.query.getQueryStmt());
                    break;
                case INPROCEEDINGS:
                    inproceedingWriter.println(this.query.getQueryStmt());
                    break;
                case ARTICLE:
                    articleWriter.println(this.query.getQueryStmt());
                    break;
            }
        }

        if (currentLevel == 2) currentElement = GroundTag.NONE;
        currentLevel--;
    }

    enum GroundTag {
        NONE, INCOLLLECTION, PROCEEDINGS, INPROCEEDINGS, ARTICLE, BOOK
    }
}
