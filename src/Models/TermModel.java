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
     private TermModel broaderTerm;
     private ArrayList<TermModel> narrowerTerm;
     private ArrayList<TermModel> relatedTerms;
     private TermModel use;
     private ArrayList<TermModel> useFor;
     private String name;
     
     public TermModel(String name){
         this.name=name;
     }
    public TermModel getBroaderTerm() {
        return broaderTerm;
    }

    public void setBroaderTerm(TermModel broaderTerm) {
        this.broaderTerm = broaderTerm;
    }

    public ArrayList<TermModel> getNarrowerTerm() {
        return narrowerTerm;
    }

    public void addNarrowerTerm(TermModel narrowerTerm) {
        if(this.narrowerTerm==null)
        {
            this.narrowerTerm=new ArrayList<TermModel>();
        }
        this.narrowerTerm.add(narrowerTerm);
    }

    public ArrayList<TermModel> getRelatedTerms() {
        return relatedTerms;
    }

    public void addRelatedTerms(TermModel relatedTerms) {
        if(this.relatedTerms==null)
        {
            this.relatedTerms=new ArrayList<TermModel>();
        }
        this.relatedTerms.add(relatedTerms);
    }

    public TermModel getUSE() {
        return use;
    }

    public void setUSE(TermModel use) {
        this.use = use;
    }

    public ArrayList<TermModel> getUseFor() {
        return useFor;
    }

    public void addUseFor(TermModel useFor) {
        if(this.useFor==null)
        {
            this.useFor=new ArrayList<TermModel>();
        }
        this.useFor.add(useFor);
    }
     
}
