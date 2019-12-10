//Fidel Mohabir
//CS211 Section 52B
//HW 15 - Towers of Hanoi
#include <iostream>
#include <vector>
using namespace std;
int main(){
	vector<int> t[3]; //Three towers A, B, C
	int n, candidate,to, from, move=0;
 	cout<< "Please enter a number of rings to move: " << endl; 
	cin>>n;
	cout<<endl;
    
	for(int i=n+1;i>=1;i--) //pushes n to 1 stacks into tower A
        	t[0].push_back(i);
	t[1].push_back(n+1); 
	t[2].push_back(n+1);
    
	from=0;
    	if((n%2)==1) // checks where to move first from (which is dependant on if it is even or odd rings)
		to=1;
    	else 
		to=2;
    	candidate=1;
    
	while( t[1].size()<n+1){
        	t[to].push_back(t[from].back()); //puts first ring in tower A into Tower[to]
        	t[from].pop_back(); //then deletes the top most ring in tower A
        	cout << "move number " << ++move <<": Transfer ring " << candidate << " from tower " << char(from+65) << " to tower " << char(to+65) << endl; //Keeps track of moves and shows the user where each ring is being moved
        
		if(t[(to+1)%3].back() < t[(to+2)%3].back()) 
        		from=(to+1)%3;
        	else
                	from=(to+2)%3;
        
		if (n%2==0) { //if even number of rings    
			if(t[(from)].back() < t[(from+2)%3].back()) //Gets next 'to' tower in the even function 
                		to=(from+2)%3;
            		else
                		to=(from+1)%3; 
		}
		else { //if odd rings
            		if(t[(from)].back() < t[(from+1)%3].back()) //Gets next 'to' tower in the even function
                		to=(from+1)%3;
            		else
                		to=(from+2)%3;
		}
		candidate=(t[from].back());
    	}
    	return 0;
}
