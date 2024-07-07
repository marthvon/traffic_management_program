#!bin/bash

javac *.java */*.java */*/*.java

java TestHarness.java -l > ./docs/adjacency_list.txt
java TestHarness.java -l -d > ./docs/adjacency_list_in_detail.txt
java TestHarness.java -s -a > ./docs/best_path.txt
java TestHarness.java -d -s -a > ./docs/best_path_in_detail.txt
java TestHarness.java -sm -a > ./docs/shortest_path.txt
java TestHarness.java -d -sm -a > ./docs/shortest_path_in_detail.txt
java TestHarness.java -d -p > ./docs/high_traffic_volume_priority_list.txt
