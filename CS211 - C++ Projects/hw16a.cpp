//Fidel Mohabir
//CS211 Section52b
//Homework #16 -  Stable Marriage (using 8 of queens template on Dr. Waxman's handouts section
#include <iostream>
#include <stdlib.h>
using namespace std;
bool ok(int* q, int col) { //Stable Marriage OK checker
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
void print(int * q){ //changed to print like the one from my stable marriage homework
	static int count =0;
	cout<<++count<<endl;
	for (int k=0; k<3; k++)
	cout << "Man " << k << "'s wife is woman " << q[k] << endl;
}
void move(int* q, int i) {
	if(i==3) {
		print(q);
 		return;
	}	

	for(int j=0;j<3;j++) {
		q[i]=j;
		if(ok(q,i)) 
 			move(q,i+1); 
	}


}
int main() {
	int q[3];
	move(q,0);
	system("pause");

} 
