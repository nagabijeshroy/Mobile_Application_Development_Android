
/**
 * Homework - 3
 * Naga Bijesh Roy Raya
 * James Budday
 * Shyam Mohan Raman
 */
package com.example.homework3;

import android.util.Log;

public class QandA {

	int int_questionnumber, int_answeropt;
	String str_question, str_imgURL;
	String[] strarr_ans;

	public QandA(String str_eachquestion, int questionnumber) {
		
		Log.d("check the value of str_eachquestion", str_eachquestion);
		
		
		
		String[] str_ques_split = str_eachquestion.split(";");
		strarr_ans = new String[str_ques_split.length-3];
		
		Log.d("check total length of string array", ""+str_ques_split.length);
		int_questionnumber = questionnumber;
		str_question = str_ques_split[0];
		Log.d("question",str_question);

		
		if (str_ques_split[1] != "") {
			strarr_ans[0] = str_ques_split[1]; 
		}
		else 
			strarr_ans[0] = "error";
		if (str_ques_split[2] != "") {
			strarr_ans[1] = str_ques_split[2];
		}
		else 
			strarr_ans[1] = "error";
		
		
		Log.d("length of options array : ",strarr_ans.length+"");
		
		
		if(str_ques_split[3] != str_ques_split[str_ques_split.length-2]){
			for(int k = 3,m=2;k < str_ques_split.length-2;k++,m++){
				strarr_ans[m]  = str_ques_split[k]; 
			}
			
		}
		
		
		
		for(int j =3;j< strarr_ans.length-2;j++ ){
			Log.d("String array of answer options : ",strarr_ans[j]);
		}
		Log.d("we have reached this postion","reached here");
		
		
		Log.d("the imgURL ",(str_ques_split.length-2)+"");
		
		if (str_ques_split[str_ques_split.length-2] != "") {
			str_imgURL = str_ques_split[str_ques_split.length-2];
		}else
			str_imgURL = "http://upload.wikimedia.org/wikipedia/commons/a/ac/No_image_available.svg";
		
		int_answeropt = Integer.parseInt(str_ques_split[str_ques_split.length-1]);
		
		Log.d("check", str_imgURL); 
	}
	
	

}
