package tariffs_manager.entities;

import javax.persistence.*;

@Entity
@Table(name="tariffs")
public class Tariff {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String tags;
    private String type;
    private int price;

    @Column(name ="short")
    private String Short;


    private String services;
    private String equip;
    private String extra;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip==null?"":equip;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra==null?"":extra;
    }

    public String getShort() {
        return Short;
    }

    public void setShort(String aShort) {
        Short = aShort;
    }
}
