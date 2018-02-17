class Driver{
	public static void main(String args[]){
		char[] temp = {'a', 'B', 'c', 'D', 'e'};
		
		System.out.println("char[] temp = {'a', 'B', 'c', 'D', 'e'}, temp:");
		
		System.out.println(temp);
		System.out.println();
		
		System.out.println("MyString string = new MyString(temp), temp[3] = 'k', temp:");
		
		MyString string = new MyString(temp);
		
		temp[3] = 'k';
		
		System.out.println(temp);
		System.out.println();
		
		System.out.println("string:");
		System.out.println(string);
		System.out.println();
		
		System.out.println("string.charAt(0)");
		System.out.println(string.charAt(0));
		System.out.println();
		
		System.out.println("string.length()");
		System.out.println(string.length());
		System.out.println();
		
		System.out.println("string.substring(3, 5)");
		System.out.println(string.substring(3, 5));
		System.out.println();
		
		System.out.println("string.toLowerCase()");
		System.out.println(string.toLowerCase());
		System.out.println();
		
		System.out.println("string.toUpperCase()");
		System.out.println(string.toUpperCase());
		System.out.println();
		
		System.out.println("string.compareTo(string.substring(0, 5)");
		System.out.println(string.compareTo(string.substring(0, 5)));
		System.out.println();
		
		System.out.println("string.compareTo(string.substring(1, 5)");
		System.out.println(string.compareTo(string.substring(1, 5)));
		System.out.println();
		
		System.out.println("string.compareTo(string.substring(0, 4)");
		System.out.println(string.compareTo(string.substring(0, 4)));
		System.out.println();
		
		System.out.println("string.getMyString()");
		System.out.println(string.getMyString());
		System.out.println();
		
		System.out.println("string.toString()");
		System.out.println(string.toString());
		System.out.println();
		
		System.out.println("MyString.valueOf(19231)");
		System.out.println(MyString.valueOf(19231));
		System.out.println();
	}
}