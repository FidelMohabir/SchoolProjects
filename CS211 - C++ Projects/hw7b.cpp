//Fidel Mohabir
//CS211 Section 52B
//Homework 7B
//Dr. Waxman said this doesn't need to be handed in and for us to just understand why it outputs like that
//I handed it in anyway because it's on blackboard but I am unsure why
#include <iostream>
using namespace std;
int main( ){
	int b[3][2];
	cout<<sizeof(b)<<endl;
	cout<<sizeof(b+0)<<endl;
	cout<<sizeof(*(b+0))<<endl;
	// the next line prints 0012FF68
	cout<<"The address of b is: "<<b<<endl;
	cout<<"The address of b+1 is: "<<b+1<<endl;
	cout<<"The address of &b is: "<<&b<<endl;
	;
	cout<<"The address of &b+1 is: "<<&b+1<<endl<<endl;
	return 0;
}	
