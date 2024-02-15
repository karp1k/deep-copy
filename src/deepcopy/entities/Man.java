package deepcopy.entities;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Man extends Dynasty {
    private String name;
    private int age;
    private List<String> favoriteBooks;
    private Map<String, Object> bucketOfObjects = new HashMap<>();
    private SomeEnum enumeration = SomeEnum.V;
    private SomeAnnotation annotation;
    private final BigDecimal income;
    private MansDog dog;
    private Boolean checked;


    public Man(String dynastyName, BigDecimal income, int age) {
        super(dynastyName);
        this.income = income;
        this.age = age;
    }

    public Man(String dynastyName, String name, int age, List<String> favoriteBooks,
               BigDecimal income, Boolean checked) {
        super(dynastyName);
        this.income = income;
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public Man addStaff(String key, Object anything) {
        bucketOfObjects.put(key, anything);
        return this;
    }

    public Map<String, Object> getBucketOfObjects() {
        return bucketOfObjects;
    }

    public MansDog getDog() {
        return dog;
    }

    public void setDog(MansDog dog) {
        this.dog = dog;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public SomeEnum getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(SomeEnum enumeration) {
        this.enumeration = enumeration;
    }

    public SomeAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(SomeAnnotation annotation) {
        this.annotation = annotation;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public class MansDog {
        private final String breed;
        private final Function<String, String> bark = feed -> feed != null && feed.equals("yummy") ? "woof!" : "snorts";

        public MansDog() {
            this.breed = "shepherd";
        }

        public MansDog(String breed) {
            this.breed = breed;
        }

    }

    public MansDog giveManADog() {
        var dog = this.new MansDog();
        this.dog = dog;
        return dog;
    }

}
