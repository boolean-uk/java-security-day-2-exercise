package com.booleanuk.api.controllers;

import com.booleanuk.api.models.*;
import com.booleanuk.api.repositories.ItemRepository;
import com.booleanuk.api.repositories.UserRepository;
import com.booleanuk.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        return new ResponseEntity<>(itemRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        // Determine the type of item and save the appropriate subclass
        Item savedItem;
        if (item instanceof Book) {
            savedItem = itemRepository.save((Book) item);
        } else if (item instanceof CD) {
            savedItem = itemRepository.save((CD) item);
        } else if (item instanceof DVD) {
            savedItem = itemRepository.save((DVD) item);
        } else if (item instanceof VideoGame) {
            savedItem = itemRepository.save((VideoGame) item);
        } else if (item instanceof BoardGame) {
            savedItem = itemRepository.save((BoardGame) item);
        } else {
            // Handle unsupported item type
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item existingItem = itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updatedItem instanceof Book && existingItem instanceof Book) {
            Book updatedBook = (Book) updatedItem;
            Book existingBook = (Book) existingItem;
            existingBook.setName(updatedBook.getName());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPageCount(updatedBook.getPageCount());

        } else if (updatedItem instanceof CD && existingItem instanceof CD) {
            CD updatedCD = (CD) updatedItem;
            CD existingCD = (CD) existingItem;
            existingCD.setName(updatedCD.getName());
            existingCD.setPrice(updatedCD.getPrice());
            existingCD.setArtist(updatedCD.getArtist());
            existingCD.setNumberOfTracks(updatedCD.getNumberOfTracks());

        } else if (updatedItem instanceof DVD && existingItem instanceof DVD) {
            DVD updatedDVD = (DVD) updatedItem;
            DVD existingDVD = (DVD) existingItem;
            existingDVD.setName(updatedDVD.getName());
            existingDVD.setPrice(updatedDVD.getPrice());
            existingDVD.setDirector(updatedDVD.getDirector());
            existingDVD.setDuration(updatedDVD.getDuration());

        } else if (updatedItem instanceof VideoGame && existingItem instanceof VideoGame) {
            VideoGame updatedVideoGame = (VideoGame) updatedItem;
            VideoGame existingVideoGame = (VideoGame) existingItem;
            existingVideoGame.setName(updatedVideoGame.getName());
            existingVideoGame.setPrice(updatedVideoGame.getPrice());
            existingVideoGame.setPlatform(updatedVideoGame.getPlatform());
            existingVideoGame.setDeveloper(updatedVideoGame.getDeveloper());

        } else if (updatedItem instanceof BoardGame && existingItem instanceof BoardGame) {
            BoardGame updatedBoardGame = (BoardGame) updatedItem;
            BoardGame existingBoardGame = (BoardGame) existingItem;
            existingBoardGame.setName(updatedBoardGame.getName());
            existingBoardGame.setPrice(updatedBoardGame.getPrice());
            existingBoardGame.setMinPlayers(updatedBoardGame.getMinPlayers());
            existingBoardGame.setMaxPlayers(updatedBoardGame.getMaxPlayers());

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input not matching the type to be updated");
        }
        return new ResponseEntity<>(itemRepository.save(existingItem), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long id) {

        Item item = itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        itemRepository.delete(item);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/borrow/{itemId}")
    public ResponseEntity<Item> borrowItem(@PathVariable int itemId) {
        Item item = this.itemRepository.findById((long) itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(item.getUser() != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item is on loan");
        }
        int currentId = getCurrentUserId();
        if(currentId < 0){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }
        User user = this.userRepository.findById(currentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        item.setUser(user);
        user.getItems().add(item);

        this.itemRepository.save(item);
        this.userRepository.save(user);

        return new ResponseEntity<>(item, HttpStatus.OK);

    }

    @PostMapping("/return/{itemId}")
    public ResponseEntity<Item> returnItem(@PathVariable int itemId) {
        Item item = this.itemRepository.findById((long) itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(item.getUser() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item is not on loan");
        }
        int currentId = getCurrentUserId();
        if(currentId < 0){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }
        User user = this.userRepository.findById(currentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(item.getUser().getId() != user.getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item is not on loan to you");
        }

        item.setUser(null);
        user.getItems().remove(item);

        this.itemRepository.save(item);
        this.userRepository.save(user);

        return new ResponseEntity<>(item, HttpStatus.OK);

    }


    private int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getId(); // Assuming UserDetailsImpl has a method to retrieve the user's ID
        } else {
            // Handle case when user is not authenticated
            return -1; // Or whatever value you want to return when user is not authenticated
        }
    }
}
