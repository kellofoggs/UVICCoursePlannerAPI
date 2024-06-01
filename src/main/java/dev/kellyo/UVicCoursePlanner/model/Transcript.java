package dev.kellyo.UVicCoursePlanner.model;
import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Transcript {

    // Map that has taken courses along with grade of course
    private HashMap<String, String> coursesTaken;

    // Map for converting
//    public static HashMap<>
    //Grade point average
    private double gpa;


    public Transcript(HashMap coursesTaken){
        this.coursesTaken = coursesTaken;



    }

    public Transcript(File file) throws IOException {
        PDDocument doc = Loader.loadPDF(file);
        PDFTextStripper strip = new PDFTextStripper();
//        PDFTextStripperByArea strip_down = new PDFTextStripperByArea();
//        strip_down.getRegions();
//        String text = strip_down.extractRegions(doc);
//        strip_down.getText()

        String text = strip.getText(doc);
        System.out.println(text);
        doc.close();
//        Scanner reader = new Scanner(file);
//        while (reader.hasNext()){
//            System.out.println(reader.nextLine());
//        }

    }

    //Getters
    public HashMap<String, String> getCoursesTaken() {
        return coursesTaken;
    }

    public double getGpa() {
        return gpa;
    }
}
