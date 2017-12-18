package com.itself.common.tool.scaffold.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 一些常量
 */
public class SysConst { 
	
	 
	
	/**
	 * 排序方式
	 * @author Administrator
	 *
	 */
	public enum SortBy{
		正序("ASC"),倒序("DESC");
		private String value;
		SortBy(String value){
			this.value = value;
		}
		public String getCode(){
			return this.value;
		}
	}
	 
}
