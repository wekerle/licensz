/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Helpers.StringHelper;
import Models.AplicationModel;
import Models.LectureModel;
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
    public LatexExporter(BufferedWriter bufferWritter)
    {
        this.out=bufferWritter;
    }
    
    public void genearateLatex(AplicationModel aplicationModel) throws IOException
    {
        for(TopicModel topic:aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                for(LectureModel lecture : session.getLectures())
                {
                    generateLatexLecture(lecture);
                    break;
                }
            }
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
            String name1="tibor, wekerle";
            String name2="wekerle, tibor";
            out.write("            \\index{"+name1+"@"+name2+"\n");
            
        }
        out.write("            }\n");
        out.write("	    {\\small 10:20 -- 10:40} \\nopagebreak\n");
        out.write("\\noindent \n");
        out.write("{\\bf \\small "+lecture.getTitle()+"}\n");
        out.write("\\nopagebreak\n");
        out.write("\n");
        out.write("\\noindent \n");
        out.write("{\\it \\footnotesize \\noindent\n");
        for(String name:lecture.getAuthors())
        {
            out.write(name+" \\hfill ICCPTBA\\\\ \n");            
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
