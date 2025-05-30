import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<Vertex<String>> seen = new HashSet<>();
    printShortWords(vertex, k, seen);
  }
  public static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> seen) {
    if(vertex == null || seen.contains(vertex)) return;
    seen.add(vertex);

    if(vertex.data.length() < k) System.out.println(vertex.data);

    for (Vertex<String> neighbor : vertex.neighbors) {
      printShortWords(neighbor, k, seen);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> seen = new HashSet<>();
    String longest = "";

    return longestWord(vertex, seen, longest);
  }

  public static String longestWord(Vertex<String> vertex, Set<Vertex<String>> seen, String longest){
    if(vertex == null || seen.contains(vertex)) return longest;
    if(vertex.data.length() > longest.length()){
      longest = vertex.data;
    }
    seen.add(vertex);
    for (Vertex<String> neighbor : vertex.neighbors) {
      return longestWord(neighbor, seen, longest);
    }

    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<Vertex<T>> seen = new HashSet<>();
    printSelfLoopers(vertex, seen);
  }

  public static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> seen) {
    if(vertex == null || seen.contains(vertex)) return;

    seen.add(vertex);

    for (Vertex<T> neighbor : vertex.neighbors) {
      if(neighbor == vertex){
        System.out.println(vertex.data);
      }printSelfLoopers(neighbor, seen);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    Set<Airport> visited = new HashSet<>();
    
    return canReach(start, destination, visited);
  }

  public static boolean canReach(Airport start, Airport destination, Set<Airport> visited) {    
    if(start == destination) return true;
    if(start == null || visited.contains(start)) return false;

    visited.add(start);
    System.out.println(start.getAirportCode());

    for (Airport outboundFlights : start.getOutboundFlights()) {
      if(canReach(outboundFlights, destination, visited)) return true;
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> seen = new HashSet<>();
    Set<T> unSeen = new HashSet<>(graph.keySet());

    unreachable(graph, starting, seen);

    for(T type : seen) {
      if(unSeen.contains(type)) unSeen.remove(type);
    }

    return unSeen;
  }

  public static <T> void unreachable(Map<T, List<T>> graph, T starting, Set<T> seen) {
    if(seen.contains(starting) || graph == null || graph.get(starting) == null) return;
    seen.add(starting);
    for(T neighbor : graph.get(starting)){
      unreachable(graph, neighbor, seen);
    }
  }
}
