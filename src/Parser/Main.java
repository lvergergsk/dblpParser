// -DentityExpansionLimit=2000000

package Parser

import DblpParserHandlers.DblpHandler
import DblpParserHandlers.StructureAnalyzerHandler
import org.xml.sax.SAXException

import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import java.io.File
import java.io.IOException

object Main {
    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val saxParserFactory = SAXParserFactory.newInstance()
        val saxParser = saxParserFactory.newSAXParser()
        val structureAnalyzerHandler = StructureAnalyzerHandler()
        val dblpHandler = DblpHandler()

        val uri = "./data/small.xml"

        val start = System.currentTimeMillis()
        //        saxParser.parse(new File(uri), structureAnalyzerHandler);
        saxParser.parse(File(uri), dblpHandler)
        structureAnalyzerHandler.printStructure()
        val end = System.currentTimeMillis()
        println("Used: " + (end - start) / 1000 + " seconds")
    }
}
