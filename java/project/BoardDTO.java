package ch20;

import java.time.LocalDateTime;

public class BoardDTO {
    private int    id, hits;
    private String title, writer, content;
    private LocalDateTime regDate;   // DATETIME ↔ LocalDateTime

    /* ───── getters / setters ───── */
    public int getId()                 { return id; }
    public void setId(int id)          { this.id = id; }
    public int getHits()               { return hits; }
    public void setHits(int hits)      { this.hits = hits; }
    public String getTitle()           { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getWriter()          { return writer; }
    public void setWriter(String writer){this.writer = writer;}
    public String getContent()         { return content; }
    public void setContent(String c)   { this.content = c; }
    public LocalDateTime getRegDate()  { return regDate; }
    public void setRegDate(LocalDateTime r){this.regDate=r;}
}
