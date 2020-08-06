package com.github.marvindaviddiaz.dto;

import java.util.List;

public class PageDTO<T> {

    private List<T> content;
    private Long totalElements;
    private Integer page;
    private Integer elementPerPage;

    public PageDTO(List<T> content, Long totalElements, Integer page, Integer elementPerPage) {
        this.content = content;
        this.totalElements = totalElements;
        this.page = page;
        this.elementPerPage = elementPerPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getElementPerPage() {
        return elementPerPage;
    }

    public void setElementPerPage(Integer elementPerPage) {
        this.elementPerPage = elementPerPage;
    }
}
