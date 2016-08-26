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
import Models.LectureModel;
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
    private HashMap<String,String> affiliations= new  HashMap<String,String>();
    private HashMap<LectureModel,ArrayList<String>> lectureWithSimilaritySet= new  HashMap<LectureModel,ArrayList<String>>();
    private ArrayList<LectureModel> lectures = new  ArrayList<LectureModel>();
    
    private ArrayList<LectureModel>  getLecturesFromfiles(String pathToFolderWithFiles)
    {
        ArrayList<LectureModel> lectures=new ArrayList<LectureModel>();

        LectureReaderFromFile lrff=new LectureReaderFromFile(pathToFolderWithFiles);
        
        while(lrff.readNext())
        {
            LectureModel lecture = lrff.getCurrent();
            lectures.add(lecture);
        }
        return lectures;
    }
    
    private ArrayList<String> getLectureSimiliratySet(LectureModel lwd,String pathToThesaurus)
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
        if(terms.isEmpty())
        {
            HashMap<String,TermModel> terms=new HashMap<String,TermModel>();
            
            File file = new File(pathToThesaurus);
            terms=new TermReaderFromFile().readTermsFromFile(file);
        
        }
        return terms;
    }
    
    public ArrayList<LectureModel> getLectures(String pathToFolderWithFiles)
    {
        if(lectures.isEmpty())
        {
            lectures=getLecturesFromfiles(pathToFolderWithFiles);        
        }
        return lectures;
    }
    
    public HashMap<LectureModel,ArrayList<String>> getLecturesWithSimilaritySets(String pathToThesaurus)
    {
        if(lectureWithSimilaritySet==null)
        {
            lectureWithSimilaritySet=new HashMap<LectureModel,ArrayList<String>>();
            
            for(LectureModel lwd : lectures)
            {
                lectureWithSimilaritySet.put(lwd, getLectureSimiliratySet(lwd,pathToThesaurus));
            }
        }
        return lectureWithSimilaritySet;
    }
     // </editor-fold>   
    

    private void groupByKeyWord(ArrayList<LectureModel> lectures)
    {
        HashMap<String,ArrayList<LectureModel>> map=new HashMap();
        HashMap<String,ArrayList<LectureModel>> map2=new HashMap();
        for(LectureModel lwd :lectures)
        {                      
            for(KeyWordModel kw:lwd.getGeneratedKeyWords())
            {
                 if(!map.containsKey(kw.getKeyWord()))
                 {
                     map.put(kw.getKeyWord(), new ArrayList<LectureModel>());
                 }
                 map.get(kw.getKeyWord()).add(lwd);
            }
           
        }
        
        HashSet<LectureModel> hs=new HashSet();
        for(Entry<String,ArrayList<LectureModel>> entry : map.entrySet())
        {
            if(entry.getValue().size()>1)
            {
                map2.put(entry.getKey(), entry.getValue());
                hs.addAll(entry.getValue());
            }
        }
               
        int x=0;
        
    }
            
    public float similarity(LectureModel lwd1,LectureModel lwd2,String pathToThesaurus)
    {
        HashMap<LectureModel,ArrayList<String>> hm=getLecturesWithSimilaritySets(pathToThesaurus);
        
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
       ArrayList<LectureModel> lectures=getLecturesFromfiles(pathToFolderWithFiles);
       
       for(LectureModel lwd:lectures)
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
           
           for(LectureModel lwd:lectures)
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
    
    public ArrayList<DayModel> getDays(int deafultBreakDuration, ArrayList<DayModel> days, ArrayList<TopicModel> topics)
    {
        for(DayModel day:days)
        {
            int i=0;
            for(TopicModel topic : topics)
            {             
                RoomModel room=new RoomModel("Sala "+i,topic.getId());
                day.addRoom(room);
                i++;
            }
            
            LocalTime startTime=day.getTotalPeriod().getStartTime();
            for(i=0; i<day.getNumberOfSessionsPerRoom();i++)
            {
                LocalTimeRangeModel time = new LocalTimeRangeModel(startTime,50);
                LocalTimeRangeModel breakTime = new LocalTimeRangeModel(time.getEndTime(),deafultBreakDuration);
                
                if(breakTime.getEndTime().isAfter(day.getTotalPeriod().getEndTime()))
                {
                    break;
                }
                
                day.addTimeRange(time);
                day.addTimeRange(breakTime);

                SessionModel breakSession=new SessionModel();
                breakSession.makeBreak("Time break");

                for(RoomModel room : day.getRooms())
                {
                    day.addSession(breakSession, breakTime, room);
                }

                startTime=breakTime.getEndTime();
            }
            
        }

        int i=0;
        for(TopicModel topic : topics)
        {             
            int j=0;
            int dayNr=0;
            for(SessionModel session : topic.getSessions())
            {
                DayModel currentDay=days.get(dayNr);
                
                if(currentDay.getTimes().size()<=j)
                {
                    dayNr++;
                    if(dayNr>=days.size())
                    {
                        dayNr--;
                        ArrayList<LocalTimeRangeModel> allTimesOfTheDay=currentDay.getTimes();                      
                        LocalTimeRangeModel lastTime=allTimesOfTheDay.get(allTimesOfTheDay.size()-1);
                        
                        LocalTimeRangeModel time = new LocalTimeRangeModel(lastTime.getEndTime(),50);
                        LocalTimeRangeModel breakTime = new LocalTimeRangeModel(time.getEndTime(),deafultBreakDuration);
                        
                        currentDay.addTimeRange(time);
                        currentDay.addTimeRange(breakTime);

                        SessionModel breakSession=new SessionModel();
                        breakSession.makeBreak("Time break");

                        for(RoomModel room : currentDay.getRooms())
                        {
                            currentDay.addSession(breakSession, breakTime, room);
                        }
                    } else 
                    {
                       currentDay=days.get(dayNr);
                       j=0;
                    }
                }
                currentDay.addSession(session, currentDay.getTimes().get(j), currentDay.getRooms().get(i));
                j+=2;
            }
            i++;
        }
        
        for(DayModel day : days)
        {
            day.cleanUpUselessBreaks();
            day.deleteEmptyRoom();
        }
        
        for(i=days.size()-1;i>=0;i--)
        {
            if(days.get(i).getNumberOfSessions()==0)
            {
                days.remove(i);
            }
        }
        
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
                for(LectureModel lecture:session.getLectures())
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
    
     public HashMap<String,String> getTeacherAffiliations(String path)
     {
        if(affiliations.isEmpty())
        {
            File file = new File(path);
            affiliations=new TeacherAffiliationReaderFromFile().readTeacherAffiliationFromFile(file);
        }      
        return affiliations;
     }
}
