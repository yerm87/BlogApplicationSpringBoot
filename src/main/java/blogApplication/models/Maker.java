package blogApplication.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Maker {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(min = 3, max = 15)
    private String name;

    @OneToMany
    @JoinColumn(name = "maker_id")
    private List<Car> cars = new ArrayList<>();

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

    public List<Car> getCars(){
        return cars;
    }
}
