package com.example.beritaapp;

public class News {
    String title;
    String tag;
    String content;
    int minAge;
    String writer;

//    public News(String title, String tag, String content, int minAge, String writer){
//        this.title = title;
//        this.tag = tag;
//        this.content = content;
//        this.minAge = minAge;
//        this.writer = writer;
//    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) { this.tag = tag;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

//    public int minimumAge(){
//        return minAge;
//    }
}
