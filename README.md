# Smart City / Smart Campus Scheduling

**Author:** Ayan Matayev  
**Group:** SE-2435  
**Course:** Design and Analysis of Algorithms

---

## ⚙️ Build and Run Instructions

### Requirements
- Java 11 or higher
- Apache Maven

### 1. Build the project
In the project root directory, run:
```bash
mvn -q -DskipTests package
2. Run the program
Use the following command:

bash
java -cp target/assignment4-1.0-SNAPSHOT.jar graph.Main data/tasks.json
3. Run unit tests
bash

mvn test






Notes

The input file data/tasks.json contains the directed graph with weights.

Output is printed to the console and includes:

Strongly Connected Components (SCC) found by Tarjan’s algorithm

Condensation DAG

Topological order of components (Kahn’s algorithm)

Shortest and Longest (Critical) paths in the DAG

All datasets are located in the /data folder.