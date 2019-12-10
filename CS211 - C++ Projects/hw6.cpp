//Fidel Mohabir
//CS211 Section 52B
//8 Queens 1d array
#include <iostream>
#include <cmath>
using namespace std;
int main() {
	int q[8], c=0, d, i;
	q[0]=0;
	next_column:
		c++;
		if (c==8) //prints after out of bounds for columns since array is 0-7
			goto print;
	q[c]=-1;
	next_row: 
		q[c]++;
		if (q[c]==8) //goes to bactrack after out of bounds for rows 
			goto backtrack;
		for (i=0; i < c; i++) 
			if (q[c]==q[i] || (c-i)==abs(q[c]-q[i])) //Row test and both diagonals
					goto next_row;
		goto next_column; //Goes to next row after checking row
	print:
		i=0; 
		while (i<8) { //i goes through all rows
			d=0;
			while (d<8) { //this while loop goes through all columns
				if (q[i]==d) //Checks for which column q[i] and d = 0 because the position queens are placed in has a 0
					cout << "1";
				else
					cout << "O"; //prints O in places that have -1
				d++; //increments d to move down column
			}
			i++; //increments i to move down rows
			cout << endl; //Goes down a line everytime it prints that entire row
			
		}
		cout << endl;
		goto backtrack; //Goes to check for other solutions

	backtrack:
		c--; //goes back to prior column
		if (c==-1)
			return 0; //ends program when no more solutions
		goto next_row; //goes to next row to check for another solution
}
