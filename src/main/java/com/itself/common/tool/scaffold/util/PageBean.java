package com.itself.common.tool.scaffold.util;

import java.util.ArrayList;
import java.util.List;
/**
 * 分页封装实体
 *
 * @param <T>
 */
public class PageBean <T>{
	private Integer currentPage = 1;//当前页:Action控制
    private Integer totalPages = 0;//总页数 :自己运算
    private Integer pageRecorders = 5; //每页记录数,默认为10,可以在初始化的时候修改
    private boolean hasNextPage = false; //是否有下一页:自己运算
    private boolean hasPreviousPage = false; //是否有前一页 :自己运算
    private List<T> objList = new ArrayList<T>();//存放欲展示的对象列表
    private Integer totalRows = 0;//总记录数,由底层service提供
    private Integer startRow = 0;//起始行号
	//增加上一页索引 [directly to target page]
    private Integer lastPageIndex = 1;
    //增加下一页索引 [directly to target page]
    private Integer nextPageIndex = 1;
    
    //获取起始行号
    public Integer getStartRow() {
    	if(currentPage <= 0){
			currentPage = 1;
		}
    	else if(currentPage > getTotalPages()){
			currentPage = getTotalPages();
		}
    	this.startRow = (currentPage - 1) * pageRecorders;
		return startRow;
	}
    
    /**
     * 简易版获取起始行号(不进行总页码判断)
    * @return
    * @Author: wangxingfei
    * @Date: 2015年5月22日
     */
    public Integer getSimpleStartRow() {
    	if(currentPage <= 0){
			currentPage = 1;
    	}
    	this.startRow = (currentPage - 1) * pageRecorders;
		return startRow;
	}

    public void setLastPageIndex(Integer lastPageIndex) {
		this.lastPageIndex = lastPageIndex;
	}

	public void setNextPageIndex(Integer nextPageIndex) {
		this.nextPageIndex = nextPageIndex;
	}

	public Integer getLastPageIndex() {
		if(this.isHasPreviousPage()){
			lastPageIndex = currentPage - 1;
			return lastPageIndex;
		}
        return 1;
    }

    public Integer getNextPageIndex() {
    	if(this.isHasNextPage()){
    		nextPageIndex = currentPage + 1;
    		return nextPageIndex;
    	}
        return this.getTotalPages();
    }


    //是否有上一页
    public boolean isHasPreviousPage() {
    	hasPreviousPage = (currentPage > 1 ? true : false);
        return hasPreviousPage;
    }

    //共有多少页,service只提供有多少条记录,多少页数由PageBean自己运算
    public Integer getTotalPages() {
        if (totalRows <= pageRecorders) return 1;
        totalPages =  (totalRows % pageRecorders == 0 ? totalRows / pageRecorders : totalRows / pageRecorders + 1);
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }


    //是否有下一页
    public boolean isHasNextPage() {
    	hasNextPage = (currentPage < this.getTotalPages() ? true : false);
        return hasNextPage;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public Integer getPageRecorders() {
        return pageRecorders;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public void setPageRecorders(Integer pageRecorders) {
        this.pageRecorders = pageRecorders;
    }

    public List<T> getObjList() {
        return objList;
    }
    
}
