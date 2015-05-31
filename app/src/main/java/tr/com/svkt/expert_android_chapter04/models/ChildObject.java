package tr.com.svkt.expert_android_chapter04.models;

import java.util.UUID;

public class ChildObject {
    private UUID id = UUID.randomUUID();
    public String name;
    public int age;
    public boolean likesVeggies = false;

    public ChildObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
