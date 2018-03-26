package DblpParserHandlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class StructureAnalyzerHandler extends DefaultHandler {
    private static final boolean DEV_MODE = true;
    int currentLevel;
    int totalGroundTag;
    int totalIncollectionTag;
    int totalWwwTag;
    int totalMastersthesisTag;
    int totalBookTag;
    int totalProceedingsTag;
    int totalPhdthesisTag;
    int totalInproceedingsTag;
    int totalArticleTag;

    Set<String> Tags;
    HashMap<String, Range> incollectionTagCountRange;
    HashMap<String, Range> wwwTagCountRange;
    HashMap<String, Range> mastersthesisTagCountRange;
    HashMap<String, Range> bookTagCountRange;
    HashMap<String, Range> proceedingsTagCountRange;
    HashMap<String, Range> phdthesisTagCountRange;
    HashMap<String, Range> inproceedingsTagCountRange;
    HashMap<String, Range> articleTagCountRange;


    HashMap<String, Integer> counter;

    public StructureAnalyzerHandler() {
        super();
        Tags = new HashSet<>();
        incollectionTagCountRange = new HashMap<>();
        wwwTagCountRange = new HashMap<>();
        mastersthesisTagCountRange = new HashMap<>();
        bookTagCountRange = new HashMap<>();
        proceedingsTagCountRange = new HashMap<>();
        phdthesisTagCountRange = new HashMap<>();
        inproceedingsTagCountRange = new HashMap<>();
        articleTagCountRange = new HashMap<>();
    }

    @Override
    public void startElement(java.lang.String uri,
                             java.lang.String localName,
                             java.lang.String qName,
                             Attributes attributes)
            throws SAXException {
        currentLevel++;
        if (currentLevel == 2) {
            Tags.add(qName);
            totalGroundTag++;
            switch (qName) {
                case "incollection":
                    totalIncollectionTag++;
                    break;
                case "www":
                    totalWwwTag++;
                    break;
                case "mastersthesis":
                    totalMastersthesisTag++;
                    break;
                case "book":
                    totalBookTag++;
                    break;
                case "proceedings":
                    totalProceedingsTag++;
                    break;
                case "phdthesis":
                    totalPhdthesisTag++;
                    break;
                case "inproceedings":
                    totalInproceedingsTag++;
                    break;
                case "article":
                    totalArticleTag++;
                    break;
            }

            counter = new HashMap<>();
        }

        if (currentLevel == 3) {
            int count = 1;
            if (counter.containsKey(qName))
                count = counter.get(qName) + 1;
            this.counter.put(qName, count);
        }
    }

    @Override
    public void characters(char[] ch,
                           int start,
                           int length)
            throws SAXException {

    }

    @Override
    public void endElement(java.lang.String uri,
                           java.lang.String localName,
                           java.lang.String qName)
            throws SAXException {
        if (currentLevel == 2) {
            switch (qName) {
                case "incollection":
                    updateTagCountRange(this.incollectionTagCountRange, counter);
                    break;
                case "www":
                    updateTagCountRange(this.wwwTagCountRange, counter);
                    break;
                case "mastersthesis":
                    updateTagCountRange(this.mastersthesisTagCountRange, counter);
                    break;
                case "book":
                    updateTagCountRange(this.bookTagCountRange, counter);
                    break;
                case "proceedings":
                    updateTagCountRange(this.proceedingsTagCountRange, counter);
                    break;
                case "phdthesis":
                    updateTagCountRange(this.phdthesisTagCountRange, counter);
                    break;
                case "inproceedings":
                    updateTagCountRange(this.inproceedingsTagCountRange, counter);
                    break;
                case "article":
                    updateTagCountRange(this.articleTagCountRange, counter);
                    break;
                default:
                    break;
            }
        }
        currentLevel--;
    }

    private void updateTagCountRange(HashMap<String, Range> ranges, HashMap<String, Integer> counter) {
        Iterator it = counter.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Integer count = (Integer) pair.getValue();
            if (ranges.containsKey(key)) {
                Range range = ranges.get(pair.getKey());
                if (count > range.max) range.max = count;
                if (count < range.min) range.min = count;
                ranges.put(key, range);
            } else {
                Range range = new Range(count, count);
                ranges.put(key, range);
            }
        }
        it = ranges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Range range = (Range) pair.getValue();
            if (!counter.containsKey(key)) {
                range.min = 0;
                ranges.put(key, range);
            }
        }
    }

    public void printStructure() {
        System.out.print("Ground tags: ");
        for (String s : Tags) System.out.print(s + " ");
        System.out.println();
        System.out.println(this.totalGroundTag + " tags in total.");
        System.out.println(this.totalIncollectionTag + " incollection tags in total.");
        System.out.println(this.totalWwwTag + " www tags in total.");
        System.out.println(this.totalMastersthesisTag + " mastersthesis tags in total.");
        System.out.println(this.totalBookTag + " book tags in total.");
        System.out.println(this.totalProceedingsTag + " proceedings tags in total.");
        System.out.println(this.totalPhdthesisTag + " phdthesis tags in total.");
        System.out.println(this.totalInproceedingsTag + " inproceedings tags in total.");
        System.out.println(this.totalArticleTag + " article tags in total.");

        System.out.print("incollection tag ranges: ");
        printRange(incollectionTagCountRange);
        System.out.print("www tag ranges: ");
        printRange(wwwTagCountRange);
        System.out.print("mastersthesis tag ranges: ");
        printRange(mastersthesisTagCountRange);
        System.out.print("book tag ranges: ");
        printRange(bookTagCountRange);
        System.out.print("proceedings tag ranges: ");
        printRange(proceedingsTagCountRange);
        System.out.print("phdThesis tag ranges: ");
        printRange(phdthesisTagCountRange);
        System.out.print("inproceedings tag ranges: ");
        printRange(inproceedingsTagCountRange);
        System.out.print("article tag ranges: ");
        printRange(articleTagCountRange);
    }

    private void printRange(HashMap<String, Range> ranges) {
        Iterator it = ranges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Range range = (Range) pair.getValue();
            System.out.printf("(%s,%d,%d) ", key, range.min, range.max);
        }
        System.out.println();
    }

    class Range {
        int min;
        int max;

        Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    class TagCounter {
        HashMap<String, Integer> counter;

        void startTag(String tagName) {
            int count = 1;
            if (counter.containsKey(tagName))
                count = counter.get(tagName) + 1;
            this.counter.put(tagName, count);
        }
    }
}
