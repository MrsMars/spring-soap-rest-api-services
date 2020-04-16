package com.aoher.versioning.controller;

import com.aoher.versioning.model.Name;
import com.aoher.versioning.model.PersonV1;
import com.aoher.versioning.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersionController {

    private static final String BOB = "Bob";
    private static final String CHARLIE = "Charlie";

    @GetMapping("v1/person")
    public PersonV1 personV1() {
        return new PersonV1(String.format("%s %s", BOB, CHARLIE));
    }

    @GetMapping("v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name(BOB, CHARLIE));
    }

    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 paramV1() {
        return new PersonV1(String.format("%s %s", BOB, CHARLIE));
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name(BOB, CHARLIE));
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 headerV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerV2() {
        return new PersonV2(new Name(BOB, CHARLIE));
    }

    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json")
    public PersonV1 producesV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v2+json")
    public PersonV2 producesV2() {
        return new PersonV2(new Name(BOB, CHARLIE));
    }
}
