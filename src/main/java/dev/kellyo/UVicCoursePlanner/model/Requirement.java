package dev.kellyo.UVicCoursePlanner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

@Getter
@Setter
@ToString
public class Requirement implements Cloneable {

    /*Attributes*/
    private String type;
    private String name;
    private String quantity;
    private ArrayList<Requirement> sub_maps;

    public Requirement(String type, String name, String quantity, ArrayList<Requirement> sub_maps){
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.sub_maps = sub_maps;


    }



    /**
     * Public wrapper method for sub reqs satisfied method
     * Takes in hashset that represents what courses on transcipt have been taken
     * */
    public boolean isSatisfied(HashSet takenCourses) {
        JSON_DB db = JSON_DB.getJson_db();
        return sub_reqs_satisfied(takenCourses, db);
    }

    private boolean sub_reqs_satisfied(HashSet takenCourses, JSON_DB db){
        //Consider edge case with variable


        // If we're at a 'complete' or 'units' type
        double level_quantity = Double.parseDouble(this.quantity);


        if (type.equals("requirement")) {
            //Evaluate sub level
            for (Requirement sub_req : sub_maps) {
                //Go down to bottom
                if (sub_req.sub_reqs_satisfied(takenCourses, db)) {
                    level_quantity = level_quantity - 1;
                }
                //
//                // If the student has taken the course
//                if ( takenCourses.contains(sub_req.getName()) ){
//                    level_quantity--;
//                    System.out.println("Student has taken: " + sub_req.getName());
//                }
            }
        }
        // course type objects do not have sub reqs, stop going down here
        if (this.type.equals("course")){

            //Instead check if the course is in the taken set
            return (takenCourses.contains(this.getName()));
//            {
//               return true;
//           }else{
//               return false;
//           }

        }

        if (level_quantity <= 0){
            return true;
        }

        if (type.equals("units")){

        }



//        For future when calculating shortest path to taking course
//        if (!type.matches("course|other")){
//            for (Requirement req: sub_reqs){
//
//            }
//        }else{
//            return true;
//        }

        return false;
    }
//    private boolean satisfied;

    public boolean checkNull(){
        return this.type == null &&
               this.name == null &&
               this.quantity == null &&
               this.sub_maps == null;
    }






    @Override
    public Requirement clone() {
        try {
            Requirement clone = (Requirement) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    //Course requirements are equal if they have the same name (course code is a course requirement's name)
    // Other requirement objects are equal if are the same object
    @Override
    public boolean equals(Object other_obj) {
        if (other_obj == null){
            return false;
        }
        if (other_obj instanceof Requirement){
            Requirement other_req = (Requirement) other_obj;
            if (this.getType() == null || other_req.getType() == null){
                return false;
            }
            if (this.getType().equals("course") && other_req.getType().equals("course")){
                return this.getName().equals(other_req.getName());
            }else{
                return super.equals(other_obj);


            }

        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, quantity, sub_maps);
    }
//    private List<Requirementtwo> sub_maps;
//        private List<dev.kellyo.UVicCoursePlanner.model.Requirement> sub;
}
