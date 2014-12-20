package com.jun.xiaoquren.dao.model;

import java.io.Serializable;

public class DocumentEvaluation implements Serializable {
	
	private static final long serialVersionUID = 4977520094205383268L;
	
    private Integer id;

    private Long documentId;

    private String documentType;

    private String evaluationType;

    private String nickname;

    private Boolean deleted;

    private Long version;

    private String createDate;

	public DocumentEvaluation() {
		// TODO Auto-generated constructor stub
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType == null ? null : documentType.trim();
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType == null ? null : evaluationType.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
