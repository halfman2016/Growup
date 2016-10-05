package com.yper.feng.growup.Util;
import android.os.Handler;

import com.google.gson.reflect.TypeToken;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Rank;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Teacher;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.Binary;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Feng on 2016/7/17.
 */
public class MDBTools {
    private static final MongoClient mongoClient = new MongoClient("boteteam.com", 27017);
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection = null;


    public MDBTools() {
        mongoDatabase = mongoClient.getDatabase("lizhi");
    }

    public void addStu(Student student) {
        mongoCollection = mongoDatabase.getCollection("students");
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(student);
        mongoCollection.insertOne(Document.parse(json));

    }

    public void addTea(Teacher tea)

    {
        mongoCollection = mongoDatabase.getCollection("teachers");
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(tea);
        mongoCollection.insertOne(Document.parse(json));

    }
    public ArrayList<Student> getStus()
    {
        ArrayList<Student> stus=new ArrayList<>();
        Student stu;
        mongoCollection=mongoDatabase.getCollection("students");
        FindIterable<Document> iterator=mongoCollection.find();
        MongoCursor mongoCursor=iterator.iterator();
        Gson gson=new GsonBuilder().create();
        while (mongoCursor.hasNext())
        {
            String json;
            Document doc=(Document) mongoCursor.next();
            json=doc.toJson();
            stu=gson.fromJson(json,Student.class);
            stus.add(stu);
        }
        //   Log.d("myapp",books.toString());
        return stus;
    }

    public ArrayList<Teacher> getTeas()
    {
        ArrayList<Teacher> teas = new ArrayList<>();
        Teacher tea;
        mongoCollection = mongoDatabase.getCollection("teachers");
        FindIterable<Document> iterable=mongoCollection.find();
        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().create();
        while (mongoCursor.hasNext()){
            Document doc= (Document) mongoCursor.next();
            String json=doc.toJson();
            tea = gson.fromJson(json, Teacher.class);
            teas.add(tea);
        }
        return teas;
    }


    public List<GradeClass> getGradeClasses() {
        ArrayList<GradeClass> gradeClasses = new ArrayList<>();
        GradeClass gradeClass ;

        mongoCollection=mongoDatabase.getCollection("classes");
        FindIterable<Document> iterable=mongoCollection.find();
        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().create();

        while (mongoCursor.hasNext())
        {
            Document doc=(Document) mongoCursor.next();
            String json=doc.toJson();
            gradeClass=gson.fromJson(json,GradeClass.class);
            gradeClasses.add(gradeClass);

        }


        return gradeClasses;
    }

    public void saveGradeClasses(ArrayList<GradeClass> gradeClasses){
        mongoCollection=mongoDatabase.getCollection("classes");
        mongoCollection.deleteMany(Filters.exists("_id"));

        Gson gson=new GsonBuilder().create();
        Document doc = new Document();
        List<Document> docs=new ArrayList<>();
        for (GradeClass temp:gradeClasses
             ) {
            doc=Document.parse(gson.toJson(temp));
            docs.add(doc);

        }

        mongoCollection.insertMany(docs);

    }

    public  void saveGradeClass(GradeClass gc){

        mongoCollection = mongoDatabase.getCollection("classes");

        Gson gson=new GsonBuilder().create();
        String json = gson.toJson(gc);
        mongoCollection.insertOne(Document.parse(json));
    }


    public HashMap<String,Rank> getRanks(){

        HashMap<String,Rank> ranks = new HashMap();

        mongoCollection= mongoDatabase.getCollection("configs");



        FindIterable<Document> iterator=mongoCollection.find(Filters.exists("ranks"));

        MongoCursor mongoCursor=iterator.iterator();
        Gson gson=new GsonBuilder().create();

        while (mongoCursor.hasNext())
        {
           Document doc=(Document) mongoCursor.next();
           String json= (String) doc.get("ranks");

            Type type =new TypeToken<HashMap<String,Rank>>(){}.getType();
            ranks=gson.fromJson(json,type);
        }
        //   Log.d("myapp",books.toString());
        return ranks;
    }

