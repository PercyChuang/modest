import com.modest.generate.GenerateFactory;


public class RunPlat {
	
	/**  
	 * @Title: 仅对plat_开头的表自动生成有效
	 * @Description:
	 * @param args
	 * @throws Exception 
	 * @author chenlin
	 * @date 2016年10月8日 下午5:51:05 
	 * @throws 
	 */
	public static void main(String[] args) throws Exception {
		 GenerateFactory.codeGenerate("lmw/generate_table_plat.properties");
	  }
}
