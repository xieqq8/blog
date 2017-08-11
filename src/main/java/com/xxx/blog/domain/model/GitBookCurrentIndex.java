package com.xxx.blog.domain.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxx.toolbox.model.TreeModel;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author xiexx
 * @date 2016/12/28
 */
public class GitBookCurrentIndex {

    private BookIndex prev;

    private BookIndex next;

    private BookIndex current;

    private List<BookIndex> indices;

    private String bookJson;

    public GitBookCurrentIndex(List<BookIndex> bookIndices) {
        this.indices = bookIndices;
        TreeModel.sortByTree(indices);
    }

    public void build(String url) {
        for (int i = 0; i < indices.size(); i++) {
            BookIndex node = indices.get(i);
            if (url.equals(node.getUrl())) {
                current = node;
                int preIndex = i - 1;
                int nextIndex = i + 1;
                if (preIndex >= 0) {
                    prev = indices.get(preIndex);
                    setChildren(prev, indices);
                }
                if (nextIndex < indices.size()) {
                    next = indices.get(nextIndex);
                    setChildren(next, indices);
                }
            }
        }

        buildGitBookPageJson();

    }

    private void setChildren(BookIndex currentNode, List<BookIndex> bookIndices) {
        TreeModel.setChildren(currentNode, bookIndices);

    }

    private void buildGitBookPageJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", current.getLabel());
        BookIndex currentParent = getParent(current);
        jsonObject.put("level", currentParent == null ? current.getOrder() + "." + current.getOrder() : currentParent.getOrder() + "." + currentParent.getOrder() + "." + current.getOrder());
        jsonObject.put("depth", current.getLevel());
        jsonObject.put("path", current.getUrl());
        jsonObject.put("ref", current.getUrl());

        if (next != null) {
            JSONObject nextjson = new JSONObject();
            nextjson.put("title", next.getLabel());
            BookIndex nextParent = getParent(next);
            nextjson.put("level", nextParent == null ? next.getOrder() + "." + next.getOrder() : nextParent.getOrder() + "." + nextParent.getOrder() + "." + next.getOrder());
            nextjson.put("depth", next.getLevel());
            nextjson.put("path", next.getUrl());
            nextjson.put("ref", next.getUrl());
            JSONArray articles = new JSONArray();
            if (next.getChildNodes() != null) {
                next.getChildNodes().stream().forEach(item -> {
                    BookIndex bookIndex = (BookIndex) item;
                    JSONObject object = new JSONObject();
                    object.put("title", bookIndex.getLabel());
                    BookIndex bookIndexParent = getParent(bookIndex);
                    object.put("level", bookIndexParent == null ? bookIndex.getOrder() + "." + bookIndex.getOrder() : bookIndexParent.getOrder() + "." + bookIndexParent.getOrder() + "." + bookIndex.getOrder());
                    object.put("depth", bookIndex.getLevel());
                    object.put("path", bookIndex.getUrl());
                    object.put("ref", bookIndex.getUrl());
                    articles.add(object);
                });
            }
            nextjson.put("articles", articles);
            jsonObject.put("next", nextjson);
        }


        if (prev != null) {
            JSONObject prevjson = new JSONObject();
            prevjson.put("title", prev.getLabel());
            BookIndex prevParent = getParent(prev);
            prevjson.put("level", prevParent == null ? prev.getOrder() + "." + prev.getOrder() : prevParent.getOrder() + "." + prevParent.getOrder() + "." + prev.getOrder());
            prevjson.put("depth", prev.getLevel());
            prevjson.put("path", prev.getUrl());
            prevjson.put("ref", prev.getUrl());
            JSONArray articles = new JSONArray();
            if (prev.getChildNodes() != null) {
                prev.getChildNodes().stream().forEach(item -> {
                    BookIndex bookIndex = (BookIndex) item;
                    JSONObject object = new JSONObject();
                    object.put("title", bookIndex.getLabel());
                    BookIndex bookIndexParent = getParent(bookIndex);
                    object.put("level", bookIndexParent == null ? bookIndex.getOrder() + "." + bookIndex.getOrder() : bookIndexParent.getOrder() + "." + bookIndexParent.getOrder() + "." + bookIndex.getOrder());
                    object.put("depth", bookIndex.getLevel());
                    object.put("path", bookIndex.getUrl());
                    object.put("ref", bookIndex.getUrl());
                    articles.add(object);
                });
            }
            prevjson.put("articles", articles);
            jsonObject.put("prev", prevjson);
        }
        jsonObject.put("dir", "");
        this.bookJson = jsonObject.toJSONString();
    }

    private BookIndex getParent(BookIndex bookIndex) {
        if (bookIndex.getPath().equals("0")) {
            return null;
        }
        return indices.stream().filter(item -> (item.getPath() + "," + item.getId()).equals(bookIndex.getPath())).collect(Collectors.toList()).get(0);
    }

    public BookIndex getPrev() {
        return prev;
    }

    public BookIndex getNext() {
        return next;
    }

    public BookIndex getCurrent() {
        return current;
    }

    public String getBookJson() {
        return bookJson;
    }
}
