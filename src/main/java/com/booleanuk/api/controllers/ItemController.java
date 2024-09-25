package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemRepository repo;

    @GetMapping
    public ResponseEntity<Response<List<Item>>> getAll(){
        Response<List<Item>> videoGamesResponse = new Response<>();
        videoGamesResponse.set(repo.findAll());

        return ResponseEntity.ok(videoGamesResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Item item = repo.findById(id).orElse(null);

        if (item == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Response<Item> itemResponse = new Response<>();
        itemResponse.set(item);

        return ResponseEntity.ok(itemResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@RequestBody Item item){
        if (item.getTitle() == null ||
                item.getType() == null ||
                item.getDescription() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        repo.save(item);
        Response<Item> itemResponse = new Response<>();
        itemResponse.set(item);

        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Item item){
        if (item.getTitle() == null ||
                item.getType() == null ||
                item.getDescription() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        Item toUpdate = repo.findById(id).orElse(null);

        if (toUpdate == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Optional.ofNullable(item.getTitle()).ifPresent(title -> toUpdate.setTitle(title));
        Optional.ofNullable(item.getType()).ifPresent(type -> toUpdate.setType(type));
        Optional.ofNullable(item.getDescription()).ifPresent(description -> toUpdate.setDescription(description));
        repo.save(toUpdate);

        Response<Item> itemResponse = new Response<>();
        itemResponse.set(toUpdate);

        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        Item toDelete = repo.findById(id).orElse(null);

        if (toDelete == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        repo.delete(toDelete);
        Response<Item> itemResponse = new Response<>();
        itemResponse.set(toDelete);

        return ResponseEntity.ok(itemResponse);
    }

}
