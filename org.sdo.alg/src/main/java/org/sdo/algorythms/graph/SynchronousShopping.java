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
    private Comparator<Key> comparator;  // optional comparator

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
     * Initializes an empty priority queue.
     */
    public MinPQ() {
      this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * using the given comparator.
     *
     * @param initCapacity the initial capacity of this priority queue
     * @param comparator   the order to use when comparing keys
     */
    public MinPQ(int initCapacity, Comparator<Key> comparator) {
      this.comparator = comparator;
      pq = (Key[]) new Object[initCapacity + 1];
      N = 0;
    }

    /**
     * Initializes an empty priority queue using the given comparator.
     *
     * @param comparator the order to use when comparing keys
     */
    public MinPQ(Comparator<Key> comparator) {
      this(1, comparator);
    }

    /**
     * Initializes a priority queue from the array of keys.
     * <p>
     * Takes time proportional to the number of keys, using sink-based heap construction.
     *
     * @param keys the array of keys
     */
    public MinPQ(Key[] keys) {
      N = keys.length;
      pq = (Key[]) new Object[keys.length + 1];
      for (int i = 0; i < N; i++)
        pq[i + 1] = keys[i];
      for (int k = N / 2; k >= 1; k--)
        sink(k);
      assert isMinHeap();
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

    /**
     * Returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key min() {
      if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
      return pq[1];
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
      if (N == pq.length - 1) resize(2 * pq.length);

      // add x, and percolate it up to maintain heap invariant
      pq[++N] = x;
      swim(N);
      assert isMinHeap();
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
      if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
      assert isMinHeap();
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
      if (comparator == null) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
      } else {
        return comparator.compare(pq[i], pq[j]) > 0;
      }
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

  public static class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes a directed edge from vertex <tt>v</tt> to vertex <tt>w</tt> with
     * the given <tt>weight</tt>.
     *
     * @param v      the tail vertex
     * @param w      the head vertex
     * @param weight the weight of the directed edge
     * @throws IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
     *                                   is a negative integer
     * @throws IllegalArgumentException  if <tt>weight</tt> is <tt>NaN</tt>
     */
    public DirectedEdge(int v, int w, double weight) {
      if (v < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
      if (w < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
      if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
      this.v = v;
      this.w = w;
      this.weight = weight;
    }

    /**
     * Returns the tail vertex of the directed edge.
     *
     * @return the tail vertex of the directed edge
     */
    public int from() {
      return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     *
     * @return the head vertex of the directed edge
     */
    public int to() {
      return w;
    }

    /**
     * Returns the weight of the directed edge.
     *
     * @return the weight of the directed edge
     */
    public double weight() {
      return weight;
    }

    /**
     * Returns a string representation of the directed edge.
     *
     * @return a string representation of the directed edge
     */
    public String toString() {
      return v + "->" + w + " " + String.format("%5.2f", weight);
    }

  }


  public static class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;                // number of vertices in this digraph
    private final int[] vertices;
    private int E;                      // number of edges in this digraph
    private List<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
    private int[] indegree;             // indegree[v] = indegree of vertex v

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
      this.indegree = new int[V];
      adj = (List<DirectedEdge>[]) new LinkedList[V];
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

    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateVertex(int v) {
      if (v < 0 || v >= V)
        throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * Adds the directed edge <tt>e</tt> to this edge-weighted digraph.
     *
     * @param e the edge
     * @throws IndexOutOfBoundsException unless endpoints of edge are between 0 and V-1
     */
    public void addEdge(DirectedEdge e) {
      int v = e.from();
      int w = e.to();
      validateVertex(v);
      validateVertex(w);
      adj[v].add(e);
      indegree[w]++;
      E++;
    }


    /**
     * Returns the directed edges incident from vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the directed edges incident from vertex <tt>v</tt> as an Iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<DirectedEdge> adj(int v) {
      validateVertex(v);
      return adj[v];
    }

    /**
     * Returns the number of directed edges incident from vertex <tt>v</tt>.
     * This is known as the <em>outdegree</em> of vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the outdegree of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public int outdegree(int v) {
      validateVertex(v);
      return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex <tt>v</tt>.
     * This is known as the <em>indegree</em> of vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the indegree of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public int indegree(int v) {
      validateVertex(v);
      return indegree[v];
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * <tt>for (DirectedEdge e : G.edges())</tt>.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
      List<DirectedEdge> list = new ArrayList<>();
      for (int v = 0; v < V; v++) {
        for (DirectedEdge e : adj(v)) {
          list.add(e);
        }
      }
      return list;
    }

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
      StringBuilder s = new StringBuilder();
      s.append(V + " " + E + NEWLINE);
      for (int v = 0; v < V; v++) {
        s.append(v + ": ");
        for (DirectedEdge e : adj[v]) {
          s.append(e + "  ");
        }
        s.append(NEWLINE);
      }
      return s.toString();
    }

    public void addVertex(int i, int fishMask) {
      this.vertices[i] = fishMask;
    }
  }

  public static class DijkstraSP {
    private final int o;
    private int mask;
    private int[] distTo;          // distTo[v] = distance  of shortest s->v path
    private int[] fish;
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
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
    public DijkstraSP(EdgeWeightedDigraph G, int s, int o, int mask) {
      this.mask = mask;
      for (DirectedEdge e : G.edges()) {
        if (e.weight() < 0)
          throw new IllegalArgumentException("edge " + e + " has negative weight");
      }

      this.o = o;

      distTo = new int[G.V()];
      fish = new int[G.V()];
      edgeTo = new DirectedEdge[G.V()];
      for (int v = 0; v < G.V(); v++) {
        distTo[v] = Integer.MAX_VALUE;
      }
      distTo[s] = 0;
      fish[s] = G.vertices[s];
      SearchEntry searchEntry = new SearchEntry();
      searchEntry.dist = 0;
      searchEntry.fish = G.vertices[s];
      searchEntry.shop = s;


      // relax vertices in order of distance from s
      pq = new MinPQ<>(G.V());
      pq.insert(searchEntry);
      while (!pq.isEmpty()) {
        searchEntry = pq.delMin();
        distTo[searchEntry.shop] = searchEntry.dist;
        fish[searchEntry.shop] = searchEntry.fish;
        int t = this.mask & searchEntry.fish;

        if (searchEntry.shop == o && t == this.mask) {
          break;
        }
        for (DirectedEdge e : G.adj(searchEntry.shop))
          relax(searchEntry, e, G);
      }
    }

    // relax edge e and update pq if changed
    private void relax(SearchEntry searchEntry, DirectedEdge e, EdgeWeightedDigraph g) {
      int v = e.from(), w = e.to();
      int bw = g.vertices[w] | searchEntry.fish;
      SearchEntry next = new SearchEntry();
      next.dist = searchEntry.dist + (int) e.weight();
      next.shop = w;
      next.fish = bw;
      if (next.dist >= distTo[next.shop] && (fish[next.shop] | (fish[next.shop] ^ next.fish)) == fish[next.shop]) {
        return;
      }

      pq.insert(next);
    }

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     * <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public int distTo(int v) {
      return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     *
     * @param v the destination vertex
     * @return <tt>true</tt> if there is a path from the source vertex
     * <tt>s</tt> to vertex <tt>v</tt>; <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
      return distTo[v] < Integer.MAX_VALUE;
    }

    /**
     * Returns a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     *
     * @param v the destination vertex
     * @return a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>
     * as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<DirectedEdge> pathTo(int v) {
      if (!hasPathTo(v)) return null;
      Stack<DirectedEdge> path = new Stack<>();
      for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
        path.push(e);
      }
      return path;
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
      shoppingCentersNetwork.addEdge(new DirectedEdge(c1, c2, w));
      shoppingCentersNetwork.addEdge(new DirectedEdge(c2, c1, w));

    }

    int result = Integer.MAX_VALUE;
    Map<Integer, Integer> resultCache = new HashMap<>();
    Map<Integer, Integer> fishCache = new HashMap<>();
    int i = 0;
    while (i < (1 << K)) {
      int cat1Dist = Integer.MAX_VALUE;
      int cat2Dist = Integer.MAX_VALUE;
      if (!resultCache.containsKey(i)) {
        DijkstraSP dijkstraSP = new DijkstraSP(shoppingCentersNetwork, 0, N - 1, i);
        resultCache.put(i, dijkstraSP.distTo(N - 1));
        resultCache.put(dijkstraSP.fish[N - 1], dijkstraSP.distTo(N - 1));
        fishCache.put(i, dijkstraSP.fish[N - 1]);
        fishCache.put(dijkstraSP.fish[N - 1], dijkstraSP.fish[N - 1]);
        cat1Dist = dijkstraSP.distTo(N - 1);

        int fish = ~dijkstraSP.fish[N - 1] & (1 << K) - 1;
        if (resultCache.containsKey(fish)) {
          cat2Dist = resultCache.get(fish);
        } else {
          DijkstraSP dijkstraSP2 = new DijkstraSP(shoppingCentersNetwork, 0, N - 1, fish);
          cat2Dist = dijkstraSP2.distTo(N - 1);
          resultCache.put(fish, dijkstraSP2.distTo(N - 1));
          resultCache.put(dijkstraSP2.fish[N - 1], dijkstraSP2.distTo(N - 1));
          fishCache.put(fish, dijkstraSP2.fish[N - 1]);
          fishCache.put(dijkstraSP2.fish[N - 1], dijkstraSP2.fish[N - 1]);
        }

      } else {
        cat1Dist = resultCache.get(i);
        int fish = ~fishCache.get(i) & (1 << K) - 1;
        cat2Dist = resultCache.get(fish);
      }

      //System.out.println(String.format("%s %s", dijkstraSP.fish[N - 1], dijkstraSP2.fish[N - 1]));
      cat1Dist = cat2Dist > cat1Dist ? cat2Dist : cat1Dist;
      //System.out.println(String.format("%s : %s", s, cat2Dist));
      if (cat1Dist < result)
        result = cat1Dist;
      if (i > (1 << K) / 2) break;
      i = fishCache.get(i) + 1;

    }

    System.out.println(result);
  }


}
