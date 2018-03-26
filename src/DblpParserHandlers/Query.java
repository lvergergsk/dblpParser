package DblpParserHandlers;

class Query {
    String tableName;

    String getQueryStmt() {
        return null;
    }

}

class Inproceeding extends Query {

    private String title, year, booktitle;

    Inproceeding() {
        tableName = "inproceeding";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    @Override
    public String getQueryStmt() {
        return String.format("INSERT INTO %s (title,year,booktitle) VALUES ('%s',%s,'%s');",
                tableName, title, year, booktitle);
    }
}