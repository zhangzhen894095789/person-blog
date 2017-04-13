package com.zhangzhen.test;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.Set;

/**
 * Created by Administrator on 2017/3/28.
 */
public class MongoDBTest {
    private static String ip="192.168.127.136";
    private static int port=27017;
    public static void main(String[] args) {


//        test001();
        //test002();
        test003();


        System.out.println("Hello World!");

    }


    private static void test003() {
        System.out.println("============");
        Mongo mg = new Mongo(ip, port);

        System.out.println(mg.getDatabaseNames());

        DB db = mg.getDB("test");

        Set<String> collectionNames = db.getCollectionNames();
        for (String string : collectionNames) {
            System.out.println(string);
        }
        // db游标
        DBCollection dbCollection = db.getCollection("person");

        DBCursor cur = dbCollection.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }



    }

    private static void test001() {

        System.out.println("======新版本api======");
        MongoClient mongoClient = new MongoClient(ip, port);

        System.out.println("数据库："+mongoClient.getDatabaseNames());

        MongoDatabase database = mongoClient.getDatabase("Test");

        MongoIterable<String> names=database.listCollectionNames();
        for (String string : names) {

            System.out.println("数据库表："+string);
        }
        MongoCollection<Document> collections = database
                .getCollection("person");
        //清空表
        collections.drop();
        /*
        FindIterable<Document> findIterable = collections.find();
        for (Document document : findIterable) {
            System.out.println(document);
        }*/

        collections.createIndex(new BasicDBObject("name",1));//1代表升序

        for(int i=0;i<15;i++)
        {
            Document cDocument=new Document();
            cDocument.put("name", "data"+i);
            cDocument.put("age", 32+i);
            //add
            collections.insertOne(cDocument);
        }
        //delete
        collections.deleteOne(new BasicDBObject("name","data2"));
        collections.deleteMany(new BasicDBObject("name","data3"));
        int count=0;
        for (Document document : collections.find()) {
            System.out.println(document);
            count++;
        }
        System.out.println("count="+count);

        System.out.println("find ");

        //find
        for (Document document : collections.find(new BasicDBObject("age",33)))
        {
            System.out.println(document.toJson());

        }

        //Document updateDocument=new Document("name","fuck");

        //edit

        //UpdateResult updateResult=collections.updateOne(new Document("name","data14"), new Document("name","nishizhu你是猪啊").append("age", 1024));

        //UpdateResult updateResult=collections.updateMany(new BasicDBObject("age","34"), new Document("name","fuck").append("age", 1024));
        //System.out.println(JSON.toJSONString(updateResult));


        //find
        for (Document document : collections.find(new BasicDBObject("age",1024)))
        {
            System.out.println(document.toJson());

        }

        //collections.createIndex(builder.get());



        mongoClient.close();

    }

    private static void test002() {
        System.out.println("=====旧版本api=======");

        MongoClient mongoClient = new MongoClient(ip, port);

        System.out.println("数据库:"+mongoClient.getDatabaseNames());

        DB db = mongoClient.getDB("test");
        Set<String> collectionNames = db.getCollectionNames();
        for (String tablename : collectionNames) {
            System.out.println("表："+tablename);
        }
        // db游标
        DBCollection dbCollection = db.getCollection("person2");
        dbCollection.drop();
        for(int i=0;i<15;i++)
        {
            BasicDBObject cDocument=new BasicDBObject();
            cDocument.put("name", "data"+i);
            cDocument.put("age", 32+i);
            //add
            dbCollection.insert(cDocument);

        }
        DBCursor cur = dbCollection.find();
        System.out.println("count="+cur.count());
        while (cur.hasNext()) {
            DBObject dbObject=cur.next();
            System.out.println(dbObject);
        }

        dbCollection.remove(new BasicDBObject("name","data13"));
        dbCollection.update(new BasicDBObject("name","data14"), new BasicDBObject("name","nishizhu你是猪啊").append("age", 1024));

        cur = dbCollection.find();
        System.out.println("count="+cur.count());
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
    }
}