    public void saveRanks(Map<String,Rank> ranks){

        mongoCollection=mongoDatabase.getCollection("configs");
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(ranks);

        Document doc=new Document().append("ranks",json);

        Document filer=Document.parse("{ranks:{$exists:true}}");
        mongoCollection.findOneAndReplace(filer,doc);

            }

    public void saveDefaultValuestoDataBase(Map<String,Integer> map)
    {
        mongoCollection=mongoDatabase.getCollection("configs");
        Document doc=new Document("defaultValues",map);
        Document filer=Document.parse("{defaultValues:{$exists:true}}");
        mongoCollection.deleteMany(filer);
        mongoCollection.insertOne(doc);


    }

    public Map<String, Integer>  getDefaultValues(){
        Map<String, Integer> map = new HashMap<>();
        mongoCollection=mongoDatabase.getCollection("configs");
        Bson doc=  Filters.exists("defaultValues");
        Gson gson=new GsonBuilder().create();
        FindIterable<Document> docs=mongoCollection.find(doc);
        MongoCursor cur=docs.iterator();
        while (cur.hasNext())
        {

            Document document= (Document) cur.next();
            map= (Map<String, Integer>) document.get("defaultValues");
//            Type type =new TypeToken<HashMap<String,Integer>>(){}.getType();
//            map=gson.fromJson(json,type);
        }

        return map;


    }


    public boolean addPhoto(String photoaddr) {
        mongoCollection=mongoDatabase.getCollection("photos");
        final String title=photoaddr.substring(photoaddr.lastIndexOf("/")+1,photoaddr.length());
        File file = new File(photoaddr);
        if (file.isFile() && file.exists()) {

            try {
                FileChannel fc=new RandomAccessFile(file,"r").getChannel();
                MappedByteBuffer byteBuffer=fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size()).load();
                final byte[] result=new byte[(int) fc.size()];
                if(byteBuffer.remaining()>0)
                {
                    byteBuffer.get(result,0,byteBuffer.remaining());
                }

        Document doc = new Document();
        doc.put("title", title);
        doc.put("photofile", result);
        mongoCollection.insertOne(doc);
       // System.out.println(result.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;

        } else {
            return false;

        }
    }

    public byte[] getPhoto(String photoName){
        mongoCollection=mongoDatabase.getCollection("photos");

        Document filer=new Document();
        filer.put("title",photoName);
        Document document=mongoCollection.find(filer).first();
        Binary bs=(Binary) document.get("photofile");
        return bs.getData();
    }

    public static File putFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public void addDayCommonAction(ArrayList<DayCommonAction> daylist){

        mongoCollection=mongoDatabase.getCollection("daycommonactions");
        mongoCollection.deleteMany(Filters.exists("_id"));

        Gson gson=new GsonBuilder().create();
        Document doc = new Document();
        List<Document> docs=new ArrayList<>();
        for (DayCommonAction temp:daylist
                ) {
            doc=Document.parse(gson.toJson(temp));
            docs.add(doc);

        }

        mongoCollection.insertMany(docs);

    }

    public  ArrayList<DayCommonAction> getTypedDayActions(String typeName){

        ArrayList <DayCommonAction> result=new ArrayList<>();


        DayCommonAction dayca;

        mongoCollection=mongoDatabase.getCollection("daycommonactions");
        FindIterable<Document> iterable;
        if (typeName=="")
        { iterable=mongoCollection.find();}
        else
        {  iterable=mongoCollection.find(Filters.eq("actionType",typeName));}

        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().create();

        while (mongoCursor.hasNext())
        {
            Document doc=(Document) mongoCursor.next();
            String json=doc.toJson();
            dayca=gson.fromJson(json,DayCommonAction.class);
            result.add(dayca);

        }


        return result;
    }
}
