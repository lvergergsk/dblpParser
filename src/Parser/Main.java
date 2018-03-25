// -DentityExpansionLimit=2000000

package Parser;

import DblpParserHandlers.StructureAnalyzerHandler;
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
        StructureAnalyzerHandler structureAnalyzerHandler = new StructureAnalyzerHandler();

        String uri = "./data/dblp.xml";

        Long start = System.currentTimeMillis();
        saxParser.parse(new File(uri), structureAnalyzerHandler);
        structureAnalyzerHandler.printStructure();
        Long end = System.currentTimeMillis();
        System.out.println("Used: " + (end - start) / 1000 + " seconds");
    }
}
