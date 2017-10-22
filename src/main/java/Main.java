import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<MinedDocument> minedDocuments = new ArrayList<MinedDocument>();
            Document doc = Jsoup.connect("https://arxiv.org/list/quant-ph/new").get();
            Elements docLinks = doc.select("dt");
            Elements docDesc = doc.select("dd");

            for(int i = 0; i < docLinks.size(); i++){
                Element element = docLinks.get(i);
                Element elementDesc = docDesc.get(i);

                MinedDocument minedDoc = new MinedDocument();
                minedDocuments.add(minedDoc);

                String link = element.select("a[href]").attr("abs:href");

                Elements elementDescDivs = elementDesc.select("div");

                String title = "No title";
                String authors = "No authors";
                String comments = "No comments";
                String subjects = "No subjects";
                String content = elementDesc.select("p").html();

                for(Element elementDiv : elementDescDivs) {
                    String text = elementDiv.text();
                    int indexSeparator = text.indexOf(' ') + 1;
                    String textValue = text.substring(indexSeparator);
                    if (text.startsWith("Title:")) {
                        title = textValue;
                    } else if(text.startsWith("Comments:")){
                        comments = textValue;
                    } else if (text.startsWith("Authors:")) {
                        authors = textValue;
                    } else if (text.startsWith("Subjects:")) {
                        subjects = textValue;
                    }
                }


                if(content==null || content.length()==0){
                    Document currentArticle = Jsoup.connect(link).get();
                    content = currentArticle.select("blockquote").first().text();
                }

                minedDoc.setLink(link);
                minedDoc.setTitle(title);
                minedDoc.setComments(comments);
                minedDoc.setAuthors(authors);
                minedDoc.setSubjects(subjects);
                minedDoc.setContent(content);

            }

            System.out.println(docLinks.size());
            System.out.println(docDesc.size());


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finishd!");
    }
}
