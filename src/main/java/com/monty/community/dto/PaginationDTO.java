package com.monty.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private List<Integer> pageList=new ArrayList<>();
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showLastPage;
    private boolean showNextPage;
    private Integer currentPage;
    private Integer totalpages;

    public void setPagination(int totalPageCount, Integer page, Integer size) {
        int totalCount=0;
        if(totalPageCount%size==0){
            totalCount=totalPageCount/size;
        }else {
            totalCount=totalPageCount/size+1;
        }
        if (page>totalCount){
            page=totalCount;
        }
        this.setCurrentPage(page);
        this.setTotalpages(totalCount);
        if(this.currentPage==1){
            this.showPrevious=false;
        }else {
            this.showPrevious=true;
        }

        if (this.currentPage==totalCount){
            this.showNextPage=false;
        }else {
            this.showNextPage=true;
        }

        this.pageList.add(this.currentPage);
        for (int showpage=1;showpage<=3;showpage++){
            if(this.currentPage-showpage>0){
                this.pageList.add(0,this.currentPage-showpage);
            }

            if (this.currentPage+showpage<=totalCount){
                this.pageList.add(this.currentPage+showpage);
            }
        }
        //判断是否展示第一页
        if(this.pageList.contains(1)){
            this.showFirstPage=false;
        }else {
            this.showFirstPage=true;
        }

        if (this.pageList.contains(totalCount)){
            this.showLastPage=false;
        }else {
            this.showLastPage=true;
        }
    }
}
