package vn.app.phims14.Classes;

import java.util.List;

/**
 * Created by Man Huynh Khuong on 11/05/16.
 */
public class Category {
    private String categoryName;
    private String description;
    private String slug;
    private boolean deleted;
    private int productParentId;
    private List<Object> productCategoryMappings;
    private int id;

    public Category() {
    }

    public Category(String categoryName, String description, String slug, boolean deleted,
                    int productParentId, List<Object> productCategoryMappings, int id) {
        this.categoryName = categoryName;
        this.description = description;
        this.slug = slug;
        this.deleted = deleted;
        this.productParentId = productParentId;
        this.productCategoryMappings = productCategoryMappings;
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getProductParentId() {
        return productParentId;
    }

    public void setProductParentId(int productParentId) {
        this.productParentId = productParentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Object> getProductCategoryMappings() {
        return productCategoryMappings;
    }

    public void setProductCategoryMappings(List<Object> productCategoryMappings) {
        this.productCategoryMappings = productCategoryMappings;
    }
}
