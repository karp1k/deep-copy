package deepcopy.entities;

public class Dynasty {

    private Man parent1;
    private Man parent2;
    private final String name;

    public Dynasty(String name) {
        this.name = name;
    }

    public Man getParent1() {
        return parent1;
    }

    public void setParent1(Man parent1) {
        this.parent1 = parent1;
    }

    public Man getParent2() {
        return parent2;
    }

    public void setParent2(Man parent2) {
        this.parent2 = parent2;
    }

    public String getName() {
        return name;
    }
}
