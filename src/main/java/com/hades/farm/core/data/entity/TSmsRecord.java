package com.hades.farm.core.data.entity;

public class TSmsRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.send_ip
     *
     * @mbggenerated
     */
    private String sendIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.send_count
     *
     * @mbggenerated
     */
    private Integer sendCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.last_time
     *
     * @mbggenerated
     */
    private Long lastTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sms_record.create_time
     *
     * @mbggenerated
     */
    private Long createTime;

    public TSmsRecord() {
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sms_record
     *
     * @mbggenerated
     */
    public TSmsRecord(Long id, String phone, String code, String sendIp, Integer sendCount, Long lastTime, Long createTime) {
        this.id = id;
        this.phone = phone;
        this.code = code;
        this.sendIp = sendIp;
        this.sendCount = sendCount;
        this.lastTime = lastTime;
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.id
     *
     * @return the value of t_sms_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.phone
     *
     * @return the value of t_sms_record.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.code
     *
     * @return the value of t_sms_record.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.send_ip
     *
     * @return the value of t_sms_record.send_ip
     *
     * @mbggenerated
     */
    public String getSendIp() {
        return sendIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.send_count
     *
     * @return the value of t_sms_record.send_count
     *
     * @mbggenerated
     */
    public Integer getSendCount() {
        return sendCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.last_time
     *
     * @return the value of t_sms_record.last_time
     *
     * @mbggenerated
     */
    public Long getLastTime() {
        return lastTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sms_record.create_time
     *
     * @return the value of t_sms_record.create_time
     *
     * @mbggenerated
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}