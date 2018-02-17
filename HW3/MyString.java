import java.util.ArrayList;
import java.util.Arrays;

class MyString{
	private char[] string;
	
	public MyString(char[] chars){
		string = Arrays.copyOf(chars, chars.length);
	}
	
	public char charAt(int index){
		return string[index];
	}
	
	public int length(){
		return string.length;
	}
	
	public MyString substring(int begin, int end){
		return new MyString(Arrays.copyOfRange(string, begin, end));
	}
	
	public MyString toLowerCase(){
		char[] temp = Arrays.copyOf(string, string.length);
		
		for(int i = 0; i < temp.length; ++i){
			temp[i] = Character.toLowerCase(temp[i]);
		}
		
		return new MyString(temp);
	}
	
	public MyString toUpperCase(){
		char[] temp = Arrays.copyOf(string, string.length);
		
		for(int i = 0; i < temp.length; ++i){
			temp[i] = Character.toUpperCase(temp[i]);
		}
		
		return new MyString(temp);
	}
	
	/// Returns:
	/// the value 0 if (this MyString) == (argument MyString);
	/// a value less than 0 if (this MyString) < (argument MyString);
	/// and a value greater than 0 if (this MyString) > (argument MyString)
	public int compareTo(MyString s){
		s = s.toLowerCase();
		char[] stringTemp = this.toLowerCase().string;
		
		for(int i = 0; i < stringTemp.length && i < s.string.length; ++i){
			if(Character.compare(stringTemp[i], s.string[i]) != 0){
				return Character.compare(stringTemp[i], s.string[i]);
			}
		}
		
		if(stringTemp.length == s.string.length){
			return 0;
		}
		
		if(stringTemp.length < s.string.length){
			return -1;
		} else {
			return 1;
		}
	}
	
	public MyString getMyString(){
		return new MyString(string);
	}
	
	public String toString(){
		return new String(string);
	}
	
	public static MyString valueOf(int i){
		ArrayList<Character> temp = new ArrayList<Character>();
		
		while(i > 10){
			temp.add(Character.forDigit(i % 10, 10));
			i = i / 10;
		}
		
		temp.add(Character.forDigit(i, 10));
		
		char[] tempArray = new char[temp.size()];
		
		for(int j = temp.size() - 1; j >= 0; --j){
			tempArray[(tempArray.length - 1) - j] = temp.get(j);
		}
		
		return new MyString(tempArray);
	}
}