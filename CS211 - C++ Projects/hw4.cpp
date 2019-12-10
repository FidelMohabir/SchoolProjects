//Fidel Mohabir
//CS211 Section 52B
//Score the Races
#include <iostream>
#include <algorithm>
#include <string>
using namespace std;
int getTeams(string a, string &b) { //This function counts the number of teams and places their name into a string
	int count=0;
	for (int i=0; i < a.length(); i++)
		if (count==0 && a[i]!='\0') //Makes sure string is not blank
			count++;
		else if (a[i+1]=='\0') { //Makes sure that function does not go out of bounds by adding team name then breaking
			b+=a[i];
			break;
		}
		else if (a[i]!=a[i+1]) { //Makes sure it only counts different letters (or teams)
			count++;
			b+=a[i];
		}
	return count;	
}
int numRun(string a) { //This function finds the number of runners in each team
	string tempA=""; //I use tempA as an array that has been concatenated
	char tempL; //tempL holds the letter that is being counted
	int count1=0, i; 
	for (i=0; i<a.length(); i++)
		if (count1==0) { //Sets tempL to the first team and increments teamscore by 1
			tempL=a[i];
			count1++;
		}
		else if (tempL==a[i]) //counts the number of runners
			count1++;
		else { //After it is finished it starts on the other team and adds the integer value into the string tempA then sets count to 1
			tempL=a[i];
			tempA+=count1;
			count1=1;
		}
	for (int i=0; i<tempA.length(); i++) //Checks to see if each value in the string is equal to the first position
		if (tempA[i]!=tempA[0]) {
			count1 = -1; //sets count to -1 then breaks
			break;
		}
	return count1; //Since number of runners can never be -1 it uses this to confirm that there are an equal number of runners in each team
}
int main() {
	/*String
	 s holds the original user string, tempS holds the sorted string and tempB holds the team names
	
	Double
	Placement[26] is an array of this size because that is the max possible number of teams.
	Both placement and min are doubles because they hold averages
	
	Int
	I initialized both variables i and c outside of the for loops instead of intializing at the beginning of each loop.
	d holds the number of runners instead of repeatedly calling the numRun() function
	position holds the position of the winning team*/
	
	string s, tempS, tempB="";
	double placement[26]={0}, min;
	int  i, c, d, position;
	cout << "Input a String of uppercase characters that will show the outcome of a race or enter done to finish" << endl;
	cin >> s; //Asks for user input
	while (s!="done") { //Infinitely loops until done is entered
		tempS=s; 
		sort(tempS.begin(), tempS.end()); //sorts tempS in order
		d=numRun(tempS); //sets d to the number of runners
		if (d!=-1) { 
			cout << "There are " << getTeams(tempS, tempB) << " teams." << endl; //Tells the user the number of teams & adds the team names to tempB by reference
			cout << "Each team has " << d << " runners." << endl;
			for ( i=0; i < s.length();i++) //This for loop adds the placement total of each team both going through the user string for placement and assigning each score for each team
				for (c=0; c < tempB.length(); c++)
					if (s[i]==tempB[c]) {
						placement[c]+=(i+1);
						break;
				}
			for (i=0; i < tempB.length(); i++) //This for loop averages out the teams placement for their score by dividing by the number of runners
				placement[i]=placement[i]/d;
			for (i=0; i<tempB.length(); i++)  //This for loop tells the user what each team scored
				cout << "Team " << tempB[i] << " score is " << placement[i] << endl;
			min=0; 
			position=0; //Min and position set to 0 here inside of the while loop
			for (i=0; i<tempB.length();i++) { //This for loop finds the team with the lowest score and saves the position in tempB
				if (min==0)
					min=placement[i];
				else if (min>placement[i]) {
					min=placement[i];
					position=i;	
				}
			}
			cout << "The winning team is " << tempB[position] << " with a score of " << min << endl; //Tells user the winning team
		}
		else
			cout << "Not the same amount of runners per team" << endl; //Error message incase not value number of runners
		cout << "Enter another string or type done" << endl; //asks for another string (until done is entered)
		cin >> s;
	}
	return 0;	
}

