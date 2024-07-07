#!bin/bash

sed -n '/^============== B E S T  P A T H ==============/,/^==============================================/p' best_path.txt | sed -n 's/\(.*\)\(\[.*\]\) Total Weight:.*/\1\2/p' > temp_best_path.txt
sed -n '/^============== B E S T  P A T H ==============/,/^==============================================/p' shortest_path.txt | sed -n 's/\(.*\)\(\[.*\]\) Total Weight:.*/\1\2/p' > temp_shortest_path.txt
diff temp_best_path.txt temp_shortest_path.txt > path_difference.txt
rm temp_best_path.txt
rm temp_shortest_path.txt