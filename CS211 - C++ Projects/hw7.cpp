//Fidel Mohabir
//CS211 Section 52B
//8 queens 1d array with backtracking no gotos
//I was not able to get this running. It infinitely loops and doesn't place a 1 in some columns. Would appreciate it if you let me know where my code was bad
#include <iostream>
#include <cmath>
#include <stdlib.h>
using namespace std;
void placeQueen(int q[], int c);
void nextCol(int q[], int c);
void backtrack(int q[], int& c);
bool isValid(int q[], int c) { //Row and Diagnol test
   for (int i=0; i < c; i++) //checks row while allowing with reference to what column the program is in
			if (q[c]==q[i] || (c-i)==abs(q[c]-q[i])) 
					return false; //returns false if queen can't be placed
	return true; //returns true otherwise
}

void print(int q[], int c) { //Prints the 8x8 board
   int i=0, d;  //I intialized i to go through the while loop and d checks the number value in the 1d array 
		while (i<8) { //i goes through all rows
			d=0; //set d as the column go from 0-7
			while (d<8) { //Stops after the 7th column
				if (q[i]==d) //Will place a 1 if q[i] is the same value as d
					cout << " 1 ";
				else 
					cout << " 0 "; //else it places a 0 
				d++; //increments d from 0-7 which is column place
			}
			i++; //increments i then skips a line and prints another row
			cout << endl; 
		}
		cout << endl;
	backtrack(q, c); //will backtrack until it is out of possible solutions
}

void backtrack(int q[], int& c){
	c--; //increments c to the prior column
	if (c==-1)
		exit(1); //terminates program once in column -1
	placeQueen(q, c); //sends program back to place the queen
}
void nextCol(int q[], int c){
	c++; //increments the column
	if (c==8)
		print(q, c); //prints if out of bounds (8)
	placeQueen(q, c);
	q[c]=-1; //sets column value to -1
	 
}
void placeQueen(int q[], int c) {
	if (isValid(q,c)) //checks if queen can be placed here
		nextCol(q, c); //if so then it goes to the next column
	else {
	    q[c]++; //if not then it increments the value of the column
	    placeQueen(q, c); //then sends it back to placeQueens to check
	}
}

int main() {
   int q[8], c=0; //q[8] is my array size and c is what I use for my columns, obviously starting on the first column
   q[0]=0;//set q[0] to 0 because the queen can be placed in the first column and row for sure
   placeQueen(q, c); //use placeQueen function to run all other functions
   return 0;
}
