package model.entities;

import java.util.Objects;

public class Department {
    private Integer id;
    private String departament;

    public Department(){}

    public Department(Integer id, String departament) {
        this.id = id;
        this.departament = departament;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(departament, that.departament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departament);
    }
}
