/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagment;

import Helpers.StringHelper;
import Models.AplicationModel;
import Models.DayModel;
import Models.LectureModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.SessionModel;
import Models.TopicModel;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Ronaldo
 */
public class LatexExporter 
{
    private BufferedWriter out=null;
    private DataManager dataManager=null;
    public LatexExporter(BufferedWriter bufferWritter)
    {
        this.out=bufferWritter;
    }
    
    public void genearateLatex(AplicationModel aplicationModel) throws IOException
    {
        this.dataManager=new DataManager(aplicationModel);
        
        for(TopicModel topic:aplicationModel.getTopics())
        {
            out.write("\\part{"+topic.getTitle()+"}\n");
            out.write("\n");
            for(SessionModel session : topic.getSessions())
            {
                generateLatexSession(session);
            }           
        }
    }
    
    private void generateLatexSession(SessionModel session) throws IOException
    {
        DayModel day=dataManager.getDayBySessionId(session.getId());
        String dayString=StringHelper.getConverter().toString(day.getDay());
        LocalTimeRangeModel time=day.getTimeRangeBySessionId(session.getId());
        RoomModel room=day.getRoomBySessionId(session.getId());
        String chairAffiliation=dataManager.getAffiliationByName(session.getChair());
        String coChairAffiliation=dataManager.getAffiliationByName(session.getCoChair());
        
        out.write("\\section{{\\bf \\large Session:\n");
        out.write(session.getTitle()+"\n");
        out.write("}}\\vspace{-15pt}\n");
        out.write("\n");
        out.write("\\noindent\\rule{\\textwidth}{0.4pt} \\nopagebreak\n");
        out.write("{\\bf\n");
        out.write(dayString+","+time.toString()+","+room.getName()+"\n");
        out.write("} \\\\ \\nopagebreak\n");
        out.write("{\\bf  Chair:\n");
        out.write(session.getChair()+","+chairAffiliation+"\n");
        out.write("} \\\\ \\nopagebreak\n");
        out.write("{\\bf  Co-Chair:\n");
        out.write(session.getCoChair()+","+coChairAffiliation+"}\\\\\n");
        out.write("\\noindent\\rule{\\textwidth}{0.4pt} \\nopagebreak\n");
        out.write("\n");

        for(LectureModel lecture : session.getLectures())
        {
            generateLatexLecture(lecture);
        }
    }
    
    private void generateLatexLecture(LectureModel lecture) throws IOException 
    {
        out.write("\\vspace*{-36pt}\n");
        out.write("    \\subsection[\n");
        out.write("    	   {\\bf "+lecture.getTitle()+"\n");
        out.write("           } \\\\\n");
        out.write("           {\\it "+StringHelper.createListSeparateComma(lecture.getAuthors())+"\n");
        out.write("           }\n");
        out.write("	]\n");
        out.write("	    {\n");
        for(String name:lecture.getAuthors())
        {
            out.write("            \\index{"+name+"@"+name+"}\n");           
        }
        out.write("            }\n");
        out.write("	    {\\small "+lecture.getPeriod().toString()+"} \\nopagebreak\n");
        out.write("\\noindent \n");
        out.write("{\\bf \\small "+lecture.getTitle()+"}\n");
        out.write("\\nopagebreak\n");
        out.write("\n");
        out.write("\\noindent \n");
        out.write("{\\it \\footnotesize \\noindent\n");
        for(String name:lecture.getAuthors())
        {
            out.write(name+" \\hfill "+dataManager.getAffiliationByName(name)+"\\\\ \n");            
        }
        out.write("}\n");
        out.write("\\nopagebreak\n");
        out.write("\\noindent \n");
        out.write("{\\bf \\small Abstract:} \\\\ \n");
        out.write("{\\small\n");
        out.write(lecture.getAbstarct()+"\n");
        out.write("}\n");
        out.write("            \\\\ \n");
        out.write("            \\noindent\\rule{\\textwidth}{0.4pt}\n");
        out.write("\n");
        
    }      
}
