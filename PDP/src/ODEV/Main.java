/**
 *
 * @author ESA
 * @since 10.04.2023
 *
 * Verilen dosya icerisindeki javadoc,cok satirli,tek satirli yorumlari tespit eden program.
 *
 */

package ODEV;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.File;

public class Main {
	public static void dosyayaYaz(String data,String dosyaismi)//yazilacak bilgi ve dosya ismini parametre olarak alarak dosyaya yazma islemini yerine getirir.
    {
        try {

            FileWriter output = new FileWriter(dosyaismi,true);
            output.write(data);
            output.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }
    public static void fonskiyonismiDosyayaYazdir(String eslesen,String dosyaismi )//gelen parametreler dogrultusunda dosyaya yazilan yorum satirarinin hangi fonksiyona ait oldugu belirtiliyor.
    {
        String fonksiyonismiRegex="(public|private|protected) [? a-zA-z]+ *\\([^\\)]*\\) *\\{[^\\}]";//fonksiyon ismini saptayan regex ifadesi
        Pattern fonskiyonKalip=Pattern.compile(fonksiyonismiRegex);
        Matcher fonskiyonEslesen=fonskiyonKalip.matcher(eslesen);
        while (fonskiyonEslesen.find())
        {
            dosyayaYaz(fonskiyonEslesen.group(), dosyaismi);//regex sinifinin group ozelligi kullanilarak fonskiyon ismi dosyaya yaziliyor
        }
    }
    public static  int tekliYorum(String eslesen) throws  IOException{// gelen string ifade icerisindeki tekli yorum satirlarini bularak ilgili dosyaya yazma islemin gerceklestiriyor.
        String tekliBul="(\\/\\/.*)";//tekli satir yorumlarini bulan regex ifadesi
        Pattern tekliKalip=Pattern.compile(tekliBul);
        Matcher tekliEslesen= tekliKalip.matcher(eslesen);
        int tekliYorumAdet=0;//kac adet tekli satir yorumu sayisini tutar
        while (tekliEslesen.find())
        {
            tekliYorumAdet++;
            fonskiyonismiDosyayaYazdir(eslesen,"teksatir.txt");//yorum satirinin hangi fonksşyona ait oldgu bilgisi yaziliyor.
            dosyayaYaz(tekliEslesen.group(),"teksatir.txt");//tekli yorum satirlari dosyaya yaziliyor.
            dosyayaYaz("\n","teksatir.txt");
            dosyayaYaz("---------------------------------------","teksatir.txt");
            dosyayaYaz("\n","teksatir.txt");
        }
        return tekliYorumAdet;//kac adet tekli satir yorumu oldgu bilgisi donduruluyor.
    }

    public static int cokluYorum(String eslesen){//coklu yorumlari bulup ilgili dosyaya yazma islemini icra ediyor.

        String cokluBul="(\\/\\*([^*]|[\\r\\n]|(\\* ([^*/]|[\\r\\n])))*\\*+\\/)";//coklu yorum satirlarini bulan regex ifadesi
        Pattern cokluKalip=Pattern.compile(cokluBul);
        Matcher cokluEslesen=cokluKalip.matcher(eslesen);
        int cokluYorumAdet=0;//coklu yorum satiri sayisini tutar.
        while (cokluEslesen.find())
        {
            cokluYorumAdet++;
            fonskiyonismiDosyayaYazdir(eslesen,"coksatir.txt");//hangi fonksiyona ait oldugu bilgisii dosyaya yaziliyor
            dosyayaYaz(cokluEslesen.group(),"coksatir.txt");//coklu yorum satirlari dosyaya yaziliyor.
            dosyayaYaz("\n","coksatir.txt");
            dosyayaYaz("---------------------------------------","coksatir.txt");
            dosyayaYaz("\n","coksatir.txt");
        }
        return cokluYorumAdet;//coklu yorum satiri sayisi donduruluyor.
    }

    public static int javadoc(String eslesen)throws IOException//javadoc yorum satirlarini saptayip ilgili dosyaya yazma isleminin gerceklestiriyor
    {
        String javadocBul="(\\/\\*\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+\\/)";//javadoc yorum satirlarini bulan regex ifadesi
        Pattern javadocKalip=Pattern.compile(javadocBul);
        Matcher javadocEslesen=javadocKalip.matcher(eslesen);
        int javadocYorumAdet=0;//javadoc yorum satiri sayisini tutar.
        while (javadocEslesen.find())
        {
            javadocYorumAdet++;
            fonskiyonismiDosyayaYazdir(eslesen,"javadoc.txt");//ilgili yorum satirinin hangi fonksiyona ait oldugu bilgisi dosyaya yazilir.
            dosyayaYaz(javadocEslesen.group(),"javadoc.txt");//yorum satirlari dosyaya yazilir
            dosyayaYaz("\n","javadoc.txt");
            dosyayaYaz("---------------------------------------","javadoc.txt");
            dosyayaYaz("\n","javadoc.txt");
        }
        return  javadocYorumAdet;//javadoc yorum satiri adedi dondurulur.
    }
    public static void javadocUst(String dosyaIsmi ) throws IOException//fonskiyon uzerindeki javadoc yorum satirlari bulur va ilgili dosyaya yazar.
    {
        String javadocBulUst="(\\/\\*\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+\\/)(\\r\\n|\\r|\\n)(public|private|protected) [? a-zA-z]+ *\\([^\\)]*\\) *\\{[^\\}]";//fonksiyon uzerindeki javadoc yorum satirlarini bulun regex ifadesi.
        String yazi=new String(Files.readAllBytes(Paths.get(dosyaIsmi)),StandardCharsets.UTF_8);//hangi dosyadaki yorumları bulacagı belirtilir.
        Pattern javadocKalipUst=Pattern.compile(javadocBulUst);
        Matcher javadocEslesenUst=javadocKalipUst.matcher(yazi);
        
        
        
        while (javadocEslesenUst.find())
        {
            
            
            	dosyayaYaz(javadocEslesenUst.group(),"javadoc.txt");//bulunan yorum satirlari ilgili dosyaya yazilir.
                dosyayaYaz("\n","javadoc.txt");
                dosyayaYaz("---------------------------------------","javadoc.txt");
                dosyayaYaz("\n","javadoc.txt");
            

        }
    }
    public static  void fonksiyonIcindekiYorumlariBul(String dosyaIsmi)throws IOException//verilen dosya icerisindeki fonksiyon bloklarını bularak ilgili yorum satirlari metodlarina gonderir ve isteneilen sekilde ekrana cikti verir.
    {
        javadocUst(dosyaIsmi);//fonksiyon cagirilarak ilk once verilen dosyadaki fonksiyon uzerindeki javadoc yorum satrilari bulunur.
        String javadocUstYorum="";
        File f=new File("javadoc.txt");
        if (f.exists())//verilen dosya icerisinde fonksiyon uzerindeki javadoc satirlari var mi yok mu onlarin saptanmasi ve varsa ilgili islemlerin yapilmasi.
        {  javadocUstYorum=new String(Files.readAllBytes(Paths.get(f.toURI())),StandardCharsets.UTF_8);
        }

        int javaDocUstYorumSayisi=0;



        String fonksiyonBul="(public|private|protected) [? a-zA-z]+ *\\([^\\)]*\\) *\\{[^\\}]*\\}";//verilen dosya icerisindeki fonksiyon bloklarini bulan regex ifadesi.
        String yazi=new String(Files.readAllBytes(Paths.get(dosyaIsmi)),StandardCharsets.UTF_8);
        Pattern kalip=Pattern.compile(fonksiyonBul);
        Matcher eslesen=kalip.matcher(yazi);


        String classBul="(public|private|protected) (class) [? a-zA-z]+";//verilen dosyadaki class'i bulduran regex ifadesi
        Pattern classKalip=Pattern.compile(classBul);
        Matcher classEslesen=classKalip.matcher(yazi);


        String fonksiyonismiRegex="(public|private|protected) [? a-zA-z]+ *\\([^\\)]*\\) *\\{[^\\}]";//verilen dosya icerisindeki fonksiyon ismimlerini bulduran regex ifadesi. 
        Pattern fonskiyonKalip=Pattern.compile(fonksiyonismiRegex);
        Matcher fonskiyonEslesen=fonskiyonKalip.matcher(yazi);
        
        if(classEslesen.find())
        {
        	System.out.println(classEslesen.group());//class ismi yazdiriliyor.
        }

        while (eslesen.find()&&fonskiyonEslesen.find())
        {

            if (javadocUstYorum.contains(fonskiyonEslesen.group()))
            {
                javaDocUstYorumSayisi++;
            }

            System.out.println();
            System.out.println("\t"+fonskiyonEslesen.group());//fonksiyon ismi yazdiriliyor.
            System.out.println();
            System.out.println("\t\tTek Satir Yorum Sayisi:  "+tekliYorum(eslesen.group()));//ilgili fonksiyona ait tekli yorum satirlari  ve kac adet oldukları bilgisi ekrana yazdiriliyor.
            System.out.println("\t\tCok Satirli Yorum Sayisi:  "+cokluYorum(eslesen.group()));//ilgili fonksiyona ait coklu yorum satirlari  ve kac adet oldukları bilgisi ekrana yazdiriliyor.
            System.out.println("\t\tJavadoc Yorum Sayisi:  "+(javaDocUstYorumSayisi+javadoc(eslesen.group())));//ilgili fonksiyona ait javadoc yorum satirlari 
            System.out.println();
            System.out.println("\t------------------------------");
            javaDocUstYorumSayisi=0;//fonksiyon uzerindeki yorum satirlari sayisi sıfıra esitlenerek yeni bir fonksiyonun uzerindeki javadoc yorum satirlari sayisi toplam fonksiyonlar uzerindeki javadoc yorumsatirlari sayisi ile karismamis oluyor. 

        }
    }
    public static void main(String[] args) throws IOException
    {
        fonksiyonIcindekiYorumlariBul(args[0]);

    }

}
