# Fault-Detection-And-Diagnosis

Fault dependency (D-matrix) is a diagnostic model which is used to catch the fault system data and its causal relationship at the hierarchical system-levels. It consists of dependencies and relationship between observable failure modes and symptoms associated with a system. Here, we describe an ontology based text mining approach for fault detection and diagnosis for automatic construction of D-matrix by mining hundreds of repair verbatim gathered during diagnosis episodes. In our approach, we first perform the document annotation phase consisting of search module, which includes the term extraction phase as well and retrieves the queries relevant to the problem statement using data mining 
algorithms. Next, a D-matrix granting possible solutions is constructed using the processed information. Proposed method 
is implemented as a prototype model and validated by real-life data collected from automobile domain.  

The main components of the code include three Java classes: NChat.java, ClientOne.java, and ClientTwo.java. NChat.java serves as the central server for the LAN chat application, managing message communication among clients. ClientOne.java and ClientTwo.java represent two instances of clients that can send and receive messages through the server.

The communication between clients involves DatagramSocket and DatagramPacket for sending and receiving messages. The messages are encoded and decoded using custom functions in the clsFunctions class. Each client has a GUI with features such as displaying chat messages, setting a display name, and sending messages.

