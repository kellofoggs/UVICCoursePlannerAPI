package dev.kellyo.UVicCoursePlanner.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File my_file = new File("results.json");
        JSON_DB db = JSON_DB.get_instance(my_file);
        File transcript_file = new File("C:\\Users\\Kelly\\Documents\\PersonalProjects\\UVicCoursePlanner\\src\\main\\java\\dev\\kellyo\\UVicCoursePlanner\\Kelly Ojukwu UVic Transcript.pdf");
        Transcript myTranscript = new Transcript(transcript_file);


    }
}
