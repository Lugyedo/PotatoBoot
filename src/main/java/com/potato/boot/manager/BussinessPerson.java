package com.potato.boot.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BussinessPerson implements Person {
    /**
     * Spring EL 表达式
     */
    @Value("#{T(System).currentTimeMillis()}")
    private Long initTime;

    private Animal animal;

    @Override
    public void service() {
        System.out.println(initTime);
        animal.use();
    }

    @Autowired
    @Override
    public void setAnimal(Animal animal) {
        System.out.println("lazyInit");
        this.animal = animal;
    }
}
