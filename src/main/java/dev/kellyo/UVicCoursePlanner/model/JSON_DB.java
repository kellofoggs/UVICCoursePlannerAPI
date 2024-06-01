package dev.kellyo.UVicCoursePlanner.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JSON_DB {

    private static boolean instance_exists;
    private HashMap<String, Course > classes_map;
    private Course courses_array[];
    private static JSON_DB json_db;
    private int db_size;

    /**
     * @return: The only allowed instance of the class
     * @param json_file: a file handle to a json database
     * Singleton getter that ensures that only one database may created at a time.
     * Enforces consistency across different classes
     *
     * */
    public static JSON_DB get_instance(File json_file){
        if (json_db == null){
           json_db = new JSON_DB(json_file);



        }
        return json_db;

    }




    //Singleton Constructor
    private JSON_DB(File json_file){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        classes_map = new HashMap<>();
        try{

            courses_array = mapper.readValue( json_file, Course[].class);
            System.out.print("SHITE");
            for (Course current: courses_array){
                classes_map.put(current.getCourseName(), current);
                db_size++;
            }
            System.out.println(db_size);
            int i = 1;
        }catch (IOException e){

        }


    }

    //Methods

    //Getters

    public static boolean isInstance_exists() {
        return instance_exists;
    }

    public HashMap<String, Course> getClasses_map() {
        return classes_map;
    }

    public Course[] getCourses_array() {
        return courses_array;
    }

    public static JSON_DB getJson_db() {
        return json_db;
    }

    public int getDb_size() {
        return db_size;
    }


    //Setters




}
