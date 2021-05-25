package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();

    }

    @Test
    public void list() {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder()
                                .firstName("joe")
                                .lastName("nem")
                                .build(),
                        Vendor.builder()
                                .firstName("na")
                                .lastName("Nota")
                                .build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(
                        Vendor.builder()
                                .firstName("na")
                                .lastName("be")
                                .build()));

        webTestClient.get()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreateVendor() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder()
                        .build()));

        Mono<Vendor> vendorToSave = Mono.just(Vendor.builder()
                .firstName("bergony")
                .lastName("bandeira")
                .build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorToSave, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateVendor() {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendoTobeSave = Mono.just(Vendor.builder()
                .firstName("bergonu")
                .lastName("bandeira")
                .build());

        webTestClient.put()
                .uri("/api/v1/vendors/1")
                .body(vendoTobeSave, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchWithChangesVendor() {

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().firstName("berg").build()));

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> venToUpdateMono = Mono.just(Vendor.builder().firstName("b").build());


        webTestClient.patch()
                .uri("/api/v1/vendors/asdfasd")
                .body(venToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());

    }

    @Test
    public void testPatchNoChangesVendor() {

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().firstName("berg").build()));

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> venToUpdateMono = Mono.just(Vendor.builder().firstName("berg").build());


        webTestClient.patch()
                .uri("/api/v1/vendors/adsfasd")
                .body(venToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, never()).save(any());

    }
}