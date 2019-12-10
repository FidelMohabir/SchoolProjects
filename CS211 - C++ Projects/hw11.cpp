//Fidel Mohabir
//CS211 Section 52B
//HW 11 - Stable Marriage
#include <iostream>
#include <stdlib.h>
#include <cmath>
using namespace std;
bool ok(int q[], int col) {
	int mp[3][3]={0,2,1, //Preference of the men 0,1,2
			0,2,1,
			1,2,0};
	int wp[3][3]={2,1,0, //Preference of the women 0,1,2
			0,1,2,
			2,0,1};
	int i; 
	for (i=0; i < col ;i++) { //Checks if two men have the same woman and if there is no instabilities in the marriage
		if (q[col]==q[i]) //checks so two men cannot be married to the same woman 
			return false;
		if ((mp[i][q[col]] < mp[i][q[i]]) && (wp[q[col]][i] < wp[q[col]][col])) //checks for instability
			return false;
		if ((mp[col][q[i]] < mp[col][q[col]]) && (wp[q[i]][col] < wp[q[i]][i])) //checks for instability
			return false;
	}
	return true;
}
void print(int q[]) {
	int i;
	for (i=0;i<3;i++) 
		cout << "Man " << i << "'s wife is woman " << q[i] << endl;
	cout << endl;
}
void backtrack(int &col) {
	col--; //goes back a column
	if (col==-1) //if column is -1 terminate program
		exit(1);
}
int main() { //8 Queens program from prior assignment
	int q[3], c=0; //
	q[0]=0;
	bool from_backtrack=false;
	while(true) {
		while (c<3) {
			if (!from_backtrack)
				q[c]=-1;
			from_backtrack=false;
			while (q[c] < 3) {
				q[c]++;
				if (q[c]==3) {
					from_backtrack=true;
					backtrack(c);
					break;
				}
				if (ok(q, c)) {
					from_backtrack=false;
					c++;
					break;
				}
			}
		}
		print(q);
		backtrack(c);
		from_backtrack=true;
	}
}
