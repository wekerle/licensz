/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import DataManagment.DataManager;
import Helpers.StringHelper;
import Models.AplicationModel;
import Models.DayModel;
import Models.LectureModel;
import Models.SessionModel;
import Models.TopicModel;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Ronaldo
 */
public class HtmlExporter 
{
    private BufferedWriter out=null;
    private DataManager dataManager=null;
    public HtmlExporter(BufferedWriter bufferWritter)
    {
        this.out=bufferWritter;
    }
    
    public void genearateHtml(AplicationModel aplicationModel) throws IOException
    {
        dataManager=new DataManager(aplicationModel);
        out.write("<div class=\"ce_text block\">\n");
        out.write("   <h1>Detailed Technical Program</h1>\n");
        for(TopicModel topic:aplicationModel.getTopics())
        {
            generateHtmlTopic(topic);
        }
        out.write("   <p>&nbsp;</p>\n");
        out.write("</div>\n");        
    }
    
    private void generateHtmlTopic(TopicModel topic) throws IOException
    {
        out.write("<h2><strong>"+topic.getTitle()+"<br></strong></h2>\n");
        for(SessionModel session:topic.getSessions())
        {
            generateHtmlSession(session);
        }
        
    }
    
    private void generateHtmlSession(SessionModel session) throws IOException
    {
        DayModel day=dataManager.getDayBySessionId(session.getId());
        String chairAffiliation=dataManager.getAffiliationByName(session.getChair());
        String coChairAffiliation=dataManager.getAffiliationByName(session.getCoChair());
        
        out.write("<h3 style=\"color: #88262d; text-transform: capitalize;\"><strong>"+session.getTitle()+" ("+StringHelper.getConverter().toString(day.getDay())+" </strong>"+day.getTimeRangeBySessionId(session.getId())+")</h3>\n");
        out.write("   <table>\n");
        out.write("      <tbody>\n");
        out.write("         <tr>\n");
        out.write("<td><strong>Chair: </strong>"+session.getChair()+"<strong><br>Co-Chair: </strong>"+session.getCoChair()+"</td>\n");
        out.write("<td style=\"text-align: right;padding-right:10px;\">"+chairAffiliation+"<br>"+coChairAffiliation+"</td>\n");
        out.write("         </tr>\n");
        out.write("      </tbody>\n");
        out.write("   </table>\n");
        
        for(LectureModel lecture : session.getLectures())
        {
            generateHtmlLecture(lecture);
        }       
    }

    private void generateHtmlLecture(LectureModel lecture) throws IOException 
    {
        out.write("   <table>\n");
        out.write("      <tbody>\n");
        out.write("         <tr>\n");
        out.write("            <td colspan=\"2\" style=\"padding-right:10px;\"><strong><a href=\"\" onclick=\"toggleAbs('abs"+lecture.getId()+"'); return false;\">"+lecture.getTitle()+"</a></strong></td>\n");
        out.write("         </tr>\n");
        out.write("         <tr>\n");           
        out.write("            <td>");
            for(String name:lecture.getAuthors())
            {
                 out.write(name+" <br>");
            }
        out.write("            </td>\n");
        out.write("            <td style=\"text-align: right;padding-right:10px;\">");
        for(String name:lecture.getAuthors())
        {
            String affiliation=dataManager.getAffiliationByName(name);           
            out.write(affiliation+"<br>");
        }
        out.write("            </td>\n");
        out.write("         </tr>\n");
        out.write("         <tr>\n");
        out.write("            <td colspan=\"2\" style=\"padding-right:10px;\">\n");
        out.write("<div id=\"abs"+lecture.getId()+"\" style=\"text-align: justify; display: none;\"><strong>Abstract:</strong> <br>\n");
        out.write(lecture.getAbstarct()+"\n");
        out.write("               </div>\n");
        out.write("            </td>\n");
        out.write("         </tr>\n");
        out.write("      </tbody>\n");
        out.write("   </table>\n");
        
    }
}
