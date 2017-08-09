package lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by xuchun on 2017/8/9.
 */
public class TestCase {
    private static final String indexPath="E:/lucene_index";
    private static final String filePath="E:/lucene_doc";

    @Test
    public void test1(){
        //索引初始化配置
        IndexWriterConfig config=new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer=null;
        try {
            //索引存放的目录
            Directory directory= FSDirectory.open(Paths.get(indexPath));
            writer=new IndexWriter(directory,config);
            Document doc=null;
            Path dir=Paths.get(filePath);
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for(Path path : stream){
                System.out.println(path.getFileName().toString());
                doc=new Document();
                doc.add(new StringField("filename",path.getFileName().toString(), Field.Store.YES));
                writer.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer!=null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
