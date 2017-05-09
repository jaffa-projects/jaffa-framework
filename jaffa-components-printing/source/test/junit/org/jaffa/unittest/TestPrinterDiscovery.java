package org.jaffa.unittest;


import de.lohndirekt.print.*;
import java.util.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

/*
 * Printers.java
 *
 * Created on August 16, 2004, 11:28 AM
 */

/**
 *
 * @author  PaulE
 */
public class TestPrinterDiscovery {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Normal Discovery");
            lookup();
//
//            PrintServiceLookup lookup = new IppPrintServiceLookup(
//                        new java.net.URI("ipp://rnd-bs.wpds.com:631"),
//                        "root","normal");
//
//            // register your lookup instance
//            PrintServiceLookup.registerServiceProvider(lookup);
//            System.out.println("Discovery with IPP Service Loaded");
//            lookup();
//
//            System.out.println("Discovery with IPP Service Loaded");
//            lookup(lookup);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void lookup() {
        List p = new ArrayList();
        //PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        if(service==null)
            System.out.println("There is no Default Printer!");
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for(int i=0;i<services.length;i++) {
            System.out.println(services[i].getName() +
                    (service!=null && service.equals(services[i]) ? "*" : "" ) +
                    " " );
            if( services[i].isAttributeCategorySupported(PrinterMakeAndModel.class) ) {
                PrinterMakeAndModel model = (PrinterMakeAndModel)services[i].getAttribute(PrinterMakeAndModel.class);
                System.out.println("    Model - " + model);
            } else
                System.out.println("    Model - n/a");
            
            PrintServiceAttributeSet as = services[i].getAttributes();
            
//System.out.println(".");
            if(as!=null) {
//System.out.println("..");
                Attribute[] a = as.toArray();
                for(int j=0;j<a.length;j++) {
//System.out.println("...");
                    System.out.print("    Attribute - " + a[j].getName() + " = ");
                    
                    if (a[j] instanceof EnumSyntax)
                        System.out.print( ((EnumSyntax)a[j]).getValue() + " [Enum]");
                    else if (a[j] instanceof IntegerSyntax)
                        System.out.print( ((IntegerSyntax)a[j]).getValue()  + " [int]");
                    else if (a[j] instanceof ResolutionSyntax)
                        System.out.print( ((ResolutionSyntax)a[j]).getCrossFeedResolution(ResolutionSyntax.DPI) + " [Resolution]");
                    else if (a[j] instanceof SetOfIntegerSyntax)
                        System.out.print( ((SetOfIntegerSyntax)a[j]).getMembers()  + " [int set]");
                    else if (a[j] instanceof Size2DSyntax)
                        System.out.print( ((Size2DSyntax)a[j]).getSize(Size2DSyntax.INCH)  + " [2D Size]");
                    else if (a[j] instanceof TextSyntax)
                        System.out.print( ((TextSyntax)a[j]).getValue()  + " [Text]");
                    else if (a[j] instanceof URISyntax)
                        System.out.print( ((URISyntax)a[j]).getURI()  + " [URI]");
                    else
                        System.out.print( "'"+ a[j].toString() +"'"  + " [?]");
                    
                    System.out.println(" (" + a[j].getCategory() + ")");
                    
                    
                }
            }
            
            
            DocFlavor[] doc = services[i].getSupportedDocFlavors();
            if(doc!=null)
                for(int j=0;j<doc.length;j++) {
                System.out.println("    Doc Flavor - " + doc[j].getMimeType() + " / " +
                        doc[j].getMediaType() +  " / " + doc[j].getMediaSubtype()  +  " / " +
                        doc[j].getRepresentationClassName()
                        );
                
                }
            
            
            
        }
    }
    
    
    
    public static void lookup(PrintServiceLookup lookup) {
        List p = new ArrayList();
        PrintService service = lookup.getDefaultPrintService();
        if(service==null)
            System.out.println("There is no Default Printer!");
        PrintService[] services = lookup.getPrintServices(null, null);
        for(int i=0;i<services.length;i++) {
            System.out.println(services[i].getName() +
                    (service!=null && service.equals(services[i]) ? "*" : "" ) +
                    " " );
            //if( services[i].isAttributeCategorySupported(PrinterMakeAndModel.class) ) {
            PrinterMakeAndModel model = (PrinterMakeAndModel)services[i].getAttribute(PrinterMakeAndModel.class);
            System.out.println("    Model - " + model);
            //} else
            //    System.out.println("    Model - n/a");
            
            PrintServiceAttributeSet as = services[i].getAttributes();
            
//System.out.println(".");
            if(as!=null) {
//System.out.println("..");
                Attribute[] a = as.toArray();
                for(int j=0;j<a.length;j++) {
//System.out.println("...");
                    System.out.print("    Attribute - " + a[j].getName() + " = ");
                    
                    if (a[j] instanceof EnumSyntax)
                        System.out.print( ((EnumSyntax)a[j]).getValue() + " [Enum]");
                    else if (a[j] instanceof IntegerSyntax)
                        System.out.print( ((IntegerSyntax)a[j]).getValue()  + " [int]");
                    else if (a[j] instanceof ResolutionSyntax)
                        System.out.print( ((ResolutionSyntax)a[j]).getCrossFeedResolution(ResolutionSyntax.DPI) + " [Resolution]");
                    else if (a[j] instanceof SetOfIntegerSyntax)
                        System.out.print( ((SetOfIntegerSyntax)a[j]).getMembers()  + " [int set]");
                    else if (a[j] instanceof Size2DSyntax)
                        System.out.print( ((Size2DSyntax)a[j]).getSize(Size2DSyntax.INCH)  + " [2D Size]");
                    else if (a[j] instanceof TextSyntax)
                        System.out.print( ((TextSyntax)a[j]).getValue()  + " [Text]");
                    else if (a[j] instanceof URISyntax)
                        System.out.print( ((URISyntax)a[j]).getURI()  + " [URI]");
                    else
                        System.out.print( "'"+ a[j].toString() +"'"  + " [?]");
                    
                    System.out.println(" (" + a[j].getCategory() + ")");
                    
                    
                }
            }
            
            
            DocFlavor[] doc = services[i].getSupportedDocFlavors();
            if(doc!=null)
                for(int j=0;j<doc.length;j++) {
                System.out.println("    Doc Flavor - " + doc[j].getMimeType() + " / " +
                        doc[j].getMediaType() +  " / " + doc[j].getMediaSubtype()  +  " / " +
                        doc[j].getRepresentationClassName()
                        );
                
                }
            
            
            
        }
    }
    
}
