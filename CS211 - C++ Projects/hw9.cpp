//Fidel Mohabir
//CS211 Section 52B
//HW 9 -Dumb Queens
//not printing
#include <iostream>
#include <cmath>
using namespace std;
bool ok(int q[][8]) {
	for (int r=0; r<8; r++)
		for (int c=7; c>0; c--) { //returns false if any of these statements are true also looks from [7] and back since i7 is the fastest loop
			if (q[r][c-1]==1) //row test
				return false;
			if (q[r-1][c-1]==1) // up diagonal test
				return false;
			if (q[r+1][c-1]==1) //down diagonal test
				return false; 
		}
	return true;
}
void print(int q[][8], int &count) {
	count++;
	cout << count << endl;
	for (int r=0; r<8; r++)
		for (int c=0; c<8; c++)
			cout << q[r][c] << " " << endl << endl;
}
int main() {
	int board[8][8]={0};
	int count = 0;
	for (int i0=0; i0<8 ; i0++)
		for (int i1=0; i1<8; i1++)
			for (int i2=0; i2<8; i2++)
				for (int i3=0; i3<8; i3++)
					for (int i4=0; i4<8; i4++)
						for (int i5=0; i5<8; i5++)
							for (int i6=0; i6<8 ; i6++)
								for (int i7=0; i7<8 ; i7++) { //sets each variable to a certain column and puts uses their number to represent the row Ex. it starts off with a queen in all columns
									board[i0][0]=1;
									board[i1][1]=1;
									board[i2][2]=1;
									board[i3][3]=1;
									board[i4][4]=1;
									board[i5][5]=1;
									board[i6][6]=1;
									board[i7][7]=1;
									if (ok(board)) //sends to print if it passes the row and diagonal test
										print(board, count);
									board[i0][0]=0; //resets where the queens were to 0
									board[i1][1]=0;
									board[i2][2]=0;
									board[i3][3]=0;
									board[i4][4]=0;
									board[i5][5]=0;
									board[i6][6]=0;
									board[i7][7]=0;
								}
	return 0;
}
