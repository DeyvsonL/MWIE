/**
 * Created by jg3ad on 22/10/2017.
 */
public class MinedDocument {

    private String Authors;
    private String title;
    private String link;
    private String comments;
    private String[] relations;
    private String subjects;
    private String content;



    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String[] getRelations() {
        return relations;
    }

    public void setRelations(String[] relations) {
        this.relations = relations;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        StringBuilder str = new StringBuilder("Authors: ").append(getAuthors()).append("\n");
        str.append("Title: ").append(getAuthors()).append("\n");
        str.append("Subjects: ").append(getSubjects()).append("\n");
        str.append("Comments: ").append(getComments()).append("\n");
        str.append("Link: ").append(getLink()).append("\n");
        for(int i = 0; i < getRelations().length; i++){
            str.append("Relation").append(i+1).append(": ").append(getRelations()[i]).append("\n");
        }
        return  str.toString();
    }

}
