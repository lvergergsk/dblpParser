package DblpParserHandlers;

class Query {
    String tableName;

    String getQueryStmt() {
        return null;
    }

}

class Incollection extends Query {

    private String title, year, booktitle;

    Incollection() {
        tableName = "incollection";
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

class Book extends Query {
    private String title, year;

    Book() {
        tableName = "book";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    String getQueryStmt() {
        return String.format("INSERT INTO %s (title,year) VALUES ('%s',%s);",
                tableName, title, year);
    }
}

class Proceeding extends Query {
    private String title, year;

    Proceeding() {
        tableName = "proceedings";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    String getQueryStmt() {
        return String.format("INSERT INTO %s (title,year) VALUES ('%s',%s);",
                tableName, title, year);
    }
}