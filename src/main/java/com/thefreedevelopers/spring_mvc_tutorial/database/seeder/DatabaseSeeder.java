package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Component class for generating seeders
 *
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {
    private final CategorySeeder categorySeeder;

    public DatabaseSeeder(CategorySeeder categorySeeder) {
        this.categorySeeder = categorySeeder;
    }

    @Override
    public void run(String... args) throws Exception {
        categorySeeder.seed();
    }
}
