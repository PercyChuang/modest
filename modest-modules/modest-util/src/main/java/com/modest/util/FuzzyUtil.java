package com.modest.util;

public class FuzzyUtil {
	/**
	 * 保留姓，名用*号代替
	 * 输入：张三丰<br/>
	 * 输出：张**<br/>
	 */
	public static String fuzzyName(String name){
		if(name != null && name.length() > 1){
			StringBuilder fuzzy = new StringBuilder();
			fuzzy.append(name.charAt(0));		
			for(int i = 1; i < name.length(); i++){
				fuzzy.append("*");
			}
			return fuzzy.toString();
		}
		return name;
	}
	
	/**
	 * 将手机中间四位用*号替换
	 * @param mobile
	 * 输入：15012345678<br/>
	 * 输出：150****5678<br/>
	 * @return
	 */
	public static String fuzzyMobile(String mobile){
		if(mobile != null && mobile.length() == 11){
			StringBuilder fuzzy = new StringBuilder();
			for(int i = 0; i < mobile.length(); i++){
				if(i > 2 && i < 7){
					fuzzy.append("*");
				}else{
					fuzzy.append(mobile.charAt(i));
				}
			}
			return fuzzy.toString();
		}
		return mobile;
	}
	
	/**
	 * 身份证号码保留首尾各两位
	 * @param idNo<br/>
	 * 输入：431223198501015030<br/>
	 * 输出：43**************30<br/>
	 * @return
	 */
	public static String fuzzyIdNo(String idNo){
		if(idNo != null && idNo.length() > 4){
			StringBuilder fuzzy = new StringBuilder();
			fuzzy.append(idNo.charAt(0));
			fuzzy.append(idNo.charAt(1));
			for(int i = 2; i < idNo.length() - 2; i++){
				fuzzy.append("*");
			}
			fuzzy.append(idNo.charAt(idNo.length()-2));
			fuzzy.append(idNo.charAt(idNo.length()-1));
			return fuzzy.toString();
		}
		
		return idNo;
	}
	
	/**
	 * 身份证号码保留首6位尾4位
	 * @param idNo<br/>
	 * 输入：431223198501015030<br/>
	 * 输出：431223********5030<br/>
	 * @return
	 */
	public static String fuzzyIdNo2(String idNo){
		if(idNo != null && idNo.length() > 10){
			StringBuilder fuzzy = new StringBuilder();
			fuzzy.append(idNo.charAt(0));
			fuzzy.append(idNo.charAt(1));
			fuzzy.append(idNo.charAt(2));
			fuzzy.append(idNo.charAt(3));
			fuzzy.append(idNo.charAt(4));
			fuzzy.append(idNo.charAt(5));
			for(int i = 6; i < idNo.length() - 4; i++){
				fuzzy.append("*");
			}
			fuzzy.append(idNo.charAt(idNo.length()-4));
			fuzzy.append(idNo.charAt(idNo.length()-3));
			fuzzy.append(idNo.charAt(idNo.length()-2));
			fuzzy.append(idNo.charAt(idNo.length()-1));
			return fuzzy.toString();
		}
	
		
		return idNo;
	}
	
	/**
	 * 银行卡保留尾4位
	 * @param bankCardNo<br/>
	 * 输入：6228480402564890018 <br/>
	 * 输出：**0018<br/>
	 * @return
	 */
	public static String fuzzyBankCardNo(String bankCardNo){
		if(bankCardNo != null && bankCardNo.length() > 4){
			StringBuilder fuzzy = new StringBuilder();
			fuzzy.append("**");
			fuzzy.append(bankCardNo.charAt(bankCardNo.length() - 4));
			fuzzy.append(bankCardNo.charAt(bankCardNo.length() - 3));
			fuzzy.append(bankCardNo.charAt(bankCardNo.length() - 2));
			fuzzy.append(bankCardNo.charAt(bankCardNo.length() - 1));
			return fuzzy.toString();
		}
		
		return bankCardNo;
	}	
	
	/**
	 * 邮箱显示首两位
	 * @param email<br/>
	 * 输入：microsoft@163.com <br/>
	 * 输出：mi*******@163.com <br/>
	 * @return
	 */
	public static String fuzzyEmail(String email){
		if(email != null && email.length() > 5){
			String[] pair = email.split("@");
			StringBuilder fuzzy = new StringBuilder();
			if(pair.length == 2){
				String prefix = pair[0];
				if(prefix.length() <= 2){
					fuzzy.append(prefix);
				}else{
					fuzzy.append(prefix.substring(0,2));
					for(int i = 2; i < prefix.length(); i++){
						fuzzy.append("*");
					}
				}
				fuzzy.append("@" + pair[1]);
			}
		
			return fuzzy.toString();
		}
		
		return email;
	}	
	
	/**
	 * 邮箱显示首两位
	 * @param email<br/>
	 * 输入：microsoft@163.com <br/>
	 * 输出：mi*******@163.com <br/>
	 * @return
	 */
	public static String fuzzy(String email){
		if(email != null && email.length() > 5){
			String[] pair = email.split("@");
			StringBuilder fuzzy = new StringBuilder();
			if(pair.length == 2){
				String prefix = pair[0];
				if(prefix.length() <= 2){
					fuzzy.append(prefix);
				}else{
					fuzzy.append(prefix.substring(0,2));
					for(int i = 2; i < prefix.length(); i++){
						fuzzy.append("*");
					}
				}
				fuzzy.append("@" + pair[1]);
			}
		
			return fuzzy.toString();
		}
		
		return email;
	}	
	
	/**
	 * 显示用户ID尾4位(自动补齐为16位)
	 * 输入：z35yz3 <br/>
	 * 输出：**** **** **** 5yz3<br/>
	 * @return
	 */
	public static String fuzzyCustomerId(String customerId){
		if(customerId == null || customerId.length() == 0){
			return null;
		}
		
		if(customerId.length() >= 4){
			return "**** **** **** " + customerId.substring(customerId.length() - 4);
		}else{
			if(customerId.length() == 3){
				return "**** **** **** *" + customerId;
			}else if(customerId.length() == 2){
				return "**** **** **** **" + customerId;
			}else if(customerId.length() == 1){
				return "**** **** **** ***" + customerId;
			}else{
				return "**** **** **** ****";
			}
		}
	}
}
