/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Models.KeyWordModel;
import Models.SessionModel;
import Models.TopicModel;
import Models.LectureModel;
import Models.LectureWithDetailsModel;
import Models.TermModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeSet;

//loveercase es trim a similarty setbe
//test similarity
/**
 *
 * @author Ronaldo
 */
public class DataCollector{
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    private HashMap<String,TermModel> terms= new  HashMap<String,TermModel>();
    private HashMap<LectureWithDetailsModel,ArrayList<String>> lectureWithSimilaritySet= new  HashMap<LectureWithDetailsModel,ArrayList<String>>();
    private ArrayList<LectureWithDetailsModel> lectures = new  ArrayList<LectureWithDetailsModel>();
    
    private ArrayList<LectureWithDetailsModel>  getLecturesFromfiles()
    {
        ArrayList<LectureWithDetailsModel> lectures=new ArrayList<LectureWithDetailsModel>();
        // laptop
       // LectureReaderFromFile lrff=new LectureReaderFromFile("C:\\Users\\Ronaldo\\Desktop\\licenszGit2\\licensz\\data\\generated");
        
        //munkaba
        LectureReaderFromFile lrff=new LectureReaderFromFile("C:\\Users\\tibor.wekerle\\Desktop\\licenszeGit\\licensz\\data\\generated");
        
        while(lrff.readNext())
        {
            LectureWithDetailsModel lecture = lrff.getCurrent();
            lectures.add(lecture);
        }
        return lectures;
    }
    
    private ArrayList<String> getLectureSimiliratySet(LectureWithDetailsModel lwd)
    {
        TreeSet<String> set=new TreeSet<String>();
        HashMap<String,TermModel> terms=getTerms();
        
        for(String keyword:lwd.getKeyWords())
        {
            set.add(keyword);
            if(terms.containsKey(keyword.toLowerCase()))
            {
                TermModel term=terms.get(keyword);
                if(term.getUSE()!=null)
                {
                    term=term.getUSE();
                    keyword=term.getName();
                }
                
                for(TermModel tm=term;tm!=null;tm=tm.getBroaderTerm())
                {
                    set.add(tm.getName());
                }
            }
        }
        
        for(KeyWordModel kw :lwd.getGeneratedKeyWords())
        {
            set.add(kw.getKeyWord());
            if(terms.containsKey(kw.getKeyWord()))
            {
                TermModel term=terms.get(kw.getKeyWord());
                if(term.getUSE()!=null)
                {
                    term=term.getUSE();
                    kw=new KeyWordModel(term.getName(),0);
                }
                
                for(TermModel tm=term;tm!=null;tm=tm.getBroaderTerm())
                {
                    set.add(tm.getName());
                }
            }        
        }
        
        return new ArrayList<String>(set);
    }
    
    // </editor-fold>
        
    // <editor-fold desc="lazy loading region" defaultstate="collapsed">
    public HashMap<String,TermModel> getTerms()
    {
        if(terms==null)
        {
            HashMap<String,TermModel> terms=new HashMap<String,TermModel>();
            // laptop
              //   File file = new File("C:\\Users\\Ronaldo\\Desktop\\licenszGit2\\licensz\\data\\ieee_thesaurus_2013.txt");
            //munkaba
            File file = new File("C:\\Users\\tibor.wekerle\\Desktop\\licenszeGit\\licensz\\data\\ieee_thesaurus_2013.txt");
            terms=new TermReaderFromFile().readTermsFromFile(file);
        
        }
        return terms;
    }
    
    public ArrayList<LectureWithDetailsModel> getLectures()
    {
        if(lectures==null)
        {
            lectures=getLecturesFromfiles();        
        }
        return lectures;
    }
    
