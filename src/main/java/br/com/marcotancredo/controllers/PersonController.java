package br.com.marcotancredo.controllers;

import br.com.marcotancredo.data.vo.v1.PersonVO;
import br.com.marcotancredo.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(
        value = "Person Endpoint",
        tags = {"PersonEndpoint"}
)
@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {

    private final PersonService personService;
    private final PagedResourcesAssembler<PersonVO> assembler;

    public PersonController(PersonService personService, PagedResourcesAssembler<PersonVO> assembler) {
        this.personService = personService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO findById(@PathVariable("id") Long id) {
        PersonVO personVO = personService.findById(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @PatchMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO disablePerson(@PathVariable("id") Long id) {
        PersonVO personVO = personService.disablePerson(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @ApiOperation(value = "Find all people recorded")
    @GetMapping(value = "/findAll", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                            @RequestParam(value = "limit", defaultValue = "15") int limit,
                                                            @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        Direction sortBy = getSortBy(sort);

        PageRequest of = PageRequest.of(offset, limit, Sort.by(sortBy, "firstName"));
        Page<PersonVO> personsVO = personService.findAll(of);
        personsVO.forEach(p -> p.add(linkTo(methodOn(PersonController.class)
                .findById(p.getKey()))
                .withSelfRel())
        );
        PagedResources<?> pagedResources = assembler.toResource(personsVO);

        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all people recorded")
    @GetMapping(value = "/findPersonByName/{firstName}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findPersonByName(
            @PathVariable("firstName") String firstName,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "15") int limit,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        Direction sortBy = getSortBy(sort);

        PageRequest of = PageRequest.of(offset, limit, Sort.by(sortBy, "firstName"));
        Page<PersonVO> personsVO = personService.findPersonByName(firstName, of);
        personsVO.forEach(p -> p.add(linkTo(methodOn(PersonController.class)
                .findById(p.getKey()))
                .withSelfRel())
        );
        PagedResources<?> resources = assembler.toResource(personsVO);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    private Direction getSortBy(String sort) {
        return "desc".equalsIgnoreCase(sort) ? Direction.DESC : Direction.ASC;
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO create(@RequestBody PersonVO entity) {
        PersonVO personVO = personService.create(entity);
        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO update(@RequestBody PersonVO entity) {
        PersonVO personVO = personService.update(entity);
        personVO.add(linkTo(methodOn(PersonController.class).findById(entity.getKey())).withSelfRel());
        return personVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
