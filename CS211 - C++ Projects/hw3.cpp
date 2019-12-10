//Fidel Mohabir
//CS211 Section 52B
//8-Queens Problem
#include <iostream>
using namespace std;
int main() {
	int b[8][8]={0}, r, c=0, i;
	b[0][0]=1;
	Nc: //This label represents next column
		r=-1;
		c++; //Switches to the next column
		if (c==8)  
			goto print; //Goes to print label once c=8 (Out of bounds for array)
	Nr: //This label represents next row
		r++; //Switches to the next row
		if (r==8)
			goto backtrack; //If R is out of bounds and there is no solution goto backtrack
		for (i=0; i<c; i++) //Row Test
			if (b[r][i]==1)
				goto Nr;
		for (i=1; (r-i) >= 0 && (c-i)>=0; i++) //Upwards diagonal test
			if (b[r-i][c-i]==1)
				goto Nr;
		for (i=1; (r+i)<8 && (c-i)>=0; i++) //Dowards iagonal Test
			if (b[r+i][c-i]==1)
				goto Nr;
		
		//All three checks to see if a 1 is present in the rows and diagonals
		
		b[r][c]=1; //Places 1 in first position that passes all three tests
		goto Nc; //Goes to Nc once a 1 is placed in that row
	backtrack:
		c--; //Goes back to last column
		if (c==-1)
			return 0; //ends program once out of solutions
		for (r=0; r<8; r++){ //Checks where the 1 is located in this column, replaces it with a 0 then proceeds after that position
			if (b[r][c]==1){
				b[r][c]=0;
				goto Nr;
			}
		}
	print: //Prints the 2d array then backtracks for other possible solutions
		for (r=0; r<8; r++) {
			for (c=0; c<8;c++) {
				cout << b[r][c] << " ";
			}
			cout << endl;
		}
		cout << endl;
		goto backtrack;
}
