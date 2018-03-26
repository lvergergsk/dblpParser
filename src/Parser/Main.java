// -DentityExpansionLimit=2000000
//ref: http://www.ws-attacks.org/XML_Entity_Expansion
//ref: https://www.youtube.com/watch?v=WQUiub2hc0c

package Parser;

import DblpParserHandlers.DblpHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
//        StructureAnalyzerHandler structureAnalyzerHandler = new StructureAnalyzerHandler();
        DblpHandler dblpHandler = new DblpHandler();
//        TestHandler testHandler=new TestHandler();


        String uri = "./data/dblp.xml";

        Long start = System.currentTimeMillis();
//        saxParser.parse(new File(uri), structureAnalyzerHandler);
        saxParser.parse(new File(uri), dblpHandler);
//        saxParser.parse(new File(uri),testHandler);
//        structureAnalyzerHandler.printStructure();
        Long end = System.currentTimeMillis();
        System.out.println("Used: " + (end - start) / 1000 + " seconds");
    }
}
