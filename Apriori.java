
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Apriori {
	
	private static ArrayList<String> support_arr = new ArrayList<String>();
	private static double support_val = 0;
	private static double confidence_val = 0;
	
	private static ArrayList<String> transact_arr(FileInputStream fs, Scanner in) //method implementation to store all transaction
	{
		ArrayList<String> transaction_arr = new ArrayList<String>();
	
		
		boolean checker = true;
		String one = in.nextLine();
		int var = 0;
		for(int i = 0; i < one.length(); i++)
		{
			if(one.charAt(i) == 'I' && one.charAt(i+1)== 'T')
			{
				var = i;
			}
		}
		
		while(checker)
		{
			try
			{
			String temp = in.nextLine();
			temp = temp.substring(var, temp.length());
			
			
			for(int i = 0; i < temp.length(); i++)
			{
				if(temp.charAt(i) == '\t' || temp.charAt(i) == '\n' || temp.charAt(i) == ' ')
					temp = temp.substring(0, i);
					
			}
			
			
			transaction_arr.add(temp);
			}
			catch(Exception ex)
			{
				checker = false;
			}
			
		}
		return transaction_arr;
		
		
	}
	
	
	//===========================================================================================================================================>
	
	private static ArrayList<String> check_distinct(ArrayList<String> arr)
	{
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < arr.size(); i++)
		{
			boolean checker = true;
			for(int j = i + 1; j < arr.size(); j++)
			{
				if(arr.get(i).contains(arr.get(j)))
				{
					checker = false;
					break;
				}
				
			}
			if(checker)
			{
				temp.add(arr.get(i));
			}
		}
		return temp;
	}
	
	
	//===========================================================================================================================================>
	
	
	private static ArrayList<String>  distinct_arr(ArrayList<String> arr)
	{
		ArrayList<String> distinct_arr = new ArrayList<String>();
		for(int i = 0; i < arr.size(); i++)
		{
			int start_val = 0;
			int end_val = 0;
			String entire = arr.get(i);
			for(int j = 0; j < entire.length(); j++)
			{
				
				if(entire.charAt(j) == ',' || j == entire.length()-1)
				{
					if(j == entire.length()-1)
					{
						end_val = entire.length();
					}
					else
					{
						end_val = j;
					}
					String temp = entire.substring(start_val, end_val);
					
						distinct_arr.add(temp);
				
					start_val = end_val + 1;
					
				}
			
				
			}
						
		}
		return distinct_arr;
		
	}
	//===========================================================================================================================================>
	
	private static ArrayList<String> subsets(ArrayList<String> arr)
	{
		ArrayList<String> arr1 = new ArrayList<String>();
		int size = arr.size();
		String var = " ";
		
		
		for(int i = 0; i < (1 << size); i++)
		{
			String temp = "{";
			for(int j = 0; j < size; j++)
			{
				if((i & (1 << j)) > 0)
				{
					temp = temp + arr.get(j) + " ";
				}
			}
			
			temp = temp + "}";
			var = temp;
			
			if(var.equals("{}"))
			{
				
				//do nothing
			}
			else
			{
				arr1.add(var);
			}
	
		}
		
		return arr1;
			
	}
	
	
	//===========================================================================================================================================>
	
	
	private static ArrayList<String> sort_array(ArrayList<String> arr)
	{
		String[] temp_arr = new String[arr.size()];
		ArrayList<String> arr1 = new ArrayList<String>();
		arr.toArray(temp_arr);
		for(int i = 0; i < temp_arr.length; i++)
		{
			for(int j = i+1; j < temp_arr.length; j++)
			{	String temp = " ";
				if(temp_arr[i].length() > temp_arr[j].length())
				{
					temp = temp_arr[i];
					temp_arr[i] = temp_arr[j];
					temp_arr[j] = temp;			
				}
			}
		}
			for(int i = 0; i < temp_arr.length; i++)
				arr1.add(temp_arr[i]);
			
			return arr1;
		
	}
	
	//===========================================================================================================================================>
	private static void start_func_calc_support(ArrayList<String> arr, ArrayList<String> trans_arr, int support_freq)
	{
		
		for(int i = 0; i < arr.size(); i++)
		{
			int start_val = 1;
			int end_val = 0;
			ArrayList<String> arrl = new ArrayList<String>();
			for(int j = 0; j < arr.get(i).length(); j++)
			{
			
				String temp1 = " ";
				
				
				if(arr.get(i).charAt(j) == ' '  )
				{
					end_val = j;
				
				 temp1 = arr.get(i).substring(start_val, end_val);
			
				
				 start_val = end_val + 1;
				 arrl.add(temp1);
				 
				 
				}
								
				
			}
			String pass_temp[] = new String[arrl.size()];
			
			
			for(int n = 0; n < arrl.size(); n++)
			{
				pass_temp[n] = arrl.get(n);
				
			}
			
			fun_calc_support(trans_arr, pass_temp, support_freq);		
		}
	}
	//===========================================================================================================================================>

	private static String[] ret_parsed_array(String parse_string)
	{
		ArrayList<String> arrl = new ArrayList<String>();
		int start_val = 1;
		int end_val = 0;
		for(int j = 0; j < parse_string.length(); j++)
		{
		
			String temp1 = " ";
			
			
			if(parse_string.charAt(j) == ' '  )
			{
				end_val = j;
			
			 temp1 = parse_string.substring(start_val, end_val);
		
			
			 start_val = end_val + 1;
			 arrl.add(temp1);
			 
			 
			}
							
			
		}
		String pass_temp[] = new String[arrl.size()];
		
		
		for(int n = 0; n < arrl.size(); n++)
			pass_temp[n] = arrl.get(n);
		
		return pass_temp;
	}
	
	
	//===========================================================================================================================================>
	
	private static void fun_calc_support(ArrayList<String> tempr, String[] temp1, int support_freq)
	{
		int total_count = 0;
		for(int i = 0; i < tempr.size(); i++)
		{
		
			int count = 0;
			
			for(int j = 0; j < temp1.length; j++)
			{
			
				int start_val = 0;
				for(int k = 0; k < tempr.get(i).length(); k++)
				{
					
					int end_val = 0;
					
					if(tempr.get(i).charAt(k) == ',' || k == tempr.get(i).length() - 1 )
					{
						String temp = " ";
						if(k == tempr.get(i).length() - 1 )
						{
							end_val = tempr.get(i).length() ;
						}
						else
						{
							end_val = k;
						}
						temp = tempr.get(i).substring(start_val, end_val);
						
						start_val = end_val + 1;
						if(temp.equals(temp1[j]))
						{
							count += 1;
						}
						//System.out.println(temp1[j] +" " + temp1[j].length() + " " + temp +" " + temp.length() +  " " + temp1[j].equals(temp));
						
					}
									
				}
				
				//System.out.println(count);
			}
			
			if(count == temp1.length)
				total_count += 1;
			
		}
		
		if(total_count < support_freq)
		{
			//do nothing
		}
		else
		{
		String support_data = Arrays.toString(temp1) + " " + String.valueOf(total_count);
		//System.out.println(support_data);
		support_arr.add(support_data);
	
		}
	}
	//===========================================================================================================================================>
	
	private static void startfunc_calc_confidence(ArrayList<String> support_arr, int total_transact_size)
	{
		for(int i = 0; i < support_arr.size(); i++)
		{
			ArrayList<String> arr = new ArrayList<String>();
			int start_val = 1;
			int end_val = 0;
			for(int j = 0; j < support_arr.get(i).length(); j++)
			{
				String temp = " ";
				String num = " ";
				if(support_arr.get(i).charAt(j) == ',' || support_arr.get(i).charAt(j) == ']')
				{
					end_val = j;
					temp = support_arr.get(i).substring(start_val, end_val);
					start_val = end_val + 2;
					arr.add(temp);
				
					
				}
				 if(support_arr.get(i).charAt(j) == ']' && support_arr.get(i).charAt(j +1) == ' ')
				{
					 end_val = support_arr.get(i).length();
					 start_val = j+1;
					 num = support_arr.get(i).substring(start_val, end_val);
					
							 
				}
				
			}
			ArrayList<String> derive_subset = subsets(arr);
			/**for(int k = 0; k < derive_subset.size(); k++)
				System.out.print(derive_subset.get(k) + " ");
			System.out.println();**/
			calc_conf(derive_subset, total_transact_size);
			
			
		}
		
	
	}
	
	
