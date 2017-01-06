package com.yunfei.gank.app.main;

import java.util.List;

/**
 * Created by yunfei on 2017/1/4.
 * email mayunfei6@gmail.com
 */

public class CategoryEntity {

  /**
   * _id : 58676f53421aa94dbbd82bc4
   * createdAt : 2016-12-31T16:41:55.141Z
   * desc : 简单易用的TextView装饰库
   * images : ["http://img.gank.io/0412420b-0fa8-41c2-bd37-8007d0388df2"]
   * publishedAt : 2017-01-04T11:39:01.326Z
   * source : chrome
   * type : Android
   * url : https://github.com/nntuyen/text-decorator
   * used : true
   * who : 蒋朋
   */

  private String _id;
  private String createdAt;
  private String desc;
  private String publishedAt;
  private String source;
  private String type;
  private String url;
  private boolean used;
  private String who;
  private List<String> images;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isUsed() {
    return used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public String getWho() {
    return who;
  }

  public void setWho(String who) {
    this.who = who;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }
}
