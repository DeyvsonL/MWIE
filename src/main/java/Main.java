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
            Elements elements = doc.select("dt");
            for(Element element : elements){
                MinedDocument minedDoc = new MinedDocument();
                minedDocuments.add(minedDoc);

                String link = element.select("a[href]").first().text();
                minedDoc.setLink(link);

                System.out.println(link);

            }
            System.out.println(elements.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finishd!");
    }
}
