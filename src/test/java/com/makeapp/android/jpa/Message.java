/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-3 下午12:48 $
 *          $Id$
 */
@Entity()
@Table(name = "message")
public class Message
{

    private Integer id;

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    private Integer type;

    @Basic
    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    private Integer status;

    @Basic
    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    private Integer senderId;

    @Basic
    public Integer getSenderId()
    {
        return senderId;
    }

    public void setSenderId(Integer senderId)
    {
        this.senderId = senderId;
    }

    private Integer receiverId;

    @Basic
    public Integer getReceiverId()
    {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId)
    {
        this.receiverId = receiverId;
    }

    //业务
    private Integer sessionId;

    @Basic
    public Integer getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(Integer sessionId)
    {
        this.sessionId = sessionId;
    }

    private String content;

    @Basic
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    private String attachment;

    @Basic
    public String getAttachment()
    {
        return attachment;
    }

    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }
}
