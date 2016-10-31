package org.sdo.algorythms.graph;

import java.util.*;

/**
 * Created by dsemenov
 * Date: 10/12/16.
 */
public class SynchronousShopping {

  public static class SearchEntry implements Comparable<SearchEntry> {
    private int dist;
    private int fish;
    private int shop;

    @Override
    public int compareTo(SearchEntry o) {
      return Integer.valueOf(dist).compareTo(o.dist);
    }
  }

  public static class MinPQ<Key> {
    private Key[] pq;                    // store items at indices 1 to N
    private int N;                       // number of items on priority queue

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param initCapacity the initial capacity of this priority queue
     */
    public MinPQ(int initCapacity) {
      pq = (Key[]) new Object[initCapacity + 1];
      N = 0;
    }


    /**
     * Returns true if this priority queue is empty.
     *
     * @return <tt>true</tt> if this priority queue is empty;
     * <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
      return N == 0;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
      return N;
    }


    // helper function to double the size of the heap array
    private void resize(int capacity) {
      assert capacity > N;
      Key[] temp = (Key[]) new Object[capacity];
      for (int i = 1; i <= N; i++) {
        temp[i] = pq[i];
      }
      pq = temp;
    }

    /**
     * Adds a new key to this priority queue.
     *
     * @param x the key to add to this priority queue
     */
    public void insert(Key x) {
      // double size of array if necessary
      //if (N == pq.length - 1) resize(2 * pq.length);

      // add x, and percolate it up to maintain heap invariant
      pq[++N] = x;
      swim(N);
      //assert isMinHeap();
    }

    /**
     * Removes and returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMin() {
      if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
      exch(1, N);
      Key min = pq[N--];
      sink(1);
      pq[N + 1] = null;         // avoid loitering and help with garbage collection
      //if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
      //assert isMinHeap();
      return min;
    }


    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    private void swim(int k) {
      while (k > 1 && greater(k / 2, k)) {
        exch(k, k / 2);
        k = k / 2;
      }
    }

    private void sink(int k) {
      while (2 * k <= N) {
        int j = 2 * k;
        if (j < N && greater(j, j + 1)) j++;
        if (!greater(k, j)) break;
        exch(k, j);
        k = j;
      }
    }

    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/
    private boolean greater(int i, int j) {
      return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j) {
      Key swap = pq[i];
      pq[i] = pq[j];
      pq[j] = swap;
    }

    // is pq[1..N] a min heap?
    private boolean isMinHeap() {
      return isMinHeap(1);
    }

    // is subtree of pq[1..N] rooted at k a min heap?
    private boolean isMinHeap(int k) {
      if (k > N) return true;
      int left = 2 * k, right = 2 * k + 1;
      if (left <= N && greater(k, left)) return false;
      if (right <= N && greater(k, right)) return false;
      return isMinHeap(left) && isMinHeap(right);
    }
  }

  public static class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final int weight;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param v      one vertex
     * @param w      the other vertex
     * @param weight the weight of this edge
     * @throws IndexOutOfBoundsException if either {@code v} or {@code w}
     *                                   is a negative integer
     * @throws IllegalArgumentException  if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, int weight) {
      if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
      if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
      if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
      this.v = v;
      this.w = w;
      this.weight = weight;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public int weight() {
      return weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
      return v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *                                  endpoints of this edge
     */
    public int other(int vertex) {
      if (vertex == v) return w;
      else if (vertex == w) return v;
      else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     * the weight of this is less than, equal to, or greater than the
     * argument edge
     */
    @Override
    public int compareTo(Edge that) {
      return Double.compare(this.weight, that.weight);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
      return String.format("%d-%d %.5f", v, w, weight);
    }

    /**
     * Unit tests the {@code Edge} data type.
     *
     * @param args the command-line arguments
     */
  }


  public static class EdgeWeightedDigraph {

    private final int V;                // number of vertices in this digraph
    private final int[] vertices;
    private int E;                      // number of edges in this digraph
    private List<Edge>[] adj;    // adj[v] = adjacency list for vertex v

