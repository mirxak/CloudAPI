package com.main.cloudapi.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by mirxak on 21.01.15.
 */
@MappedSuperclass
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity {
//    @JsonNotUpdatable
    protected Long id;
    /**
     * дата создания
     */
    @JsonIgnore
    private Long createdDate;
    /**
     * id User создавшего
     */
    @JsonIgnore
    private Long createdBy;
    /**
     * удален (1)
     */
    @JsonIgnore
    private Integer isDeleted=0;
    /**
     * id User удалившего
     */
    @JsonIgnore
    private Long deletedBy;
    /**
     * дата удаления
     */
    @JsonIgnore
    private Long deletedDate;
    /**
     * дата изменения
     */
    @JsonIgnore
    private Long modifiedDate;
    /**
     * пользователь поменявший
     */
    @JsonIgnore
    private Long modifiedUser;

    @JsonIgnore
    @Transient
    public abstract String getTable();

    //    public abstract Long getId();
//    public abstract void setId(Long id);
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    //@Column(name = "id", nullable = false)
//    @Transient
//    //@JsonIgnore

    //<editor-fold desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id", insertable = false, updatable = false)
//    @JsonNotUpdatable
    public Long getId() {
        return id;
    }

    //@JsonNotUpdatable
    public void setId(Long id) {
        this.id = id;
    }
    //</editor-fold>

    //<editor-fold desc="created_date">
    @Column(name = "created_date")
    @JsonProperty("created_date")
    public Long getCreatedDate() {
        return createdDate;
    }


    @JsonIgnore
    public void setCreatedDate(Long createdDate) {
        if (this.createdDate == null) {
            this.createdDate = createdDate;
        }
    }
    //</editor-fold>

    //<editor-fold desc="created_by">
    @Column(name = "created_by")
    @JsonProperty("created_by")
    public Long getCreatedBy() {
        return createdBy;
    }


    @JsonIgnore
    public void setCreatedBy(Long createdBy) {
        if (this.createdBy == null) {
            this.createdBy = createdBy;
        }
    }
    //</editor-fold>



    @JsonProperty("is_deleted")
    @Column(name = "is_deleted")
    public Integer getIsDeleted() {
        return isDeleted==null?0:isDeleted;
    }


    @JsonIgnore
    //  //@JsonProperty("is_deleted")
    public void setIsDeleted(Integer IsDeleted) {
        isDeleted = IsDeleted;
    }




    @JsonProperty("deleted_by")
    @Column(name = "deleted_by")
    public Long getDeletedBy() {
        return deletedBy;
    }

    @JsonIgnore
    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    @JsonProperty("deleted_date")
    @Column(name = "deleted_date")
    public Long getDeletedDate() {
        return deletedDate;
    }
    @JsonIgnore
    public void setDeletedDate(Long deletedDate) {
        this.deletedDate = deletedDate;
    }

    @JsonProperty("last_modified_date")
    @Column(name = "last_modified_date")
    public Long getModifiedDate() {
        return modifiedDate==null?createdDate:modifiedDate;
    }
    @JsonIgnore
    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonProperty("last_modified_user")
    @Column(name = "last_modified_user")
    public Long getModifiedUser() {
        return modifiedUser==null?createdBy:modifiedUser;
    }
    @JsonIgnore
    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Override
    public boolean equals(Object entity) {
        if(entity==null){
            return false;
        }
        if (entity instanceof BaseEntity) {
            BaseEntity b = (BaseEntity) entity;
            if(this.getClass().equals(entity.getClass())){
                return !(this.getId() == null || b.getId() == null)
                        && this.getId().equals(b.getId());
            }

        }
        return false;
    }

    public int hashCode(){
        if(id==null){
            return super.hashCode();
        }
        else{
            return id.hashCode();
        }
    }

    @Override
    public String toString() {
//        return JsonUtils.getJson(this,false);
        return null;
    }

    @JsonIgnore
    @Transient
    public abstract String getObjectCategory();

    @JsonIgnore
    @Transient
    public abstract BaseEntity generateEntity();
}
