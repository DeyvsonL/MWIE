import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            List<MinedDocument> minedDocuments = wrapper();
            setRelations(minedDocuments);
            /*
            for(MinedDocument md : minedDocuments){
                System.out.println(md.toString());
            }
            */

            for(int i=0; i<3;i++){
                System.out.println(minedDocuments.get(i).toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finishd!");
    }

    private static List<MinedDocument> wrapper() throws IOException {
        System.out.println("Iniciando Wrapper");
        List<MinedDocument> minedDocuments = new ArrayList<MinedDocument>();
        Document doc = Jsoup.connect("https://arxiv.org/list/quant-ph/new").get();
        Elements docLinks = doc.select("dt");
        Elements docDesc = doc.select("dd");

        System.out.print("Lendo Documentos");
        for(int i = 0; i < docLinks.size(); i++){
            System.out.print(".");
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
        System.out.println("\nEncerrando Wrapper...\n\n");

        return minedDocuments;
    }

    public static void setRelations(List<MinedDocument> minedDocuments) throws IOException {
        System.out.print("Capturando relações");

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for(MinedDocument md : minedDocuments){
            System.out.print(".");

            Annotation doc = new Annotation(md.getContent());
            pipeline.annotate(doc);
            md.setRelations(getRelations(doc));
        }
        System.out.println("\nEncerrando captura de relações...\n\n");
    }

    private static List<String> getRelations(Annotation doc) {
        List<String> relations= new ArrayList<String>();

        int cont=1;
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            // Print the triples
            for (RelationTriple triple : triples) {

                /*
                System.out.println(cont);
                System.out.println(triple.confidence);
                System.out.println(triple.confidenceGloss());
                System.out.println(triple.relationGloss());
                System.out.println(triple.relationLemmaGloss());
                System.out.println(triple.objectGloss());
                System.out.println(triple.objectLemmaGloss());
                System.out.println(triple.objectLink());
                System.out.println(triple.subjectGloss());
                System.out.println(triple.subjectLemmaGloss());
                System.out.println(triple.subjectLink());
                System.out.println("\n\n");
                */
                relations.add(triple.relationGloss() + " (" + triple.objectGloss()+"," + triple.subjectGloss()+")");
            }
            cont++;
        }

        return relations;
    }
}