    /**
     * Initializes an empty edge-weighted digraph with <tt>V</tt> vertices and 0 edges.
     *
     * @param V the number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    public EdgeWeightedDigraph(int V) {
      if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
      this.V = V;
      this.vertices = new int[V];
      this.E = 0;
      adj = (List<Edge>[]) new LinkedList[V];
      for (int v = 0; v < V; v++) {
        adj[v] = new LinkedList<>();
      }
    }


    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
      return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
      return E;
    }

    /**
     * Adds the directed edge <tt>e</tt> to this edge-weighted digraph.
     *
     * @param e the edge
     * @throws IndexOutOfBoundsException unless endpoints of edge are between 0 and V-1
     */
    public void addEdge(Edge e) {
      int v = e.either();
      int w = e.other(v);
      adj[v].add(e);
      adj[w].add(e);
      E++;
    }


    /**
     * Returns the directed edges incident from vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the directed edges incident from vertex <tt>v</tt> as an Iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Edge> adj(int v) {
      return adj[v];
    }


    public void addVertex(int i, int fishMask) {
      this.vertices[i] = fishMask;
    }
  }

  public static class DijkstraSP {
    private int[][] distTo;          // distTo[v] = distance  of shortest s->v path
    private MinPQ<SearchEntry> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex <tt>s</tt> to every other
     * vertex in the edge-weighted digraph <tt>G</tt>.
     *
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s, int K) {
      int masks = 1 << K;

      distTo = new int[G.V()][masks];
      for (int v = 0; v < G.V(); v++) {
        for (int j = 0; j < masks; j++) {
          distTo[v][j] = Integer.MAX_VALUE;
        }
      }
      distTo[s][G.vertices[s]] = 0;
      SearchEntry searchEntry = new SearchEntry();
      searchEntry.dist = 0;
      searchEntry.fish = G.vertices[s];
      searchEntry.shop = s;


      // relax vertices in order of distance from s
      pq = new MinPQ<>(G.V()*100);
      pq.insert(searchEntry);
      while (!pq.isEmpty()) {
        searchEntry = pq.delMin();
        for (Edge e : G.adj(searchEntry.shop))
          relax(searchEntry, e, G);
      }
    }

    // relax edge e and update pq if changed
    private void relax(SearchEntry searchEntry, Edge e, EdgeWeightedDigraph g) {
      int w = e.other(searchEntry.shop);
      int bw = g.vertices[w] | searchEntry.fish;
      SearchEntry next = new SearchEntry();
      next.dist = searchEntry.dist + e.weight();
      next.shop = w;
      next.fish = bw;
      if (next.dist >= distTo[next.shop][bw]) {
        return;
      }
      distTo[next.shop][next.fish] = next.dist;
      pq.insert(next);
    }

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     * <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public int distTo(int v, int mask) {
      return distTo[v][mask];
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String[] header = scanner.nextLine().split(" ");
    int N = Integer.parseInt(header[0]);
    int M = Integer.parseInt(header[1]);
    int K = Integer.parseInt(header[2]);
    EdgeWeightedDigraph shoppingCentersNetwork = new EdgeWeightedDigraph(N);
    for (int i = 0; i < N; i++) {
      String[] fish = scanner.nextLine().split(" ");
      int fishMask = 0;
      for (int j = 1; j < fish.length; j++) {
        fishMask |= 1 << (Integer.parseInt(fish[j]) - 1);
      }

      shoppingCentersNetwork.addVertex(i, fishMask);
    }
    for (int i = 0; i < M; i++) {
      String[] road = scanner.nextLine().split(" ");
      int c1 = Integer.parseInt(road[0]) - 1;
      int c2 = Integer.parseInt(road[1]) - 1;
      int w = Integer.parseInt(road[2]);
      shoppingCentersNetwork.addEdge(new Edge(c1, c2, w));
    }

    DijkstraSP dijkstraSP = new DijkstraSP(shoppingCentersNetwork, 0, K);

    int result = Integer.MAX_VALUE;
    for (int i = 0; i < 1 << K; i++) {
      for (int j = 0; j < 1 << K; j++) {
        if ((i | j) == ((1 << K) - 1)) {
          int distTo1 = dijkstraSP.distTo(N - 1, i);
          int distTo2 = dijkstraSP.distTo(N - 1, j);
          distTo1 = distTo1 > distTo2 ? distTo1 : distTo2;
          if (result > distTo1)
            result = distTo1;
        }
      }
    }


    System.out.println(result);
  }


}
