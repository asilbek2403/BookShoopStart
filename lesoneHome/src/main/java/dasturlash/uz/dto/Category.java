package dasturlash.uz.dto;

import java.time.LocalDateTime;

public class Category {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private boolean visible;

    public String toWrite(){
        return id+"#"+name+"#"+createdDate+"#"+visible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