//==============================================================================================================================================>	
	
	
	private static void calc_conf(ArrayList<String> arr, int total_transact_size)
	{
		for(int i = 0; i < arr.size(); i++)
		{
			int length = ret_parsed_array(arr.get(arr.size()-1)).length ;
			
			String str_to_find[] = ret_parsed_array(arr.get(i));
			
			
			for(int j = 0; j < arr.size(); j++)
			{
			
				String str_to_search[] = ret_parsed_array(arr.get(j));
				//System.out.println("String you're comparing is" + " " + Arrays.toString(str_to_find) + " " + Arrays.toString(str_to_search));
				//System.out.println((str_to_find.length ) + " " +( str_to_search.length) + "======" + length);
				
				if(((str_to_find.length ) + (str_to_search.length  ))== length)
				{
					boolean checker = true;
				for(int k = 0; k < str_to_find.length; k++ )
				{
					
				
					for(int l = 0; l < str_to_search.length; l++)
					{
						//System.out.println(str_to_find[k] + " " + str_to_search[l]);
						if(str_to_find[k].equals(str_to_search[l]))
						{
							
							checker = false; 
							//System.out.println(checker);
							break;
							
						}
						
									}
				}
				
				if(checker)
				{
					//System.out.println(Arrays.toString(str_to_find) + "--->" + Arrays.toString(str_to_search));
					String temp = Arrays.toString(str_to_find) + "--->" + Arrays.toString(str_to_search);
					String left = Arrays.toString(str_to_find);
					String total_arr[] = new String[str_to_find.length + str_to_search.length];
					int l = 0;
					for(int m = 0; m < str_to_find.length; m++)
					{
						total_arr[l] = str_to_find[m];
						l++;
					}
					for(int n = 0; n < str_to_search.length; n++)
					{
						total_arr[l] = str_to_search[n];
						l++;
					}
					String total = Arrays.toString(total_arr);
					double val = (double) val_parser(total_arr)/val_parser(str_to_find);
					
					if(val*100 > confidence_val)
					{
						System.out.println(temp+ " " + "(" + (double) ((val_parser(total_arr)/ (double) total_transact_size ) )* 100 + "," + val*100 + ")");
						//System.out.println(val_parser(total_arr) + " " + val_parser(str_to_find));
						//System.out.println(Arrays.deepToString(str_to_find));
					}
					
					
					
				}
				
				
			}
			
		}		
	}
		
	}
	
	
