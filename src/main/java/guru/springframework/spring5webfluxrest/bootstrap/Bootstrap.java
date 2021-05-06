package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private  final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {

        //noinspection ConstantConditions
        if(0 == categoryRepository.count().block()){

            //Load data
            System.out.println("### LOADING DATA ON BOOTSTRAP");

            categoryRepository.save(Category.builder()
                    .description("Fruit").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();

            System.out.println("Load Categories" + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Joe")
                    .lastName("Buck").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Michel")
                    .lastName("Buck").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jessie")
                    .lastName("Buck").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Bill")
                    .lastName("Buck").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jimmy")
                    .lastName("Buck").build()).block();

            System.out.println("Load Vendors" + vendorRepository.count().block());

        }


    }
}
