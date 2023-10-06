# Problem 2-1


Let $`\mathcal{N}`$ be the set of all elements of type $`\textsf{N}`$, and $`\mathsf{null} \notin \mathcal{N}`$ be an distinguished element to represent `null`. Write formal abstract specifications of the interfaces below with respect to following abstract values:

- A graph is a pair $`G = (V, E)`$, where $`V \subseteq \mathcal{N}`$ and $`E \subseteq V \times V`$.
- A tree is a triple $`T = (V, E, \hat{v})`$, where $`(V, E)`$ is a graph and $`\hat{v} \in \mathcal{N}`$ denotes the root.

Other data types, such as `boolean`, `int`, `Set<N>`, etc. have conventional abstract values, e.g., Boolean values, integers, and subsets of $`\mathcal{N}`$, etc.


## `Graph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this} \land (w, v) \in E_{this}
```
The graph should not contain self-loops  

##### containsVertex

```java 
boolean containsVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns true if vertex is in $`V_{this}`$; and
    - returns false, otherwise.

##### containsEdge

```java
boolean containsEdge(N source, N target);
```

- requires: source and target vertices are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: true if source and target is connected by an edge

##### getNeighborhood

```java
Set<N> getNeighborhood(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: returns the set of vertices that are connected to the vertex by an edge, immutable


## `Tree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this} \land (w, v) \in E_{this}  
```
For all vertices v in V_this, there exists only one path from root to v.  
There must be a single root node in the tree.  
The tree should not contain self-loops.  

##### getDepth

```java
int getDepth(N vertex);
```

- requires:   
  + vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$.
- ensures:  
  + returns 0 if vertex is getRoot(); and
  + returns getDepth(getParent(vertex)) + 1, otherwise.

##### getHeight

```java
int getHeight();
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$.
- ensures: 
  + returns __IllegalArgumentException__ if vertex is not in the tree
  + returns 0 if vertex is getRoot() and
  + returns max(getHeight(getChildren(vertex))) + 1, otherwise.

##### getChildren

```java
Set<N> getChildren(N vertex);
```

- requires: vertex is in the tree
- ensures:
    + returns __IllegalArgumentException__ if vertex is not in the tree
    + returns the set of vertices that are connected to the vertex by an edge, immutable

##### getParent

```java
Optional<N> getParent(N vertex);
```

- requires: vertex is in the tree
- ensures: 
    + returns the parent of the vertex, if it exists
    + returns Optional.empty() if the vertex is the root


