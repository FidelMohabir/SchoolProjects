//Fidel Mohabir
//CSCI211 52B
//RAT CLASS
#include <iostream>
#include <stdlib.h>
using namespace std;
class Rat{
	private:
	 int n;
	 int d;
	public:
 // constructors

 // default constructor
	 Rat(){
	 	n=0;
 		d=1;
 	}

 // 2 parameter constructor
 	Rat(int i, int j){
 		n=i;
 		d=j;
 	}
 // conversion constructor
 	Rat(int i){
 		n=i;
 		d=1;
 	}

 //accessor functions (usually called get() and set(...) )
 	int getN(){ return n;}
 	int getD(){ return d;}
 	void setN(int i){ n=i;}
 	void setD(int i){ d=i;}

 //arithmetic operators

 	Rat operator+(Rat r){
 		Rat t;
 		t.n=n*r.d+d*r.n;
 		t.d=d*r.d;
		reduce(t);
 		return t;
	}

	Rat operator-(Rat r) {
		Rat t;
		t.n=n*r.d-d*r.n; //numerator*demoninator of second Rat - Denominator*numerator of second Rat
		t.d=d*r.d; //Denomation*Denominator of second Rat
		reduce(t); //Reduces the Rat function to simplest form
		return t; 
	}

	Rat operator*(Rat r) {
		Rat t;
		t.n=r.n*n; //numerator*numerator of second Rat
		t.d=r.d*d; //denominator*denominator of second Rat
		reduce(t); //reduces Rat function to simplest form
		return t;
	}

	Rat operator/(Rat r) {
		Rat t;
		t.n=r.d*n; //Numerator*denominator of second Rat
		t.d=r.n*d; //Denominator*numberator of second Rat
		reduce(t); //Reduces Rat function into simplest form
		return t;
	}

 // 2 overloaded i/o operators
 	friend ostream& operator<<(ostream& os, Rat r);
 	friend istream& operator>>(istream& is, Rat& r);
	friend void reduce(Rat& r);
	friend void improper(Rat& r);
}; //end Rat

// operator<<() is NOT a member function but since it was declared a friend of Rat
// it has access to its private parts.
	ostream& operator<<(ostream& os, Rat r){
 		os<<r.n<<" / "<<r.d<<endl;
 		return os;}

// operator>>() is NOT a member function but since it was declared a friend of Rat
// it has access to its provate parts.
	istream& operator>>(istream& is, Rat& r){
 		is>>r.n>>r.d;
 		return is;
	}
	void reduce(Rat& r) { //Reduce function
		for (int i=2;i<=r.d; i++) { //starts from 2 since anything%1 will always return a 0
			if(r.n%i==0 && r.d%i==0) {
				r.n=r.n/i; //reduces the rat when a common factor is found 
				r.d=r.d/i; //reduces the rat when a common factor is found
				break;
			}
		}
	}
	void printImproper(Rat& r) { //print improper function
	int w; //w represents the whole numbers
	w=r.n/r.d; //amount of whole numebrs contained within the fraction
	r.n=r.n%r.d; //aamount left over becomes the new rat function
	if (w==0) //if no whole numbers
		cout << r << endl; //just prints out the rat
	else
		cout << w << " " << r << endl; //prints w(space)rat else
	}

int gcd(int n, int d){
    if (d==0)return n;
    return gcd(d, n%d);
}

int main(){
 	Rat x(1,2), y(2,3), z;
 	z=x+y;
 	cout<<z;

 	x.setN(3);
 	y.setD(2);
 	z=x+y;
 	cout << z;
 	cin>>x;
 	cout<< x;
 	z= x+5;
 	cout<< z;
 	system("pause");
 	return 0;
}
