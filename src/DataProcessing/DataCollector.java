/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Models.ConstraintModel;
import Models.DayModel;
import Models.KeyWordModel;
import Models.SessionModel;
import Models.TopicModel;
import Models.LectureWithDetailsModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.TermModel;
import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    private ArrayList<LectureWithDetailsModel>  getLecturesFromfiles(String pathToFolderWithFiles)
    {
        ArrayList<LectureWithDetailsModel> lectures=new ArrayList<LectureWithDetailsModel>();

        LectureReaderFromFile lrff=new LectureReaderFromFile(pathToFolderWithFiles);
        
        while(lrff.readNext())
        {
            LectureWithDetailsModel lecture = lrff.getCurrent();
            lectures.add(lecture);
        }
        return lectures;
    }
    
    private ArrayList<String> getLectureSimiliratySet(LectureWithDetailsModel lwd,String pathToThesaurus)
    {
        TreeSet<String> set=new TreeSet<String>();
        HashMap<String,TermModel> terms=getTerms(pathToThesaurus);
        
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
    public HashMap<String,TermModel> getTerms(String pathToThesaurus)
    {
        if(terms==null)
        {
            HashMap<String,TermModel> terms=new HashMap<String,TermModel>();
            
            File file = new File(pathToThesaurus);
            terms=new TermReaderFromFile().readTermsFromFile(file);
        
        }
        return terms;
    }
    
    public ArrayList<LectureWithDetailsModel> getLectures(String pathToFolderWithFiles)
    {
        if(lectures==null)
        {
            lectures=getLecturesFromfiles(pathToFolderWithFiles);        
        }
        return lectures;
    }
    
    public HashMap<LectureWithDetailsModel,ArrayList<String>> getLecturesWithSimilaritySets(String pathToThesaurus)
    {
        if(lectureWithSimilaritySet==null)
        {
            lectureWithSimilaritySet=new HashMap<LectureWithDetailsModel,ArrayList<String>>();
            
            for(LectureWithDetailsModel lwd : lectures)
            {
                lectureWithSimilaritySet.put(lwd, getLectureSimiliratySet(lwd,pathToThesaurus));
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
            
    public float similarity(LectureWithDetailsModel lwd1,LectureWithDetailsModel lwd2,String pathToThesaurus)
    {
        HashMap<LectureWithDetailsModel,ArrayList<String>> hm=getLecturesWithSimilaritySets(pathToThesaurus);
        
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
    
    public ArrayList<TopicModel> getTopics(String pathToFolderWithFiles)
    {
        
        ArrayList<TopicModel> topics =new ArrayList<TopicModel>();        
       // ArrayList<Session> sessions=getSessions();
       
       ArrayList<String> topicsName=new ArrayList<String>();       
       ArrayList<LectureWithDetailsModel> lectures=getLecturesFromfiles(pathToFolderWithFiles);
       
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
    
    public ArrayList<DayModel> getDays(int deafultBreakDuration, ArrayList<DayModel> days, ArrayList<TopicModel> topics,String pathToFolderWithFiles)
    {
        ArrayList<LocalTimeRangeModel> timeRanges=new ArrayList<LocalTimeRangeModel>();
        ArrayList<RoomModel> rooms=new ArrayList<RoomModel>();
        DayModel day =new DayModel();
        
        int maxSessionNumber=0;
        int i=0;
        for(TopicModel topic : topics)
        {
            if(topic.getSessions().size()>maxSessionNumber)
           {
               maxSessionNumber=topic.getSessions().size();
           }
           i++;
        }
        
        i=0;
        for(TopicModel topic : topics)
        {             
            RoomModel room=new RoomModel("Sala "+i);
            day.addRoom(room);
            rooms.add(room);
            i++;
        }
        
        LocalTime startTime=LocalTime.of(8, 0);
        for(i=0; i<maxSessionNumber;i++)
        {
            LocalTimeRangeModel time = new LocalTimeRangeModel(startTime,50);
            timeRanges.add(time);
            day.addTimeRange(time);
            
            LocalTimeRangeModel breakTime = new LocalTimeRangeModel(time.getEndTime(),deafultBreakDuration);
            day.addTimeRange(breakTime);

            SessionModel breakSession=new SessionModel();
            breakSession.makeBreak("Time break");

            for(RoomModel room : rooms)
            {
                day.addSession(breakSession, breakTime, room);
            }
            
            startTime=breakTime.getEndTime();
        }
        
        i=0;
        for(TopicModel topic : topics)
        {             
            int j=0;
            for(SessionModel session : topic.getSessions())
            {
               day.addSession(session, timeRanges.get(j), rooms.get(i));
               j++;
            }
            i++;
        }
    
        days.add(day);
        
        return days;
    }
    
    public ArrayList<ConstraintModel> getConstraints(ArrayList<TopicModel> topics)
    {
        ArrayList<ConstraintModel> constraints=new ArrayList<ConstraintModel>();
        
        for(TopicModel topic : topics)
        {
            for(SessionModel session : topic.getSessions())
            {
                for(String name:session.getChairs())
                {
                    ConstraintModel constraint=new ConstraintModel();
                    name = name.trim();
                    
                    constraint.setTeacherName(name);
                    constraints.add(constraint);
                }
                for(LectureWithDetailsModel lecture:session.getLectures())
                {
                    for(String name:lecture.getAuthors())
                    {
                        ConstraintModel constraint=new ConstraintModel();                       
                        name = name.trim();
                        
                        constraint.setTeacherName(name);
                        constraints.add(constraint);
                    }                   
                }
            }
        }
        
        return arrangingConstraints(constraints);
    }
    
    public ArrayList<ConstraintModel> arrangingConstraints(ArrayList<ConstraintModel> constraints)
    {
        //sort
        Collections.sort(constraints, new Comparator<ConstraintModel>() 
        {
            @Override
            public int compare(ConstraintModel constraint1, ConstraintModel constraint2)
            {
                return  constraint1.getTeacherName().compareTo(constraint2.getTeacherName());
            }
        });
        
        //remove dublicates
        ArrayList<ConstraintModel> newConstraints=new ArrayList<ConstraintModel>();
        newConstraints.add(constraints.get(0));
        
       for(int i=1;i<constraints.size();i++)
       {
           if(constraints.get(i).getTeacherName().compareTo(constraints.get(i-1).getTeacherName())!=0)
           {
               newConstraints.add(constraints.get(i));
           }
       }
       
       return newConstraints;
    }
}
