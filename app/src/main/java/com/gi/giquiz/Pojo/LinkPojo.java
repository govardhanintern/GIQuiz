package com.gi.giquiz.Pojo;

public class LinkPojo {
    String video_link_id;
    String video_link;
    String status;

    public String getVideo_link_id() {
        return video_link_id;
    }

    public void setVideo_link_id(String video_link_id) {
        this.video_link_id = video_link_id;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LinkPojo{" +
                "video_link_id='" + video_link_id + '\'' +
                ", video_link='" + video_link + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
