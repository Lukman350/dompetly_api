package dev.lukmann.category;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

}
