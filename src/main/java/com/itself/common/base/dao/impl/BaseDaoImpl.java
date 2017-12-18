package com.itself.common.base.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.itself.common.base.dao.BaseDao;
import com.itself.common.tool.scaffold.util.GenericsUtils;
import com.itself.common.tool.scaffold.util.PageBean;
import com.itself.common.tool.scaffold.util.SysConst.SortBy;


//@Repository("baseDaoImpl")
public class BaseDaoImpl<T> implements BaseDao<T> {

	// 从spring注入原有的sqlSessionTemplate
	@Autowired
	private SqlSessionTemplate sqlSession;

	protected Class<T> entityClass;
	protected String sqlMapNamespace;

	public static final String POSTFIX_SELECT = ".select";
	public static final String POSTFIX_INSERT = ".insert";
	public static final String POSTFIX_UPDATE = ".update";
	public static final String POSTFIX_UPDATE_SELECTIVE = ".update4Selective";
	public static final String POSTFIX_DELETE_PK = ".deleteByPrimaryKey";
	public static final String POSTFIX_DELETE = ".delete";
	public static final String POSTFIX_SELECT_MAP = ".selectByMap";
	public static final String POSTFIX_SELECT_COUNT = ".selectCount";
	public static final String POSTFIX_SELECT_PAGE = ".selectPage";
	public static final String POSTFIX_INSERT_BATCH = ".insertBatch";
	public static final String POSTFIX_SELECT_IDS = ".selectByIds";

