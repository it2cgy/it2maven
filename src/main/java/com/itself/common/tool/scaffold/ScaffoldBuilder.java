package com.itself.common.tool.scaffold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ScaffoldBuilder {
	protected final Log logger = LogFactory.getLog(getClass());
	protected final static String test_PREFIX ="test.java.";
	protected final static String SRC_PREFIX ="main.java.";
	protected final static String PKG_PREFIX = "com.";
	protected final static String PKG_SUFFIX_MODEL_path = ".domain.po";
	protected final static String PKG_SUFFIX_MODEL = ".domain.po.";
	protected final static String PKG_SUFFIX_DAO = ".dao.";
	protected final static String PKG_SUFFIX_MANAGER = ".service.";
	protected final static String PKG_IMPL = "impl";

	protected String pkgName;
	protected String clzName;
	protected String clzNamePO;
	protected TableInfo tableInfo;
	private final Map<String, String> mapping;

	public ScaffoldBuilder(String pkgName, String clzName, TableInfo tableInfo) {
		this.pkgName = PKG_PREFIX + pkgName;
		this.clzName = clzName;
		this.clzNamePO = clzName+"PO";
		this.tableInfo = tableInfo;

		mapping = new HashMap<String, String>();

		mapping.put("clzNamePO", clzNamePO);
		mapping.put("clzName", clzName);
		mapping.put("clzNameLC", StringUtils.uncapitalize(clzName));
		mapping.put("tblName", tableInfo.getName());
		mapping.put("modelPath", getModelPath());
		mapping.put("daoPath", getDaoPath());
		mapping.put("daoImplPath", getDaoImplPath());
		mapping.put("managerPath", getManagerPath());
		
		mapping.put("fieldsDeclareInfo", tableInfo.getFieldsDeclareInfo());
		
		mapping.put("resultMap", tableInfo.getResultMap());
		mapping.put("baseField", tableInfo.getColumnNames());
		mapping.put("otherCondition", tableInfo.getOtherCondition());
		mapping.put("likeCondition", tableInfo.getLikeCondition());
		mapping.put("oneEqualCondition", tableInfo.getOneEqualCondition());
		
		mapping.put("primaryKey", tableInfo.getPrimaryKey());
		mapping.put("insertStatement", tableInfo.getInsertStatement());
		mapping.put("insertStatementList1", tableInfo.getInsertStatementList1());
		mapping.put("insertStatementList2", tableInfo.getInsertStatementList2());
		mapping.put("updateStatement", tableInfo.getUpdateStatement());
		mapping.put("updateMapModel", tableInfo.getUpdateMapModel());
	}

	public String getModelPath() {
		return pkgName + PKG_SUFFIX_MODEL + clzNamePO;
	}

	public String getSqlMapFile() {
		return pkgName + PKG_SUFFIX_MODEL + clzName + ".xml";
	}

	public String getDaoPath() {
		return pkgName + PKG_SUFFIX_DAO + clzName + "Dao";
	}

	public String getDaoImplPath() {
		return pkgName + PKG_SUFFIX_DAO + PKG_IMPL + ScaffoldUtil.DOT + clzName + "Dao"
				+ StringUtils.capitalize(PKG_IMPL);
	}

	public String getManagerPath() {
		return pkgName + PKG_SUFFIX_MANAGER + clzName + "Service";
	}

	public List<FileGenerator> buildGenerators() {
		List<FileGenerator> list = new ArrayList<FileGenerator>();
		// model
		list.add(new FileGenerator(SRC_PREFIX,pkgName + PKG_SUFFIX_MODEL_path, clzNamePO, "Model.txt", mapping));
		list.add(new FileGenerator(SRC_PREFIX,pkgName + PKG_SUFFIX_MODEL_path +".mapping", clzName, "SqlMap.txt", mapping, "xml"));

		// dao
		list.add(new FileGenerator(SRC_PREFIX,pkgName + ".dao", clzName + "Dao", "Dao.txt", mapping));
		list.add(new FileGenerator(SRC_PREFIX,pkgName + ".dao.impl", clzName + "DaoImpl", "DaoImpl.txt", mapping));
//
//		// service
		list.add(new FileGenerator(SRC_PREFIX,pkgName + ".service", clzName + "Service", "Service.txt", mapping));
		list.add(new FileGenerator(test_PREFIX,pkgName + ".service", clzName + "ServiceTest", "ServiceTest.txt", mapping));
//		// controller
		list.add(new FileGenerator(SRC_PREFIX,pkgName + ".controller", clzName + "Controller", "Controller.txt", mapping));
		return list;
	}

}