    public HashMap<LectureWithDetailsModel,ArrayList<String>> getLecturesWithSimilaritySets()
    {
        if(lectureWithSimilaritySet==null)
        {
            lectureWithSimilaritySet=new HashMap<LectureWithDetailsModel,ArrayList<String>>();
            
            for(LectureWithDetailsModel lwd : lectures)
            {
                lectureWithSimilaritySet.put(lwd, getLectureSimiliratySet(lwd));
            }
        }
        return lectureWithSimilaritySet;
    }
     // </editor-fold>   
    

    private void groupByKeyWord(ArrayList<LectureWithDetailsModel> lectures)
    {
        HashMap<String,ArrayList<LectureWithDetailsModel>> map=new HashMap();
        HashMap<String,ArrayList<LectureWithDetailsModel>> map2=new HashMap();
        for(LectureWithDetailsModel lwd :lectures)
        {                      
            for(KeyWordModel kw:lwd.getGeneratedKeyWords())
            {
                 if(!map.containsKey(kw.getKeyWord()))
                 {
                     map.put(kw.getKeyWord(), new ArrayList<LectureWithDetailsModel>());
                 }
                 map.get(kw.getKeyWord()).add(lwd);
            }
           
        }
        
        HashSet<LectureWithDetailsModel> hs=new HashSet();
        for(Entry<String,ArrayList<LectureWithDetailsModel>> entry : map.entrySet())
        {
            if(entry.getValue().size()>1)
            {
                map2.put(entry.getKey(), entry.getValue());
                hs.addAll(entry.getValue());
            }
        }
               
        int x=0;
        
    }
            
    public float similarity(LectureWithDetailsModel lwd1,LectureWithDetailsModel lwd2)
    {
        HashMap<LectureWithDetailsModel,ArrayList<String>> hm=getLecturesWithSimilaritySets();
        
        ArrayList<String> set1=hm.get(lwd1);
        ArrayList<String> set2=hm.get(lwd2);
        int inter=0,unio=0,index1=0,index2=0;
        
        while(index1<set1.size() && index2<set2.size())
        {
            int comparResult=set1.get(index1).compareTo(set2.get(index2));
            if(comparResult<0)
            {
                index1++;
            }else if(comparResult==0)
            {
                index1++;
                index2++;
                inter++;
            }else
            {
                index2++;
            }
            unio++;
        }
        
        if(set1.size()-1==index1){
            unio+=set2.size()-index2;
        }
        if(set2.size()-1==index2)
        {
            unio+=set1.size()-index1;
        }
        
        return (float)inter/unio;
    }
    
    public TopicModel groupSessions()
    {
        return null;
    }
    
    public ArrayList<TopicModel> getTopics()
    {
        
        ArrayList<TopicModel> topics =new ArrayList<TopicModel>();        
       // ArrayList<Session> sessions=getSessions();
       
       ArrayList<String> topicsName=new ArrayList<String>();       
       ArrayList<LectureWithDetailsModel> lectures=getLecturesFromfiles();
       
       for(LectureWithDetailsModel lwd:lectures)
       {
           if(!topicsName.contains(lwd.getTopic()))
           {
               topicsName.add(lwd.getTopic());
           }
       }
       
       for(String topic:topicsName)
       {
           TopicModel p= new TopicModel();
           p.setTitle(topic);
           topics.add(p);
       }
       
       for(TopicModel t :topics)
       {
           ArrayList<SessionModel> sessions=new ArrayList<SessionModel>();
           SessionModel s=new SessionModel();
           int i=1;
           s.setTitle(t.getTitle()+" "+i);
           
           for(LectureWithDetailsModel lwd:lectures)
            {                
                if(lwd.getTopic().equals(t.getTitle()))
                {                                                        
                    if(s.getLectures().size()<4){
                        s.getLectures().add(lwd);
                    }else
                    {
                        sessions.add(s);
                        s=new SessionModel();
                        i++;
                        s.setTitle(t.getTitle()+" "+i);
                    }
                }
            }
           sessions.add(s);
           
           t.setSessions(sessions);
       }         
        return topics;
    }

}
