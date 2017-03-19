package com.example.vishal.monitor;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by vishal on 3/1/17.
 */
public class Injector {

    /**
     * @param args
     */
    public static final String protectedCreate = ".method protected onCreate(Landroid/os/Bundle;)V";
    public static final String publicCreate = ".method public onCreate(Landroid/os/Bundle;)V";
    public static final String METHOD1 = "getLastKnownLocation";
    public static final String METHOD2 = "getLastLocation";
    public static final String METHOD3 = "onLocationChanged";
    public static final String TAG = "Injector";
    static String mainpackage=null;
    static String mainactivity=null;
    public static int scanAndInject(String folderPath, Context context) throws IOException {
        // TODO Auto-generated method stub
        //folderPath = "/home/vishal/code/ap/experiment/fb_lite_java";
        getMainActivity(folderPath);

        //get absolute path
        if(mainactivity.charAt(0)=='.')
        {
            //System.out.println("relative");
            mainactivity = mainpackage+"/"+mainactivity.substring(1);

        }
        else if(!mainactivity.contains("."))
        {
            mainactivity = mainpackage+"/"+mainactivity;
        }
        else
        {
            //System.out.println("absolute");

            mainactivity = mainactivity.replace('.', '/');
        }
        Log.d(TAG, "scanAndInject: mainactivity="+mainactivity);
        //copy Reporter to main package
        //eventually I realised that mainActivity doesnt have anything to do with main package
        String activityPackage = mainactivity.substring(0,mainactivity.lastIndexOf("/"));
        //new Operator().fileCopy(context.getFilesDir().getAbsolutePath()+"/Reporter.smali",folderPath+"/smali/"+mainpackage+"/Reporter.smali");
        new Operator().fileCopy(context.getFilesDir().getAbsolutePath()+"/Reporter.smali",folderPath+"/smali/"+activityPackage+"/Reporter.smali");
        //obj.findAndReplace("/home/vishal/workspace/Injector/files/Reporter.smali", mainpackage);
        //findAndReplace(folderPath+"/smali/"+mainpackage+"/Reporter.smali",mainpackage);
        findAndReplace(folderPath+"/smali/"+activityPackage+"/Reporter.smali",activityPackage);
        //scan n modify activity : add reporter.initContext to MainActivity
        //obj.injectMainActivity("/home/vishal/workspace/Injector/files/MainActivity.smali");
        injectMainActivity(folderPath+"/smali/"+mainactivity+".smali");
        //obj.injectClasses("/home/vishal/workspace/Injector/files/");
        injectClasses(folderPath+"/smali/");
        ///home/vishal/code/ap/experiment/Where_Am_I_v2/smali_dup/com/smartmobsolutions/android/
        //obj.injectClasses("/home/vishal/code/ap/experiment/Where_Am_I_v2/smali_dup/com/smartmobsolutions/android/whereami/");
        return 0;
    }

