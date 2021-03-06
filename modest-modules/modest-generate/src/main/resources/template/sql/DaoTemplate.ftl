package ${dao.strPackage};

import cn.xn.lhlc.base.BaseDao;
import com.xiaoniu.mybatis.paginator.domain.PageList;
import com.xiaoniu.mybatis.paginator.domain.PageRequest;
import ${entity.fullName};

 /**
 * 
 * 描述： Dao接口
 * 
 * @创建人： ${generate.author}
 * 
 * @创建时间：${generate.createDate}
 * 
 * @Copyright (c) 深圳市利民网有限公司-版权所有
 */
public interface ${dao.shortName} extends BaseDao{
	
	public PageList<${entity.shortName}> query${entity.shortName}(PageRequest pageRequest); 

}