//============================================================================================================================================>	
	
	private static int val_parser(String[] str_to_check)
	{
		
		int val_for_parse = 0;
		for(int p = 0; p < support_arr.size(); p++)
		{
			int count = 0;
			for(int q = 0; q < str_to_check.length; q++)
			{
				int start_val = 1;
				int end_val = 0;
				String temp_var = " ";
				for(int b = 0; b < support_arr.get(p).length(); b++)
				{
					
					 if(support_arr.get(p).charAt(b) == ',' || support_arr.get(p).charAt(b) == ']')
					 {
						 
						 end_val = b;
						 temp_var = support_arr.get(p).substring(start_val, end_val);
						 start_val = end_val + 2;
						 if(temp_var.equals(str_to_check[q]))
								 {
							 		count++;
							 		//System.out.println(count);
								 }
					 }
				}
				
				
				if(count == str_to_check.length && support_arr.get(p).split("]")[0].length()+1== Arrays.toString(str_to_check).length())
				{
					//System.out.println((support_arr.get(p).substring(Arrays.toString(str_to_check).length()+ 1, support_arr.get(p).length())));
					val_for_parse = Integer.parseInt(support_arr.get(p).substring(Arrays.toString(str_to_check).length()+ 1, support_arr.get(p).length()));
					//System.out.println(Arrays.toString(str_to_check)+  Arrays.toString(str_to_check).length()+ " " + support_arr.get(p) + support_arr.get(p).length());
					
				}
			}
		}
		
		
		return val_for_parse;
	}
	
	//===========================================================================================================================================>
	
	public static void main(String[] args) throws FileNotFoundException
	{
		support_val = Integer.parseInt(args[0]);
		confidence_val = Integer.parseInt(args[1]);
		FileInputStream fs = new FileInputStream(args[2]);
		Scanner in = new Scanner(fs);
		ArrayList<String> arr = transact_arr(fs, in);//Store all transaction
		double support_percent_frequency = (double)(support_val/100)*arr.size();
		int support_frequency =(int) Math.ceil(support_percent_frequency);
	    ArrayList<String> arr1 = distinct_arr(arr); //parse the individual items, duplicate items exist
	    ArrayList<String> arr3 = check_distinct(arr1);// returns all the distinct items
	    ArrayList<String> arr4 = subsets(arr3); // returns the subsets of the given items
	    ArrayList<String> arr5 = sort_array(arr4); // returns the sorted subsets based on the string length
	    start_func_calc_support(arr5, arr, support_frequency);//parse the sorted subset array and transform into string array
	    System.out.println("printing total transactions");
	    System.out.println();
	    for(int i = 0; i <arr.size(); i++)
	    	System.out.println(arr.get(i));
	    System.out.println();
	    System.out.println("printing all association rules for given support and confidence");
	    System.out.println();
	    startfunc_calc_confidence(support_arr, arr.size());
	    
	    	    
	}

}

