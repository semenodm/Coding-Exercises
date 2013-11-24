package org.sdo.coding.camel;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.drools.decisiontable.InputType;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ByteArrayResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class DefaultDroolsDecisionTableBuilder implements DroolsDecisionTableBuilder{

    private String rulesPath;
    private String templatePath;
    private InputType inputType;
    private int startRow;
    private int startCol;

    public KnowledgeBase buildKBase() {
        //first we compile the decision table into a whole lot of rules.
        final ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();

        String basePricingDRL = null;
        try {
            basePricingDRL = converter.compile(getSpreadsheetStream(rulesPath), getBasePricingRulesStream(templatePath), inputType, startRow, startCol);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid spreadsheet stream.", e);
        }

        //compile the drls
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(new ByteArrayResource(basePricingDRL.getBytes()), ResourceType.DRL);

        System.out.println(basePricingDRL);

        //compilation errors?
        if (kbuilder.hasErrors()) {
            System.out.println("Error compiling resources:");
            Iterator<KnowledgeBuilderError> errors = kbuilder.getErrors().iterator();
            while (errors.hasNext()) {
                System.out.println("\t" + errors.next().getMessage());
            }
            throw new IllegalStateException("Error compiling resources");
        }

        //BUILD KBASE
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        return kbase;

    }

    static InputStream getSpreadsheetStream(String rulesPath) throws IOException {
        return ResourceFactory.newClassPathResource(rulesPath).getInputStream();
    }

    static InputStream getBasePricingRulesStream(String templatePath) throws IOException {
        return ResourceFactory.newClassPathResource(templatePath).getInputStream();
    }

    public void setRulesPath(String rulesPath) {
        this.rulesPath = rulesPath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }
}