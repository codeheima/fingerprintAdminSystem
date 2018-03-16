package org.ma.db.jdbc.bean;

import java.util.List;

import org.ma.db.jdbc.DataObject;



/**
 * 分页 
 *
 */
public class Pagination {
	
	//查询当前页结果集
	private List<DataObject> datas = null;
	//当前页码
	private int currentPage;
	//最大页
	private int totalPage;
	//页大小
	private int pageSize;
	//总记录数
	private int totalNum;
	/**
	 * 构建分页集对象
	 * @param dataList		查询结果集
	 * @param totalCount	总记录数
	 * @param currentPage	当前页码
	 * @param pageSize		页大小
	 */
	public Pagination(List<DataObject> datas,int totalCount,int currentPage,int pageSize){
		this.datas = datas;
		this.totalNum = totalCount;
		this.pageSize = pageSize;
		if(pageSize<=0){
			this.currentPage = 1;
			this.totalPage = 1;
		}else{
			this.currentPage = currentPage;
			this.totalPage =(this.totalNum % this.pageSize == 0)? this.totalNum / this.pageSize:this.totalNum / this.pageSize + 1;
		}
	}

	public List<DataObject> getDatas() {
		return datas;
	}

	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean hasNext(){
		if(currentPage<totalPage)
			return true;
		else
			return false;
	}
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean hasPreview(){
		if(currentPage>1)
			return true;
		else
			return false;
	}
	/**
	 * 得到当前页
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 得到页大小
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 得到总记录数
	 * @return
	 */
	public int getTotalCount() {
		return totalNum;
	}
	/**
	 * 得到总页数
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}	
	
}
