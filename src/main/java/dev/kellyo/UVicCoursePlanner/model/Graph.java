package dev.kellyo.UVicCoursePlanner.model;

import java.util.LinkedList;

public class Graph<E> {

    private Edge[] edgeMap;
    //dev.kellyo.UVicCoursePlanner.model.Graph uses both list of edges and adjacency list for fastest performance
    private class Edge {

        private Vertex vertex_one;
        private Vertex vertex_two;

        public Edge(Vertex v, Vertex w){
            vertex_one = v;
            vertex_two = w;


        }






    }



    private class Vertex<E>{

        private E data;
        private LinkedList<E> adjacencyList;
        /**
         * Constructor for vertex that allows
         * */
        public Vertex(E input){
            this.data = input;
            this.adjacencyList = new LinkedList<E>();
        }



    }
}