	public String getSqlMapNamespace() {
		return sqlMapNamespace;
	}

	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	/**
	 * 在构造函数中将泛型T.class赋给entityClass. sqlNamespace 带包名
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		sqlMapNamespace = entityClass.getName();
	}

	
	public T get(Serializable id) {
		if (id == null)
			return null;// 如果id为空,不再查询
		return sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT, id);
	}

	
	public int insert(T o) {
		return sqlSession.insert(sqlMapNamespace + POSTFIX_INSERT, o);
	}
 
	public void insertBatch(List<T> list) {
		if (list == null || list.size() == 0)
			return;
		int total = list.size();// 总记录数
		int once = 1500;
		for (int i = 1; i <= (total / once) + 1; i++) {
			List<T> temp = list.subList((i - 1) * once, i * once > total ? total : i * once);
			sqlSession.insert(sqlMapNamespace + POSTFIX_INSERT_BATCH, temp);
		}
	}

	public int update(T o) {
		return sqlSession.update(sqlMapNamespace + POSTFIX_UPDATE, o);
	}
	
	
	public int update4Selective(T o) {
		return sqlSession.update(sqlMapNamespace + POSTFIX_UPDATE_SELECTIVE, o);
	}

	
	public int delete(Integer id) {
		return sqlSession.delete(sqlMapNamespace + POSTFIX_DELETE_PK, id);
	}

	
	public int delete(T o) {
		return sqlSession.delete(sqlMapNamespace + POSTFIX_DELETE, o);
	}

	
	public List<T> findAll() {
		return sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT);
	}

	
	public List<T> findBy(String name, Object value) {
		Assert.hasText(name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, value);
		return findByMap(map);
	}

	
	public List<T> findBy(String name, Object value, boolean isLike, String sortName, SortBy sortBy) {
		Assert.hasText(name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, value);
		return findByMap(map, isLike, sortName, sortBy);
	}

	
	public List<T> findBy(String name, Object value, boolean isLike) {
		Assert.hasText(name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, value);
		return findByMap(map, isLike, null, null);
	}

	
	public List<T> findBy(String name, Object value, String sortName, SortBy sortBy) {
		Assert.hasText(name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, value);
		return findByMap(map, false, sortName, sortBy);
	}

	@SuppressWarnings("unchecked")
	
	public T findUniqueBy(String name, Object value) {
		Assert.hasText(name);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put(name, value);
			map.put("findBy", "True");
			return (T) sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT_MAP, map);
		} catch (Exception e) {
			return null;
		}
	}

	public List<T> findByMapOneEqual(Map<String, Object> map) {
		map.put("oneEqual", "true");  
		return sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_MAP, map);
	}

	
	public List<T> findByMap(Map<String, Object> map) {
		if (map == null)
			return findAll();
		return findByMap(map, false, null, null);
	}

	@SuppressWarnings("unchecked")
	
	public T findUniqueByMap(Map<String, Object> map) {
		try {
			map.put("findBy", "true");
			return (T) sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT_MAP, map);
		} catch (Exception e) {
			return null;
		}
	}

	
	public List<T> findByMap(Map<String, Object> map, boolean isLike, String sortName, SortBy sortBy) {
		if (map == null)
			return findAll();
		if (isLike) {
			map.put("findByLike", "true");
		} else {
			map.put("findBy", "true");
		}
		if (StringUtils.isNotBlank(sortName)) {
			// 需要对sortName做过滤,防止sql注入
			if (sortName.indexOf("'") != -1)
				sortName.replace("'", "");
			map.put("sortName", sortName);
			map.put("sortBy", sortBy.getCode());
		}
		return sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_MAP, map);
	}

	
	public List<T> findByMap(Map<String, Object> map, boolean isLike) {
		return findByMap(map, isLike, null, null);
	}

	
	public List<T> findByMap(Map<String, Object> map, String sortName, SortBy sortBy) {
		return findByMap(map, false, sortName, sortBy);
	}

	
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map) {
		find4Page(pageBean, map, false, null, null);
	}

	
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, boolean isLike) {
		find4Page(pageBean, map, isLike, null, null);
	}

	
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, String sortName, SortBy sortBy) {
		find4Page(pageBean, map, false, sortName, sortBy);
	}

	
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, String searchkey) {
		if (map == null)
			map = new HashMap<String, Object>();
		map.put("findBy", "true"); 
		if(StringUtils.isNotBlank(searchkey)){
			map.put("searchstr", searchkey); 
		}
		 
		Integer totalRows = sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT_COUNT, map);
		pageBean.setTotalRows(totalRows);
		if (totalRows != null && totalRows > 0) {
			map.put("startRow", pageBean.getSimpleStartRow());
			map.put("pageSize", pageBean.getPageRecorders());
			 
			List<T> list = sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_PAGE, map);
			pageBean.setObjList(list);
		}
	}

	
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, boolean isLike, String sortName,
			SortBy sortBy) {
		if (map == null)
			map = new HashMap<String, Object>();
		if (isLike) {
			map.put("findByLike", "true");
		} else {
			map.put("findBy", "true");
		}
		 
		Integer totalRows = sqlSession.selectOne(sqlMapNamespace + POSTFIX_SELECT_COUNT, map);
		pageBean.setTotalRows(totalRows);
		if (totalRows != null && totalRows > 0) {
			map.put("startRow", pageBean.getSimpleStartRow());
			map.put("pageSize", pageBean.getPageRecorders());
			if (StringUtils.isNotBlank(sortName)) {
				if (sortName.indexOf("'") != -1)
					sortName.replace("'", "");
				map.put("sortName", sortName);
				map.put("sortBy", sortBy.getCode());
			}
			List<T> list = sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_PAGE, map);
			pageBean.setObjList(list);
		}
	}
	public List<T> findByIds(String ids) {
		ids = checkSqlParam(ids);
		return sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_IDS, ids);
	}
//	public List<T> findByNoticeIds(NoticeOwnerDTO noticeOwnerDTO){
//		return sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_NOTICE_IDS, noticeOwnerDTO);
//	}
//	public List<T> findByRulesIds(RulesOwnerDTO RulesOwnerDTO){
//		return sqlSession.selectList(sqlMapNamespace + POSTFIX_SELECT_RULES_IDS, RulesOwnerDTO);
//	}
	/**
	 * 参数校验, 防止sql注入
	 * 
	 * @param param
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年7月23日
	 */
	protected String checkSqlParam(String param) {
		if (StringUtils.isBlank(param))
			return "";
		if (param.indexOf("'") != -1) {
			param = param.replace("'", "");
		}
		return param;
	}

}