    public static int injectClasses(String path)
    {
        boolean changeFlag = false;
        String reportCall = "invoke-static {}, L"+mainpackage+"/Reporter;->report()V";
        try {
            String dirArray[] = {""};
            Collection<File> fileCollection =  FileUtils.listFiles(new File(path), FileFilterUtils.suffixFileFilter(".smali"), TrueFileFilter.INSTANCE);
            Log.d(TAG, "injectClasses: files to scan:"+fileCollection.size());
                      for(File file : fileCollection)
                            {
                                if(file.getAbsolutePath().contains("google")||file.getAbsolutePath().contains("android")) {
                                    Log.d(TAG, "injectClasses: skipping file:"+file.getName());
                                    continue;
                                }
                                changeFlag = false;
                                Log.d(TAG, "injectClasses: "+"scanning -- "+file);
                                //System.out.println("scanning -- "+file);
                                try {
                                    int index = 0;
                                    List<String> lines = FileUtils.readLines(file, "UTF-8");
                                    ListIterator<String> lstIt = lines.listIterator();
                                    while(lstIt.hasNext())
                                    {
                                        String line = lstIt.next();
                                        //index++;
                                        if((line.contains("getLastKnownLocation") || line.contains("getLastLocation")) && !line.contains("method"))
                                        {
                                            Log.d(TAG, "injectClasses: "+"---->"+line);
                                            changeFlag = true;
                                            lstIt.previous();
                                            lstIt.add(reportCall);
                                            lstIt.next();
                                            lstIt.next();
                                            //index+=2;
                                        }
                                        //if (line.contains(".method public onLocationChanged(Landroid/location/Location;)V") )
                                        if (line.contains("method") && line.contains("onLocationChanged"))
                                        {
                                            changeFlag = true;
                                            Log.d(TAG, "injectClasses: "+"---->"+line);

                                            line = lstIt.next();
                                            while(!line.contains("line") && lstIt.hasNext())
                                            {
                                                Log.d(TAG, "injectClasses: "+"---->"+line);
                                                //lstIt.add(reportCall);
                                                line = lstIt.next();
                                            }
                                            //else
                                            if(lstIt.hasNext())
                                                lstIt.add(reportCall);

                                        }
                                    }
                                    if(changeFlag)
                                        FileUtils.writeLines(file,"UTF-8",lines);

                                    //Files.write(file, lines, StandardCharsets.UTF_8);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    public static int injectMainActivity(String dirPath)
    {

        List<String> lines;
        try {
            lines = FileUtils.readLines(new File(dirPath), "UTF-8");


            int index=0;
            for (Iterator iterator = lines.iterator(); iterator.hasNext();)
            {
                index++;
                String strLine = (String) iterator.next();
                if(strLine.equalsIgnoreCase(Injector.publicCreate)||strLine.equalsIgnoreCase(Injector.protectedCreate))
                {
                    //System.out.println("signature1 found at: "+index);
                    index++;
                    strLine=(String)iterator.next();
                    while( !strLine.contains("invoke-super") && !strLine.contains("onCreate"))//!strLine.contains("invoke-super") &&
                    {
                        //System.out.println("in");
                        index++;
                        strLine=(String)iterator.next();
                    }
                    //System.out.println("signature2 found at: "+index);
                    break;

                }

            }
            Log.d(TAG, "injectMainActivity: "+"signature found at: "+index);

            String initContextCall = "invoke-static {p0}, L"+mainpackage+"/Reporter;->initContext(Landroid/app/Activity;)V";
            lines.add(index+1, initContextCall);
            //Files.write(path, lines, StandardCharsets.UTF_8);
            FileUtils.writeLines(new File(dirPath),"UTF-8",lines);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    public static int findAndReplace(String filepath, String packagename)
    {
        try {
            //without java8
            String content = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
            Log.d(TAG, "findAndReplace: content---------->"+content);
            content = content.replaceAll("packagename", "L"+packagename);
            //File tempFile = new File("OutputFile");
            Log.d(TAG, "findAndReplace: content---------->"+content);
            FileUtils.writeStringToFile(new File(filepath), content,Charset.defaultCharset());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMainActivity(String destFolder)
    {
        try {
            //refrence - http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
            File fXmlFile = new File(destFolder+"/AndroidManifest.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            Injector.mainpackage = doc.getDocumentElement().getAttribute("package").replace('.', '/');
            Log.d(TAG, "getMainActivity: "+"Package :" + Injector.mainpackage);
            //System.out.println("Package :" + Injector.mainpackage);
            NodeList nList = doc.getElementsByTagName("activity");
            boolean action=false;
            boolean category=false;

            for (int temp = 0; temp < nList.getLength(); temp++) {//Loop through all the activities
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    //System.out.println(eElement.getAttribute("android:name"));
                    NodeList intentList = eElement.getElementsByTagName("intent-filter");

                    for(int i=0;i<intentList.getLength();i++)//Loop through all the intent-filter
                    {
                        action=false;
                        category=false;
                        Node iNode = intentList.item(i);
                        if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList acList = iNode.getChildNodes();
                            for(int j=0; j<acList.getLength();j++) //Loop through all the nodes of intent-filter
                            {
                                Node acnNode = acList.item(j);
                                if(acnNode.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element acNode = (Element)acnNode;
                                    //System.out.println("	intent-node="+acNode.getAttribute("android:name"));

                                    if(acNode.getNodeName().equalsIgnoreCase("action") && acNode.getAttribute("android:name").equalsIgnoreCase("android.intent.action.MAIN"))
                                    {
                                        action = true;
                                    }

                                    if(acNode.getNodeName().equalsIgnoreCase("category") && acNode.getAttribute("android:name").equalsIgnoreCase("android.intent.category.LAUNCHER"))
                                    {
                                        category = true;
                                    }
                                }
                                //System.out.println("action="+action+" category="+category);
                                if(action && category)
                                {
                                    //System.out.println("action & category found -->");
                                    break;
                                }
                            }
                        }
                        if(action && category)
                        {
                            //System.out.println("intent found -->"+iNode.getNodeName());
                            break;
                        }

                    }
                    if(action && category)
                    {
                        //System.out.println("activity found -->"+eElement.getAttribute("android:name"));
                        Injector.mainactivity = eElement.getAttribute("android:name");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}