## `MutableGraph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object,
and $`G_{next} = (V_{next}, E_{next})`$ be an abstract value of the graph object _modified by_ the method call. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this} \land (w, v) \in E_{this}
```

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} \cup \{\texttt{vertex}\}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified)
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \notin V_{this}`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
    + $`V_{next} = V_{this} \setminus \{\texttt{vertex}\}`$; 
    + $`E_{next} = \{(v, w) \in E_{this} \mid v \neq \texttt{vertex} \land w \neq \texttt{vertex}\}`$; 
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \in V_{this}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: source and target vertices are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
    + $`V_{next} = V_{this} \cup \{\texttt{source}, \texttt{target}\}`$ if source and target are not in the graph, put them in the graph
    + $`E_{next} = E_{this} \cup \{(\texttt{source}, \texttt{target}), (\texttt{target}, \texttt{source})\}`$ if the edge does not already exist, add it to the graph
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`E_{next} \neq E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: source and target vertices are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
    + $`E_{next} = E_{this} \setminus \{(\texttt{source}, \texttt{target})\}`$ if the edge exists, remove it from the graph
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`E_{next} \neq E_{this}`$.


## `MutableTree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current tree object,
and $`T_{next} = (V_{next}, E_{next}, \hat{v}_{next})`$ be an abstract value of the tree object _modified by_ the method call. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this} \land (w, v) \in E_{this}
```
For all pairs of vertices (v, w) in V_{this}, there exists a sequence of edges in E_{this} that connects vertex v to vertex w.  
There must be a single root node in the tree.  
The tree should not contain self-loops.  

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
    + $`V_{next} = V_{this} \cup \{\texttt{vertex}\}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified)
    + If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \notin V_{this}`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
    + $`V_{next} = V_{this} \setminus \{\texttt{vertex}\}`$; 
    + $`E_{next} = \{(v, w) \in E_{this} \mid v \neq \texttt{vertex} \land w \neq \texttt{vertex}\}`$ for vertices that are descendants of given vertex, remove them from the tree 
    + If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \in V_{this}`$.
    + If the vertex is the root, throw __llegalArgumentException__ 

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: source and target vertices are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
    + $`V_{next} = V_{this} \cup \texttt{target}`$ source is already in the tree and target is not in the tree, so create target node and add it to the tree
    + $`E_{next} = E_{this} \cup \{(\texttt{source}, \texttt{target}), (\texttt{target}, \texttt{source})\}`$ 
    + If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`E_{next} \neq E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: source and target vertices are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
    + $`E_{next} = E_{this} \setminus E_{related}`$ where 
    + if the edge exists, remove it from the graph and remove all edges related to it
    + if $`(\texttt{source}, \texttt{target})`$ is in $`E_{this}`$, then remove $`(\texttt{source}, \texttt{target})`$ and $`(\texttt{target}, \texttt{source})`$ from $`E_{this}`$
        + Also remove all edges that are related to $`\texttt{source}`$ and $`\texttt{target}`$ from $`E_{this}`$
    + If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`E_{next} \neq E_{this}`$.


# Problem 2-2

Identify whether the abstract interfaces satisfy the Liskov substitution principle.
For each question, explain your reasoning _using the abstract specifications that you have defined in Problem 1_. 


##### `Tree<N>` and `Graph<N>`

* Is `Tree<N>` a subtype of `Graph<N>`?  
> __Yes__, because Tree has strong specification than Graph. Tree has to satisfy the class invariant of Graph and Tree's class invariant like root node, no self-loop, and connected graph. 
> Also, there is no method overriding in Tree except toRepr().  
> Therefore, Tree is a subtype of Graph.


##### `MutableGraph<N>` and `Graph<N>`

* Is `MutableGraph<N>` a subtype of `Graph<N>`?
> __No__, Although MutableGraph has strong specification than Graph, and MutableGraph satisfies the class invariant of Graph, mutableGraph has methods such as addVertex, removeVertex, addEdge, removeEdge which can change member variable of Graph.    
> Therefore, MutableTree tris to change a member variable of Graph. MutableGraph is not a subtype of Graph.
```java
Graph graph = new mutableGraph(args);
doSomething(graph); // such as addVertex, removeVertex, addEdge, removeEdge
if (graph.equals(new Graph(args)))
    System.out.println("MutableGraph is a subtype of Graph");
else
    System.out.println("MutableGraph is not a subtype of Graph");
```
##### `MutableTree<N>` and `Tree<N>`

* Is `MutableTree<N>` a subtype of `Tree<N>`?
> __No__, Although MutableTree has strong specification than Tree, and satisfies the class invariant of Tree, there are also methods such as addVertex, removeVertex, addEdge, removeEdge which can change member variable of Tree.  
> Therefore, MutableTree tris to change a member variable of Tree. MutableTree is not a subtype of Tree.
```java
Tree tree = new mutableTree(args);
doSomething(tree); // such as addVertex, removeVertex, addEdge, removeEdge
if (tree.equals(new Tree(args)))
    System.out.println("MutableTree is a subtype of Tree");
else
    System.out.println("MutableTree is not a subtype of Tree");
```
##### `MutableTree<N>` and `MutableGraph<N>`

* Is `MutableTree<N>` a subtype of `MutableGraph<N>`?  
> __No__, addVertex method for MutableTree has different result rather MutableGraph does.   
> For example, if we add a vertex which is not in MutableGraph, the vertex will be added to the tree. And return value is true.    
> However, we cannot add a vertex by using addVertex method in MutableGraph because of tree's invariant property. And its return value is always false by MutableTree's interface code.   
> Therefore, MutableTree is not a subtype of MutableGraph. 
```java
// Example code
T = MutableGraph<N>
S = MutableTree<N>
// for a vertex v that is not null, and not in both T and S
// T.addVertex(v) will returns true
// S.addVertex(v) will returns false
// Postcondition is different
assert T.addVertex(v) == S.addVertex(v)
```


