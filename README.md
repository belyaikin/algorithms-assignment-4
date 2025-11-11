# 📚 Algorithms Assignment 4: Optimization and Scheduling for Smart City Services

## 🎯 Executive Summary & Project Goal

This project implements a comprehensive algorithmic solution designed for dependency-based scheduling, applicable to scenarios like **Smart City Service Tasks** or internal analytics subtasks. The core objective is to **consolidate and optimize** task execution by addressing both cyclic and acyclic dependencies efficiently.

The solution involves a three-stage pipeline based on graph theory:
1.  **Identification of Cycles (SCC):** Use **Strongly Connected Components** algorithms to detect and compress cyclic dependencies.
2.  **Order Planning (Topological Sort):** Apply **Topological Sorting** to the resulting Condensation Graph to establish a valid execution sequence.
3.  **Optimal Timing (DAG Paths):** Calculate the **Critical Path (Longest Path)** within the DAG to determine the minimum time required to complete the entire set of tasks.

This implementation, developed in **Java**, demonstrates a mastery of fundamental linear-time graph algorithms and their application in practical scheduling problems.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## 🛠 Implemented Algorithms & Technical Specifications

The solution is compartmentalized into distinct packages, ensuring high code quality and reusability.

### 1. Strongly Connected Components (SCC)

* **Algorithms Implemented:** Both **Kosaraju's Algorithm** (`KosarajuSCC.java`) and **Tarjan's Algorithm** (`TarjanSCC.java`) were implemented to find SCCs.
* **Purpose:** To isolate sets of mutually dependent tasks (cycles) and construct the **Condensation Graph** (a Directed Acyclic Graph, or DAG).
* **Complexity:** $O(|V| + |E|)$ (Linear Time).

### 2. Topological Sort

* **Algorithms Implemented:** Both **Kahn's Algorithm** (`KahnTopologicalSort.java`) and the **DFS-based Topological Sort** (`DFSTopologicalSort.java`) were provided.
* **Purpose:** To generate a linear ordering of the Condensation Graph's nodes, ensuring all dependencies are met.
* **Complexity:** $O(|V| + |E|)$ (Linear Time).

### 3. Shortest/Longest Paths in a DAG (DAG-SP)

* **Implementation:** The core logic is in `DAGPathFinder.java`.
* **Weight Model:** **Edge Weights** were chosen to represent the *duration* or *cost* of transitioning between tasks/components.
* **Functionality:**
    * **Single-Source Shortest Path:** Finds the minimum time/cost from a starting task to all others.
    * **Longest Path (Critical Path):** Calculated by **negating all edge weights** and running the shortest path algorithm.
* **Complexity:** $O(|V| + |E|)$ (Linear Time).

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## 📂 Repository Structure

The project uses a standard Maven structure (`pom.xml` included).
/ ├── src/ │ ├── main/java/algorithms/assignment/ │ │ ├── dag_paths/ # DAGPathFinder.java │ │ ├── graph/ # Core Graph, Vertex, Edge, Neighbor classes │ │ ├── strongly_connected_components/ # KosarajuSCC.java, TarjanSCC.java │ │ ├── topological_sort/ # DFSTopologicalSort.java, KahnTopologicalSort.java │ │ ├── Main.java # Main Execution Logic │ │ └── Metrics.java # Instrumentation Class ├── test/ # JUnit Test Cases ├── data/ # Placeholder for Input Datasets ├── pom.xml # Maven Configuration └── README.md # This document


## ⚙️ Build and Execution Instructions

### Prerequisites
* Java Development Kit (JDK) 11 or later.
* Apache Maven.

### Step-by-Step Guide

**1. Clone the Repository:**
```
git clone [https://github.com/belyaikin/algorithms-assignment-4.git](https://github.com/belyaikin/algorithms-assignment-4.git)
cd algorithms-assignment-4
```

Compile and Package with Maven:
```
mvn clean package
```

# Prepare Input Data:

Ensure your graph data file (e.g., tasks.json or similar format) is placed inside the data/ directory.

Run the Main Application:
The Main.java class handles the full algorithmic pipeline (SCC -> Topo Sort -> DAG Paths)
```
java -cp target/algorithms-assignment-4-1.0-SNAPSHOT.jar algorithms.assignment.Main data/test_case_large.txt
```

# Conclusions and Recommendations
Dependency Resolution: The SCC phase acts as an immediate diagnostic tool. Any SCC with a size $>1$ signals an inherent flaw (a mutual dependency loop) in the task design that requires external resolution.Optimal Planning: The use of Topological Sort on the Condensation Graph provides a guaranteed, valid schedule.Critical Time Prediction: The Longest Path calculation provides the most valuable output: the minimum time $T_{min}$ in which the entire project can be completed. Management should focus its efforts on reducing the duration of tasks that lie on this critical path.
