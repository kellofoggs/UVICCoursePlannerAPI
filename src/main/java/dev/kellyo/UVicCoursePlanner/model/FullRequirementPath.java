package dev.kellyo.UVicCoursePlanner.model;

import dev.kellyo.UVicCoursePlanner.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

//@AllArgsConstructor
public class FullRequirementPath {

    private ObjectId id;
    private String courseCode;
    private Requirement headReq;

    private LinkedList vertices;
    private LinkedList edgeList;

    private HashSet<Requirement> seenVertices;
    private HashMap<Requirement, LinkedList<Requirement>>adjacency_map;
    private HashMap<Requirement, Integer> requirementIntegerHashMap;

    public FullRequirementPath(){
        adjacency_map = new HashMap<>();
        requirementIntegerHashMap = new HashMap<>();
        seenVertices = new HashSet<>();



    }

    public void addRequirement(Requirement source, Requirement target){

        if (adjacency_map.get(source) != null){
            adjacency_map.get(source).add(target);

        }else{
            LinkedList<Requirement> new_list = new LinkedList<>();
            new_list.add(target);
            adjacency_map.put(source, new_list);
        }

    }



    //Create an adjacency list graph from the pre-requisite tree, we are assured that all of our prerequisite graphs are DAGS
    // We want to know every edge in the tree, so we use if an edge has been visited rather than vertex to determine next step
    public void build_pre_req_graph(Requirement starting_req, CourseService courseService){

        // Want to keep track of visited edges, instead of nodes to account for
        List<Edge> output_list = new LinkedList<Edge>();
        HashMap output_map = new HashMap();
        LinkedList<Requirement> nodes = new LinkedList<>();
        HashSet<Edge> seenEdges = new HashSet<>();
        HashSet<String> visitedCourses = new HashSet<>();
        HashSet visitedNodes = new HashSet();

        Queue<Requirement> to_visit_queue = new LinkedList<>();
        Requirement clone_req = starting_req.clone();
        int head_index;
        int nodes_seen = 0;
        Requirement current;

        LinkedList<Requirement> neighbors;
        if (clone_req.getType() != null) {
            to_visit_queue.add(clone_req);
        }

        // Transform adjacency list implementation into edge list and vertex list implementation for use with cytoscape.js
        while (!to_visit_queue.isEmpty()){
            //Visit the current node
            current = to_visit_queue.remove();
            neighbors = new LinkedList<>();

            if (current.getSub_maps() != null){
                neighbors.addAll(current.getSub_maps());
            }

            if (current.getType().equals("course")){
                //If we've already seen the course, don't add the edge

                visitedCourses.add(current.getName());
                Optional<Course> optional_course = courseService.singleCourse(current.getName());
                Course courseObj;
                if (optional_course.isPresent()){
                    courseObj = optional_course.get().clone();

                    neighbors.add(courseObj.getPrereqs().clone());
//                    to_visit_queue.add(courseObj.getPrereqs());
                }

            }else{



            }




            if (!seenVertices.contains(current)){
                seenVertices.add(current);
                requirementIntegerHashMap.put(current, nodes_seen);
                nodes_seen++;
            }

//            System.out.println(current);
            //Add neighbor nodes to "to visit" nodes list





            //If we're at a 'course' requirement, we want to expand the pre req tree by having that course's pre reqs
            // in the tree as well as endpoints of outgoing edges from course type requirement

            current.setSub_maps(null);

//            to_visit_queue.addAll(neighbors);


            if (!visitedNodes.contains(current)) {


                for (Requirement neighbor : neighbors) {
                    if (!seenVertices.contains(neighbor)) {
                        seenVertices.add(neighbor);
                        requirementIntegerHashMap.put(neighbor, nodes_seen);
//                    System.out.println(neighbor);
                        nodes_seen++;
                    }
                    Edge potential_edge = new Edge(current, neighbor);

                    // If we've already looked at this edge don't add it to the to visit queue
                    if (seenEdges.contains(potential_edge) || neighbor.getType() == null) {
                        continue;
                    } else {
                        to_visit_queue.add(neighbor);
                        seenEdges.add(potential_edge);

                        // If we haven't seen this edge then add it to the graph edge list
                        //Else

                    }
                }
            }

            visitedNodes.add(current);


        }
//
        System.out.println("Seen edges" + seenEdges);
//        System.out.println("HEY YALL\n"+this.requirementIntegerHashMap);
        LinkedList edges = new LinkedList<>((seenEdges));
    }

    public void current_course(Requirement current){

    }


    private class Graph {

        // A Vertex should only be in a graph once, so put it into a hash set
        HashSet<Vertex> vertices;
        LinkedList<Edge> edgeList;

        public Graph() {


        }


        public void addEdge(Vertex source, Vertex target) {
            //Add vertex to vertices if its not already in the seen vertices
            if (!this.vertices.contains(source)){
                this.vertices.add(source);
            }
            Edge potential_edge = new Edge(source, target);
            if (!edgeList.contains(potential_edge)){
                this.edgeList.add(potential_edge);
            }
//            if ( seenEdges.contains(potential_edge) ){
//                continue;
//            }else {
//                to_visit_queue.add(neighbor);
//                seenEdges.add(potential_edge);
//        }

        }
    }
    private class Edge{
        private Object source;
        private Object target;

        public Edge(Object source, Object target){
            this.source = source;
            this.target = target;
        }

        public Object getSource() {
            return source;
        }

        public Object getTarget() {
            return target;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Edge){
                Edge other_edge = (Edge)obj;
                return other_edge.getSource().equals(this.getSource()) && other_edge.getTarget().equals(this.getTarget());
            }else{
                return false;
            }

        }

        @Override
        public int hashCode() {
            return Objects.hash(source, target);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "source=" + source +
                    ", target=" + target +
                    '}';
        }
    }

    private class Vertex{
        private int vertexNumber;
//        private

    }
}
