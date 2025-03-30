package com.example.jobs_top.dto.res;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> content;
    private int totalPages;
    private int currentPage;
    private long totalElements;

    public PaginatedResponse(List<T> content, int totalPages, int currentPage, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
