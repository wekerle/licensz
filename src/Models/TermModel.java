/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Ronaldo
 */

// ki kell javitsam a kicsi beture a neveket
public class TermModel {
     private TermModel BroaderTerm;
     private ArrayList<TermModel> NarrowerTerm;
     private ArrayList<TermModel> RelatedTerms;
     private TermModel USE;
     private ArrayList<TermModel> UseFor;
     private String Name;
     
     public TermModel(String Name){
         this.Name=Name;
     }
    public TermModel getBroaderTerm() {
        return BroaderTerm;
    }

    public void setBroaderTerm(TermModel BroaderTerm) {
        this.BroaderTerm = BroaderTerm;
    }

    public ArrayList<TermModel> getNarrowerTerm() {
        return NarrowerTerm;
    }

    public void addNarrowerTerm(TermModel NarrowerTerm) {
        if(this.NarrowerTerm==null)
        {
            this.NarrowerTerm=new ArrayList<TermModel>();
        }
        this.NarrowerTerm.add(NarrowerTerm);
    }

    public ArrayList<TermModel> getRelatedTerms() {
        return RelatedTerms;
    }

    public void addRelatedTerms(TermModel RelatedTerms) {
        if(this.RelatedTerms==null)
        {
            this.RelatedTerms=new ArrayList<TermModel>();
        }
        this.RelatedTerms.add(RelatedTerms);
    }

    public TermModel getUSE() {
        return USE;
    }

    public void setUSE(TermModel USE) {
        this.USE = USE;
    }

    public ArrayList<TermModel> getUseFor() {
        return UseFor;
    }

    public void addUseFor(TermModel UseFor) {
        if(this.UseFor==null)
        {
            this.UseFor=new ArrayList<TermModel>();
        }
        this.UseFor.add(UseFor);
    }
     
}
