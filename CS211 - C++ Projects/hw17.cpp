#include <iostream>
#include <fstream>
#include <vector>
using namespace std;
void sort (int* students, int& steps) { //used to bubble sort function to sort through the array
	int i, j, temp; //temp holds value that makes the sort function work
	for (i=0;i<20;i++)
		for (j=0; j<20; j++)
			if (students[j]>students[j+1]) { //changes array if students[j] is greater than students[j+1] (making sure there the student height is ascending
				temp = students[j+1];
				students[j+1]=students[j];
				students[j]=temp;
				steps++; //counts the number of steps which is passed by reference
			}
}
bool ascending(int* students) { //this makes sure that the array is ascending 
	int i;
	for (i=0;i<19;i++)
		if (students[i]>students[i+1])
			return false; //returns false if not ascending
	return true;			
}
int main() {
    ifstream in("input.txt");
    int numRows, temp;
    in >> numRows;
    for (int i = 1; i <= numRows; ++i) { 
        in >> temp;                
        int steps = 0;             
	int students[20]; 
        for (int j = 1; j <= 20; ++j) { //sets each number in a position of the array
             in>>temp;
	     students[j-1]=temp; 
	}
	 while (!ascending(students)) //boolean statement that repeats the sort function until array is completely ascending
		sort(students, steps); 
         cout << i << " " << steps << endl;
     }
     in.close();
     return 0;
 }
